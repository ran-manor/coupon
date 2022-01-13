package sql;

import Beans.Coupon;
import Beans.Customer;
import DBDAO.CustomersDBDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TesterSQL {
    public static void main(String[] args) {
        try {
            DataBaseManager.dropDataBase();
            DataBaseManager.createDataBase();


        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

    }


}
