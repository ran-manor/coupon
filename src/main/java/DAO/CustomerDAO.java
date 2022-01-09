package DAO;

import Beans.Company;
import Beans.Customer;

import java.util.ArrayList;

public interface CustomerDAO {
    boolean isCustomerExists(String email, String password);
    boolean addCustomer(Customer customer);
    void deleteCustomer(long customerID);
    ArrayList<Customer> getAllCustomers();
    Customer getOneCustomer(long customerID);
    void updateCustomer(Customer customer);
}
