package exceptions;

/**
 * Created by kobis on 21 Jun, 2021
 */
public enum AdminErrorMsg {

    COMPANY_NAME_EXIST("cannot add company with exiting company name"),
    COMPANY_EMAIL_EXIST("cannot add company with exiting company email"),
    COMPANY_ID_UPDATE ("cannot update company ID"),
    COMPANY_NAME_UPDATE ("cannot update company name"),
    CUSTOMER_EMAIL_EXIST ("adding failed- email already exists"),
    CUSTOMER_UPDATE_ID ("cannot update customer ID");


//hhh

    private String msg;

    AdminErrorMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}