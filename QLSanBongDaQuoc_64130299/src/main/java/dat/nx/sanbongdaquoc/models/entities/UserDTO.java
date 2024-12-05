package dat.nx.sanbongdaquoc.models.entities;

import java.sql.Timestamp;

public class UserDTO {
	private String userID;
	private String fullName;
	private String email;
	private String phoneNumber;
	private String passwordHash;
	private boolean isAdmin;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public UserDTO() {
	}

	public UserDTO(String userID, String fullName, String email, String phoneNumber, String passwordHash,
			boolean isAdmin, Timestamp createdAt, Timestamp updatedAt) {
		this.userID = userID;
		this.fullName = fullName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.passwordHash = passwordHash;
		this.isAdmin = isAdmin;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	
}