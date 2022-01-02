package DBDAO;

import Beans.Company;
import DAO.CompaniesDAO;
import sql.ConnectionPool;
import sql.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CompaniesDBDAO implements CompaniesDAO {

    private final String TABLE_PATH = DBUtils.SCHEMA_PATH + ".`companies`";

    private final String ADD_COMPANY = "INSERT INTO " + TABLE_PATH +
            " (`id`,`name`, `email`, `password`) " +
            "VALUES (?,?,?,?)";
    private final String DELETE_COMPANY = "";
    private final String GET_COMPANIES_ALL = "SELECT * FROM " + TABLE_PATH;
    private final String GET_COMPANIES_SPECIFY = " WHERE email=? AND password=?";

//    private final String ADD_COUPON = "INSERT INTO `CouponMania`.`coupons` " +
//            "(`customer_id`,`category_id`, `title`, `description`, `start_date`,`end_date`,`amount`, `price`, `image`)   )" +
//            "VALUES (?,?,?,?,?,?,?,?,?);";

    @Override
    public boolean isCompanyExists(String email, String password) {
        Map<Integer,Object> params = new HashMap<>();
        params.put(1 , email);
        params.put(2 , password);

        ResultSet result = DBUtils.getResultSetQuery(GET_COMPANIES_ALL + GET_COMPANIES_SPECIFY , params );
        try {
            if (result.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addCompany(Company company) {
//        boolean isOK = true;
//        Connection connection = null;
//        Map<Integer,Object> parmas = new HashMap<>();
//        try {
//            connection = ConnectionPool.getInstance().getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement(ADD_COMPANY);
//            parmas.put(1,company.getId());
//            parmas.put(2,company.getName());
//            parmas.put(3,company.getEmail());
//            parmas.put(4,company.getPassword());
//            DBUtils.runUpdateQuery(ADD_COMPANY , parmas);
//        } catch (InterruptedException | SQLException err) {
//            System.out.printf(err.getMessage());
//            isOK = false;
//        }
//        finally {
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
//        return isOK;
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
