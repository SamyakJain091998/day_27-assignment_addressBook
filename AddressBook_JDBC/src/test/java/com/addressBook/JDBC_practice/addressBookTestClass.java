package com.addressBook.JDBC_practice;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.hamcrest.*;
import com.google.gson.*;

import io.restassured.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.addressBook.service.*;
import com.addressBook.JDBC_practice_Exception.AddressBookException;
import com.addressBook.pojoClasses.*;

public class addressBookTestClass {

	@Ignore
	@Test
	public void test() {
		Assert.assertTrue(true);
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

			addressBookService.updateContactEmailId("sammy", "jainsamyak941998@gmail.com");

			boolean result = addressBookService.checkAddressBookInSyncWithDB("sammy");
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

			addressBookService.deleteContact("dummy1");

			boolean result = addressBookService.checkAddressBookInSyncWithDB("dummy1");
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

		Assert.assertEquals(7, countOfContacts);
	}

/////////////////////////////////////////////////////////////REST ASSURE start/////////////////////////////////////////////////////////////////

	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	public Contacts[] getContactsList() {
		// TODO Auto-generated method stub
		Response response = RestAssured.get("/address_book_table");
		System.out.println("Contact entries in json server -> " + response.asString());
		Contacts[] arrayOfContacts = new Gson().fromJson(response.asString(), Contacts[].class);
		return arrayOfContacts;
	}

	public Response addContactToJsonServer(Contacts ContactData) {
		// TODO Auto-generated method stub
		String contactJson = new Gson().toJson(ContactData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(contactJson);
		return request.post("/address_book_table");
	}

	@Ignore
	@Test
	public void givenContacInJsonServer_WhenRetrieved_ShouldMatchTheCount() {
		Contacts[] arrayOfContacts = getContactsList();
		AddressBookService addressBookService;
		addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));
		long entries = addressBookService.countEntries();
		System.out.println("----Number of entries : " + entries);
		Assert.assertEquals(2, entries);
	}

	@Ignore
	@Test
	public void givenNewContact_WhenAdded_ShouldMatchTheCount() {

		AddressBookService addressBookService;
		Contacts[] arrayOfContacts = getContactsList();
		addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));

		Contacts contactData = new Contacts(0, "Sammyy", "Jainn", "dadabadii", "kota", "rajasthan", 324009,
				"12345678900", "j.samyakinfo@gmail.in");

		Response response = addContactToJsonServer(contactData);
		int statusCode = response.getStatusCode();
		System.out.println("----Status codeis : " + statusCode);
		Assert.assertEquals(201, statusCode);

		contactData = new Gson().fromJson(response.asString(), Contacts.class);
		addressBookService.addContactsToPayroll(contactData);
		long entries = addressBookService.countEntries();
		System.out.println("----Number of entries : " + entries);
		Assert.assertEquals(3, entries);

	}

	@Ignore
	@Test
	public void givenMultipleContacts_WhenAdded_ShouldMatchTheCount() {

		AddressBookService addressBookService;
		Contacts[] arrayOfContacts = getContactsList();
		addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));

		Contacts[] arrayOfNewContacts = {
				new Contacts(0, "dummy1", "lastName1", "address1", "city1", "state1", 1234567, "012345678900",
						"abc@gmail.com"),
				new Contacts(0, "dummy2", "lastName2", "address2", "city2", "state2", 12345678, "00123456789000",
						"abcd@gmail.com"),
				new Contacts(0, "dummy3", "lastName3", "address3", "city3", "state3", 123456789, "01234567890000",
						"abcde@gmail.com") };

		for (Contacts contactData : arrayOfNewContacts) {
			Response response = addContactToJsonServer(contactData);
			int statusCode = response.getStatusCode();
			System.out.println("----Status codeis : " + statusCode);
			Assert.assertEquals(201, statusCode);
			contactData = new Gson().fromJson(response.asString(), Contacts.class);
			addressBookService.addContactsToPayroll(contactData);
		}

		long entries = addressBookService.countEntries();

		System.out.println("----Number of entries : " + entries);
		Assert.assertEquals(4, entries);
	}

	@Ignore
	@Test
	public void givenNewLastNameForContact_WhenUpdated_ShouldMatch200response() throws AddressBookException {

		AddressBookService addressBookService;
		Contacts[] arrayOfContacts = getContactsList();
		addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));

		addressBookService.updateContactLastNameOnJsonServer("Manu", "jainnn");
		Contacts contactData = addressBookService.getAddressBookData("Manu");

		String empJson = new Gson().toJson(contactData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(empJson);
		Response response = request.put("/address_book_table/" + contactData.getId());
		int statusCode = response.getStatusCode();
		System.out.println("===========> Status code is : " + statusCode);
		Assert.assertEquals(200, statusCode);

		long entries = addressBookService.countEntries();
		System.out.println("----Number of entries : " + entries);
		Assert.assertEquals(6, entries);
	}

	@Test
	public void givenEmployeeToDelete_WhenDeleted_ShouldMatch200response() throws AddressBookException {

		AddressBookService addressBookService;
		Contacts[] arrayOfContacts = getContactsList();
		addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));

		Contacts contactData = addressBookService.getAddressBookData("Sammy");

		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Response response = request.delete("/address_book_table/" + contactData.getId());
		int statusCode = response.getStatusCode();
		Assert.assertEquals(200, statusCode);

		addressBookService.deleteContact(contactData.getFirstName());

		try {
			boolean result = addressBookService.checkAddressBookInSyncWithDB("Sammy");
			System.out.println("result it : --------------> " + result);
			Assert.assertFalse(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
