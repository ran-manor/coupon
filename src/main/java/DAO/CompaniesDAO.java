package DAO;

import Beans.Company;

import java.util.ArrayList;
/**
 * interface that holds the methods regarding companies in the database that will be implemented in the DBDao
 * (including full CRUD functionality)
 */
public interface CompaniesDAO {
    Company isCompanyExists(String email, String password);
    boolean    addCompany(Company company);
    void deleteCompany(long companyID);
    ArrayList<Company> getAllCompanies();
    void updateCompany(Company company);
    Company getOneCompany(long companyID);
}
