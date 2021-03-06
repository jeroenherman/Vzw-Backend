package be.voedsaam.vzw.business.repository.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import be.voedsaam.vzw.business.Destination;
import be.voedsaam.vzw.business.Drive;
import be.voedsaam.vzw.business.repository.DriveRepository;

public class DriveRepositoryMySQL implements DriveRepository {
	
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	
	public DriveRepositoryMySQL() {
		entityManagerFactory = Persistence.createEntityManagerFactory("VZW_LOCAL");
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	@Override
	public Drive create(Drive aggregate) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		if(aggregate.getDriver()!=null)
		aggregate.setDriver(entityManager.merge(aggregate.getDriver()));
		if(aggregate.getAttendee()!=null)
		aggregate.setAttendee(entityManager.merge(aggregate.getAttendee()));
		if(aggregate.getDepotHelp()!=null)
		aggregate.setDepotHelp(entityManager.merge(aggregate.getDepotHelp()));		
		for (Destination destination : aggregate.getDestinations()) {
			destination = findDestinationById(destination.getId());
		}
		aggregate = entityManager.merge(aggregate);
		entityTransaction.commit();
		return aggregate;
		}
	

	@Override
	public Drive update(Drive aggregate) {
		return create(aggregate);
	}

	@Override
	public boolean delete(Drive aggregate) {
		Drive drive = find(aggregate);
		entityManager.getTransaction().begin();
		entityManager.remove(drive);
		entityManager.getTransaction().commit();
		return !exists(aggregate);
	}

	@Override
	public boolean createAll(Collection<Drive> aggregates) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(aggregates);
		entityTransaction.commit();
		return true;
	}
	
	

	@Override
	public List<Drive> getAll() {
		return entityManager.createQuery("select d from Drive d", Drive.class).getResultList();
	}

	@Override
	public boolean updateAll(Collection<Drive> aggregates) {
		aggregates.forEach(a -> update(a));
		return true;
	}

	@Override
	public boolean deleteAll(Collection<Drive> aggregates) {
		aggregates.forEach(a-> delete(a));
		return false;
	}

	@Override
	public Drive getByID(Long id) {
		return entityManager.find(Drive.class, id);
	}

	@Override
	public Drive find(Drive aggregate) {
		if (aggregate.getId()!=null)
		return getByID(aggregate.getId());
		return null;
		
	}


	@Override
	public boolean exists(Drive aggregate) {
		if (aggregate.getId()==null)
			return false;
		 return getByID(aggregate.getId())!=null;
	}

	@Override
	public void close() {	
		entityManager.close();
		entityManagerFactory.close();
	}

	@Override
	public Destination findDestinationById(Long id) {
		if(id !=null)
		return entityManager.find(Destination.class, id);
		return null;
	}

	@Override
	public Destination addDestination(Destination destination) {
		Destination found = findDestinationById(destination.getId());
		if (found!=null)
		return found;
		return entityManager.merge(destination);
		
	}



}
