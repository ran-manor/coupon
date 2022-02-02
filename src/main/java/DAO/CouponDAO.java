package DAO;

import Beans.Company;
import Beans.Coupon;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * interface that holds the methods regarding coupons in the database that will be implemented in the DBDao
 * (including full CRUD functionality)
 */
public interface CouponDAO {
    boolean addCoupon(Coupon coupon);
    void deleteCoupon(long couponID);
    ArrayList<Coupon> getAllCoupons();
    Coupon getOneCoupon(long couponID);
    void updateCoupon(Coupon coupon);
    void addCouponPurchase(long customerID,long couponID);
    void deleteCouponPurchase(long customerID,long couponID);
    void deleteCouponPurchaseByCouponID( long couponID);
    void deleteCouponPurchaseByCustomerID( long couponID);
    HashMap<Long , ArrayList<Long>> getAllCouponPurchases();
}
