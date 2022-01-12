package exceptions;

/**
 * Created by kobis on 21 Jun, 2021
 */
public enum CouponErrorMsg {

    COUPON_TITLE_EXIST("cannot add coupon with exiting coupon title"),
    COUPON_UPDATE_COUPON_ID("cannot update coupon id"),
    COUPON_UPDATE_COMPANY_ID("cannot update coupon's company id"),
    AMOUNT_EQUAL_ZERO("The coupon can not be purchased. The amount has reached 0");
//hhh

    private String msg;

    CouponErrorMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}