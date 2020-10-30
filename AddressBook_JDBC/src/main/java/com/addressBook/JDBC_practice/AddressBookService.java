package com.addressBook.JDBC_practice;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AddressBookService {
	private List<Contacts> addressBookList;
	private List<AddressBook> typicalAddressBookList;

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

	public List<AddressBook> readTypicalAddressBookData() throws Exception {
		// TODO Auto-generated method stub
		this.typicalAddressBookList = addressBookDBService.readAddressBookData();

		return this.typicalAddressBookList;
	}

	private Contacts getAddressBookData(String firstName) {
		// TODO Auto-generated method stub
		Contacts addressBookData;
		addressBookData = this.addressBookList.stream()
				.filter(addressBookDataItem -> addressBookDataItem.getFirstName().equals(firstName)).findFirst()
				.orElse(null);
		return addressBookData;
	}

	private AddressBook getTypicalAddressBookData(String address_book_name) {
		// TODO Auto-generated method stub
		AddressBook typicalAddressBookData;
		typicalAddressBookData = this.typicalAddressBookList.stream().filter(
				typicalAddressBookDataItem -> typicalAddressBookDataItem.getAddressBookName().equals(address_book_name))
				.findFirst().orElse(null);
		return typicalAddressBookData;
	}

	public void addContactToAddressBook(String firstName, String lastName, String address, String city, String state,
			int zip, String mobileNumber, String emailId) throws AddressBookException, Exception {
		// TODO Auto-generated method stub
		boolean index = true;
		for (Contacts contacts : addressBookList) {
			if ((firstName.equals(contacts.getFirstName()) && lastName.equals(contacts.getLastName())
					&& address.equals(contacts.getAddress()))
					|| (firstName.equals(contacts.getFirstName()) && lastName.equals(contacts.getLastName())
							&& city.equals(contacts.getCity()))
					|| (firstName.equals(contacts.getFirstName()) && lastName.equals(contacts.getLastName())
							&& state.equals(contacts.getState()))) {
				System.out.println("Oops!! The person is already registered. Please enter a valid entry.");
				index = false;
				break;
			}
			if (mobileNumber.equals(contacts.getMobileNumber()) || emailId.equals(contacts.getEmailId())) {
				System.out.println(
						"Oops!! The person is already registered with the same emailId or mobile number. Please enter a valid entry.");
				index = false;
				break;
			}
		}
		if (index == false) {
			System.exit(0);
		}
		addressBookList.add(addressBookDBService.addContactToAddressBook(firstName, lastName, address, city, state, zip,
				mobileNumber, emailId));
	}

	public void addNewAddressBook(String address_book_name) throws AddressBookException, Exception {
		// TODO Auto-generated method stub
		for (AddressBook addressBookObj : typicalAddressBookList) {
			if (address_book_name.equals(addressBookObj.getAddressBookName())) {
				System.out.println("Oops the addressBook with this name already exists!");
				System.exit(0);
			}
		}
		typicalAddressBookList.add(addressBookDBService.addNewAddressBook(address_book_name));
	}

	public void addContactToAddressBook(List<Contacts> contactsList) throws Exception {
		// TODO Auto-generated method stub
		contactsList.forEach(addressBookData -> {
			try {
				System.out.println("Contact being added : " + addressBookData.getFirstName());

				this.addContactToAddressBook(addressBookData.getFirstName(), addressBookData.getLastName(),
						addressBookData.getAddress(), addressBookData.getCity(), addressBookData.getState(),
						addressBookData.getZip(), addressBookData.getMobileNumber(), addressBookData.getEmailId());
				System.out.println("Employee added : " + addressBookData.getFirstName());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					throw new AddressBookException("Oops there's an exception!!!");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	public boolean checkAddressBookInSyncWithDB(String firstName) throws Exception {
		List<Contacts> addressBookDataList;
		try {
			addressBookDataList = addressBookDBService.getAddressBookData(firstName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new AddressBookException("Oops there's an exception!!!");
		}
		// TODO Auto-generated method stub
//		System.out.println(addressBookDataList.get(0).getEmailId());
		if (addressBookDataList.size() == 0) {
			return false;
		}
		return addressBookDataList.get(0).equals(getAddressBookData(firstName));
	}

	public boolean checkTypicalAddressBookInSyncWithDB(String address_book_name) throws Exception {
		List<AddressBook> typicalAddressBookDataList;
		try {
			typicalAddressBookDataList = addressBookDBService.getTypicalAddressBookData(address_book_name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new AddressBookException("Oops there's an exception!!!");
		}
		// TODO Auto-generated method stub
//		System.out.println(addressBookDataList.get(0).getEmailId());
		if (typicalAddressBookDataList.size() == 0) {
			return false;
		}
		return typicalAddressBookDataList.get(0).equals(getTypicalAddressBookData(address_book_name));
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

	public void deleteContact(String firstName) {
		// TODO Auto-generated method stub
		int result;
		try {
			result = addressBookDBService.deleteContactData(firstName);

			if (result == 0)
				return;
			Contacts addressBookData = this.getAddressBookData(firstName);
			if (addressBookData != null) {
				addressBookList.remove(addressBookList.indexOf(addressBookData));
			}
		} catch (AddressBookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
