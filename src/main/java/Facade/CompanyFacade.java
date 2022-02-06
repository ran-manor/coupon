package Facade;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import exceptions.CompanyErrorMsg;
import exceptions.CouponSystemExceptions;
import exceptions.LoginErrorMsg;
import lombok.Data;
import utils.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
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
                throw new CouponSystemExceptions(CompanyErrorMsg.COUPON_ADDING_FAILED_NAME_EXISTS);
            }
        }

        if (coupon.getEndDate().before(DateUtils.localDateToSqlDate(LocalDate.now()))){
            throw new CouponSystemExceptions(CompanyErrorMsg.COUPON_ADDING_FAILED_EXPIRED_DATE);
        }

        if (coupon.getCompanyId() != companyId) {
            throw new CouponSystemExceptions(CompanyErrorMsg.COUPON_ADDING_FAILED_WRONG_COMPANYID);
        }
        couponDAO.addCoupon(coupon);
        System.out.println("Coupon was added successfully");

    }

    public void updateCoupon(Coupon coupon) throws CouponSystemExceptions {
        loginCheck();
        List<Coupon> allCoupons = getCompanyCoupons();

        if (coupon.getCompanyId() != companyId) {
            throw new CouponSystemExceptions(CompanyErrorMsg.COUPON_UDATE_FAILED_DIFFRENT_COMPANY_ID);
        }
        if (!allCoupons.stream().map(c -> c.getId()).collect(Collectors.toList()).contains(coupon.getId())) {
            throw new CouponSystemExceptions(CompanyErrorMsg.COUPON_UDATE_FAILED_NO_ID);
        }
        couponDAO.updateCoupon(coupon);

    }

    //region delete coupon
    public void deleteCoupon(Coupon coupon) throws CouponSystemExceptions {
        deleteCoupon(coupon.getId());
    }

    public void deleteCoupon(long id) throws CouponSystemExceptions {
        loginCheck();

        //todo: check if can be changes to getonecoupon
        if (!couponDAO.getAllCoupons().stream().anyMatch(coupon -> id == coupon.getId())) {
            throw new CouponSystemExceptions(CompanyErrorMsg.COUPON_DELETE_FAILED_COUPON_DOESNT_EXIST);
        }

        if (couponDAO.getOneCoupon(id).getCompanyId() != getCompanyId()) {
            throw new CouponSystemExceptions(CompanyErrorMsg.COUPON_DELETE_FAILED_COUPON_OF_OTHER_COMPANY);
        }

        couponDAO.deleteCoupon(id);
        couponDAO.deleteCouponPurchaseByCouponID(id);
    }
    //endregion

    //region get company coupons
    public ArrayList<Coupon> getCompanyCoupons() throws CouponSystemExceptions {
        return getCompanyCouponsFilter(coupon -> coupon.getCompanyId() == companyId);
    }

    public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws CouponSystemExceptions {
        return getCompanyCouponsFilter(coupon -> coupon.getCompanyId() == companyId && coupon.getPrice() <= maxPrice);
    }

    public ArrayList<Coupon> getCompanyCoupons(Category category) throws CouponSystemExceptions {
        return getCompanyCouponsFilter(coupon -> coupon.getCompanyId() == companyId && coupon.getCategory().value == category.value);
    }

    private ArrayList<Coupon> getCompanyCouponsFilter(Predicate<Coupon> predicate) throws CouponSystemExceptions {
        loginCheck();
        return new ArrayList<Coupon>(couponDAO.getAllCoupons().stream()
                .filter(predicate)
                .collect(Collectors.toList()));
    }
    //endregion

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

