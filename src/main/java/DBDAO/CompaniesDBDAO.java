package DBDAO;

import Beans.Company;
import DAO.CompaniesDAO;
import sql.DBUtils;

import java.util.ArrayList;

public class CompaniesDBDAO implements CompaniesDAO {

    private final String TABLE_PATH = DBUtils.SCHEMA_PATH + ".`companies`";

    private final String ADD_COMPANY = "";
    private final String DELETE_COMPANY = "";
    private final String GET_COMPANIES_ALL = "SELECT * FROM " + TABLE_PATH;
    private final String GET_COMPANIES_SPECIFY = " WHERE id=?";

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
