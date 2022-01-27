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
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//@AllArgsConstructor
//@NoArgsConstructor
//@Data
public class CustomerFacade extends ClientFacade {
    private long customerId = -1;

    @Override
    public boolean login(String email, String password) throws CouponSystemExceptions {
        Customer customer = customerDAO.isCustomerExists(email, password);
        if (customer == null) {
            throw new CouponSystemExceptions(LoginErrorMsg.CUSTOMER_NO_MATCHING_INFO);
        }
        customerId = customer.getId();
        return true;
    }

    public void purchaseCoupon(Coupon coupon) throws CouponSystemExceptions {
        purchaseCoupon(coupon.getId());
    }

    public void purchaseCoupon(long couponId) throws CouponSystemExceptions {
        loginCheck();

        Coupon coupon = couponDAO.getOneCoupon(couponId);
        if (coupon == null) {
            throw new CouponSystemExceptions(CustomerErrorMsg.COUPON_PURCHASE_FAIL_COUPON_NULL);
        } else if (coupon.getAmount() == 0) {
            throw new CouponSystemExceptions(CustomerErrorMsg.AMOUNT_EQUAL_ZERO);
        } else if (coupon.getEndDate().before(new Date())) {
            throw new CouponSystemExceptions(CustomerErrorMsg.EXPIRED_DATE);
        }
        HashMap<Long, ArrayList<Long>> allCoupons = couponDAO.getAllCouponPurchases();
        if (allCoupons != null) {
            ArrayList<Long> couponsByCustomerId = allCoupons.get(customerId);
            if (couponsByCustomerId != null) {
                if (couponsByCustomerId.contains(coupon.getId())) {
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
    }

    public ArrayList<Coupon> getAllAvailableCoupons() throws CouponSystemExceptions {
        return couponDAO.getAllCoupons();
    }

    public ArrayList<Coupon> getCustomersCoupons() throws CouponSystemExceptions {

        return getCustomerCouponsFilter(null);
    }

    public ArrayList<Coupon> getCustomersCoupons(Category category) throws CouponSystemExceptions {

        return getCustomerCouponsFilter(coupon -> coupon.getCategory().equals(category));
    }

    public ArrayList<Coupon> getCustomersCoupons(double maxPrice) throws CouponSystemExceptions {

        return getCustomerCouponsFilter(coupon -> coupon.getPrice() <= maxPrice);
    }

    public Customer getCustomerDetails() throws CouponSystemExceptions {
        loginCheck();

        Customer customer = customerDAO.getOneCustomer(customerId);

        if (customer == null) {
            throw new CouponSystemExceptions(CustomerErrorMsg.CUSTOMER_NOT_EXIST);
        }

        ArrayList<Long> couponPurchaseIDs = couponDAO.getAllCouponPurchases().get(this.customerId);
        if (couponPurchaseIDs != null) {
            customer.setCoupons(couponPurchaseIDs.stream()
                    .map(id -> couponDAO.getOneCoupon(id))
                    .collect(Collectors.toList()));
        }
        return customer;
    }

    private void loginCheck() throws CouponSystemExceptions {
        if (customerId < 0) {
            throw new CouponSystemExceptions(LoginErrorMsg.CANT_ACCESS_FUNCTION_BAD_LOGIN);
        }
    }

    private ArrayList<Coupon> getCustomerCouponsFilter(Predicate<Coupon> predicate) throws CouponSystemExceptions {
        loginCheck();
        ArrayList<Long> ownedCouponsId = couponDAO.getAllCouponPurchases().get(customerId);
        return new ArrayList<>(ownedCouponsId.stream()
                .map(id -> couponDAO.getOneCoupon(id))
                .filter(predicate == null ? coupon -> true : predicate)
                .collect(Collectors.toList()));
    }

}

