package DBDAO;

import Beans.Customer;
import DAO.CustomerDAO;
import sql.DBUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomersDBDAO implements CustomerDAO {

    private final String TABLE_PATH = DBUtils.SCHEMA_PATH + ".`customers`";

    protected final String ADD_CUSTOMER = "INSERT INTO " +
            TABLE_PATH + " (`first_name`,`last_name`,`email`,`password`)" +
            "VALUES (?,?,?,?);";

    private final String UPDATE_CUSTOMER = "UPDATE " + TABLE_PATH +
            " SET first_name=?, last_name=?, email=? ,password=?" +
            "WHERE id=?";

    private final String DELETE_CUSTOMER = "DELETE FROM " + TABLE_PATH + " WHERE id=?";
    private final String GET_ALL_CUSTOMERS = "SELECT * FROM " + TABLE_PATH;

    private final String GET_ONE_CUSTOMER_BY_ID = "SELECT * FROM " + TABLE_PATH + " WHERE id LIKE ?";
    private final String IS_CUSTOMER_EXISITS = "SELECT * FROM " + TABLE_PATH + " WHERE email=? AND password=?";

    private Connection connection;
    private boolean isOK;

    //TODO: make is customer exists and getonecustomer one function
    @Override
    public Customer isCustomerExists(String email, String password) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);
        ResultSet resultSet;
        try {
            resultSet = DBUtils.runQueryForResultSet(IS_CUSTOMER_EXISITS, params);
            if (resultSet.next()) {
                Customer customer = Customer.builder()
                        .id(resultSet.getLong("id"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .coupons(new ArrayList<>())
                        .build();
                return customer;
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return null;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        Map<Integer, Object> parmas = new HashMap<>();

        parmas.put(1, customer.getFirstName());
        parmas.put(2, customer.getLastName());
        parmas.put(3, customer.getEmail());
        parmas.put(4, customer.getPassword());
        return DBUtils.runQuery(ADD_CUSTOMER, parmas);
    }

    @Override
    public void deleteCustomer(long customerID) {
        Map<Integer, Object> params = new HashMap<Integer, Object>();
        params.put(1, customerID);
        DBUtils.runQuery(DELETE_CUSTOMER, params);
    }

    @Override
    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = DBUtils.runQueryForResultSet(GET_ALL_CUSTOMERS);
            while (resultSet.next()) {
                Customer customer = Customer.builder()
                        .id(resultSet.getLong("id"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .coupons(new ArrayList<>())
                        .build();
                customers.add(customer);
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return customers;
    }

    @Override
    public Customer getOneCustomer(long customerID) {
        Map<Integer, Object> params = new HashMap<Integer, Object>();
        params.put(1, customerID);
        ResultSet resultSet;
        try {
            resultSet = DBUtils.runQueryForResultSet(GET_ONE_CUSTOMER_BY_ID, params);
            if (resultSet.next()) {
                Customer customer = Customer.builder()
                        .id(resultSet.getLong("id"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .coupons(new ArrayList<>())
                        .build();
                return customer;
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return null;
    }


    @Override
    public void updateCustomer(Customer customer) {
        Map<Integer, Object> parmas = new HashMap<>();
        parmas.put(1, customer.getFirstName());
        parmas.put(2, customer.getLastName());
        parmas.put(3, customer.getEmail());
        parmas.put(4, customer.getPassword());
        parmas.put(5, customer.getId());

        DBUtils.runQuery(UPDATE_CUSTOMER, parmas);
    }
}
