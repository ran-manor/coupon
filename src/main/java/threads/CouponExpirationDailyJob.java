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

    /**
     * sets the thread to not quit and the coupons dbdao.
     */
    public CouponExpirationDailyJob() {
        this.couponDAO = new CouponsDBDAO();
        this.quit = false;
    }

    /**
     * a daily job that runs every 24 hours, checks if a coupons end date has passed, and deletes it if so.
     */
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

    /**
     * stops the thread from running.
     */
    public void stop() {
        this.quit = true;
    }
}
