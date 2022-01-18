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
    //
    public <T extends ClientFacade> T login(String email, String password, ClientType clientType)  {
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

    private <T extends ClientFacade> T tryLogin (String email, String password , T facade){
        try {
            facade.login(email ,password);
            System.out.println(DateUtils.getLocalDateTime() + email + " was logged ");
            return facade;
        }catch (CouponSystemExceptions err){
            System.out.println(err.getMessage());
            return null;
        }
    }
}


/*
*    public ClientFacade login(String email, String password, ClientType clientType)  {
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
* */
