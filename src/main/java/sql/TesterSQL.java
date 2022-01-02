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
            List<Coupon>coupons = new ArrayList<>();
            DataBaseManager.dropDataBase();
            DataBaseManager.createDataBase();
            DataBaseManager.createTables();
            CustomersDBDAO customersDBDAO = null;
            Customer customer = new Customer(1,"ran","manor","Raran@gmail.com","122345",coupons);
            customersDBDAO.addCustomer(customer);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }
}
