package exceptions;

/**
 * Created by kobis on 21 Jun, 2021
 */
public enum CouponErrorMsg {

    COUPON_TITLE_EXIST("cannot add coupon with exiting title"),
    COUPON_UPDATE_COUPON_ID("cannot update coupon id"),
    COMPANY_ID_NOT_EXIST("cannot update company with exiting non exist id");

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