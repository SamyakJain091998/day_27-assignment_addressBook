package com.addressBook.JDBC_practice;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class addressBookTestClass {

	@Ignore
	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() throws Exception {
		AddressBookService addressBookService = new AddressBookService();
		List<Contacts> AddressBookData = addressBookService.readAddressBookData();
		Assert.assertEquals(2, AddressBookData.size());
	}

	@Ignore
	@Test
	public void givenNewContact_WhenAdded_ShouldSyncWithDB() throws Exception {
		AddressBookService addressBookService = new AddressBookService();
		addressBookService.readAddressBookData();
		addressBookService.addContactToAddressBook("sammy", "jain", "iiitdmj", "jabalpur", "mp", 482005, "7580813216",
				"2016224@iiitdmj.ac.in");
		boolean result = addressBookService.checkAddressBookInSyncWithDB("sammy");
		Assert.assertTrue(result);
	}

	@Ignore
	@Test
	public void givenNewEmailIdForContact_WhenUpdated_ShouldSyncWithDB() throws Exception {
		AddressBookService addressBookService = new AddressBookService();
		try {
			List<Contacts> AddressBookData = addressBookService.readAddressBookData();

			addressBookService.updateContactEmailId("samyak", "jainsamyak941998@gmail.com");

			boolean result = addressBookService.checkAddressBookInSyncWithDB("samyak");
			Assert.assertTrue(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new AddressBookException("Oops there's an exception!");
		}
	}

	@Ignore
	@Test
	public void givenFirstNameOfContact_WhenContactDeleted_ShouldSyncWithDB() throws Exception {
		AddressBookService addressBookService = new AddressBookService();
		try {
			List<Contacts> AddressBookData = addressBookService.readAddressBookData();

			addressBookService.deleteContact("manu");

			boolean result = addressBookService.checkAddressBookInSyncWithDB("manu");
			Assert.assertEquals(false, result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new AddressBookException("Oops there's an exception!");
		}
	}

	@Ignore
	@Test
	public void givenMultipleContacts_WhenAdded_ShouldSyncWithDB() throws Exception {
		AddressBookService addressBookService = new AddressBookService();
		try {
			List<Contacts> AddressBookData = addressBookService.readAddressBookData();

			Contacts[] arrayOfContacts = {
					new Contacts("dummy1", "lastNameDummy1", "iiitdmj", "jabalpur", "mp", 482005, "7580813216",
							"2016224@iiitdmj.ac.in"),
					new Contacts("dummy2", "lastNameDummy2", "Pdpmiiitdmj", "jbp", "madhya pradesh", 482005,
							"7580813216", "2016224@iiitdmj.ac.in"),
					new Contacts("dummy3", "lastNameDummy3", "iiit", "jblpr", "centrl india", 482005, "7580813216",
							"2016224@iiitdmj.ac.in") };

			addressBookService.addContactToAddressBook(Arrays.asList(arrayOfContacts));
			boolean result = addressBookService.checkAddressBookInSyncWithDB("dummy1");
			Assert.assertEquals(true, result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new AddressBookException("Oops there's an exception!");
		}
	}

	@Ignore
	@Test
	public void givenNewAddressBook_WhenAdded_ShouldSyncWithDB() throws Exception {
		AddressBookService addressBookService = new AddressBookService();
		List<AddressBook> typicalAddressBookList = addressBookService.readTypicalAddressBookData();

		addressBookService.addNewAddressBook("type4");

		boolean result = addressBookService.checkTypicalAddressBookInSyncWithDB("type3");
		Assert.assertTrue(result);
	}

	@Ignore
	@Test
	public void givenNewContact_WhenAdded_ShouldNotBeADuplicateAndShouldSyncWithDB() throws Exception {
		AddressBookService addressBookService = new AddressBookService();
		addressBookService.readAddressBookData();
		addressBookService.addContactToAddressBook("sammy", "jain", "iiitdmJabalpur", "jabalpr", "mpreadhes", 482005,
				"780813216", "2016224@iiItdmj.ac.innn");
		boolean result = addressBookService.checkAddressBookInSyncWithDB("sammy");
		Assert.assertTrue(result);
	}

	@Ignore
	@Test
	public void givenACityOrState_WhenQueriedUpon_ShouldReturnTheNumberOfPersonLivingInTheCityOrTheState()
			throws Exception {
		AddressBookService addressBookService = new AddressBookService();
		List<Contacts> addressBookList = addressBookService.readAddressBookData();

//		addressBookService.queryBasisPlace("ujjain", "city");
		List<Contacts> matchingContactsList = addressBookService.queryBasisPlace("mp", "state");

		Assert.assertEquals(3, matchingContactsList.size());
	}

	@Ignore
	@Test
	public void givenACityOrState_WhenQueriedUpon_ShouldCreateADictionaryOfPersonLivingInTheCityOrTheState()
			throws Exception {
		AddressBookService addressBookService = new AddressBookService();
		List<Contacts> addressBookList = addressBookService.readAddressBookData();

//		addressBookService.queryBasisPlace("ujjain", "city");
		List<Contacts> matchingContactsList = addressBookService.queryBasisPlace("mp", "state");

		Assert.assertEquals(3, matchingContactsList.size());
	}

	@Ignore
	@Test
	public void givenAddressBook_WhenSortedBasisContactName_ShouldReturnTheSortedList() throws Exception {
		AddressBookService addressBookService = new AddressBookService();
		List<Contacts> addressBookList = addressBookService.readAddressBookData();

//		addressBookService.queryBasisPlace("ujjain", "city");
		List<Contacts> matchingContactsList = addressBookService.sortContactsBasisContactName();
		System.out.println(matchingContactsList);
		Assert.assertEquals(true, true);
	}

	@Ignore
	@Test
	public void givenAddressBook_WhenSortedBasisCityStateOrZip_ShouldReturnTheSortedList() throws Exception {
		AddressBookService addressBookService = new AddressBookService();
		List<Contacts> addressBookList = addressBookService.readAddressBookData();
		List<Contacts> matchingContactsList = null;

		matchingContactsList = addressBookService.sortContactsBasisCityStateOrZip("city");
//		matchingContactsList = addressBookService.sortContactsBasisCityStateOrZip("state");
//		matchingContactsList = addressBookService.sortContactsBasisCityStateOrZip("zip");

		System.out.println(matchingContactsList);
		Assert.assertEquals(true, true);
	}

	@Ignore
	@Test
	public void givenAddressBook_WhenDataRetrieved_ShouldReturnNumberOfContactDetailsAddedInAParticularDate()
			throws Exception {
		AddressBookService addressBookService = new AddressBookService();
		List<Contacts> addressBookList = addressBookService.readAddressBookData();

		LocalDate date1 = LocalDate.of(2017, 1, 1);
		LocalDate date2 = LocalDate.of(2020, 12, 30);
		int countOfContacts = addressBookService.getDetailsBetweenAPeriod(date1, date2);
//		System.out.println(countOfContacts);
		Assert.assertEquals(7, countOfContacts);
	}

	@Test
	public void givenMultipleContacts_WhenAddedUsingThreads_ShouldSyncWithDB() throws Exception {
		AddressBookService addressBookService = new AddressBookService();
		try {
			List<Contacts> AddressBookData = addressBookService.readAddressBookData();

			Contacts[] arrayOfContacts = {
					new Contacts("dummy1", "lastNameDummy1", "iiitdmj", "jabalpur", "mp", 482005, "1234567890",
							"2016224@iiitdmj.ac.in"),
					new Contacts("dummy2", "lastNameDummy2", "Pdpmiiitdmj", "jbp", "madhya pradesh", 482005,
							"9876543210", "2016224@iiitdmj.ac.com"),
					new Contacts("dummy3", "lastNameDummy3", "iiit", "jblpr", "centrl india", 482005, "7580813***",
							"2016224@iiitdmj.ac.**") };
			/*
			 * Instant start = Instant.now();
			 * addressBookService.addContactToAddressBook(Arrays.asList(arrayOfContacts));
			 * Instant end = Instant.now(); System.out.println("Duration without thread : "
			 * + Duration.between(start, end));
			 */

///////////////////////////////////

			Instant threadStart = Instant.now();
			addressBookService.addContactToAddressBookWithThread(Arrays.asList(arrayOfContacts));
			Instant threadEnd = Instant.now();

			Thread.sleep(80);
			System.out.println("Duration with thread : " + Duration.between(threadStart, threadEnd));

			List<Contacts> numberOfExistingContacts = addressBookService.readAddressBookData();
			System.out.println("=====================" + numberOfExistingContacts + "=====================");
			System.out.println("-----------size is-------------" + numberOfExistingContacts.size());

			Assert.assertEquals(4, numberOfExistingContacts.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new AddressBookException("Oops there's an exception!");
		}
	}
}
