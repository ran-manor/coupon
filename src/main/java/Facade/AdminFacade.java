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
import java.util.stream.Collectors;

public class AdminFacade extends ClientFacade {
    //TODO: make access only via loginmanager ( make uninstantiatable by tester )
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

    public void addCompany(Company company) {
        //TODO: CAN WE DO THIS INLINE?
        if (!loginCheck()) {return;}
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
        for (Coupon coupon : company.getCoupons()) {
            couponDAO.addCoupon(coupon);
        }
    }

    public void updateCompany(Company company) {
        if (!loginCheck()) {return;}

        try {
            if (company != null) {
                if (getOneCompany(company.getId()) != null) {
                    companiesDAO.updateCompany(company);
                    return;
                }
            }
            throw new CouponSystemExceptions(AdminErrorMsg.COMPANY_UPDATE_FAIL_NOT_EXIST);

//            if (company == null){
//                throw new CouponSystemExceptions(AdminErrorMsg.COMPANY_UPDATE_FAIL_NOT_EXIST);
//            }
//            if (getOneCompany(company.getId())!= null){
//                companiesDAO.updateCompany(company);
//            }
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
    }

    //
    public void deleteCompany(long companyId) {
        if (!loginCheck()) {return;}

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
        if (!loginCheck()) {return null;}

        return companiesDAO.getAllCompanies();
    }

    public Company getOneCompany(long companyId) {
        //TODO: maybe we should return new company instead of null
        if (!loginCheck()) {return null;}

        Company company = companiesDAO.getOneCompany(companyId);
        try {
            if (company == null) {
                throw new CouponSystemExceptions(AdminErrorMsg.COMPANY_NOT_EXISTS);
            }

            company.setCoupons(new ArrayList<>(couponDAO.getAllCoupons().stream()
                    .filter(coupon -> coupon.getCompanyId() == companyId)
                    .collect(Collectors.toList())));

        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());

        }
        return company;
    }

    public void addCustomer(Customer customer) {
        if (!loginCheck()) {return;}
        ArrayList<Customer> customers = customerDAO.getAllCustomers();
        try {
            for (Customer item : customers) {
                if (item.getEmail().equals(customer.getEmail())) {
                    throw new CouponSystemExceptions(AdminErrorMsg.CUSTOMER_EMAIL_EXIST);
                }
            }
            customerDAO.addCustomer(customer);
            System.out.println(DateUtils.getLocalDateTime() + "Customer " + customer.getFirstName() + " " + customer.getLastName() + " was added.");
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
    }

    //
    public void updateCustomer(Customer customer) {
        if (!loginCheck()) {return;}

        try {
            if (customer != null) {
                if (getOneCompany(customer.getId()) != null) {
                    customerDAO.updateCustomer(customer);
                    return;
                }
            }
            throw new CouponSystemExceptions(AdminErrorMsg.COMPANY_UPDATE_FAIL_NOT_EXIST);
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
    }

    //TODO: add coupon delete failed error
    public void deleteCustomer(long customerId) {
        if (!loginCheck()) {return;}

        couponDAO.deleteCouponPurchaseByCustomerID(customerId);
        customerDAO.deleteCustomer(customerId);
    }

    public ArrayList<Customer> getAllCustomers() {
        if (!loginCheck()) {return null;}

        return customerDAO.getAllCustomers();
    }

    public Customer getOneCustomer(long customerId) {
        if (!loginCheck()) {return null;}

        Customer customer = customerDAO.getOneCustomer(customerId);
        try {
            if (customer == null) {
                throw new CouponSystemExceptions(AdminErrorMsg.CUSTOMER_NOT_EXIST);
            }
            //ArrayList<Long> couponPurchaseIDs = couponDAO.getAllCouponPurchases().get(customer.getId());
            customer.setCoupons(couponDAO.getAllCouponPurchases()
                    .get(customer.getId()).stream()
                    .map(id-> couponDAO.getOneCoupon(id))
                    .collect(Collectors.toList()));

        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());

        }
        return customer;
    }


    private boolean loginCheck(){
        try{
            if (!isOK){
                throw new CouponSystemExceptions(LoginErrorMsg.CANT_ACCESS_FUNCTION_BAD_LOGIN);
            }
        }catch (CouponSystemExceptions err){
            System.out.println(err.getMessage());
        }
        return isOK;
    }

}
