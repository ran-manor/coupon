package Facade;

import Beans.Company;
import Beans.Customer;

import java.util.ArrayList;

public class AdminFacade extends ClientFacade{
    public AdminFacade(){}
    @Override
    public boolean login(String email, String password) {
        return false;
    }
    public void addCompany(Company company){}
    public void updateCompany(Company company){}
    public void deleteCompany(int companyId){}
    public ArrayList<Company> getAllCompanies(){
        return null;
    }
    public Company getOneCompany(int companyId){
        return null;
    }
    public void addCustomer(Customer customer){}
    public void updateCustomer(Customer customer){}
    public void deleteCustomer(int customerId){}
    public ArrayList<Customer> getAllCutomers(){
        return null;
    }
    public Customer getOneCustomer(int customerId){
        return null;
    }

}
