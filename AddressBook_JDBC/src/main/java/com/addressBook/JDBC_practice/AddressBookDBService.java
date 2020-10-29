package com.addressBook.JDBC_practice;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookDBService {
	private PreparedStatement addressBookDataStatement;
	private static AddressBookDBService addressBookDBService;

	private AddressBookDBService() {

	}

	public static AddressBookDBService getInstance() {
		if (addressBookDBService == null) {
			addressBookDBService = new AddressBookDBService();
		}
		return addressBookDBService;
	}

	private Connection getConnection() {
		Connection connection = null;
		final String DB_URL = "jdbc:mysql://localhost:3307/address_book_assignment";
		final String USER = "root";
		final String PASS = "Admin@123";
		try {
			System.out.println("Connecting to database: " + DB_URL);
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connection is successfull..!!" + connection);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return connection;
	}

	public List<Contacts> readData() throws Exception {
		String sql = "SELECT * from address_book_table";
		List<Contacts> addressBookList = new ArrayList<>();
		Connection connection = this.getConnection();
		System.out.println("Creating statement..");
		Statement statement;
		try {
			statement = connection.createStatement();
			System.out.println("Statement created successfully..");
			ResultSet resultSet = statement.executeQuery(sql);
			addressBookList = this.getAddressBookData(resultSet);

//			resultSet.close();
//			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new AddressBookException("Oops there's an exception!");
		}
		return addressBookList;
	}

	private List<Contacts> getAddressBookData(ResultSet resultSet) throws Exception {
		// TODO Auto-generated method stub
		List<Contacts> addressBookList = new ArrayList<>();
		Contacts addressBookData = null;
		try {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String address = resultSet.getString("address");
				String city = resultSet.getString("city");
				String state = resultSet.getString("state");
				int zip = resultSet.getInt("zip");
				String mobileNumber = resultSet.getString("mobileNumber");
				String emailId = resultSet.getString("emailId");

				addressBookData = new Contacts(id, firstName, lastName, address, city, state, zip, mobileNumber,
						emailId);
				addressBookList.add(addressBookData);
			}
			return addressBookList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new AddressBookException("Oops there's an exception!");
		}
	}

	public Contacts addContactToAddressBook(String firstName, String lastName, String address, String city,
			String state, int zip, String mobileNumber, String emailId) throws AddressBookException, Exception {
		// TODO Auto-generated method stub
		int id = -1;
		Contacts addressBookData = null;
		String sql = String.format(
				"INSERT INTO address_book_table ( firstName,  lastName,  address,  city,\r\n"
						+ "			 state,  zip,  mobileNumber,  emailId) "
						+ "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
				firstName, lastName, address, city, state, zip, mobileNumber, emailId);
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if (rowAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next())
					id = resultSet.getInt(1);
			}
			addressBookData = new Contacts(id, firstName, lastName, address, city, state, zip, mobileNumber, emailId);
		} catch (Exception e) {
			// TODO: handle exception
			throw new AddressBookException("Oops there's an exception here!");

		}
		return addressBookData;
	}

	public List<Contacts> getAddressBookData(String firstName) throws Exception {
		// TODO Auto-generated method stub
		List<Contacts> addressBookList = null;
		if (this.addressBookDataStatement == null)
			try {
				String sql = "SELECT * FROM address_book_table WHERE firstName = ?";
				this.prepareStatementForAddressBook(sql);
				addressBookDataStatement.setString(1, firstName);
				ResultSet resultSet = addressBookDataStatement.executeQuery();
				addressBookList = this.getAddressBookData(resultSet);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new AddressBookException("Oops there's an exception!");
			}
		return addressBookList;
	}

	private void prepareStatementForAddressBook(String sql) throws Exception {
		Connection connection = this.getConnection();
		try {
			addressBookDataStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new AddressBookException("Oops there's an exception!");
		}
	}
}
