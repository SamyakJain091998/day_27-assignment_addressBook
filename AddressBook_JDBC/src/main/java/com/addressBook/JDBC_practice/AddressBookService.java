package com.addressBook.JDBC_practice;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookService {
	private List<Contacts> addressBookList;
	private List<AddressBook> typicalAddressBookList;
	private HashMap<String, List<Contacts>> cityMap = new HashMap<String, List<Contacts>>();
	private HashMap<String, List<Contacts>> stateMap = new HashMap<String, List<Contacts>>();

	private AddressBookDBService addressBookDBService;

	public AddressBookService() {
		addressBookDBService = AddressBookDBService.getInstance();
	}

	public AddressBookService(List<Contacts> addressBookList) {
		this();
		this.addressBookList = new ArrayList<>(addressBookList);
	}

	public List<Contacts> readAddressBookData() throws Exception {
		// TODO Auto-generated method stub
		this.addressBookList = addressBookDBService.readData();

		return this.addressBookList;
	}

	public long countEntries() {
		return addressBookList.size();
	}

	public void addContactsToPayroll(Contacts contactData) {
		// TODO Auto-generated method stub
		addressBookList.add(contactData);
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

	public List<Contacts> queryBasisPlace(String placeName, String placeType) {
		// TODO Auto-generated method stub
		int result;
		List<Contacts> contactsList = null;
		try {
			contactsList = addressBookDBService.retrievePlaceDetails(placeName, placeType);
		} catch (AddressBookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (contactsList.size() == 0) {
			return null;
		}
		if (placeType.toLowerCase().equals("city")) {
			cityMap.put(placeType.toLowerCase(), contactsList);
		}
		if (placeType.toLowerCase().equals("state")) {
			stateMap.put(placeType.toLowerCase(), contactsList);
		}
		return contactsList;
	}

	public List<Contacts> sortContactsBasisContactName() throws Exception {
		// TODO Auto-generated method stub
		this.addressBookList = addressBookDBService.readData();
		Collections.sort(addressBookList, new Comparator<Contacts>() {
			@Override
			public int compare(Contacts contact1, Contacts contact2) {
				return contact1.getFirstName().compareToIgnoreCase(contact2.getFirstName());
			}
		});
		return this.addressBookList;
	}

	public List<Contacts> sortContactsBasisCityStateOrZip(String sortingBasis) throws Exception {
		// TODO Auto-generated method stub
		this.addressBookList = addressBookDBService.readData();
		sortingBasis = sortingBasis.toLowerCase();
		if (sortingBasis.equals("city")) {
			Collections.sort(addressBookList, new Comparator<Contacts>() {
				@Override
				public int compare(Contacts contact1, Contacts contact2) {
					return contact1.getCity().compareToIgnoreCase(contact2.getCity());
				}
			});
		} else if (sortingBasis.equals("state")) {
			Collections.sort(addressBookList, new Comparator<Contacts>() {
				@Override
				public int compare(Contacts contact1, Contacts contact2) {
					return contact1.getState().compareToIgnoreCase(contact2.getState());
				}
			});
		} else if (sortingBasis.equals("zip")) {
			Collections.sort(addressBookList, new Comparator<Contacts>() {
				@Override
				public int compare(Contacts contact1, Contacts contact2) {
					return contact1.getZip() - contact2.getZip();
				}
			});
		}
		return this.addressBookList;
	}

	public int getDetailsBetweenAPeriod(LocalDate date1, LocalDate date2) throws AddressBookException, Exception {
		// TODO Auto-generated method stub
		List<Contacts> resultList;
		try {
			resultList = addressBookDBService.queryForContactDetailsBetweenAParticularPeriod(date1, date2);
		} catch (AddressBookException e) {
			// TODO Auto-generated catch block
			throw new AddressBookException("Oops there's an exception!!!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new AddressBookException("Oops there's an exception!!!");
		}
		if (resultList == null) {
			return 0;
		}
		return resultList.size();
	}
}
