package be.voedsaam.vzw.service.manager;

import java.time.LocalDateTime;
import java.util.Collection;

import be.voedsaam.vzw.business.repository.UserRepository;
import be.voedsaam.vzw.service.dto.DestinationDTO;
import be.voedsaam.vzw.service.dto.DriveDTO;
import be.voedsaam.vzw.service.dto.UserDTO;

public interface DriveManagement {
	
	public DriveDTO addDrive(DriveDTO newDrive);
	public boolean removeDrive(DriveDTO oldDrive);
	public Collection<DriveDTO> getDriveList(LocalDateTime from, LocalDateTime to);
	public Collection<DriveDTO> getDrivesByDriver(UserDTO user);
	public Collection<DriveDTO> getDrivesByAttendee(UserDTO user);
	public Collection<DriveDTO> getDrivesByDepotHelp(UserDTO user);
	public Collection<DriveDTO> getDrivesByDestination(DestinationDTO destination);
	public DriveDTO changeDrive(DriveDTO newDrive );
	public DriveDTO addDrive(DriveDTO drive1, DestinationDTO start, DestinationDTO first);
	public DriveDTO addDestination(DriveDTO drive1, DestinationDTO destination3);
	public Collection<DestinationDTO> getDestinationsByDrive(DriveDTO driveDTO);

}
