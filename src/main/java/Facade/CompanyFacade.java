package Facade;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import exceptions.CompanyErrorMsg;
import exceptions.CouponSystemExceptions;
import exceptions.LoginErrorMsg;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CompanyFacade extends ClientFacade{

    private long companyId;
    //aa
    public CompanyFacade(){}
    @Override
    public boolean login(String email, String password) throws CouponSystemExceptions {
        Company result = companiesDAO.isCompanyExists(email, password);
        if (result == null){
            throw new CouponSystemExceptions(LoginErrorMsg.COMPANY_NO_MATCHING_INFO);
        }
        companyId = result.getId();
        return true;
    }

    public void addCoupon(Coupon coupon){
        Company company = companiesDAO.getOneCompany(this.getCompanyId());
        try {
            for (Coupon item : company.getCoupons()) {
                if (item.getTitle().equals(coupon.getTitle())) {
                    throw new CouponSystemExceptions(CompanyErrorMsg.COUPON_NAME_EXISTS);
                }
            }
        } catch (CouponSystemExceptions err){
            System.out.println(err.getMessage());
        }
    }

    public void updateCoupon(Coupon coupon){
        List<Coupon> allCoupons = getCompanyCoupons();
        try {
            if (coupon.getId() != companyId) {
                throw new CouponSystemExceptions(CompanyErrorMsg.COUPON_UDATE_FAILED_DIFFRENT_COMPANY_ID);
            }
            if (!allCoupons.stream().map(c -> c.getId()).collect(Collectors.toList()).contains(coupon.getId())) {
                throw new CouponSystemExceptions(CompanyErrorMsg.COUPON_UDATE_FAILED_NO_ID);
            }
        }catch (CouponSystemExceptions err){
            System.out.println(err.getMessage());
        }
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
