package be.voedsaam.vzw.business.repository.impl.mysql;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import be.voedsaam.vzw.business.User;
import be.voedsaam.vzw.business.repository.UserRepository;

public class UserRepositoryMySQL implements UserRepository {
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	

	public UserRepositoryMySQL() {
		entityManagerFactory = Persistence.createEntityManagerFactory("VZW_LOCAL");
		entityManager = entityManagerFactory.createEntityManager();
	}

	public void close() {
		entityManager.close();
		entityManagerFactory.close();
	}

	@Override
	public User create(User aggregate) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(aggregate);
		entityTransaction.commit();
		return aggregate;
	}

	@Override
	public User find(User aggregate) {
		User found = null;
		if (exists(aggregate)) {

			found = entityManager
					.createQuery("select u from User u where u.email = :email and u.password = :password"
							+ " or u.firstName= :firstName and u.lastName = :lastName", User.class)
					.setParameter("email", aggregate.getEmail())
					.setParameter("password", aggregate.getPassword())
					.setParameter("firstName", aggregate.getFirstName())
					.setParameter("lastName", aggregate.getLastName())
					.getSingleResult();

		}
		return found;
	}

	public User findByName(User aggregate) {
		User found = null;
		if (exists(aggregate)) {

			found = entityManager
					.createQuery("select u from User u where  u.firstName= :firstName and u.lastName = :lastName",
							User.class)
					.setParameter("firstName", aggregate.getFirstName())
					.setParameter("lastName", aggregate.getLastName()).getSingleResult();

		}
		return found;
	}

	@Override
	public User update(User aggregate) {
		User update = find(aggregate);
		entityManager.getTransaction().begin();
		update = aggregate;
		entityManager.getTransaction().commit();
		return update;
	}

	@Override
	public boolean delete(User aggregate) {
		User user = find(aggregate);
		entityManager.getTransaction().begin();
		entityManager.remove(user);
		entityManager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean createAll(Collection<User> aggregates) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(aggregates);
		entityTransaction.commit();
		return true;
	}

	@Override
	public List<User> getAll() {
		return entityManager.createQuery("select u from User u", User.class).getResultList();
	}

	@Override
	public boolean updateAll(Collection<User> aggregates) {
		aggregates.forEach(a -> update(a));
		return true;
	}

	@Override
	public boolean deleteAll(Collection<User> aggregates) {
		aggregates.forEach(a -> delete(a));
		return false;
	}

	@Override
	public User getByID(Long id) {
		return entityManager.find(User.class, id);
	}

	@Override
	public boolean exists(User aggregate) {

		Long count = (Long) entityManager
				.createQuery("select count(u) from User u where u.email = :email and u.password = :password "
						+ "or u.firstName= :firstName and u.lastName = :lastName")
				.setParameter("email", aggregate.getEmail())
				.setParameter("password", aggregate.getPassword())
				.setParameter("firstName", aggregate.getFirstName())
				.setParameter("lastName", aggregate.getLastName())
				.getSingleResult();
		return ((count.equals(0L)) ? false : true);
	}

}
