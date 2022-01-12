package DAO;

import Beans.Company;

import java.util.ArrayList;

public interface CompaniesDAO {
    Company isCompanyExists(String email, String password);
    boolean    addCompany(Company company);
    void deleteCompany(long companyID);
    ArrayList<Company> getAllCompanies();
    void updateCompany(Company company);
    Company getOneCompany(long companyID);
}
