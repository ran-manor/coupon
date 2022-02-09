package exceptions;

public enum LoginErrorMsg {
    USER_NO_LOGIN("Cannot run program - Please enter via login."),
    CUSTOMER_NO_MATCHING_INFO("No customer with matching info. Enter a different email or password."),
    COMPANY_NO_MATCHING_INFO("No company with matching info. Enter a different email or password."),
    ADMIN_NO_MATCHING_INFO("No admin with matching info. Enter a different email or password."),
    CANT_ACCESS_FUNCTION_BAD_LOGIN("Can't access function before login.");


    private String msg;

    /**
     * c'tor that sets message to message contained in the enum.
     *
     * @param msg the message to set.
     */
    LoginErrorMsg(String msg) {
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
