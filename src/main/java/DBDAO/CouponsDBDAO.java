package DBDAO;

import Beans.Coupon;
import DAO.CouponDAO;
import sql.DBUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CouponsDBDAO implements CouponDAO {

    private final String ADD_COUPON = "INSERT INTO `CouponMania`.`coupons` " +
            "(`company_id`,`category_id`, `title`, `description`, `start_date`,`end_date`,`amount`, `price`, `image`)   )" +
            "VALUES (?,?,?,?,?,?,?,?,?);";

    @Override
    public void addCoupon(Coupon coupon) {
        Map<Integer,Object> params = new HashMap<>();
        params.put(1,coupon.getCompanyId());
        params.put(2,coupon.getCategory());
        params.put(3,coupon.getTitle());
        params.put(4,coupon.getDescription());
        params.put(5,coupon.getStartDate());
        params.put(6,coupon.getEndDate());
        params.put(7,coupon.getAmount());
        params.put(8,coupon.getPrice());
        params.put(9,coupon.getImage());
        DBUtils.runQuery(ADD_COUPON,params);


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
