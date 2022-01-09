package Facade;

import DAO.CompaniesDAO;
import DAO.CouponDAO;
import DAO.CustomerDAO;
import DBDAO.CompaniesDBDAO;
import DBDAO.CouponsDBDAO;
import DBDAO.CustomersDBDAO;

public abstract class ClientFacade {
    protected CompaniesDAO companiesDAO = new CompaniesDBDAO();
    protected CustomerDAO customerDAO = new CustomersDBDAO();
    protected CouponDAO couponDAO = new CouponsDBDAO();

    public abstract boolean login(String email, String password);


}
