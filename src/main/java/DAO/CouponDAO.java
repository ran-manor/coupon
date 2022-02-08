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
    /**
     *  adds a coupon to the Database.
     * @param coupon the coupon to add.
     * @return true if adding was successful, false if not.
     */
    boolean addCoupon(Coupon coupon);
    /**
     * deletes coupon from the database by couponID.
     * @param couponID the coupon's id.
     */
    void deleteCoupon(long couponID);
    /**
     * get an arraylist of all coupons from the DateBase.
     * @return an arraylist holding all the companies.
     */
    ArrayList<Coupon> getAllCoupons();
    /**
     * gets one coupon by coupon id.
     * @param couponID the couponID to search for.
     * @return the coupon that was found. returns null if coupon wasn't found.
     */
    Coupon getOneCoupon(long couponID);
    /**
     * updates a coupon details in the database.
     * @param coupon the coupon with updated details.
     */
    void updateCoupon(Coupon coupon);
    /**
     * adds a new coupon purchase with the corresponding customerID to 'customer_coupons' table.
     * @param customerID the customerID of the purchase.
     * @param couponID the couponID of the purchased coupon.
     */
    void addCouponPurchase(long customerID,long couponID);
    /**
     * deletes a coupon purchase by couponID and customerID from 'customer_coupons' table.
     * by so, deleting a specific coupon purchase by a specific customer.
     * @param customerID the customerID of the purchase.
     * @param couponID the couponID of the purchased coupon.
     */
    void deleteCouponPurchase(long customerID,long couponID);
    /**
     * deletes a coupon purchase by couponID from 'customer_coupons' table.
     * by so, deleting all it's purchases.
     * @param couponID the couponID of the purchased coupon.
     */
    void deleteCouponPurchaseByCouponID( long couponID);
    /**
     * deletes a customer by customerID from 'customer_coupons' table.
     * by so, deleting all he's purchases.
     * @param customerID the customerID of the customer to delete.
     */
    void deleteCouponPurchaseByCustomerID( long customerID);
    /**
     * creates a hashmap representing all coupon purchases:
     * the key is the customer id, the value is an arraylist of all the couponId's of coupons he owns.
     * @return hashMap of all the coupon purchases by customers.
     */
    HashMap<Long , ArrayList<Long>> getAllCouponPurchases();
}
