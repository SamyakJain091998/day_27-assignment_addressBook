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

import org.apache.commons.math3.analysis.function.Add;

public class AddressBookDBService {
	private int connectionCounter = 0;
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
		connectionCounter++;
		Connection connection = null;
		final String DB_URL = "jdbc:mysql://localhost:3307/address_book_assignment";
		final String USER = "root";
		final String PASS = "Admin@123";
		try {
			System.out.println("Processing Thread : " + Thread.currentThread().getName()
					+ " Connecting to database with id : " + connectionCounter);
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Processing Thread : " + Thread.currentThread().getName() + " Id : " + connectionCounter
					+ " Connection is successfuly : " + connection);
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

	public List<AddressBook> readAddressBookData() throws Exception {
		String sql = "SELECT * from address_book";
		List<AddressBook> typicalAddressBookList = new ArrayList<>();
		Connection connection = this.getConnection();
		System.out.println("Creating statement..");
		Statement statement;
		try {
			statement = connection.createStatement();
			System.out.println("Statement created successfully..");
			ResultSet resultSet = statement.executeQuery(sql);
			typicalAddressBookList = this.getTypicalAddressBookData(resultSet);

//			resultSet.close();
//			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new AddressBookException("Oops there's an exception!");
		}
		return typicalAddressBookList;
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

	private List<AddressBook> getTypicalAddressBookData(ResultSet resultSet) throws Exception {
		// TODO Auto-generated method stub
		List<AddressBook> typicalAddressBookList = new ArrayList<>();
		AddressBook typicalAddressBookData = null;
		try {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String address_book_name = resultSet.getString("address_book_name");

				typicalAddressBookData = new AddressBook(id, address_book_name);
				typicalAddressBookList.add(typicalAddressBookData);
			}
			return typicalAddressBookList;
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

	public AddressBook addNewAddressBook(String address_book_name) throws AddressBookException, Exception {
		// TODO Auto-generated method stub
		int id = -1;
		AddressBook typicalAddressBookData = null;
		String sql = String.format("INSERT INTO address_book ( address_book_name ) " + "VALUES ('%s')",
				address_book_name);
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if (rowAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next())
					id = resultSet.getInt(1);
			}
			typicalAddressBookData = new AddressBook(id, address_book_name);
		} catch (Exception e) {
			// TODO: handle exception
			throw new AddressBookException("Oops there's an exception here!");

		}
		return typicalAddressBookData;
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
				e.printStackTrace();
				throw new AddressBookException("Oops there's an exception!");
			}
		return addressBookList;
	}

	public List<AddressBook> getTypicalAddressBookData(String address_book_name) throws Exception {
		// TODO Auto-generated method stub
		List<AddressBook> typicalAddressBookList = null;
		if (this.addressBookDataStatement == null)
			try {
				String sql = "SELECT * FROM address_book WHERE address_book_name = ?";
				this.prepareStatementForAddressBook(sql);
				addressBookDataStatement.setString(1, address_book_name);
				ResultSet resultSet = addressBookDataStatement.executeQuery();
				typicalAddressBookList = this.getTypicalAddressBookData(resultSet);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new AddressBookException("Oops there's an exception!");
			}
		return typicalAddressBookList;
	}

	public List<Contacts> queryForContactDetailsBetweenAParticularPeriod(LocalDate date1, LocalDate date2)
			throws Exception {
		// TODO Auto-generated method stub
		List<Contacts> addressBookList = null;
		if (this.addressBookDataStatement == null)
			try {
				String sql = "SELECT * FROM address_book_table WHERE date_added BETWEEN CAST(? as date) and CAST(? as date)";
				this.prepareStatementForAddressBook(sql);
				addressBookDataStatement.setDate(1, Date.valueOf(date1));
				addressBookDataStatement.setDate(2, Date.valueOf(date2));

				ResultSet resultSet = addressBookDataStatement.executeQuery();
				addressBookList = this.getAddressBookData(resultSet);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new AddressBookException("Oops there's an exception!");
			}
		return addressBookList;
	}

	public int updateContactData(String firstName, String emailId) throws AddressBookException, Exception {
		// TODO Auto-generated method stub
		return this.updateEmployeeDataUsingStatement(firstName, emailId);
	}

	public int deleteContactData(String firstName) throws AddressBookException, Exception {
		// TODO Auto-generated method stub
		return this.deleteEmployeeDataUsingStatement(firstName);
	}

	private int updateEmployeeDataUsingStatement(String firstName, String emailId)
			throws AddressBookException, Exception {
		// TODO Auto-generated method stub
		String sql = String.format("update address_book_table set emailId = '%s' where firstName = '%s';", emailId,
				firstName);
		try (Connection connection = this.getConnection();) {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO: handle exception
			throw new AddressBookException("Oops there's an exception!");
		}
	}

	public List<Contacts> retrievePlaceDetails(String placeName, String placeType)
			throws AddressBookException, Exception {
		// TODO Auto-generated method stub
		List<Contacts> addressBookList = null;
		placeType = placeType.toLowerCase();
		if (this.addressBookDataStatement == null) {
			try {
				String sql = null;
				if (placeType == "city") {
					sql = String.format("select * from address_book_table where city = '%s';", placeName);
				} else if (placeType == "state") {
					sql = String.format("select * from address_book_table where state = '%s';", placeName);
				} else {
					System.out.println("You entered a wrong place type. PLease check and retry..");
					System.exit(0);
				}
				this.prepareStatementForAddressBook(sql);
				ResultSet resultSet = addressBookDataStatement.executeQuery();
				addressBookList = this.getAddressBookData(resultSet);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new AddressBookException("Oops there's an exception!");
			}
		}
		return addressBookList;
	}

	private int deleteEmployeeDataUsingStatement(String firstName) throws AddressBookException, Exception {
		// TODO Auto-generated method stub
		String sql = String.format("delete from address_book_table where firstName = '%s';", firstName);
		try (Connection connection = this.getConnection();) {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO: handle exception
			throw new AddressBookException("Oops there's an exception!");
		}
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
