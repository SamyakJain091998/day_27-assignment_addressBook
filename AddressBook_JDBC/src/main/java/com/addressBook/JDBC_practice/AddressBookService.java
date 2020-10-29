package com.addressBook.JDBC_practice;

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
}
