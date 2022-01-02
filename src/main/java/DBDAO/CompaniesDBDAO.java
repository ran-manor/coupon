package DBDAO;

import Beans.Company;
import DAO.CompaniesDAO;

import java.util.ArrayList;

public class CompaniesDBDAO implements CompaniesDAO {
    @Override
    public boolean isCompanyExists(String email, String password) {
        return false;
    }

    @Override
    public void addCompany(Company company) {

    }

    @Override
    public void deleteCompany(int companyID) {

    }

    @Override
    public ArrayList<Company> getAllCompanies() {
        return null;
    }

    @Override
    public Company getOneCompany(int companyID) {
        return null;
    }
}
