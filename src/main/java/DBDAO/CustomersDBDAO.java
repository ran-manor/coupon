package DBDAO;

import Beans.Customer;
import DAO.CustomerDAO;
import sql.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomersDBDAO implements CustomerDAO {
    private final String ADD_CUSTOMER="INSERT INTO `couponmania`.`customers` " +
            "(`first_name`,`last_name`,`email`,`password`)" +
            "VALUES (?,?,?,?);";

    private final String UPDATE_CUSTOMER = "UPDATE `couponmania`.`customers` " +
            "SET first_name=?, last_name=?, email=? ,password=?" +
            "WHERE id=?";

    private final String DELETE_CUSTOMER = "DELETE FROM `couponmania`.`customers` WHERE id=?";

    private final String GET_ALL_CUSTOMERS = "SELECT * FROM `couponmania`.`customers`";

    private final String GET_ONE_CUSTOMER_BY_ID = "SELECT * FROM `couponmania`.`customers` WHERE id LIKE ?";
    private Connection connection;
    private boolean isOK;

    @Override
    public boolean isCustomerExists(String email, String password) {
        return false;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        Map<Integer,Object> parmas = new HashMap<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_CUSTOMER);
            parmas.put(1,customer.getFirstName());
            parmas.put(2,customer.getLastName());
            parmas.put(3,customer.getEmail());
            parmas.put(4,customer.getPassword());
        } catch (InterruptedException | SQLException err) {
            System.out.printf(err.getMessage());
            isOK = false;
        }
        finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return isOK;

    }

    @Override
    public void deleteCustomer(int customerID) {

    }

    @Override
    public ArrayList<Customer> getAllCompanies() {
        return null;
    }

    @Override
    public Customer getOneCustomer(int customerID) {
        return null;
    }

    @Override
    public void updateCustomer(Customer customer) {

    }
}
