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
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//@AllArgsConstructor
//@NoArgsConstructor
//@Data
public class CustomerFacade extends ClientFacade {
    private long customerId = -1;

    /**
     * searches for customer with corresponding email and password and sets the customerid in the facade to the id from the DataBase.
     * @param email client email.
     * @param password client password.
     * @return was the login successful.
     * @throws CouponSystemExceptions login error.
     */
    @Override
    public boolean login(String email, String password) throws CouponSystemExceptions {
        Customer customer = customerDAO.isCustomerExists(email, password);
        if (customer == null) {
            throw new CouponSystemExceptions(LoginErrorMsg.CUSTOMER_NO_MATCHING_INFO);
        }
        customerId = customer.getId();
        return true;
    }

    /**
     * adds a coupon from the database to purchases table if the coupon passes conditions, and decreases coupon amount by 1.
     * @param coupon the coupon to purchase.
     * @throws CouponSystemExceptions no login exception.
     */
    public void purchaseCoupon(Coupon coupon) throws CouponSystemExceptions {
        purchaseCoupon(coupon.getId());
    }

    /**
     * adds a coupon by couponID from the database to purchases table if the coupon passes conditions, and decreases coupon amount by 1.
     * @param couponId the id of coupon to purchase.
     * @throws CouponSystemExceptions no login exception.
     */
    public void purchaseCoupon(long couponId) throws CouponSystemExceptions {
        loginCheck();

        Coupon coupon = couponDAO.getOneCoupon(couponId);
        if (coupon == null) {
            throw new CouponSystemExceptions(CustomerErrorMsg.COUPON_PURCHASE_FAIL_COUPON_NULL);
        }
        if (coupon.getAmount() <= 0) {
            throw new CouponSystemExceptions(CustomerErrorMsg.AMOUNT_EQUAL_ZERO);
        }
        if (coupon.getEndDate().before(new Date())) {
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

    /**
     * gets all the available coupons from the database.
     * @return arraylist containing all the available coupons.
     * @throws CouponSystemExceptions no login exception.
     */
    public ArrayList<Coupon> getAllAvailableCoupons() throws CouponSystemExceptions {
        loginCheck();

        return new ArrayList<>(couponDAO.getAllCoupons().stream()
                .filter(coupon -> coupon.getAmount() >0).collect(Collectors.toList()));
    }

    /**
     * gets all coupons the facade's customer owns.
     * @return an arraylist containing all coupons the facade's customer owns.
     * @throws CouponSystemExceptions no login error.
     */
    public ArrayList<Coupon> getCustomersCoupons() throws CouponSystemExceptions {
        return getCustomerCouponsFilter(null);
    }

    /**
     * gets all coupons the facade's customer owns filtered by category.
     * @param category the category to filter by.
     * @return an arraylist containing all coupons the facade's customer owns filtered by category.
     * @throws CouponSystemExceptions no login error.
     */
    public ArrayList<Coupon> getCustomersCoupons(Category category) throws CouponSystemExceptions {

        return getCustomerCouponsFilter(coupon -> coupon.getCategory().equals(category));
    }

    /**
     * gets all coupons the facade's customer owns under a max price.
     * @param maxPrice maxprice to filter by.
     * @return an arraylist containing all coupons the facade's customer owns under a max price.
     * @throws CouponSystemExceptions no login error.
     */
    public ArrayList<Coupon> getCustomersCoupons(double maxPrice) throws CouponSystemExceptions {

        return getCustomerCouponsFilter(coupon -> coupon.getPrice() <= maxPrice);
    }

    /**
     * returns all the facade's customer coupons, filters by a given predicate (the predicate can be null.)
      * @param predicate the filter to apply.
     * @return an arraylist of coupons filtered by the predicate if not null.
     * @throws CouponSystemExceptions login error.
     */
    private ArrayList<Coupon> getCustomerCouponsFilter(Predicate<Coupon> predicate) throws CouponSystemExceptions {
        loginCheck();
        Function<Stream<Coupon> , List<Coupon>> filterFunc = couponStream ->
                predicate == null ? couponStream.collect(Collectors.toList()) :
                couponStream.filter(predicate).collect(Collectors.toList());

        return new ArrayList<>(filterFunc.apply(
                couponDAO.getAllCouponPurchases()
                        .get(this.customerId)
                        .stream()
                        .map(id -> couponDAO.getOneCoupon(id))
        ));
    }

    /**
     * gets the facade's customer Details.
     * @return this facade's customer.
     * @throws CouponSystemExceptions no login exception.
     */
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

    /**
     * this method locks all functions for use if they have no login. checks by id.
     * @throws CouponSystemExceptions no login exception.
     */
    private void loginCheck() throws CouponSystemExceptions {
        if (customerId < 0) {
            throw new CouponSystemExceptions(LoginErrorMsg.CANT_ACCESS_FUNCTION_BAD_LOGIN);
        }
    }

}

