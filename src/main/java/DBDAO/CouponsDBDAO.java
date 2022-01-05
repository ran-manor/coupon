package DBDAO;

import Beans.Category;
import Beans.Coupon;
import DAO.CouponDAO;
import sql.DBUtils;
import sql.db;

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

    private final String UPDATE_COUPON = "UPDATE `CouponMania`.`coupons`" +
            "SET title=?, description=?, start_date=?, end_date=?, amount=?, price=?, image=?" +
            "WHERE id=?";

    private final String ADD_COUPON_PURCHASE = "INSERT INTO `CouponMania`.`customers_coupons` " +
            "(`customer_id`,`coupon_id`) VALUES (?,?);";

    private final String DELETE_COUPON_PURCHASE = "DELETE FROM `CouponMania`.`customers_coupons` " +
            "WHERE customer_id=? AND coupon_id=?";

    @Override
    public boolean addCoupon(Coupon coupon) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getCompanyId());
        params.put(2, coupon.getCategory().value);
        params.put(3, coupon.getTitle());
        params.put(4, coupon.getDescription());
        params.put(5, coupon.getStartDate());
        params.put(6, coupon.getEndDate());
        params.put(7, coupon.getAmount());
        params.put(8, coupon.getPrice());
        params.put(9, coupon.getImage());
       return DBUtils.runQueryGetId(coupon,ADD_COUPON, params);
    }

    @Override
    public void deleteCoupon(int couponID) {

        DBUtils.runQuery(DELETE_COUPON_BY_ID, 1, couponID);
    }

    @Override
    public ArrayList<Coupon> getAllCoupons() {
        ArrayList<Coupon> coupons = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = DBUtils.runQueryForResult(GET_ALL_COUPONS);
            while (resultSet.next()) {
                Coupon coupon = Coupon.builder()
                        .id(resultSet.getInt("id"))
                        .category(Category.getCategoryById(resultSet.getInt("category_id")))
                        .companyId(resultSet.getInt("company_id"))
                        .description(resultSet.getString("description"))
                        .title(resultSet.getString("title"))
                        .startDate(resultSet.getDate("start_date"))
                        .endDate(resultSet.getDate("end_date"))
                        .amount(resultSet.getInt("amount"))
                        .price(resultSet.getInt("price"))
                        .image(resultSet.getString("image"))
                        .build();
                coupons.add(coupon);
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return coupons;
    }


    @Override
    public Coupon getOneCoupon(int couponID) {
        ResultSet res;
        try {
            res = DBUtils.runQueryForResultSet(GET_ONE_COUPON, 1, couponID);
            if (res != null) {
                Coupon coupon = Coupon.builder()
                        .id(res.getInt("id"))
                        .category(Category.getCategoryById(res.getInt("category_id")))
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
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getCompanyId());
        params.put(2, coupon.getCategory().value);
        params.put(3, coupon.getTitle());
        params.put(4, coupon.getDescription());
        params.put(5, coupon.getStartDate());
        params.put(6, coupon.getEndDate());
        params.put(7, coupon.getAmount());
        params.put(8, coupon.getPrice());
        params.put(9, coupon.getImage());
        DBUtils.runQuery(UPDATE_COUPON, params);

    }

    @Override
    public void addCouponPurchase(int customerID, int couponID) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        params.put(2, couponID);
        DBUtils.runQueryForResultSet(ADD_COUPON_PURCHASE, params);

    }

    @Override
    public void deleteCouponPurchase(int customerID, int couponID) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        params.put(2, couponID);
        DBUtils.runQueryForResultSet(DELETE_COUPON_PURCHASE, params);
    }
}
