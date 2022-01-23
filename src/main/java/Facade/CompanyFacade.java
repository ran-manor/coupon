package Facade;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import exceptions.CompanyErrorMsg;
import exceptions.CouponSystemExceptions;
import exceptions.LoginErrorMsg;
import exceptions.LoginException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CompanyFacade extends ClientFacade{

    //TODO: make checks if the companyId has a valid value
    private long companyId;
    private void setCompanyId(long companyId){
        this.companyId = companyId;
    }
    //aa
//    public CompanyFacade(String email, String password) throws CouponSystemExceptions {
//        login( email,  password);
//    }
    //TODO: check if login needs to be a boolean method
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
//        if (companyId < 1) return;
//        Company company = companiesDAO.getOneCompany(this.getCompanyId());
        try {
            for (Coupon item : getCompanyCoupons()) {
                if (item.getTitle().equals(coupon.getTitle())) {
                    throw new CouponSystemExceptions(CompanyErrorMsg.COUPON_NAME_EXISTS);
                }
            }
            if (coupon.getCompanyId() != companyId){
                throw new CouponSystemExceptions(CompanyErrorMsg.COUPON_WRONG_COMPANYID);
            }
            couponDAO.addCoupon(coupon);
            System.out.println("Coupon was added successfully");
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

