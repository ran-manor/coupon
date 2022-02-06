package DBDAO;

import Beans.Company;
import DAO.CompaniesDAO;
import sql.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * class that implements CompaniesDAO, holding all the sql queries to the corresponding table in sql.
 */
public class CompaniesDBDAO implements CompaniesDAO {

    private final String TABLE_PATH = DBUtils.SCHEMA_PATH + ".`companies`";

    private final String ADD_COMPANY = "INSERT INTO " + TABLE_PATH +
            " (`name`, `email`, `password`) " +
            "VALUES (?,?,?)";
    private final String DELETE_COMPANY = "DELETE FROM " + TABLE_PATH + " WHERE id=?";

    private final String GET_COMPANIES_ALL = "SELECT * FROM " + TABLE_PATH;
    private final String GET_COMPANIES_SPECIFY_EMAIL_PASSWORD = " WHERE email=? AND password=?";
    private final String GET_COMPANIES_SPECIFY_ID = " WHERE id=?";
    private final String UPDATE_COMPANY = "UPDATE `couponmania`.`companies` " +
            "SET name=?, email=? ,password=?" +
            "WHERE id=?";

    /**
     * adds company to the database.
     * @param company company to add.
     * @return was adding successful.
     */
    @Override
    public boolean addCompany(Company company) {
        Map<Integer, Object> params = new HashMap<>();
        //params.put(1, company.getId());
        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());
        return DBUtils.runQuery(ADD_COMPANY, params);
    }

    /**
     * deletes company from the database.
     * @param companyID company to deleteby companyID.
     */
    @Override
    public void deleteCompany(long companyID) {
        Map<Integer, Object> params = new HashMap<Integer, Object>();
        params.put(1, companyID);
        DBUtils.runQuery(DELETE_COMPANY, params);
    }

    /**
     * get an arraylist of all companies from the DateBase.
     * @return the companies arraylist.
     */
    @Override
    public ArrayList<Company> getAllCompanies() {
        ArrayList<Company> companies = new ArrayList<>();
        ResultSet result = null;
        try {
            result = DBUtils.runQueryForResultSet(GET_COMPANIES_ALL);
            while (result.next()) {
                companies.add(Company.builder()
                        .id(result.getLong("id"))
                        .name(result.getString("name"))
                        .email(result.getString("email"))
                        .password(result.getString("password"))
                        .coupons(new ArrayList<>())
                        .build());
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            err.printStackTrace();
        }
        return companies;
    }

    /**
     * update a company details in the database. find the company to update by id.
     * @param company company with updated details.
     */
    @Override
    public void updateCompany(Company company) {
        Map<Integer, Object> parmas = new HashMap<>();
        parmas.put(1, company.getName());
        parmas.put(2, company.getEmail());
        parmas.put(3, company.getPassword());
        parmas.put(4, company.getId());
        DBUtils.runQuery(UPDATE_COMPANY, parmas);
    }

    /**
     * get one company from the database by companyID. returns null if company was not found.
     * @param companyID the id to search for.
     * @return the company retrieved by the query, null if no company was found.
     */
    @Override
    public Company getOneCompany(long companyID) {
        Map<Integer, Object> params = new HashMap<Integer, Object>();
        params.put(1, companyID);

        ResultSet result = null;

        try {
            result = DBUtils.runQueryForResultSet(GET_COMPANIES_ALL + GET_COMPANIES_SPECIFY_ID, params);
            if (result.next()) {
                return Company.builder()
                        .id(result.getLong("id"))
                        .name(result.getString("name"))
                        .email(result.getString("email"))
                        .password(result.getString("password"))
                        .coupons(new ArrayList<>())
                        .build();
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            err.printStackTrace();
        }
        return null;
    }

    /**
     * tries to get a company by email and password to use with login. returns the company if a company was found, else returns null.
     * @param email company client email.
     * @param password company client password.
     * @return the company found.
     */
    @Override
    public Company isCompanyExists(String email, String password) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);
        ResultSet result;
        Company returnCompany = null;
        try {
            result = DBUtils.runQueryForResultSet(GET_COMPANIES_ALL + GET_COMPANIES_SPECIFY_EMAIL_PASSWORD, params);
            if (result.next()) {
                returnCompany = Company.builder()
                        .id(result.getLong("id"))
                        .name(result.getString("name"))
                        .email(result.getString("email"))
                        .password(result.getString("password"))
                        .coupons(new ArrayList<>())
                        .build();
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            err.printStackTrace();
        }
        return returnCompany;
    }

}
