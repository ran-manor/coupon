import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DBDAO.CompaniesDBDAO;
import DBDAO.CouponsDBDAO;
import DBDAO.CustomersDBDAO;
import DBDAO.TesterDBDAO;
import Facade.AdminFacade;
import Facade.CompanyFacade;
import Facade.CustomerFacade;
import login.ClientType;
import login.LoginManager;
import sql.DataBaseManager;
import threads.CouponExpirationDailyJob;
import utils.DateUtils;

import java.sql.SQLException;
import java.util.ArrayList;

public class Tester {
    public static void main(String[] args) {
        applicationStart();

        AdminFacade adminFacadeWrong = LoginManager.getInstance().login("asdasd" , "asda3243" , ClientType.ADMINISTRATOR);
        AdminFacade adminFacadeWrong2 = LoginManager.getInstance().login("admin@admin.com" , "admin" , ClientType.COMPANY);
        AdminFacade adminFacade = LoginManager.getInstance().login("admin@admin.com" , "admin" , ClientType.ADMINISTRATOR);


        adminFacade.addCompany(new Company("rancorp","rancorp@rancorp.com","lootercorp"));


        CompanyFacade companyFacadeWrong = LoginManager.getInstance().login("admin@admin.com" , "admin" , ClientType.COMPANY);
        CompanyFacade companyFacade = LoginManager.getInstance().login("rancorp@rancorp.com" , "lootercorp" , ClientType.COMPANY);

        //region addcoupons
        companyFacade.addCoupon(new Coupon(companyFacade.getCompanyId()
                , Category.Vacation
                , "happy vacation", "a happy vacation" ,
                DateUtils.getRandomSqlStartDate(),DateUtils.getRandomSqlEndDate(),
                18 , 1800 ,"urlasd"));
        companyFacade.addCoupon(new Coupon(companyFacade.getCompanyId() + 1
                , Category.Electricity
                , "happy electricity", "a happy electricity" ,
                DateUtils.getRandomSqlStartDate(),DateUtils.getRandomSqlEndDate(),
                18 , 1800 ,"urlasd"));
        companyFacade.addCoupon(new Coupon(companyFacade.getCompanyId()
                , Category.Electricity
                , "happy electricity", "a happy electricity" ,
                DateUtils.getRandomSqlStartDate(),DateUtils.getRandomSqlEndDate(),
                18 , 1800 ,"urlasd"));
        //endregion

        Company c1 = adminFacade.getOneCompany(3);
        //todo: add print table
        System.out.println(c1);

        adminFacade.addCustomer(new Customer("ran" , "manor" , "manr@asdad.com" , "234fs"));
//        System.out.println(adminFacade.getOneCustomer());
        Company tempCompany = adminFacade.getOneCompany(200);
        tempCompany.setEmail("blablabla");
//        tempCompany.setCoupons();
//        adminFacade.updateCompany((adminFacade.getOneCompany(200).setEmail("notrancorp@not.com")));
        adminFacade.updateCompany((adminFacade.getOneCompany(3).setEmail("notrancorp@not.com")));







//        CustomerFacade customerFacadeWrong;
//        CustomerFacade customerFacade;


    }

    private static void applicationStart(){
        DataBaseManager.dropDataBase();
        DataBaseManager.createDataBase();

        initMockData(new CompaniesDBDAO(),new CouponsDBDAO(), new CustomersDBDAO());

        CouponExpirationDailyJob job = new CouponExpirationDailyJob();
        Thread thread = new Thread(job);
        thread.start();
    }
    public static void initMockData(CompaniesDBDAO companiesDBDAO, CouponsDBDAO couponsDBDAO, CustomersDBDAO customersDBDAO) {
        companiesDBDAO.addCompany(Company.builder()
                .password("2142")
                .name("company a")
                .coupons(new ArrayList<Coupon>())
                .email("asdasd")
                .build());
        companiesDBDAO.addCompany(Company.builder()
                .password("2143")
                .name("company b")
                .coupons(new ArrayList<Coupon>())
                .email("asdasdasd")
                .build());
        //


        couponsDBDAO.addCoupon(Coupon.builder()
                .description("coupon a company a")
                .amount(3)
                .companyId(1)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Electricity)
                .price(12)
                .image("url")
                .title("coupon 1")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .description("coupon b company a")
                .amount(5)
                .companyId(1)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Electricity)
                .price(15)
                .image("url")
                .title("coupon 2")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .description("coupon a company b")
                .amount(8)
                .companyId(2)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Vacation)
                .price(18)
                .image("url123")
                .title("coupon 3")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .description("coupon B company b")
                .amount(1)
                .companyId(2)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Vacation)
                .price(18)
                .image("url12387")
                .title("coupon 4")
                .build());

        customersDBDAO.addCustomer(Customer.builder()
                .email("customer1@mail")
                .firstName("nir1")
                .lastName("katz1")
                .password("82342")
                .coupons(new ArrayList<Coupon>())
                .build());
        customersDBDAO.addCustomer(Customer.builder()
                .email("customer1@mail")
                .firstName("nir2")
                .lastName("katz2")
                .password("82342")
                .coupons(new ArrayList<Coupon>())
                .build());
        customersDBDAO.addCustomer(Customer.builder()
                .email("customer1@mail")
                .firstName("nir3")
                .lastName("katz3")
                .password("82342")
                .coupons(new ArrayList<Coupon>())
                .build());
        customersDBDAO.addCustomer(Customer.builder()
                .email("customer1@mail")
                .firstName("nir4")
                .lastName("katz4")
                .password("82342")
                .coupons(new ArrayList<Coupon>())
                .build());
        customersDBDAO.addCustomer(Customer.builder()
                .email("customer1@mail")
                .firstName("nir5")
                .lastName("katz5")
                .password("82342")
                .coupons(new ArrayList<Coupon>())
                .build());
    }



}

