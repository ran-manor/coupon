package DBDAO;

import Beans.Category;
import Beans.Coupon;

import java.sql.Date;
import java.time.LocalDate;

public class TesterDBDAO {
    public static void main(String[] args) {

        Coupon c = new Coupon(1,200, Category.Vacation,"lalala", "bibibib",
                Date.valueOf(LocalDate.now()));
    }
}
