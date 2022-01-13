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

    public ClientFacade login(String email, String password, ClientType clientType)  {
        switch (clientType) {
            case ADMINISTRATOR:
                ClientFacade adminFacade = new AdminFacade();
                try {
                    adminFacade.login(email ,password);
                    System.out.println(DateUtils.getLocalDateTime() + email + " was logged ");
                    return adminFacade;
                }catch (CouponSystemExceptions err){
                    System.out.println(err.getMessage());
                    return null;
                }
            case COMPANY:
                ClientFacade companyFacade = new CompanyFacade();
                try {
                    companyFacade.login(email ,password);
                    System.out.println(DateUtils.getLocalDateTime() + email + " was logged ");
                    return companyFacade;
                }catch (CouponSystemExceptions err){
                    System.out.println(err.getMessage());
                    return null;
                }
            case CUSTOMER: //->ctrl+space for see all enum values
                ClientFacade customerFacade = new CustomerFacade();
                try {
                    customerFacade.login(email ,password);
                    System.out.println(DateUtils.getLocalDateTime() + email + " was logged ");
                    return customerFacade;
                }catch (CouponSystemExceptions err){
                    System.out.println(err.getMessage());
                    return null;
                }
        }
        return null;
    }
}
