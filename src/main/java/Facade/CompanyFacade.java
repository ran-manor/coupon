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

    /**
     * sets this company id in the facade.
     * @param companyId value to set.
     */
    private void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    /**
     * searches for company with corresponding email and password.
     * sets the companyID in the facade to the id from the DataBase.
     * @param email client email.
     * @param password client password.
     * @return true is the login was successful.
     * @throws CouponSystemExceptions login error- no matching info.
     */
    @Override
    public boolean login(String email, String password) throws CouponSystemExceptions {
        Company result = companiesDAO.isCompanyExists(email, password);
        if (result == null) {
            throw new CouponSystemExceptions(LoginErrorMsg.COMPANY_NO_MATCHING_INFO);
        }
        companyId = result.getId();
        return true;
    }

    /**
     * gets a coupon to add to the DataBase.
     * checks if it passes the conditions and adds it if so.
     * @param coupon coupon to add.
     * @throws CouponSystemExceptions if coupon doesn't pass conditions.
     */
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

    /**
     * gets a coupon with updated values to update in the DataBase.
     * checks if it passes the conditions and updates it.
     * @param coupon coupon with updated details.
     * @throws CouponSystemExceptions coupon dont pass conditions.
     */
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
    /**
     * gets a coupon to delete from the DataBase.
     * if it passes the conditions- deletes it.
     * @param coupon coupon to delete.
     * @throws CouponSystemExceptions coupon doesn't pass conditions.
     */
    public void deleteCoupon(Coupon coupon) throws CouponSystemExceptions {
        deleteCoupon(coupon.getId());
    }

    /**
     * gets a couponID to delete from the DataBase.
     * if the coupon passes the conditions - deletes it.
     * @param id couponID to delete coupon by it.
     * @throws CouponSystemExceptions if coupon doesn't pass conditions.
     */
    public void deleteCoupon(long id) throws CouponSystemExceptions {
        loginCheck();

        Coupon checkCoupon = couponDAO.getOneCoupon(id);

        if (checkCoupon == null) {
            throw new CouponSystemExceptions(CompanyErrorMsg.COUPON_DELETE_FAILED_COUPON_DOESNT_EXIST);
        }

        if (checkCoupon.getCompanyId() != this.getCompanyId()) {
            throw new CouponSystemExceptions(CompanyErrorMsg.COUPON_DELETE_FAILED_COUPON_OF_OTHER_COMPANY);
        }

        couponDAO.deleteCoupon(id);
        couponDAO.deleteCouponPurchaseByCouponID(id);
    }
    //endregion

    //region get company coupons

    /**
     * gets all the coupons of the facade's company.
     * @return arraylist containing all the company's coupons.
     * @throws CouponSystemExceptions no login exception.
     */
    public ArrayList<Coupon> getCompanyCoupons() throws CouponSystemExceptions {
        return getCompanyCouponsFilter(coupon ->
                coupon.getCompanyId() == companyId);
    }

    /**
     * gets all the coupons of the facade's company under a max price.
     * @param maxPrice maxPrice for the coupons.
     * @return arraylist containing all the company's coupons under the price.
     * @throws CouponSystemExceptions no login exception.
     */
    public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws CouponSystemExceptions {
        return getCompanyCouponsFilter(coupon ->
                coupon.getCompanyId() == companyId && coupon.getPrice() <= maxPrice);
    }
    /**
     * gets all the coupons of the facade's company of a specific category.
     * @param category category for the coupons.
     * @return arraylist containing all the company's coupons under the given category.
     * @throws CouponSystemExceptions no login exception.
     */
    public ArrayList<Coupon> getCompanyCoupons(Category category) throws CouponSystemExceptions {
        return getCompanyCouponsFilter(coupon ->
                coupon.getCompanyId() == companyId && coupon.getCategory().value == category.value);
    }

    /**
     * gets all coupons from the dbdao and filters them by a given predicate.
     * collects relevant coupons to an arraaylist.
     * @param predicate the predicate to filter the array by.
     * @return the arraylist of filtered coupons.
     * @throws CouponSystemExceptions no login exception.
     */
    private ArrayList<Coupon> getCompanyCouponsFilter(Predicate<Coupon> predicate) throws CouponSystemExceptions {
        loginCheck();
        return new ArrayList<Coupon>(couponDAO.getAllCoupons().stream()
                .filter(predicate)
                .collect(Collectors.toList()));
    }
    //endregion

    /**
     * gets the facade's company detail from the database.
     * @return the company's details.
     * @throws CouponSystemExceptions no login exception.
     */
    public Company getCompanyDetails() throws CouponSystemExceptions {
        loginCheck();

        return companiesDAO.getOneCompany(companyId).setCoupons(getCompanyCoupons());
    }

    /**
     * this method locks all functions for use if they have no login.
     * checks by id.
     * @throws CouponSystemExceptions no login exception.
     */
    private void loginCheck() throws CouponSystemExceptions {
        if (companyId < 0) {
            throw new CouponSystemExceptions(LoginErrorMsg.CANT_ACCESS_FUNCTION_BAD_LOGIN);
        }
    }
}

