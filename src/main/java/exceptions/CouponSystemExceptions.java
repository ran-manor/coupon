package exceptions;


public class CouponSystemExceptions extends Exception{
    public CouponSystemExceptions(String msg){
        super(msg);
    }
    public CouponSystemExceptions(CompanyErrorMsg errors) {
        super(errors.getMsg());
    }
    public CouponSystemExceptions(AdminErrorMsg errors) {
        super(errors.getMsg());
    }
    public CouponSystemExceptions(CustomerErrorMsg errors) {
        super(errors.getMsg());
    }
    public CouponSystemExceptions(LoginErrorMsg errors) {
        super(errors.getMsg());
    }
}
