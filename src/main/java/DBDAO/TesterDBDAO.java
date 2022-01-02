package DBDAO;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class TesterDBDAO {
    public static void main(String[] args) {

//        Coupon c = new Coupon(1,200, Category.Vacation,"lalala", "bibibib",
//                Date.valueOf(LocalDate.now()));

        new CompaniesDBDAO().addCompany(new Company(1 , "nir" , "mail" , "1234" , new ArrayList<>()));
    }
}
