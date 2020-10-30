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
//		System.out.println(addressBookDataList.get(0).getEmailId());
		return addressBookDataList.get(0).equals(getAddressBookData(firstName));
	}

	public void updateContactEmailId(String firstName, String emailId) {
		// TODO Auto-generated method stub
		int result;
		try {
			result = addressBookDBService.updateContactData(firstName, emailId);
			if (result == 0)
				return;
			Contacts addressBookData = this.getAddressBookData(firstName);
			if (addressBookData != null)
				addressBookData.setEmailId(emailId);
		} catch (AddressBookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
