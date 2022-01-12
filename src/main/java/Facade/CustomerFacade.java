package Facade;

import Beans.Category;
import Beans.Coupon;
import Beans.Customer;
import exceptions.CouponErrorMsg;
import exceptions.CouponSystemExceptions;
import exceptions.CustomerErrorMsg;

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
    public void purchaseCoupon(Coupon coupon)throws Exception{
        Customer customer = customerDAO.getOneCustomer((int) customerId);
        if(coupon.getAmount()==0 ){
            throw new CouponSystemExceptions(CouponErrorMsg.AMOUNT_EQUAL_ZERO);
        }
        else if(coupon.getEndDate().after(new Date())){
            //todo: throw exp
        }
        else if (couponDAO.getAllCouponPurchases().get(customerId).contains(coupon.getId())){
            //todo: throw exp
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
