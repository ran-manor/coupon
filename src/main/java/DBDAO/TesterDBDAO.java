package DBDAO;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TesterDBDAO {
    public static void main(String[] args) {
        CustomersDBDAO customersDBDAO = new CustomersDBDAO();
        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
        CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
        ArrayList<Coupon> coupons = new ArrayList<>();
        //  Customer customer1 = new Customer("ran", "manor", "Raran1@gmail.com", "122345", coupons);
        // customersDBDAO.addCustomer(customer1);

        //Customer newCustomer1 = customersDBDAO.getOneCustomer(1);
        //System.out.println(newCustomer1);
//       Customer testCustomer = customersDBDAO.getOneCustomer(1);
//        System.out.println(testCustomer);
//       testCustomer.setLastName("trilili");
//        System.out.println(testCustomer);
//       customersDBDAO.updateCustomer(testCustomer);
        //       System.out.println(customersDBDAO.isCustomerExists("Raran1@gmail.com", "122345"));

        Company company1 = new Company("McDonalds1", "mcmc@mcdonalds.com", "ronald1234", coupons);
         companiesDBDAO.addCompany(company1);
        // System.out.println(companiesDBDAO.isCompanyExists("mcmc@mcdonalds.com", "ronald1234"));
        // System.out.println(companiesDBDAO.getAllCompanies());
//        Company testCompany = companiesDBDAO.getOneCompany(1);
//        System.out.println("something " + testCompany);
//       testCompany.setEmail("mcmc@bigmac.yummy");
//        companiesDBDAO.updateCompany(testCompany);
////        System.out.println(companiesDBDAO.getAllCompanies());
//        System.out.println(companiesDBDAO.getOneCompany(1));


    }
}
