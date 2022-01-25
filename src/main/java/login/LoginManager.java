package login;

import Facade.AdminFacade;
import Facade.ClientFacade;
import Facade.CompanyFacade;
import Facade.CustomerFacade;
import exceptions.CouponSystemExceptions;
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
    //
    public <T extends ClientFacade> T login(String email, String password, ClientType clientType) throws CouponSystemExceptions {
        switch (clientType) {
            case ADMINISTRATOR:
                ClientFacade adminFacade = new AdminFacade();
                return (T) tryLogin(email , password , adminFacade);
            case COMPANY:
                ClientFacade companyFacade = new CompanyFacade();
                return (T)tryLogin(email , password , companyFacade);
            case CUSTOMER: //->ctrl+space for see all enum values
                ClientFacade customerFacade = new CustomerFacade();
                return (T) tryLogin(email , password , customerFacade);
            default:
                return null;
        }
    }

    private <T extends ClientFacade> T tryLogin (String email, String password , T facade) throws CouponSystemExceptions {
            facade.login(email ,password);
            System.out.println(DateUtils.getLocalDateTime() + "" + email + " was logged\n");
            return facade;
    }
}
