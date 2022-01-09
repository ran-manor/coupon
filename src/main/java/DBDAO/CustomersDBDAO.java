package DBDAO;

import Beans.Customer;
import DAO.CustomerDAO;
import sql.DBUtils;
import sql.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomersDBDAO implements CustomerDAO {
    protected final static String ADD_CUSTOMER = "INSERT INTO `couponmania`.`customers` " +
            "(`first_name`,`last_name`,`email`,`password`)" +
            "VALUES (?,?,?,?);";

    private final String UPDATE_CUSTOMER = "UPDATE `couponmania`.`customers` " +
            "SET first_name=?, last_name=?, email=? ,password=?" +
            "WHERE id=?";

    private final String DELETE_CUSTOMER = "DELETE FROM `couponmania`.`customers` WHERE id=?";

    private final String GET_ALL_CUSTOMERS = "SELECT * FROM `couponmania`.`customers`";

    private final String GET_ONE_CUSTOMER_BY_ID = "SELECT * FROM `couponmania`.`customers` WHERE id LIKE ?";
    private final String IS_CUSTOMER_EXISITS = "SELECT * FROM `couponmania`.`customers` WHERE email=? AND password=?";

    private Connection connection;
    private boolean isOK;

    @Override
    public boolean isCustomerExists(String email, String password) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);
        ResultSet resultSet;
        try {
            resultSet = DBUtils.runQueryForResultSet(IS_CUSTOMER_EXISITS, params);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return false;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        Map<Integer, Object> parmas = new HashMap<>();

        parmas.put(1, customer.getFirstName());
        parmas.put(2, customer.getLastName());
        parmas.put(3, customer.getEmail());
        parmas.put(4, customer.getPassword());
        return DBUtils.runQueryGetId(ADD_CUSTOMER, parmas);
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
            resultSet = DBUtils.runQueryForResult(GET_ALL_CUSTOMERS);
            while (resultSet.next()) {
                Customer customer = Customer.builder()
                        .id(resultSet.getLong("id"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
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
