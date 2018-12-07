package ua.nure.kn.bondarchuk.usermanagement2;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1335022776348683131L;
	private Long id;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;

	 public User() {
	    }

	 public User(Long id, String firstName, String lastName, Date dateOfBirth) {
	     this.id = id;
	     this.firstName = firstName;
	     this.lastName = lastName;
	     this.dateOfBirth = dateOfBirth;
	    }
	    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFullName() {
		return (new StringBuilder(getLastName())).append(", ").append(getFirstName()).toString();
		// return getLastName() + ", " + getFirstName();
	}

	public long getAge() {
		Calendar currentCalendar = Calendar.getInstance();
		Calendar bdayCalendar = Calendar.getInstance();
		bdayCalendar.setTime(getDateOfBirth());
		int age = currentCalendar.get(Calendar.YEAR) - bdayCalendar.get(Calendar.YEAR);
		if (currentCalendar.get(Calendar.DAY_OF_YEAR) < bdayCalendar.get(Calendar.DAY_OF_YEAR) && age > 0) {
			age--;
		}
		return age;
		
									
	}

}