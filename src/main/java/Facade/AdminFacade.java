package Facade;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import exceptions.AdminErrorMsg;
import exceptions.CouponSystemExceptions;
import exceptions.CustomerErrorMsg;
import exceptions.LoginErrorMsg;
import utils.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminFacade extends ClientFacade {
    boolean isOK = false;

    @Override
    public boolean login(String email, String password) throws CouponSystemExceptions {
        //admin true details
        final String adminEmail = "admin@admin.com";
        final String adminPassword = "admin";
//        return email.equals(adminEmail) && password.equals(adminPassword);

        if (!(email.equals(adminEmail) && password.equals(adminPassword))) {

            throw new CouponSystemExceptions(LoginErrorMsg.ADMIN_NO_MATCHING_INFO);
        }
        isOK = true;
        return true;
    }

    public void addCompany(Company company) throws CouponSystemExceptions {
        loginCheck();

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

    //
    public void deleteCompany(long companyId) throws CouponSystemExceptions {
        loginCheck();

        ArrayList<Coupon> coupons = couponDAO.getAllCoupons();
        for (Coupon item : coupons) {
            if (item.getCompanyId() == companyId) {
                couponDAO.deleteCouponPurchaseByCouponID(item.getId());
                couponDAO.deleteCoupon(item.getId());
            }
        }
        companiesDAO.deleteCompany(companyId);
    }

    //todo: add coupons setter
    public ArrayList<Company> getAllCompanies() throws CouponSystemExceptions {
        loginCheck();
        return companiesDAO.getAllCompanies();
    }

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

    public void deleteCustomer(long customerId) throws CouponSystemExceptions {
        loginCheck();

        couponDAO.deleteCouponPurchaseByCustomerID(customerId);
        customerDAO.deleteCustomer(customerId);
    }

    public ArrayList<Customer> getAllCustomers() throws CouponSystemExceptions {
        loginCheck();

        //TODO: make this a method?
        ArrayList<Customer> customers = customerDAO.getAllCustomers();
        for (Customer customer : customers) {
            ArrayList<Long> couponPurchaseIDs = couponDAO.getAllCouponPurchases().get(customer.getId());
            if (couponPurchaseIDs != null) {
                customer.setCoupons(couponPurchaseIDs.stream()
                        .map(id -> couponDAO.getOneCoupon(id))
                        .collect(Collectors.toList()));
            }
        }
        return customers;
    }

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

    private void loginCheck() throws CouponSystemExceptions {
        if (!isOK) {
            throw new CouponSystemExceptions(LoginErrorMsg.CANT_ACCESS_FUNCTION_BAD_LOGIN);
        }
    }

}

