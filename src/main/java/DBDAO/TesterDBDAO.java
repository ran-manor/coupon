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
        Customer customer1 = new Customer("ran", "manor", "Raran1@gmail.com", "122345", coupons);
       // customersDBDAO.addCustomer(customer1);

        //Customer newCustomer1 = customersDBDAO.getOneCustomer(1);
        //System.out.println(newCustomer1);
       Customer testCustomer = customersDBDAO.getOneCustomer(1);
        System.out.println(testCustomer);
       testCustomer.setLastName("abu jamal");
        System.out.println(testCustomer);
       customersDBDAO.updateCustomer(testCustomer);
    }
}
