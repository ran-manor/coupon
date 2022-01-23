package exceptions;

public enum LoginErrorMsg {
    USER_NO_LOGIN("Cannot run program - Please enter via login."),
    CUSTOMER_NO_MATCHING_INFO("No customer with matching info. Enter a different email or password."),
    COMPANY_NO_MATCHING_INFO("No company with matching info. Enter a different email or password."),
    ADMIN_NO_MATCHING_INFO("No admin with matching info. Enter a different email or password."),
    CANT_ACCESS_FUNCTION_BAD_LOGIN("Can't access function before login.");


    private String msg;

    LoginErrorMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
