package DBDAO;

import Beans.Coupon;
import DAO.CouponDAO;
import sql.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CouponsDBDAO implements CouponDAO {

    private final String ADD_COUPON = "INSERT INTO `CouponMania`.`coupons` " +
            "(`company_id`,`category_id`, `title`, `description`, `start_date`,`end_date`,`amount`, `price`, `image`)" +
            "VALUES (?,?,?,?,?,?,?,?,?);";

    private final String DELETE_COUPON_BY_ID = "DELETE FROM `CouponMania`.`coupons` WHERE id=?";

    private final String GET_ALL_COUPONS = "SELECT * FROM `CouponMania`.`coupons`";

    private final String GET_ONE_COUPON = "SELECT * FROM `CouponMania`.`coupons` WHERE id=?";

    @Override
    public void addCoupon(Coupon coupon) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getCompanyId());
        params.put(2, coupon.getCategoryId());
        params.put(3, coupon.getTitle());
        params.put(4, coupon.getDescription());
        params.put(5, coupon.getStartDate());
        params.put(6, coupon.getEndDate());
        params.put(7, coupon.getAmount());
        params.put(8, coupon.getPrice());
        params.put(9, coupon.getImage());
        DBUtils.runUpdateQuery(ADD_COUPON, params);
    }

    @Override
    public void deleteCoupon(int couponID) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponID);
        DBUtils.runUpdateQuery(DELETE_COUPON_BY_ID, params);
    }

    @Override
    public ArrayList<Coupon> getAllCoupons() {
        ArrayList<Coupon> coupons = new ArrayList<>();
        ResultSet res = DBUtils.getResultSetQuery(GET_ALL_COUPONS);
        while (true) {
            try {
                if (!res.next()) break;
                Coupon coupon = Coupon.builder()
                        .id(res.getInt("id"))
                        .categoryId(res.getInt("category_id"))
                        .companyId(res.getInt("company_id"))
                        .description(res.getString("description"))
                        .title(res.getString("title"))
                        .startDate(res.getDate("start_date"))
                        .endDate(res.getDate("end_date"))
                        .amount(res.getInt("amount"))
                        .price(res.getInt("price"))
                        .image(res.getString("image"))
                        .build();
                coupons.add(coupon);
            } catch (SQLException err) {
                System.out.println(err.getMessage());
            }
        }
        return coupons;
    }


    @Override
    public Coupon getOneCoupon(int couponID) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponID);
        ResultSet res = DBUtils.getResultSetQuery(GET_ONE_COUPON, params);
        try {
            if (res != null) {
                Coupon coupon = Coupon.builder()
                        .id(res.getInt("id"))
                        .categoryId(res.getInt("category_id"))
                        .companyId(res.getInt("company_id"))
                        .description(res.getString("description"))
                        .title(res.getString("title"))
                        .startDate(res.getDate("start_date"))
                        .endDate(res.getDate("end_date"))
                        .amount(res.getInt("amount"))
                        .price(res.getInt("price"))
                        .image(res.getString("image"))
                        .build();
                return coupon;
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
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
