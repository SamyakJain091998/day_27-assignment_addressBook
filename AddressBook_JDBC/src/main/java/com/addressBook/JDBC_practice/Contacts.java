package com.addressBook.JDBC_practice;

import java.sql.Date;
import java.util.Objects;

public class Contacts {
	private int id;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private int zip;
	private String mobileNumber;
	private String emailId;

	public Contacts() {

	}

	public Contacts(String firstName, String lastName, String address, String city, String state, int zip,
			String mobileNumber, String emailId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.mobileNumber = mobileNumber;
		this.emailId = emailId;
	}

	public Contacts(int id, String firstName, String lastName, String address, String city, String state, int zip,
			String mobileNumber, String emailId) {
		this(firstName, lastName, address, city, state, zip, mobileNumber, emailId);
		this.id = id;
	}

	///////// SETTERS//////////

	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	////////// GETTERS//////////

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public int getZip() {
		return zip;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.firstName, this.lastName, this.address, this.city, this.state, this.zip,
				this.mobileNumber, this.emailId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contacts other = (Contacts) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (mobileNumber == null) {
			if (other.mobileNumber != null)
				return false;
		} else if (!mobileNumber.equals(other.mobileNumber))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (zip != other.zip)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ("Id: " + this.getId() + "First Name: " + this.getFirstName() + " Last Name: " + this.getLastName()
				+ " Address: " + this.getAddress() + " City : " + this.getCity() + " State: " + this.getState()
				+ " Zip: " + this.getZip() + " Mobile Number: " + this.getMobileNumber() + " Email Id: "
				+ this.getEmailId());
	}

}