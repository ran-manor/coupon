package Facade;

import DAO.CompaniesDAO;
import DAO.CouponDAO;
import DAO.CustomerDAO;
import DBDAO.CompaniesDBDAO;
import DBDAO.CouponsDBDAO;
import DBDAO.CustomersDBDAO;
import exceptions.CouponSystemExceptions;

public abstract class ClientFacade {
    protected CompaniesDAO companiesDAO = new CompaniesDBDAO();
    protected CustomerDAO customerDAO = new CustomersDBDAO();
    protected CouponDAO couponDAO = new CouponsDBDAO();

    /**
     * login method of the client.
     * @param email client email.
     * @param password client password.
     * @return true if the login was successful, false is not.
     * @throws CouponSystemExceptions login error.
     */
    public abstract boolean login(String email, String password) throws CouponSystemExceptions;


}
