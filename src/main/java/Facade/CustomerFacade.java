package Facade;

import Beans.Category;
import Beans.Coupon;
import Beans.Customer;
import exceptions.CouponSystemExceptions;
import exceptions.CustomerErrorMsg;
import exceptions.LoginErrorMsg;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


//@AllArgsConstructor
//@NoArgsConstructor
//@Data
public class CustomerFacade extends ClientFacade {
    private long customerId;

    @Override
    public boolean login(String email, String password) throws CouponSystemExceptions {
        Customer customer = customerDAO.isCustomerExists(email, password);
        if (customer == null){
            throw new CouponSystemExceptions(LoginErrorMsg.CUSTOMER_NO_MATCHING_INFO);
        }
        customerId = customer.getId();
        return true;
    }


    public void purchaseCoupon(Coupon passCoupon) {
        purchaseCoupon(passCoupon.getId());
    }
    public void purchaseCoupon(long couponId){
        if (!loginCheck()) return;

        Coupon coupon = couponDAO.getOneCoupon(couponId);
//        Customer customer = customerDAO.getOneCustomer((int) customerId);
        try {
            //todo: beutify
            if (coupon == null){
                throw new CouponSystemExceptions(CustomerErrorMsg.COUPON_PURCHASE_FAIL_COUPON_NULL);
            } else if (coupon.getAmount() == 0) {
                throw new CouponSystemExceptions(CustomerErrorMsg.AMOUNT_EQUAL_ZERO);
            } else if (coupon.getEndDate().before(new Date())) {
                throw new CouponSystemExceptions(CustomerErrorMsg.EXPIRED_DATE);
            }
            HashMap<Long , ArrayList<Long>> allCoupons = couponDAO.getAllCouponPurchases();
            if (allCoupons != null) {
                ArrayList<Long> couponsByCustomerId = allCoupons.get(customerId);
                if (couponsByCustomerId != null){
                    if (couponsByCustomerId.contains(coupon.getId())){
                        throw new CouponSystemExceptions(CustomerErrorMsg.COUPON_ALREADY_EXISTS);
                    }
                }
            }
            //gets here if the purchase answers all conditions
            couponDAO.addCouponPurchase(customerId, coupon.getId());

            //Decrease coupon amount by 1
            Coupon c = couponDAO.getOneCoupon(coupon.getId());
            c.setAmount(c.getAmount() - 1);
            couponDAO.updateCoupon(c);

        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
    }

    public ArrayList<Coupon> getCustomersCoupons() {
        if (!loginCheck()) return null;


        ArrayList<Long> ownedCouponsId = couponDAO.getAllCouponPurchases().get(customerId);

        ArrayList<Coupon> ownedCoupons = new ArrayList<>();
        for (Long id : ownedCouponsId) {
            ownedCoupons.add(couponDAO.getOneCoupon(id));
        }
        return ownedCoupons;


    }

    public ArrayList<Coupon> getCustomersCoupons(Category category) {
        if (!loginCheck()) return null;

        ArrayList<Long> ownedCouponsId = couponDAO.getAllCouponPurchases().get(customerId);

        ArrayList<Coupon> ownedCoupons = new ArrayList<>();
        for (Long id : ownedCouponsId) {
//            ownedCoupons.add(couponDAO.getOneCoupon(id));
            if (couponDAO.getOneCoupon(id).getCategory() == category) {
                ownedCoupons.add(couponDAO.getOneCoupon(id));
            }
        }
        return ownedCoupons;

    }
//
    public ArrayList<Coupon> getCustomersCoupons(double maxPrice) {
        if (!loginCheck()) return null;

        ArrayList<Long> ownedCouponsId = couponDAO.getAllCouponPurchases().get(customerId);

        ArrayList<Coupon> ownedCoupons = new ArrayList<>();
        for (Long id : ownedCouponsId) {
//            ownedCoupons.add(couponDAO.getOneCoupon(id));
            if (couponDAO.getOneCoupon(id).getPrice() < maxPrice) {
                ownedCoupons.add(couponDAO.getOneCoupon(id));
            }
        }
        return ownedCoupons;
    }

    public Customer getCustomerDetails() {
        if (!loginCheck()) return null;

        Customer customer = customerDAO.getOneCustomer(customerId);
        try {
            if (customer == null) {
                throw new CouponSystemExceptions(CustomerErrorMsg.CUSTOMER_NOT_EXIST);
            }
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());

        }
        return customer;
    }



    private boolean loginCheck(){
        boolean isOK = true;
        try {
            if (customerId < 1){
                    throw new CouponSystemExceptions(LoginErrorMsg.CANT_ACCESS_FUNCTION_BAD_LOGIN);
            }
        } catch (CouponSystemExceptions err){
            System.out.println(err.getMessage());
            isOK = false;
        }
        return isOK;
    }
}
