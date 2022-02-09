package Facade;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import exceptions.AdminErrorMsg;
import exceptions.CouponSystemExceptions;
import exceptions.LoginErrorMsg;
import utils.ArtUtils;
import utils.DateUtils;

import java.util.ArrayList;

import java.util.stream.Collectors;

public class AdminFacade extends ClientFacade {
    boolean isOK = false;

    /**
     * checks if login credentials matches admin login credentials.
     * @param email client email.
     * @param password client password.
     * @return true if the login was successful.
     * @throws CouponSystemExceptions login error if the login credentials doesn't match.
     */
    @Override
    public boolean login(String email, String password) throws CouponSystemExceptions {
        //admin true details
        final String adminEmail = "admin@admin.com";
        final String adminPassword = "admin";
        if (!(email.equals(adminEmail) && password.equals(adminPassword))) {
            throw new CouponSystemExceptions(LoginErrorMsg.ADMIN_NO_MATCHING_INFO);
        }
        isOK = true;
        return true;
    }

    /**
     * adds a company to the database if the new company passes conditions.
     * @param company the company to add.
     * @throws CouponSystemExceptions if the company doesn't pass the conditions.
     */
    public void addCompany(Company company) throws CouponSystemExceptions {
        loginCheck();

        if (company == null){throw new CouponSystemExceptions("Cant add null company.");}

        ArrayList<Company> companies = companiesDAO.getAllCompanies();
        for (Company item : companies) {
            if (item.getName().equals(company.getName())) {
                throw new CouponSystemExceptions(AdminErrorMsg.COMPANY_NAME_EXIST);
            }
            if (item.getEmail().equals(company.getEmail())) {
                throw new CouponSystemExceptions(AdminErrorMsg.COMPANY_EMAIL_EXIST);
            }
        }
        companiesDAO.addCompany(company);
        for (Coupon coupon : company.getCoupons()) {
            couponDAO.addCoupon(coupon);
        }
    }

    /**
     * update a company in the database if the given company's id exists in the database.
     * @param company the company to update with updated info.
     * @throws CouponSystemExceptions if the company doesn't exist.
     */
    public void updateCompany(Company company) throws CouponSystemExceptions {
        loginCheck();

        if (company != null) {
            if (getOneCompany(company.getId()) != null) {
                companiesDAO.updateCompany(company);
                return;
            }
        }
        throw new CouponSystemExceptions(AdminErrorMsg.COMPANY_UPDATE_FAIL_NOT_EXIST);
    }

    /**
     * deletes a company from the DataBase by companyID.
     * @param companyId the id of the company to delete.
     * @throws CouponSystemExceptions if company doesn't exist.
     */
    public void deleteCompany(long companyId) throws CouponSystemExceptions {
        loginCheck();

        Company company = companiesDAO.getOneCompany(companyId);

        if (company == null) {
            throw new CouponSystemExceptions(AdminErrorMsg.COMPANY_NOT_EXISTS);
        }

        ArrayList<Coupon> coupons = couponDAO.getAllCoupons();
        for (Coupon item : coupons) {
            if (item.getCompanyId() == companyId) {
                couponDAO.deleteCouponPurchaseByCouponID(item.getId());
                couponDAO.deleteCoupon(item.getId());
            }
        }
        companiesDAO.deleteCompany(companyId);
        System.out.println(ArtUtils.ANSI_RED+"Company number "+companyId+" was successfully deleted"+ArtUtils.ANSI_RESET);
    }

    /**
     * gets all companies from the database.
     * @return arraylist of all companies.
     * @throws CouponSystemExceptions no login exception.
     */
    public ArrayList<Company> getAllCompanies() throws CouponSystemExceptions {
        loginCheck();

        return new ArrayList<>(companiesDAO.getAllCompanies().stream()
                .map(company -> company.setCoupons(new ArrayList<>(couponDAO.getAllCoupons()
                .stream()
                        .filter(coupon -> coupon.getCompanyId() == company.getId())
                        .collect(Collectors.toList()))))
                        .collect(Collectors.toList()));
    }

