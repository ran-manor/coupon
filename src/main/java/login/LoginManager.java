package login;

import Facade.AdminFacade;
import Facade.ClientFacade;
import Facade.CompanyFacade;
import Facade.CustomerFacade;
import exceptions.CouponSystemExceptions;
import exceptions.LoginException;
import utils.DateUtils;

public class LoginManager {
    private static LoginManager instance = null;

    private LoginManager() {
    }

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

    public ClientFacade login(String email, String password, ClientType clientType) throws LoginException, CouponSystemExceptions {
        switch (clientType) {
            case ADMINISTRATOR:
                ClientFacade adminFacade = new AdminFacade();
                if (!adminFacade.login(email, password)) {
                    throw new LoginException("Invalid user name or password");
                }
                System.out.println(DateUtils.getLocalDateTime() + email + " was logged ");
                return adminFacade;
            case COMPANY:
                ClientFacade companyFacade = new CompanyFacade();
                if (!companyFacade.login(email, password)) {
                    throw new LoginException("Invalid user name or password");
                }
                System.out.println(DateUtils.getLocalDateTime() + email + " was logged ");
                return companyFacade;

            case CUSTOMER: //->ctrl+space for see all enum values

                ClientFacade customerFacade = new CustomerFacade();
                if (!customerFacade.login(email, password)) {
                    throw new LoginException("Invalid user name or password");
                }
                System.out.println(DateUtils.getLocalDateTime() + email + " was logged ");
                return customerFacade;
        }
        return null;
    }
}
