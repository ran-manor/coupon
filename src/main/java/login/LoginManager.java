package login;

import Facade.AdminFacade;
import Facade.ClientFacade;
import Facade.CompanyFacade;
import Facade.CustomerFacade;
import exceptions.CouponSystemExceptions;
import utils.DateUtils;

public class LoginManager {
    private static LoginManager instance = null;

    /**
     * private constructor that applies the singleton design pattern.
     */
    private LoginManager() {
    }

    /**
     * double checks if the instance isn't null, also by locking the entire class after first check.
     * if so, creates a new LoginManager and sets it to this instance.
     *
     * @return this instance.
     */
    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    /**
     * sorts what type of client is trying to login and send his login details to tryLogin function.
     *
     * @param email      client email.
     * @param password   client password.
     * @param clientType the type of client that tries to login.
     * @param <T>        type of facade this function returns.
     * @return a new facade after login try.
     * @throws CouponSystemExceptions throws login error if login failed.
     */
    public <T extends ClientFacade> T login(String email, String password, ClientType clientType) throws CouponSystemExceptions {
        switch (clientType) {
            case ADMINISTRATOR:
                ClientFacade adminFacade = new AdminFacade();
                return (T) tryLogin(email, password, adminFacade);
            case COMPANY:
                ClientFacade companyFacade = new CompanyFacade();
                return (T) tryLogin(email, password, companyFacade);
            case CUSTOMER: //->ctrl+space for see all enum values
                ClientFacade customerFacade = new CustomerFacade();
                return (T) tryLogin(email, password, customerFacade);
            default:
                return null;
        }
    }

    /**
     * takes a generic facade, email and password, and tries to login into it using the email and password.
     *
     * @param email    client email.
     * @param password client password.
     * @param facade   the facade this function will try login into.
     * @param <T>      type of facade this function works with.
     * @return the facade that was passed, after login.
     * @throws CouponSystemExceptions throws login error if login failed.
     */
    private <T extends ClientFacade> T tryLogin(String email, String password, T facade) throws CouponSystemExceptions {
        facade.login(email, password);
        System.out.println(DateUtils.getLocalDateTime() + " " + email + " was logged\n");
        return facade;
    }
}
