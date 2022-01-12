package Facade;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;

import java.util.ArrayList;

public class AdminFacade extends ClientFacade {

    public AdminFacade() {
    }

    @Override
    public boolean login(String email, String password) {
        final String adminEmail = "admin@admin.com";
        final String adminPassword = "admin";
        return email.equals(adminEmail) && password.equals(adminPassword);
    }

    public void addCompany(Company company) {
        ArrayList<Company> companies = companiesDAO.getAllCompanies();
        boolean isOK = true;
        for (Company item : companies) {
            if (item.getName().equals(company.getName())) {
                isOK = false;
                break;
            }
            if (item.getEmail().equals(company.getEmail())) {
                isOK = false;
                break;
            }
        }
        if (isOK) {
            companiesDAO.addCompany(company);
        }
    }

    public void updateCompany(Company company) {
        companiesDAO.updateCompany(company);
    }

    //
    public void deleteCompany(long companyId) {
        ArrayList<Coupon> coupons = couponDAO.getAllCoupons();
        for (Coupon item : coupons) {
            if (item.getCompanyId() == companyId) {
                couponDAO.deleteCouponPurchaseByCouponID(item.getId());
                couponDAO.deleteCoupon(item.getId());
            }
        }
        companiesDAO.deleteCompany(companyId);
    }

    public ArrayList<Company> getAllCompanies() {
        return companiesDAO.getAllCompanies();
    }

    public Company getOneCompany(long companyId) {
        return companiesDAO.getOneCompany(companyId);
    }

    public void addCustomer(Customer customer) {
        ArrayList<Customer> customers = customerDAO.getAllCustomers();
        boolean isOK = true;
        for (Customer item : customers) {
            if (item.getEmail().equals(customer.getEmail())) {
                isOK = false;
                break;
            }
        }
        if (isOK) {
            customerDAO.addCustomer(customer);
        }
    }

    //
    public void updateCustomer(Customer customer) {
        customerDAO.updateCustomer(customer);
    }

    public void deleteCustomer(long customerId) {
        couponDAO.deleteCouponPurchaseByCustomerID(customerId);
        customerDAO.deleteCustomer(customerId);
    }

    public ArrayList<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public Customer getOneCustomer(long customerId) {
        return customerDAO.getOneCustomer(customerId);
    }

}
