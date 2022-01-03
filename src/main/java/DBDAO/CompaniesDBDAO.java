package DBDAO;

import Beans.Company;
import DAO.CompaniesDAO;
import sql.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CompaniesDBDAO implements CompaniesDAO {

    private final String TABLE_PATH = DBUtils.SCHEMA_PATH + ".`companies`";

    private final String ADD_COMPANY = "INSERT INTO " + TABLE_PATH +
            " (`id`,`name`, `email`, `password`) " +
            "VALUES (?,?,?,?)";
    private final String DELETE_COMPANY = "DELETE FROM "+ TABLE_PATH + " WHERE id=?";

    private final String GET_COMPANIES_ALL = "SELECT * FROM " + TABLE_PATH;
    private final String GET_COMPANIES_SPECIFY_EMAIL_PASSWORD = " WHERE email=? AND password=?";
    private final String GET_COMPANIES_SPECIFY_ID = " WHERE id=?";

//    private final String ADD_COUPON = "INSERT INTO `CouponMania`.`coupons` " +
//            "(`customer_id`,`category_id`, `title`, `description`, `start_date`,`end_date`,`amount`, `price`, `image`)   )" +
//            "VALUES (?,?,?,?,?,?,?,?,?);";

    @Override
    public boolean isCompanyExists(String email, String password) {
        Map<Integer,Object> params = new HashMap<>();
        params.put(1 , email);
        params.put(2 , password);

        try {
            return DBUtils.getResultSetQuery(GET_COMPANIES_ALL + GET_COMPANIES_SPECIFY_EMAIL_PASSWORD, params ).next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean addCompany(Company company) {
        Map<Integer,Object> params = new HashMap<>();
        params.put(1 , company.getId());
        params.put(2 , company.getName());
        params.put(3 , company.getEmail());
        params.put(4, company.getPassword());
        return DBUtils.runUpdateQuery(ADD_COMPANY , params);
    }

    @Override
    public void deleteCompany(int companyID) {
        Map<Integer,Object> params = new HashMap<>();
        params.put( 1 , companyID);
        DBUtils.runUpdateQuery(DELETE_COMPANY , params);
    }

    @Override
    public ArrayList<Company> getAllCompanies() {
        ArrayList<Company> companies = new ArrayList<>();
        ResultSet result = DBUtils.getResultSetQuery(GET_COMPANIES_ALL);

            try {
                while (result.next()) {
                    companies.add(Company.builder()
                            .id(result.getInt("id"))
                            .name(result.getString("name"))
                            .email(result.getString("email"))
                            .password(result.getString("password"))
                            .build());
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return companies;

        }

    @Override
    public Company getOneCompany(int companyID) {
        Map<Integer , Object> params = new HashMap<>();
        params.put(1 , companyID);
        ResultSet result = DBUtils.getResultSetQuery(GET_COMPANIES_ALL + GET_COMPANIES_SPECIFY_ID , params);
        try {
            if (result.next()){
                return Company.builder()
                        .id(result.getInt("id"))
                        .name(result.getString("name"))
                        .email(result.getString("email"))
                        .password(result.getString("password"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
