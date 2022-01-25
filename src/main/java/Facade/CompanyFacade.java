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
public class CompanyFacade extends ClientFacade {

    private long companyId = -1; //0-admin

    private void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean login(String email, String password) throws CouponSystemExceptions {
        Company result = companiesDAO.isCompanyExists(email, password);
        if (result == null) {
            throw new CouponSystemExceptions(LoginErrorMsg.COMPANY_NO_MATCHING_INFO);
        }
        companyId = result.getId();
        return true;
    }

    public void addCoupon(Coupon coupon) throws CouponSystemExceptions {
        loginCheck();

        for (Coupon item : getCompanyCoupons()) {
            if (item.getTitle().equals(coupon.getTitle())) {
                throw new CouponSystemExceptions(CompanyErrorMsg.COUPON_NAME_EXISTS);
            }
        }
        if (coupon.getCompanyId() != companyId) {
            throw new CouponSystemExceptions(CompanyErrorMsg.COUPON_WRONG_COMPANYID);
        }
        couponDAO.addCoupon(coupon);
        System.out.println("Coupon was added successfully");

    }

    public void updateCoupon(Coupon coupon) throws CouponSystemExceptions {
        loginCheck();


        List<Coupon> allCoupons = getCompanyCoupons();

        if (coupon.getId() != companyId) {
            throw new CouponSystemExceptions(CompanyErrorMsg.COUPON_UDATE_FAILED_DIFFRENT_COMPANY_ID);
        }
        if (!allCoupons.stream().map(c -> c.getId()).collect(Collectors.toList()).contains(coupon.getId())) {
            throw new CouponSystemExceptions(CompanyErrorMsg.COUPON_UDATE_FAILED_NO_ID);
        }

    }

    public void deleteCoupon(Coupon coupon) throws CouponSystemExceptions {
        loginCheck();
        deleteCoupon(coupon.getId());
    }

    public void deleteCoupon(long id) throws CouponSystemExceptions {
        loginCheck();

        couponDAO.deleteCoupon(id);
        couponDAO.deleteCouponPurchaseByCouponID(id);
    }
    //...

    public ArrayList<Coupon> getCompanyCoupons() throws CouponSystemExceptions {
        loginCheck();

        return new ArrayList<Coupon>(couponDAO.getAllCoupons().stream()
                .filter(coupon -> coupon.getCompanyId() == companyId)
                .collect(Collectors.toList()));

    }

    public ArrayList<Coupon> getCompanyCoupons(Category category) throws CouponSystemExceptions {
        loginCheck();
        return new ArrayList<Coupon>(couponDAO.getAllCoupons().stream()
                .filter(coupon -> coupon.getCompanyId() == companyId && coupon.getCategory().value == category.value)
                .collect(Collectors.toList()));
    }

    public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws CouponSystemExceptions {
        loginCheck();

        return new ArrayList<Coupon>(couponDAO.getAllCoupons().stream()
                .filter(coupon -> coupon.getCompanyId() == companyId && coupon.getPrice() < maxPrice)
                .collect(Collectors.toList()));
    }

    public Company getCompanyDetails() throws CouponSystemExceptions {
        loginCheck();

        return companiesDAO.getOneCompany(companyId).setCoupons(getCompanyCoupons());
    }

    private void loginCheck() throws CouponSystemExceptions {
        if (companyId < 0) {
            throw new CouponSystemExceptions(LoginErrorMsg.CANT_ACCESS_FUNCTION_BAD_LOGIN);
        }
    }
}

