package DBDAO;

import Beans.Coupon;
import DAO.CouponDAO;
import sql.DBUtils;

import java.util.ArrayList;

public class CouponsDBDAO implements CouponDAO {

    private final String ADD_COUPON = "INSERT INTO `CouponMania`.`coupons` " +
            "(`customer_id`,`category_id`, `title`, `description`, `start_date`,`end_date`,`amount`, `price`, `image`)   )" +
            "VALUES (?,?,?,?,?,?,?,?,?);";

    @Override
    public void addCoupon(Coupon coupon) {
        DBUtils.runQuery(ADD_COUPON);


    }

    @Override
    public void deleteCoupon(int couponID) {

    }

    @Override
    public ArrayList<Coupon> getAllCompanies() {
        return null;
    }

    @Override
    public Coupon getOneCoupon(int couponID) {
        return null;
    }

    @Override
    public void updateCoupon(Coupon coupon) {

    }

    @Override
    public void addCouponPurchase(int customerID, int couponID) {

    }

    @Override
    public void deleteCouponPurchase(int customerID, int couponID) {

    }
}
