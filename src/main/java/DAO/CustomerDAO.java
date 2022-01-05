package DAO;

import Beans.Company;
import Beans.Customer;

import java.util.ArrayList;

public interface CustomerDAO {
    boolean isCustomerExists(String email, String password);
    boolean addCustomer(Customer customer);
    void deleteCustomer(int customerID);
    ArrayList<Customer> getAllCustomers();
    Customer getOneCustomer(int customerID);
    void updateCustomer(Customer customer);
}
