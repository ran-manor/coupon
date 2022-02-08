package exceptions;

/**
 * Created by kobis on 21 Jun, 2021
 */
public enum AdminErrorMsg {

    COMPANY_NAME_EXIST("cannot add company with exiting company name"),
    COMPANY_EMAIL_EXIST("cannot add company with exiting company email"),
    COMPANY_ID_UPDATE ("cannot update company ID"),
    COMPANY_NAME_UPDATE ("cannot update company name"),
    CUSTOMER_EMAIL_EXIST ("Customer adding failed - email already exists"),
    CUSTOMER_UPDATE_ID ("cannot update customer ID"),
    CUSTOMER_UPDATE_FAILED_NOT_EXIST("Customer update failed - The customer doesn't exist"),
    CUSTOMER_NOT_EXIST("The customer doesn't exist"),
    COMPANY_NOT_EXISTS("The company doesn't exist"),
    COMPANY_UPDATE_FAIL_NOT_EXIST("Company update failed - The company doesn't exist");



    private String msg;

    /**
     * c'tor that sets message to message contained in the enum.
     * @param msg the message to set.
     */
    AdminErrorMsg(String msg) {
        this.msg = msg;
    }

    /**
     * get the error message.
     * @return string message contained in the error value.
     */
    public String getMsg() {
        return msg;
    }


}