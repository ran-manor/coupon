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
        return false;
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

    public void deleteCompany(int companyId) {
        ArrayList<Coupon> coupons = couponDAO.getAllCoupons();
        for (Coupon item : coupons){
            if (item.getCompanyId()== companyId){
                couponDAO.deleteCouponPurchaseByCouponID(item.getId());
                couponDAO.deleteCoupon(item.getId());
            }
        }
        companiesDAO.deleteCompany(companyId);
    }

    public ArrayList<Company> getAllCompanies() {
        return null;
    }

    public Company getOneCompany(int companyId) {
        return null;
    }

    public void addCustomer(Customer customer) {
    }

    public void updateCustomer(Customer customer) {
    }

    public void deleteCustomer(int customerId) {
    }

    public ArrayList<Customer> getAllCutomers() {
        return null;
    }

    public Customer getOneCustomer(int customerId) {
        return null;
    }

}
