package Facade;

import Beans.Category;
import Beans.Coupon;
import Beans.Customer;
import exceptions.CouponSystemExceptions;
import exceptions.CustomerErrorMsg;

import java.util.ArrayList;
import java.util.Date;


//@AllArgsConstructor
//@NoArgsConstructor
//@Data
public class CustomerFacade extends ClientFacade {
    private long customerId;

    @Override
    public boolean login(String email, String password) {
        return customerDAO.isCustomerExists(email, password);
    }


    public void purchaseCoupon(Coupon coupon) {
        Customer customer = customerDAO.getOneCustomer((int) customerId);
        try {
            if (coupon.getAmount() == 0) {
                throw new CouponSystemExceptions(CustomerErrorMsg.AMOUNT_EQUAL_ZERO);
            } else if (coupon.getEndDate().after(new Date())) {
                throw new CouponSystemExceptions(CustomerErrorMsg.EXPIRED_DATE);
            } else if (couponDAO.getAllCouponPurchases().get(customerId).contains(coupon.getId())) {
                throw new CouponSystemExceptions(CustomerErrorMsg.COUPON_ALREADY_EXISTS);
            }
            //gets here if the purchase answers all conditions
            else {
                couponDAO.addCouponPurchase(customerId, coupon.getId());

                //Decrease coupon amount by 1
                Coupon c = couponDAO.getOneCoupon(coupon.getId());
                c.setAmount(c.getAmount() - 1);
                couponDAO.updateCoupon(c);
            }
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
    }

    public ArrayList<Coupon> getCustomersCoupons() {
        ArrayList<Long> ownedCouponsId = couponDAO.getAllCouponPurchases().get(customerId);

        ArrayList<Coupon> ownedCoupons = new ArrayList<>();
        for (Long id : ownedCouponsId) {
            ownedCoupons.add(couponDAO.getOneCoupon(id));
        }
        return ownedCoupons;


    }

    public ArrayList<Coupon> getCustomersCoupons(Category category) {
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
}
