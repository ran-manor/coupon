package exceptions;




public class CouponSystemExceptions extends Exception{
    public CouponSystemExceptions(String msg){
        super(msg);
    }
    public CouponSystemExceptions(CompanyErrorMsg errors) {
        super(errors.getMsg());
    }
    public CouponSystemExceptions(CouponErrorMsg errors) {
        super(errors.getMsg());
    }
    public CouponSystemExceptions(CustomerErrorMsg errors) {
        super(errors.getMsg());
    }
}
