package DBDAO;

import Beans.Customer;
import DAO.CustomerDAO;

import java.util.ArrayList;

public class CustomersDBDAO implements CustomerDAO {
    @Override
    public boolean isCustomerExists(String email, String password) {
        return false;
    }

    @Override
    public void addCustomer(Customer customer) {

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
