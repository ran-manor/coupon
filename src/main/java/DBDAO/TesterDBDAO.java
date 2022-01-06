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
//
//       Coupon c = new Coupon(1,2, 2,"lalala", "bibibib",
//               Date.valueOf(LocalDate.now()),Date.valueOf(LocalDate.now().plusDays(15)), 15 , 15.5, "bababa" );
////
////        //new CompaniesDBDAO().addCompany(new Company(1 , "nir" , "mail" , "1234" , new ArrayList<>()));
      ArrayList<Coupon> coupons = new ArrayList<>();
        Customer customer = new Customer("ran","manor","Raran@gmail.com","122345",coupons);
       // Customer customer1 = new Customer("ran1","manor1","Raran1@gmail.com","122345",coupons);
//
     CustomersDBDAO customersDBDAO = new CustomersDBDAO();
     customersDBDAO.addCustomer(customer);
//     Customer customer1 =customersDBDAO.getCustomerByEmail(customer.getEmail());
      //  System.out.println(customer1);
//     customersDBDAO.addCustomer(new Customer("ran","manor","Raran@gmail.com","122345",coupons));
        customersDBDAO.addCustomer(new Customer("ran1","manor1","Raran1@gmail.com","122345",coupons));

//        customersDBDAO.deleteCustomer(1);
//        customersDBDAO.deleteCustomer(2);
//        customersDBDAO.addCustomer(customer);
//        customersDBDAO.addCustomer(customer1);



//        customersDBDAO.deleteCustomer(5);
//       customersDBDAO.addCustomer(customer1);
//        System.out.println(customersDBDAO.getAllCustomers());
//        CouponsDBDAO dbCou = new CouponsDBDAO();
//        dbCou.addCoupon(c);
    }
}
