package DBDAO;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class TesterDBDAO {
    public static void main(String[] args) {

       Coupon c = new Coupon(1,2, 2,"lalala", "bibibib",
               Date.valueOf(LocalDate.now()),Date.valueOf(LocalDate.now().plusDays(15)), 15 , 15.5, "bababa" );

        //new CompaniesDBDAO().addCompany(new Company(1 , "nir" , "mail" , "1234" , new ArrayList<>()));

        CouponsDBDAO dbCou = new CouponsDBDAO();
        dbCou.addCoupon(c);
    }
}
