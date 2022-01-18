package Facade;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import exceptions.AdminErrorMsg;
import exceptions.CouponSystemExceptions;
import exceptions.CustomerErrorMsg;
import exceptions.LoginErrorMsg;

import java.util.ArrayList;

public class AdminFacade extends ClientFacade {
    //TODO: make access only via loginmanager ( make uninstantiatable by tester )
    public AdminFacade()  {

    }

    @Override
    public boolean login(String email, String password) throws CouponSystemExceptions {
        //admin true details
        final String adminEmail = "admin@admin.com";
        final String adminPassword = "admin";
//        return email.equals(adminEmail) && password.equals(adminPassword);

        if (!(email.equals(adminEmail) && password.equals(adminPassword))){

            throw new CouponSystemExceptions(LoginErrorMsg.ADMIN_NO_MATCHING_INFO);
        }
        return true;
    }

    public void addCompany(Company company) {
        ArrayList<Company> companies = companiesDAO.getAllCompanies();
        try {
            for (Company item : companies) {
                if (item.getName().equals(company.getName())) {
                    throw new CouponSystemExceptions(AdminErrorMsg.COMPANY_NAME_EXIST);
                }
                if (item.getEmail().equals(company.getEmail())) {
                    throw new CouponSystemExceptions(AdminErrorMsg.COMPANY_EMAIL_EXIST);
                }
            }
            companiesDAO.addCompany(company);
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
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
        Company company = companiesDAO.getOneCompany(companyId);
        try {
            if (company == null) {
                throw new CouponSystemExceptions(AdminErrorMsg.COMPANY_NOT_EXIST);
            }
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());

        }
        return company;
    }

    public void addCustomer(Customer customer) {
        ArrayList<Customer> customers = customerDAO.getAllCustomers();
        try {
            for (Customer item : customers) {
                if (item.getEmail().equals(customer.getEmail())) {
                    throw new CouponSystemExceptions(AdminErrorMsg.CUSTOMER_EMAIL_EXIST);
                }
            }
            customerDAO.addCustomer(customer);
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
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
        Customer customer = customerDAO.getOneCustomer(customerId);
        try {
            if (customer == null) {
                throw new CouponSystemExceptions(AdminErrorMsg.CUSTOMER_NOT_EXIST);
            }
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());

        }
        return customer;
    }

}
