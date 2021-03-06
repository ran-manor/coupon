import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DBDAO.CompaniesDBDAO;
import DBDAO.CouponsDBDAO;
import DBDAO.CustomersDBDAO;

import Facade.AdminFacade;
import Facade.CompanyFacade;
import Facade.CustomerFacade;
import exceptions.CouponSystemExceptions;
import login.ClientType;
import login.LoginManager;
import sql.ConnectionPool;
import sql.DataBaseManager;
import threads.CouponExpirationDailyJob;
import utils.ArtUtils;
import utils.DateUtils;
import utils.TablePrinter;

import java.util.ArrayList;


public class Tester {
    public static void main(String[] args) {

        applicationStart();
        CouponExpirationDailyJob job = new CouponExpirationDailyJob();
        Thread dailyJob = new Thread(job);
        dailyJob.start();

        AdminFacade adminFacade = null;
        try {
            adminFacade = LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }

        CustomerFacade customerFacade = null;
        try {
            customerFacade = LoginManager.getInstance().login("alon@mintz.com", "34567", ClientType.CUSTOMER);
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        CompanyFacade companyFacade = null;
        try {
            companyFacade = LoginManager.getInstance().login("all@in.com", "4567", ClientType.COMPANY);
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //region admin facade in action
        //add company method
        try {
            adminFacade.addCompany(Company.builder()
                    .password("1111")
                    .name("Tester Company")
                    .coupons(new ArrayList<Coupon>())
                    .email("Tester@test.com")
                    .build());
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        System.out.println("Add company method:");
        try {
            System.out.println(adminFacade.getOneCompany(6));
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //update company method, get one company method
        try {
            Company companyToUpdate = adminFacade.getOneCompany(1);
            System.out.println("Company to update is:");
            System.out.println(companyToUpdate);
            companyToUpdate.setEmail("New Email");
            adminFacade.updateCompany(companyToUpdate);
            System.out.println("Update company method (email update):");
            System.out.println(adminFacade.getOneCompany(1));
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //get all companies method
        System.out.println("Get all companies method:");
        try {
            System.out.println(adminFacade.getAllCompanies());
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //delete company method
        try {
            System.out.println("Company to delete:\n" +
                    adminFacade.getOneCompany(6));
            adminFacade.deleteCompany(6);
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        System.out.println();
        System.out.println("Add customer method:");
        //add customer method
        try {
            adminFacade.addCustomer(Customer.builder()
                    .email("nadav@honig.com")
                    .firstName("nadav")
                    .lastName("honig")
                    .password("22222")
                    .coupons(new ArrayList<Coupon>())
                    .build());
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //get one customer method
        try {
            System.out.println(adminFacade.getOneCustomer(6));
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        System.out.println("All customers:");
        //get all customers method
        try {
            TablePrinter.print(adminFacade.getAllCustomers());
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //update customer method
        try {
            Customer customerToUpdate = adminFacade.getOneCustomer(1);
            System.out.println("Customer to update: " + customerToUpdate.getFirstName() + " " + customerToUpdate.getLastName());
            customerToUpdate.setLastName("new last name");
            adminFacade.updateCustomer(customerToUpdate);
            System.out.println("All customers after nir's last name was updated:");
            TablePrinter.print(adminFacade.getAllCustomers());
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //delete customer method
        try {
            System.out.println("Customer to delete: " +
                    adminFacade.getOneCustomer(6).getFirstName());
            adminFacade.deleteCustomer(6);
            System.out.println("All customers after nadav was deleted");
            TablePrinter.print(adminFacade.getAllCustomers());
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //endregion
        //region company facade in action
        //get company details method
        try {
            Company showCompany = companyFacade.getCompanyDetails();
            System.out.println(showCompany.getName() + " company details:");
            System.out.println(showCompany);
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //get all company's coupons method
        try {
            System.out.println("All of the company coupons:");
            TablePrinter.print(companyFacade.getCompanyCoupons());
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //get all company's coupons under max price method
        try {
            System.out.println("Company coupons max price 50:");
            TablePrinter.print(companyFacade.getCompanyCoupons(50));
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //get all company's coupons by category method
        try {
            System.out.println("Company Tattoo coupons:");
            TablePrinter.print(companyFacade.getCompanyCoupons(Category.Tattoos));
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //add coupon method
        try {
            companyFacade.addCoupon(Coupon.builder()
                    .companyId(4)
                    .description("We love jokers!")
                    .amount(5)
                    .startDate(DateUtils.getRandomSqlStartDate())
                    .endDate(DateUtils.getRandomSqlEndDate())
                    .category(Category.Tattoos)
                    .price(75)
                    .image("url")
                    .title("Joker tattoo")
                    .build());
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        System.out.println("All of the company coupons after adding another tattoo coupon:");
        try {
            TablePrinter.print(companyFacade.getCompanyCoupons());
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //update coupon method
        try {
            Coupon couponToUpdate = companyFacade.getCompanyCoupons().get(4);
            couponToUpdate.setTitle("New and better LIMOUSINE");
            companyFacade.updateCoupon(couponToUpdate);
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        try {
            System.out.println("All of the company coupons after updating limousine coupon:");
            TablePrinter.print(companyFacade.getCompanyCoupons());
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //delete coupon method
        try {
            companyFacade.deleteCoupon(26);
            System.out.println("All of the company coupons after deleting Joker tattoo coupon:");
            TablePrinter.print(companyFacade.getCompanyCoupons());
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //endregion
        //region customer facade in action
        System.out.println("Logged in customer details:");
        //get customer details method
        try {
            TablePrinter.print(customerFacade.getCustomerDetails());
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //purchase coupon by inserting coupon method
        try {
            customerFacade.purchaseCoupon(customerFacade.getAllAvailableCoupons().get(7));
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //purchase coupon by inserting couponID method
        try {
            customerFacade.purchaseCoupon(3);
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        try {
            customerFacade.purchaseCoupon(13);
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        try {
            customerFacade.purchaseCoupon(17);
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        try {
            customerFacade.purchaseCoupon(20);
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //get all customer's coupons method
        System.out.println("Logged in customer coupons after purchasing:");
        try {
            TablePrinter.print(customerFacade.getCustomersCoupons());
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //get all customer's coupons under max price method
        System.out.println("Logged in customer coupons max price 60:");
        try {
            TablePrinter.print(customerFacade.getCustomersCoupons(60));
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //get all customer's coupons by category method
        System.out.println("Logged in customer tattoo coupons:");
        try {
            TablePrinter.print(customerFacade.getCustomersCoupons(Category.Tattoos));
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //showing that after a company was deleted, it's coupons are removed from the customer's purchased coupons
        try {
            adminFacade.deleteCompany(4);
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        System.out.println("Logged in customer's coupons after erasing company id 4:");
        try {
            TablePrinter.print(customerFacade.getCustomersCoupons());
        } catch (CouponSystemExceptions err) {
            System.out.println(err.getMessage());
        }
        //endregion

        applicationEnd();
        dailyJob.interrupt();


    }

    private static void applicationEnd() {
        try {
            ConnectionPool.getInstance().closeAllConnection();
        } catch (InterruptedException err) {
            System.out.println(err.getMessage());
        }

    }

    private static void applicationStart() {
        System.out.println(ArtUtils.YELLOW_BRIGHT + ArtUtils.banner + ArtUtils.ANSI_RESET);
        DataBaseManager.dropDataBase();
        DataBaseManager.createDataBase();

        initMockData(new CompaniesDBDAO(), new CouponsDBDAO(), new CustomersDBDAO());


    }

    public static void initMockData(CompaniesDBDAO companiesDBDAO, CouponsDBDAO couponsDBDAO, CustomersDBDAO customersDBDAO) {
        companiesDBDAO.addCompany(Company.builder()
                .password("1234")
                .name("Sick Adventures")
                .coupons(new ArrayList<Coupon>())
                .email("sick@adventures.com")
                .build());
        companiesDBDAO.addCompany(Company.builder()
                .password("2345")
                .name("Rocky Power")
                .coupons(new ArrayList<Coupon>())
                .email("rocky@power.com")
                .build());
        companiesDBDAO.addCompany(Company.builder()
                .password("3456")
                .name("Mystic Life")
                .coupons(new ArrayList<Coupon>())
                .email("mystic@life.com")
                .build());
        companiesDBDAO.addCompany(Company.builder()
                .password("4567")
                .name("All In")
                .coupons(new ArrayList<Coupon>())
                .email("all@in.com")
                .build());
        companiesDBDAO.addCompany(Company.builder()
                .password("5678")
                .name("Viva La Vida")
                .coupons(new ArrayList<Coupon>())
                .email("viva@lavida.com")
                .build());

        // coupons for company id 1
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(1)
                .description("Tasty Hamburger!!!")
                .amount(5)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Food)
                .price(15)
                .image("url")
                .title("Sick Hamburger")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(1)
                .description("Fly in the Sky")
                .amount(10)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Xtreme)
                .price(20)
                .image("url")
                .title("Sky Dive")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(1)
                .description("Do a random tattoo!!!")
                .amount(7)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Tattoos)
                .price(100)
                .image("url")
                .title("Random Tattoo")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(1)
                .description("What a sick island")
                .amount(10)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Vacation)
                .price(50)
                .image("url")
                .title("Ibiza")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(1)
                .description("vroom vroom!")
                .amount(5)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Cars)
                .price(15)
                .image("url")
                .title("Lamboorgini")
                .build());

        // coupons for company id 2
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(2)
                .description("Strong Pizza!!!")
                .amount(5)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Food)
                .price(15)
                .image("url")
                .title("Rocky Pizza")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(2)
                .description("Travel the mountain")
                .amount(10)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Xtreme)
                .price(70)
                .image("url")
                .title("Rock Climbing")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(2)
                .description("Immortalize the one and only!")
                .amount(3)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Tattoos)
                .price(50)
                .image("url")
                .title("Rocky Balboa Tattoo")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(2)
                .description("The PIZZA nation!")
                .amount(10)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Vacation)
                .price(100)
                .image("url")
                .title("Italy")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(2)
                .description("Made in Italy")
                .amount(8)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Cars)
                .price(100)
                .image("url")
                .title("Alpha Romeo")
                .build());

        // coupons for company id 3
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(3)
                .description("India's finest dish")
                .amount(5)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Food)
                .price(15)
                .image("url")
                .title("Chicken Tika")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(3)
                .description("Week long track")
                .amount(12)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Xtreme)
                .price(20)
                .image("url")
                .title("Mystical journey")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(3)
                .description("Spiritual body tattoo")
                .amount(10)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Tattoos)
                .price(50)
                .image("url")
                .title("Ying Yang tattoo")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(3)
                .description("Mama India")
                .amount(8)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Vacation)
                .price(90)
                .image("url")
                .title("India")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(3)
                .description("Slow but wonderful")
                .amount(3)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Cars)
                .price(40)
                .image("url")
                .title("Riqsha")
                .build());

        // coupons for company id 4
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(4)
                .description("Two person dinner")
                .amount(5)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Food)
                .price(15)
                .image("url")
                .title("Gordon Ramsey")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(4)
                .description("Don't come if you ain't gonna win")
                .amount(10)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Xtreme)
                .price(60)
                .image("url")
                .title("Gambling tournament")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(4)
                .description("Have your body love the cards")
                .amount(2)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Tattoos)
                .price(50)
                .image("url")
                .title("Ace of spades tattoo")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(4)
                .description("The capital of gambling")
                .amount(5)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Vacation)
                .price(15)
                .image("url")
                .title("Las Vegas")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(4)
                .description("Drive like the KING you are")
                .amount(40)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Cars)
                .price(60)
                .image("url")
                .title("Limousine")
                .build());

        // coupons for company id 5
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(5)
                .description("Traditional spanish tastes")
                .amount(7)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Food)
                .price(5)
                .image("url")
                .title("Tapas")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(5)
                .description("Ole!")
                .amount(10)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Xtreme)
                .price(15)
                .image("url")
                .title("Bull fight")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(5)
                .description("Just for fun")
                .amount(10)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Tattoos)
                .price(100)
                .image("url")
                .title("Tomato tattoo")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(5)
                .description("Casa Del Papel")
                .amount(15)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Vacation)
                .price(70)
                .image("url")
                .title("Madrid")
                .build());
        couponsDBDAO.addCoupon(Coupon.builder()
                .companyId(5)
                .description("Get ready for a crazy time")
                .amount(40)
                .startDate(DateUtils.getRandomSqlStartDate())
                .endDate(DateUtils.getRandomSqlEndDate())
                .category(Category.Cars)
                .price(25)
                .image("url")
                .title("Bull ride")
                .build());


        customersDBDAO.addCustomer(Customer.builder()
                .email("nir@katz.com")
                .firstName("nir")
                .lastName("katz")
                .password("12345")
                .coupons(new ArrayList<Coupon>())
                .build());
        customersDBDAO.addCustomer(Customer.builder()
                .email("ran@manor.com")
                .firstName("ran")
                .lastName("manor")
                .password("23456")
                .coupons(new ArrayList<Coupon>())
                .build());
        customersDBDAO.addCustomer(Customer.builder()
                .email("alon@mintz.com")
                .firstName("alon")
                .lastName("mintz")
                .password("34567")
                .coupons(new ArrayList<Coupon>())
                .build());
        customersDBDAO.addCustomer(Customer.builder()
                .email("zeev@mindali.com")
                .firstName("zeev")
                .lastName("mindali")
                .password("45678")
                .coupons(new ArrayList<Coupon>())
                .build());
        customersDBDAO.addCustomer(Customer.builder()
                .email("barak@hamdani.com")
                .firstName("barak")
                .lastName("hamdani")
                .password("56789")
                .coupons(new ArrayList<Coupon>())
                .build());
    }


}

