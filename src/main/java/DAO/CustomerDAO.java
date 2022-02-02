package DAO;

import Beans.Company;
import Beans.Customer;

import java.util.ArrayList;
/**
 * interface that holds the methods regarding customers in the database that will be implemented in the DBDao
 * (including full CRUD functionality)
 */
public interface CustomerDAO {
    Customer isCustomerExists(String email, String password);
    boolean addCustomer(Customer customer);
    void deleteCustomer(long customerID);
    ArrayList<Customer> getAllCustomers();
    Customer getOneCustomer(long customerID);
    void updateCustomer(Customer customer);
}
