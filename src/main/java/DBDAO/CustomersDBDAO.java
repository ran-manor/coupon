package DBDAO;

import Beans.Customer;
import DAO.CustomerDAO;
import sql.ConnectionPool;
import sql.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private final String IS_CUSTOMER_EXISITS = "SELECT * FROM `couponmania`.`customers` WHERE email=? AND password=?";

    private Connection connection;
    private boolean isOK;

    @Override
    public boolean isCustomerExists(String email, String password) {
        boolean isExist = true;
        Map<Integer,Object>parmas = new HashMap<>();
        ResultSet resultSet =DBUtils.getResultSetQuery(IS_CUSTOMER_EXISITS,parmas);;
        parmas.put(1,email);
        parmas.put(2,parmas);
        try {
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void addCustomer(Customer customer) {
        Map<Integer,Object> parmas = new HashMap<>();

            parmas.put(1,customer.getFirstName());
            parmas.put(2,customer.getLastName());
            parmas.put(3,customer.getEmail());
            parmas.put(4,customer.getPassword());
        DBUtils.runUpdateQuery(ADD_CUSTOMER,parmas);
    }

    @Override
    public void deleteCustomer(int customerID) {
        Map<Integer,Object>parms = new HashMap<>();
        parms.put(1,customerID);
    DBUtils.runUpdateQuery(DELETE_CUSTOMER,parms);
    }

    @Override
    public ArrayList<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<Customer>();
        ResultSet resultSet = DBUtils.getResultSetQuery(GET_ALL_CUSTOMERS);

            try {
                while (!resultSet.next()) {
                    Customer customer = Customer.builder()
                            .firstName(resultSet.getString("first_name"))
                            .lastName(resultSet.getString("last_name"))
                            .email(resultSet.getString("email"))
                            .password(resultSet.getString("password"))
                            .build();
                    customers.add(customer);
                }
            }
        catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return (ArrayList<Customer>) customers;
    }

    @Override
    public Customer getOneCustomer(int customerID) {
        Map<Integer,Object>parmas = new HashMap<>();
        parmas.put(1,customerID);
        ResultSet resultSet = DBUtils.getResultSetQuery(GET_ONE_CUSTOMER_BY_ID,parmas);
        if(resultSet!=null){
            try {
                Customer customer = Customer.builder()
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .build();
                return customer;
            } catch (SQLException err) {
                System.out.println(err.getMessage());
            }
        }
        return null;
    }

    @Override
    public void updateCustomer(Customer customer) {
        Map<Integer,Object> parmas = new HashMap<>();
        parmas.put(1,customer.getFirstName());
        parmas.put(2,customer.getLastName());
        parmas.put(3,customer.getEmail());
        parmas.put(4,customer.getPassword());
        DBUtils.runUpdateQuery(UPDATE_CUSTOMER,parmas);
    }
}
