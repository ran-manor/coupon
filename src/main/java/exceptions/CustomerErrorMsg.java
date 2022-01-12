package exceptions;

/**
 * Created by kobis on 21 Jun, 2021
 */
public enum CustomerErrorMsg {

    CUSTOMER_NAME_EXIST("cannot add customer with exiting customer name"),
    CUSTOMER_EMAIL_EXIST("cannot add customer with exiting customer email"),
    CUSTOMER_ID_NOT_EXIST("cannot update customer with exiting non exist id"),
    CUSTOMER_UPDATE_ID_EXIST("cannot update customer with exiting id");


    private String msg;

    CustomerErrorMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}