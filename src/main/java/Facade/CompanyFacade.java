package Facade;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import exceptions.CouponSystemExceptions;
import lombok.Data;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Data
public class CompanyFacade extends ClientFacade{

    private long companyId;

    public CompanyFacade(){}
    @Override
    public boolean login(String email, String password) {
        Company result = companiesDAO.isCompanyExists(email , password);
        if (result != null){
            companyId = result.getId();
            return true;
        }
        //todo: throw dont exist exception
        return false;
    }

    public void addCoupon(Coupon coupon) throws CouponSystemExceptions {
        Company company = companiesDAO.getOneCompany(this.getCompanyId());
        boolean isOk= true;
        for (Coupon item:company.getCoupons()) {
            if(item.getTitle().equals(coupon.getTitle()))
                throw new CouponSystemExceptions()
                isOk=false;
                break;
        }
        if(isOk){
        couponDAO.addCoupon(coupon);}
    }

    public void updateCoupon(Coupon coupon){
        //todo: make sure the coupon you are updating has the same company id and id
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
        return new ArrayList<Coupon>(couponDAO.getAllCoupons().stream()
                        .filter(coupon -> coupon.getCompanyId() == companyId)
                        .collect(Collectors.toList()));

    }
    public ArrayList<Coupon> getCompanyCoupons(Category category){
        return new ArrayList<Coupon>(couponDAO.getAllCoupons().stream()
                .filter(coupon -> coupon.getCompanyId() == companyId && coupon.getCategory().value == category.value)
                .collect(Collectors.toList()));
    }
    public ArrayList<Coupon> getCompanyCoupons(double maxPrice){
        return new ArrayList<Coupon>(couponDAO.getAllCoupons().stream()
                .filter(coupon -> coupon.getCompanyId() == companyId && coupon.getPrice() < maxPrice)
                .collect(Collectors.toList()));
    }

    public Company getCompanyDetails(){
        return companiesDAO.getOneCompany(companyId);
    }
}