    /**
     * gets one company from the database by given companyID.
     * @param companyId the id of the company to get.
     * @return the company that was found.
     * @throws CouponSystemExceptions if the company doesn't exist.
     */
    public Company getOneCompany(long companyId) throws CouponSystemExceptions {
        loginCheck();

        Company company = companiesDAO.getOneCompany(companyId);

        if (company == null) {
            throw new CouponSystemExceptions(AdminErrorMsg.COMPANY_NOT_EXISTS);
        }
        company.setCoupons(new ArrayList<>(couponDAO.getAllCoupons().stream()
                .filter(coupon -> coupon.getCompanyId() == companyId)
                .collect(Collectors.toList())));
        return company;
    }
//todo: format indent in all file
    /**
     * adds a customer to the database if passes conditions.
     * @param customer the customer to add.
     * @throws CouponSystemExceptions if the new customer doesn't pass conditions.
     */
    public void addCustomer(Customer customer) throws CouponSystemExceptions {
        loginCheck();

        ArrayList<Customer> customers = customerDAO.getAllCustomers();
        for (Customer item : customers) {
            if (item.getEmail().equals(customer.getEmail())) {
                throw new CouponSystemExceptions(AdminErrorMsg.CUSTOMER_EMAIL_EXIST);
            }
        }
        customerDAO.addCustomer(customer);
        System.out.println(DateUtils.getLocalDateTime() + "Customer " + customer.getFirstName() + " " + customer.getLastName() + " was added.");
    }

    /**
     * updates a customer in the database.
     * @param customer the customer to update with updated details.
     * @throws CouponSystemExceptions if the customer doesn't exist.
     */
    public void updateCustomer(Customer customer) throws CouponSystemExceptions {
        loginCheck();

        if (customer != null) {
            if (getOneCompany(customer.getId()) != null) {
                customerDAO.updateCustomer(customer);
                return;
            }
        }
        throw new CouponSystemExceptions(AdminErrorMsg.COMPANY_UPDATE_FAIL_NOT_EXIST);

    }

    /**
     * deletes a customer from the database by a given customerID.
     * @param customerId the id of the customer to delete.
     * @throws CouponSystemExceptions if the customer doesn't exist.
     */
    public void deleteCustomer(long customerId) throws CouponSystemExceptions {
        loginCheck();

        Customer customer = customerDAO.getOneCustomer(customerId);
        if (customer == null) {
            throw new CouponSystemExceptions(AdminErrorMsg.CUSTOMER_NOT_EXIST);
        }

        couponDAO.deleteCouponPurchaseByCustomerID(customerId);
        customerDAO.deleteCustomer(customerId);
        System.out.println(ArtUtils.ANSI_RED+"Customer number "+customerId+" was successfully deleted"+ArtUtils.ANSI_RESET);
    }

    /**
     * gets all customers from the database.
     * @return arraylist of all customers.
     * @throws CouponSystemExceptions no login exception.
     */
    public ArrayList<Customer> getAllCustomers() throws CouponSystemExceptions {
        loginCheck();

        ArrayList<Customer> customers = customerDAO.getAllCustomers();
        for (Customer customer : customers) {
            ArrayList<Long> couponPurchaseIDs = couponDAO.getAllCouponPurchases().get(customer.getId());
            if (couponPurchaseIDs != null) {
                customer.setCoupons(couponPurchaseIDs.stream()
                        .map(id -> couponDAO.getOneCoupon(id))
                        .collect(Collectors.toList()));
            }
            else {
                customer.setCoupons(new ArrayList<>());
            }
        }
        return customers;
    }

    /**
     * get's one customer by id from the database.
     * @param customerId customer id to search for.
     * @return the customer that was found.
     * @throws CouponSystemExceptions customer doesn't exist.
     */
    public Customer getOneCustomer(long customerId) throws CouponSystemExceptions {
        loginCheck();

        Customer customer = customerDAO.getOneCustomer(customerId);
        if (customer == null) {
            throw new CouponSystemExceptions(AdminErrorMsg.CUSTOMER_NOT_EXIST);
        }
        ArrayList<Long> couponPurchaseIDs = couponDAO.getAllCouponPurchases().get(customerId);
        if (couponPurchaseIDs != null) {
            customer.setCoupons(couponPurchaseIDs.stream()
                    .map(id -> couponDAO.getOneCoupon(id))
                    .collect(Collectors.toList()));
        }
        return customer;
    }

    /**
     * this method locks all functions for use if they have no login.
     * @throws CouponSystemExceptions no login exception.
     */
    private void loginCheck() throws CouponSystemExceptions {
        if (!isOK) {
            throw new CouponSystemExceptions(LoginErrorMsg.CANT_ACCESS_FUNCTION_BAD_LOGIN);
        }
    }

}

