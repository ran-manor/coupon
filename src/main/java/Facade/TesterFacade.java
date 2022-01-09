package Facade;

public class TesterFacade {
    public static void main(String[] args) {
        CustomerFacade customerFacade = new CustomerFacade();


        System.out.println(customerFacade.getCustomersCoupons());
    }
}
