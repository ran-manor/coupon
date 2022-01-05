package DAO;

import Beans.Company;
import Beans.Coupon;

import java.util.ArrayList;

public interface CouponDAO {
    boolean addCoupon(Coupon coupon);
    void deleteCoupon(int couponID);
    ArrayList<Coupon> getAllCoupons();
    Coupon getOneCoupon(int couponID);
    void updateCoupon(Coupon coupon);
    void addCouponPurchase(int customerID,int couponID);
    void deleteCouponPurchase(int customerID,int couponID);
}
