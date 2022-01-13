package Facade;

import Beans.Category;
import Beans.Coupon;
import Beans.Customer;
import exceptions.AdminErrorMsg;
import exceptions.CouponSystemExceptions;

import java.util.ArrayList;
import java.util.Date;


//@AllArgsConstructor
//@NoArgsConstructor
//@Data
public class CustomerFacade extends ClientFacade{
    //todo: removie with login
    private long customerId ;

    //todo: implement
    @Override
    public boolean login(String email, String password) {
        return  customerDAO.isCustomerExists(email,password);
    }


    //TODO: EXCEPTIONS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void purchaseCoupon(Coupon coupon)throws CouponSystemExceptions{
        Customer customer = customerDAO.getOneCustomer((int) customerId);
        if(coupon.getAmount()==0 ){
            throw new CouponSystemExceptions(AdminErrorMsg.AMOUNT_EQUAL_ZERO);
        }
        else if(coupon.getEndDate().after(new Date())){
            throw new CouponSystemExceptions(AdminErrorMsg.EXPIRED_DATE);
        }
        else if (couponDAO.getAllCouponPurchases().get(customerId).contains(coupon.getId())){
            throw new CouponSystemExceptions(AdminErrorMsg.COUPON_ALREADY_EXISTS);
        }
        //gets here if the purchase answers all conditions
        else {
            couponDAO.addCouponPurchase(customerId , coupon.getId());

            //Decrease coupon amount by 1
            Coupon c = couponDAO.getOneCoupon(coupon.getId());
            c.setAmount(c.getAmount()-1);
            couponDAO.updateCoupon(c);
        }



    }
    public ArrayList<Coupon> getCustomersCoupons(){
        ArrayList<Long> ownedCouponsId = couponDAO.getAllCouponPurchases().get(customerId);

        ArrayList<Coupon> ownedCoupons = new ArrayList<>();
        for (Long id : ownedCouponsId) {
            ownedCoupons.add(couponDAO.getOneCoupon(id));
        }
        return ownedCoupons;


    }
    public ArrayList<Coupon> getCustomersCoupons(Category category){
        ArrayList<Long> ownedCouponsId = couponDAO.getAllCouponPurchases().get(customerId);

        ArrayList<Coupon> ownedCoupons = new ArrayList<>();
        for (Long id : ownedCouponsId) {
//            ownedCoupons.add(couponDAO.getOneCoupon(id));
            if (couponDAO.getOneCoupon(id).getCategory() == category){
                ownedCoupons.add(couponDAO.getOneCoupon(id));
            }
        }
        return ownedCoupons;

    }
    public ArrayList<Coupon> getCustomersCoupons(double maxPrice){
        ArrayList<Long> ownedCouponsId = couponDAO.getAllCouponPurchases().get(customerId);

        ArrayList<Coupon> ownedCoupons = new ArrayList<>();
        for (Long id : ownedCouponsId) {
//            ownedCoupons.add(couponDAO.getOneCoupon(id));
            if (couponDAO.getOneCoupon(id).getPrice() < maxPrice){
                ownedCoupons.add(couponDAO.getOneCoupon(id));
            }
        }
        return ownedCoupons;
    }
    public Customer getCustomerDetails(){
        return customerDAO.getOneCustomer(customerId);
    }
}
