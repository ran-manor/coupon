package Facade;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;

import java.util.ArrayList;

public class CompanyFacade extends ClientFacade{
    private int companyId;
    public CompanyFacade(){}
    @Override
    public boolean login(String email, String password) {
        return false;
    }
    public void addCoupon(Coupon coupon){}
    public void updateCoupon(Coupon coupon){}
    public void deleteCoupon(Coupon coupon){}
    public ArrayList<Coupon> getCompanyCoupons(){
        return null;
    }
    public ArrayList<Coupon> getCompanyCoupons(Category category){
        return null;
    }
    public ArrayList<Coupon> getCompanyCoupons(double maxPrice){
        return null;
    }
    public Company getCompanyDetails(){
        return null;
    }
}
