package DAO;

import Beans.Company;
import Beans.Customer;

import java.util.ArrayList;
/**
 * interface that holds the methods regarding customers in the database that will be implemented in the DBDao
 * (including full CRUD functionality)
 */
public interface CustomerDAO {
    /**
     * searches for a customer in the DataBase by email and password.
     * @param email the email of the customer to find.
     * @param password the password of the customer to find.
     * @return the found customer. returns null if not found.
     */
    Customer isCustomerExists(String email, String password);
    /**
     * adds a customer to the DataBase.
     * @param customer the customer to add.
     * @return true if adding was successful. false if not.
     */
    boolean addCustomer(Customer customer);
    /**
     * deletes a customer form the DataBase by customerID.
     * @param customerID the id of the customer to delete.
     */
    void deleteCustomer(long customerID);
    /**
     * gets ana arraylist of all customers in the DataBase.
     * @return arraylist holding all the customers.
     */
    ArrayList<Customer> getAllCustomers();
    /**
     * searches for a customer in the DataBase by customerID.
     * @param customerID the id of the customer to find.
     * @return the customer that was found. returns null if not found.
     */
    Customer getOneCustomer(long customerID);
    /**
     * updates customer details in the DataBase.
     * @param customer the customer to update, with updated details.
     */
    void updateCustomer(Customer customer);
}
