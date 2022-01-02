package DAO;

import Beans.Company;

import java.util.ArrayList;

public interface CompaniesDAO {
    boolean isCompanyExists(String email, String password);
    void addCompany(Company company);
    void deleteCompany(int companyID);
    ArrayList<Company> getAllCompanies();
    Company getOneCompany(int companyID);
}
