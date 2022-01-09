package DBDAO;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import utils.DateUtils;

import java.util.ArrayList;

public class TesterDBDAO {
    public static void main(String[] args) {
        CustomersDBDAO customersDBDAO = new CustomersDBDAO();
        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
        CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
        ArrayList<Coupon> coupons = new ArrayList<>();


        initMockData(companiesDBDAO, couponsDBDAO, customersDBDAO);
        //todo: tests


        couponsDBDAO.addCouponPurchase(3 , 4);
        couponsDBDAO.addCouponPurchase(1 , 2);
        couponsDBDAO.addCouponPurchase(1 , 3);
        couponsDBDAO.addCouponPurchase(2 , 1);


        System.out.println(couponsDBDAO.getAllCouponPurchases());


    }


    //      Coupon testCoupon = couponsDBDAO.getOneCoupon(1) ;
    //System.out.println(couponsDBDAO.getAllCoupons());
    //      testCoupon.setAmount(5);
    //      couponsDBDAO.updateCoupon(testCoupon);


    public static void initMockData(CompaniesDBDAO companiesDBDAO , CouponsDBDAO couponsDBDAO , CustomersDBDAO customersDBDAO){
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
