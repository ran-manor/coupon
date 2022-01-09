package DAO;

import Beans.Company;
import Beans.Coupon;

import java.util.ArrayList;

public interface CouponDAO {
    boolean addCoupon(Coupon coupon);
    void deleteCoupon(long couponID);
    ArrayList<Coupon> getAllCoupons();
    Coupon getOneCoupon(long couponID);
    void updateCoupon(Coupon coupon);
    void addCouponPurchase(long customerID,long couponID);
    void deleteCouponPurchase(long customerID,long couponID);
}
