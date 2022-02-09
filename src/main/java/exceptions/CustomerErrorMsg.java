package exceptions;


public enum CustomerErrorMsg {

    CUSTOMER_NAME_EXIST("cannot add customer with exiting customer name"),
    CUSTOMER_EMAIL_EXIST("cannot add customer with exiting customer email"),
    CUSTOMER_ID_NOT_EXIST("cannot update customer with exiting non exist id"),
    CUSTOMER_UPDATE_ID_EXIST("cannot update customer with exiting id"),
    COUPON_ALREADY_EXISTS("cannot purchase a coupon the customer already owns"),
    COUPON_PURCHASE_FAIL_COUPON_NULL("Cannot purchase coupon - coupon does not exist."),
    CUSTOMER_UPDATE_COUPON_ID("cannot update coupon id"),
    CUSTOMER_UPDATE_COMPANY_ID("cannot update coupon's company id"),
    AMOUNT_EQUAL_ZERO("The coupon can not be purchased. The amount has reached 0"),
    EXPIRED_DATE("cannot purchase an expired coupon"),
    CUSTOMER_NOT_EXIST("The customer doesn't exist");


    private String msg;

    /**
     * c'tor that sets message to message contained in the enum.
     *
     * @param msg the message to set.
     */
    CustomerErrorMsg(String msg) {
        this.msg = msg;
    }

    /**
     * get the error message.
     *
     * @return string message contained in the error value.
     */
    public String getMsg() {
        return msg;
    }
}