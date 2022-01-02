package sql;

import Beans.Category;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DataBaseManager {
    public static final String URL = "jdbc:mysql://localhost:3306/";
    public static final String USER_NAME = "root";
    public static final String USER_PASS = "12345678";

    public static final int MAX_CONNECTION = 10;

    private static final String DROP_SCHEMA = "DROP SCHEMA  `CouponMania`";
    private static final String CREATE_SCHEMA = "CREATE SCHEMA IF NOT EXISTS `CouponMania`";

    private static final String CREATE_TABLE_COMPANIES = "CREATE TABLE IF NOT EXISTS `CouponMania`.`companies` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  `email` VARCHAR(45) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`))";


    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE IF NOT EXISTS `CouponMania`.`customers` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `first_name` VARCHAR(45) NOT NULL,\n" +
            "  `last_name` VARCHAR(45) NOT NULL,\n" +
            "  `email` VARCHAR(45) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`))";
    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE IF NOT EXISTS `CouponMania`.`categories` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`))";

    private static final String ADD_CATEGORY = "INSERT INTO `CouponMania`.`categories` " +
            "(`name`)" +
            "VALUES (?);";

    private static final String CREATE_TABLE_COUPONS = "CREATE TABLE IF NOT EXISTS `CouponMania`.`coupons` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `company_id` INT NOT NULL,\n" +
            "  `category_id` INT NOT NULL,\n" +
            "  `title` VARCHAR(45) NOT NULL,\n" +
            "  `description` VARCHAR(45) NOT NULL,\n" +
            "  `start_date` DATE NOT NULL,\n" +
            "  `end_date` DATE NOT NULL,\n" +
            "  `amount` INT NOT NULL,\n" +
            "  `price` DOUBLE NOT NULL,\n" +
            "  `image` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  INDEX `company_id_idx` (`company_id` ASC) VISIBLE,\n" +
            "  INDEX `category_id_idx` (`category_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `company_id`\n" +
            "    FOREIGN KEY (`company_id`)\n" +
            "    REFERENCES `CouponMania`.`companies` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `category_id`\n" +
            "    FOREIGN KEY (`category_id`)\n" +
            "    REFERENCES `CouponMania`.`categories` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION)";

    private static final String CREATE_TABLE_CUSTOMERS_COUPONS = "CREATE TABLE IF NOT EXISTS `CouponMania`.`customers_coupons` (\n" +
            "  `customer_id` INT NOT NULL,\n" +
            "  `coupon_id` INT NOT NULL,\n" +
            "  PRIMARY KEY (`customer_id`, `coupon_id`),\n" +
            "  INDEX `coupon_id_idx` (`coupon_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `customr_id`\n" +
            "    FOREIGN KEY (`customer_id`)\n" +
            "    REFERENCES `CouponMania`.`customers` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `coupon_id`\n" +
            "    FOREIGN KEY (`coupon_id`)\n" +
            "    REFERENCES `CouponMania`.`coupons` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION)";

    public static void createDataBase() throws SQLException {
        DBUtils.runUpdateQuery(CREATE_SCHEMA);
    }

    public static void dropDataBase() throws SQLException{
        DBUtils.runUpdateQuery(DROP_SCHEMA);
    }

    public static void initCategories(){
        for (Category item : Category.values()){
            Map<Integer,Object> params = new HashMap<>();
            params.put(1,item);
            DBUtils.runUpdateQuery(ADD_CATEGORY);
        }
    }

    public static void createTables() throws SQLException{
        DBUtils.runUpdateQuery(CREATE_TABLE_COMPANIES);
        DBUtils.runUpdateQuery(CREATE_TABLE_CUSTOMERS);
        DBUtils.runUpdateQuery(CREATE_TABLE_CATEGORIES);
        initCategories();
        DBUtils.runUpdateQuery(CREATE_TABLE_COUPONS);
        DBUtils.runUpdateQuery(CREATE_TABLE_CUSTOMERS_COUPONS);
    }

//    public static void dropTable() throws SQLException{
//        DBUtils.runQuery(DROP_TABLE);
//    }

}
