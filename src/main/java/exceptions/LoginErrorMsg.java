package exceptions;

public enum LoginErrorMsg {
    CUSTOMER_NO_MATCHING_INFO("No customer with matching info. Enter a different email or password."),
    COMPANY_NO_MATCHING_INFO("No company with matching info. Enter a different email or password."),
    ADMIN_NO_MATCHING_INFO("No admin with matching info. Enter a different email or password.");


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
