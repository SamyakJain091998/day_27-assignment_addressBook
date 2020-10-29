package com.addressBook.JDBC_practice;

import static org.junit.Assert.*;

import java.time.LocalDate;
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

	@Test
	public void givenNewContact_WhenAdded_ShouldSyncWithDB() throws Exception {
		AddressBookService addressBookService = new AddressBookService();
		addressBookService.readAddressBookData();
		addressBookService.addContactToAddressBook("sammy", "jain", "iiitdmj", "jabalpur", "mp", 482005, "7580813216",
				"2016224@iiitdmj.ac.in");
		boolean result = addressBookService.checkAddressBookInSyncWithDB("sammy");
		Assert.assertTrue(result);
	}

}
