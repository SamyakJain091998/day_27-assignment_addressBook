package com.addressBook.JDBC_practice;

public class AddressBook {
	private int addressBookId;
	private String addressBookName;

	public AddressBook() {

	}

	public AddressBook(int addressBookId, String addressBookName) {
		this.addressBookId = addressBookId;
		this.addressBookName = addressBookName;
	}

	public AddressBook(String addressBookName) {
		this.addressBookName = addressBookName;
	}

	public int getAddressBookId() {
		return addressBookId;
	}

	public void setAddressBookId(int addressBookId) {
		this.addressBookId = addressBookId;
	}

	public String getAddressBookName() {
		return addressBookName;
	}

	public void setAddressBookName(String addressBookName) {
		this.addressBookName = addressBookName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + addressBookId;
		result = prime * result + ((addressBookName == null) ? 0 : addressBookName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressBook other = (AddressBook) obj;
		if (addressBookId != other.addressBookId)
			return false;
		if (addressBookName == null) {
			if (other.addressBookName != null)
				return false;
		} else if (!addressBookName.equals(other.addressBookName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AddressBook [addressBookId=" + addressBookId + ", addressBookName=" + addressBookName + "]";
	}

}
