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
    private long companyId = -1; //0-admin
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
        if (!loginCheck()) {return;}

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
        if (!loginCheck()) {return;}


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
        if (!loginCheck()) {return;}


        deleteCoupon(coupon.getId());
    }
    public void deleteCoupon(long id){
        if (!loginCheck()) {return;}

        couponDAO.deleteCoupon(id);
        couponDAO.deleteCouponPurchaseByCouponID(id);
    }
    //...

    public ArrayList<Coupon> getCompanyCoupons(){
        if (!loginCheck()) {return null;}

        return new ArrayList<Coupon>(couponDAO.getAllCoupons().stream()
                        .filter(coupon -> coupon.getCompanyId() == companyId)
                        .collect(Collectors.toList()));

    }
    public ArrayList<Coupon> getCompanyCoupons(Category category){

        if (!loginCheck()) {return null;}
        return new ArrayList<Coupon>(couponDAO.getAllCoupons().stream()
                .filter(coupon -> coupon.getCompanyId() == companyId && coupon.getCategory().value == category.value)
                .collect(Collectors.toList()));
    }
    public ArrayList<Coupon> getCompanyCoupons(double maxPrice){
        if (!loginCheck()) {return null;}

        return new ArrayList<Coupon>(couponDAO.getAllCoupons().stream()
                .filter(coupon -> coupon.getCompanyId() == companyId && coupon.getPrice() < maxPrice)
                .collect(Collectors.toList()));
    }

    public Company getCompanyDetails(){
        if (!loginCheck()) {return null;}

        return companiesDAO.getOneCompany(companyId).setCoupons(getCompanyCoupons());
    }

    private boolean loginCheck(){

        boolean isOK = true;
        try {
            if (companyId < 0){
                throw new CouponSystemExceptions(LoginErrorMsg.CANT_ACCESS_FUNCTION_BAD_LOGIN);
            }
        } catch (CouponSystemExceptions err){
            System.out.println(err.getMessage());
            isOK = false;
        }
        return isOK;
    }
    }
    //TODO: לבדוק אם עדיף להצמד לארכיטקטורה המקורית או לעשות מה שיותר טוב

