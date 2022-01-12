package exceptions;

/**
 * Created by kobis on 21 Jun, 2021
 */
public enum CustomerErrorMsg {

    COMPANY_NAME_EXIST("cannot add company with exiting customer name"),
    COMPANY_EMAIL_EXIST("cannot add company with exiting customer email"),
    COMPANY_ID_NOT_EXIST("cannot update company with exiting non exist id");

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