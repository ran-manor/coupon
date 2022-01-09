package Facade;

import Beans.Category;
import Beans.Coupon;
import Beans.Customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class CustomerFacade extends ClientFacade{

    private int customeId;

    public CustomerFacade(){}
    @Override
    public boolean login(String email, String password) {
        return false;
    }
    public void purchaseCoupon(Coupon coupon){
        Customer customer = customerDAO.getOneCustomer(customeId);
        if(coupon.getAmount()==0 ){
            //todo: throw exp
        }
        else if(coupon.getEndDate().after(new Date())){
            //todo: throw exp
        }


    }
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
