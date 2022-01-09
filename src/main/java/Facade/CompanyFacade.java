package Facade;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import lombok.Data;

import java.util.ArrayList;
@Data
public class CompanyFacade extends ClientFacade{
    private int companyId;
    public CompanyFacade(){}
    @Override
    public boolean login(String email, String password) {
        return false;
    }
    public void addCoupon(Coupon coupon){
        Company company = companiesDAO.getOneCompany(this.getCompanyId());
        boolean isOk= true;
        for (Coupon item:company.getCoupons()) {
            if(item.getTitle().equals(coupon.getTitle()))
                isOk=false;
                break;
        }
        if(isOk){
        couponDAO.addCoupon(coupon);}
    }
    public void updateCoupon(Coupon coupon){
        couponDAO.updateCoupon(coupon);
    }
    public void deleteCoupon(Coupon coupon){
        deleteCoupon(coupon.getId());
    }
    public void deleteCoupon(long id){
        couponDAO.deleteCoupon(id);
        couponDAO.deleteCouponPurchaseByCouponID(id);
    }
    //...

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
