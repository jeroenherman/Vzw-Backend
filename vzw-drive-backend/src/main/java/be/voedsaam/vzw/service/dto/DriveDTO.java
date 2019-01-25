package be.voedsaam.vzw.service.dto;

import java.time.LocalDateTime;

import be.voedsaam.vzw.business.User;

public class DriveDTO {
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String driver;
	private String attendee;
	private String depotHelp;
	private Long id;

	public DriveDTO() {
		startTime = LocalDateTime.now();
		endTime = startTime.plusHours(1);

	}

	public DriveDTO(LocalDateTime startTime, LocalDateTime endTime) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) throws ArithmeticException {
		if (endTime.isBefore(startTime))
			throw new ArithmeticException("End time must be greater then start time");
		this.endTime = endTime;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getAttendee() {
		return attendee;
	}

	public void setAttendee(String attendee) {
		this.attendee = attendee;
	}

	public String getDepotHelp() {
		return depotHelp;
	}

	public void setDepotHelp(String depotHelp) {
		this.depotHelp = depotHelp;
	}

	public void setId(Long id) {
		this.id = id;

	}
	
	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "DriveDTO [startTime=" + startTime + ", endTime=" + endTime + ", driver=" + driver + ", attendee="
				+ attendee + ", depotHelp=" + depotHelp + "]";
	}

}
