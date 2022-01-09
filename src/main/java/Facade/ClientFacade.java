package Facade;

import DAO.CompaniesDAO;
import DAO.CouponDAO;
import DAO.CustomerDAO;

public abstract class  ClientFacade {
    protected CompaniesDAO companiesDAO;
     protected CustomerDAO customerDAO;
    protected CouponDAO couponDAO;

    public abstract boolean login(String email, String password);


}
