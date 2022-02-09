package exceptions;


public class CouponSystemExceptions extends Exception {
    /**
     * Constructs a CouponSystemExceptions object with a given reason.
     *
     * @param msg reason.
     */
    public CouponSystemExceptions(String msg) {
        super(msg);
    }

    /**
     * Constructs a CouponSystemExceptions object with a given Company error.
     *
     * @param errors error from enum.
     */
    public CouponSystemExceptions(CompanyErrorMsg errors) {
        super(errors.getMsg());
    }

    /**
     * Constructs a CouponSystemExceptions object with a given Admin error.
     *
     * @param errors error from enum.
     */
    public CouponSystemExceptions(AdminErrorMsg errors) {
        super(errors.getMsg());
    }


    /**
     * Constructs a CouponSystemExceptions object with a given customer error.
     *
     * @param errors error from enum.
     */
    public CouponSystemExceptions(CustomerErrorMsg errors) {
        super(errors.getMsg());
    }


    /**
     * Constructs a CouponSystemExceptions object with a given login error.
     *
     * @param errors error from enum.
     */
    public CouponSystemExceptions(LoginErrorMsg errors) {
        super(errors.getMsg());
    }
}
