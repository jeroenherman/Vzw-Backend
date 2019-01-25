package be.voedsaam.vzw.service.manager;

import java.util.List;

import be.voedsaam.vzw.service.dto.UserDTO;

public interface VzwManagement {
	public UserDTO login(UserDTO user);
	public UserDTO addUser(UserDTO user);
	public boolean removeUser(UserDTO user);
	public List<UserDTO> getAllUsers();
	public boolean logOut();
	public UserDTO getCurrentUser();
}
