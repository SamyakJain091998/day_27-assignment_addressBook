package com.addressBook.JDBC_practice;

import static org.junit.Assert.*;
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

	@Test
	public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() throws Exception {
		AddressBookService addressBookService = new AddressBookService();
		List<Contacts> AddressBookData = addressBookService.readAddressBookData();
		Assert.assertEquals(2, AddressBookData.size());
	}

}
