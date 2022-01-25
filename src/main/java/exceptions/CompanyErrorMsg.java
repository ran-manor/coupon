package exceptions;


public enum CompanyErrorMsg {
    COUPON_NAME_EXISTS("Cannot add coupon - coupon with the same name already exists for this company."),
    COUPON_WRONG_COMPANYID("Cannot add coupon - this coupon's company id doesn't match your company id"),
    COUPON_UDATE_FAILED_NO_ID("Coupon update failed, no coupon with this ID"),
    COUPON_UDATE_FAILED_DIFFRENT_COMPANY_ID("Coupon update failed - cannot update coupon to different company ID"),
    COUPON_DELETE_FAILED_COUPON_DOESNT_EXIST("Can't delete coupon - this coupon does not exist."),
    COUPON_DELETEE_FAILED_COUPON_OF_OTHER_COMPANY("Can't delete coupon - this coupon is owned by another company.");

    //aa
    private String msg;

    CompanyErrorMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}