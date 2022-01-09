package Facade;

import Beans.Category;
import Beans.Coupon;
import Beans.Customer;

import java.util.ArrayList;

public class CustomerFacade extends ClientFacade{

    private int customeId;

    public CustomerFacade(){}
    @Override
    public boolean login(String email, String password) {
        return false;
    }
    public void purchaseCoupon(Coupon coupon){}
    public ArrayList<Coupon> getCustomersCoupons(){
        return null;
    }
    public ArrayList<Coupon> getCustomersCoupons(Category category){
        return null;
    }
    public ArrayList<Coupon> getCustomersCoupons(double maxPrice){
        return null;
    }
    public Customer getCustomerDetails(){
        return null;
    }
}
