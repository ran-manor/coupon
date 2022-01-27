package threads;

import Beans.Coupon;
import DAO.CouponDAO;
import DBDAO.CouponsDBDAO;
import utils.DateUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CouponExpirationDailyJob implements Runnable {

    private final CouponDAO couponDAO;
    private boolean quit;

    public CouponExpirationDailyJob() {
        this.couponDAO = new CouponsDBDAO();
        this.quit = false;
    }


    @Override
    public void run() {

        while (!quit){
                System.out.println("==================== DailyJob Thread is running ====================");
                couponDAO.getAllCoupons().stream()
                        .filter(coupon -> coupon.getEndDate().before(DateUtils.localDateToSqlDate(LocalDate.now())))
                        .forEach(coupon -> couponDAO.deleteCoupon(coupon.getId()));

            try {
                Thread.sleep(1000*60*60*24);
            } catch (InterruptedException err) {
                System.out.println(err.getMessage());
                stop();
            }
        }
    }

    public void stop() {
        this.quit = true;
    }
}
