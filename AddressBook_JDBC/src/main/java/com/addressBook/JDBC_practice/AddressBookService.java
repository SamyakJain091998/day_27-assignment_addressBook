package com.addressBook.JDBC_practice;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AddressBookService {
	private List<Contacts> addressBookList;

	private AddressBookDBService addressBookDBService;

	public AddressBookService() {
		addressBookDBService = AddressBookDBService.getInstance();
	}

	public AddressBookService(List<Contacts> addressBookList) {
		this();
		this.addressBookList = addressBookList;
	}

	public List<Contacts> readAddressBookData() throws Exception {
		// TODO Auto-generated method stub
		this.addressBookList = addressBookDBService.readData();

		return this.addressBookList;
	}

	private Contacts getAddressBookData(String firstName) {
		// TODO Auto-generated method stub
		Contacts addressBookData;
		addressBookData = this.addressBookList.stream()
				.filter(addressBookDataItem -> addressBookDataItem.getFirstName().equals(firstName)).findFirst()
				.orElse(null);
		return addressBookData;
	}

	public void addContactToAddressBook(String firstName, String lastName, String address, String city, String state,
			int zip, String mobileNumber, String emailId) throws AddressBookException, Exception {
		// TODO Auto-generated method stub
		addressBookList.add(addressBookDBService.addContactToAddressBook(firstName, lastName, address, city, state, zip,
				mobileNumber, emailId));
	}

	public boolean checkAddressBookInSyncWithDB(String firstName) throws Exception {
		List<Contacts> addressBookDataList;
		try {
			addressBookDataList = addressBookDBService.getAddressBookData(firstName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new AddressBookException("Oops there's an exception!");
		}
		// TODO Auto-generated method stub
		return addressBookDataList.get(0).equals(getAddressBookData(firstName));
	}
}
