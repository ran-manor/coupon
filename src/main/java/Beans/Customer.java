package Beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Coupon> coupons = new ArrayList<>();

    private void setPassword(String password){
    }

    public Customer(String firstName, String lastName, String email, String password, List<Coupon> coupons) {
        this(firstName, lastName, email, password);
        this.coupons = coupons;
    }

    public Customer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;

    }

    public Customer setLastName(String lastName) {
        this.lastName = lastName;
        return this;

    }

    public Customer setEmail(String email) {
        this.email = email;
        return this;
    }

    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }


    private void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons:\n" + couponPrinter() +
                '}'+
                "\n===========================================================================================\n";
    }

    private String couponPrinter(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Coupon coupon:coupons) {
            sb.append(coupon+"\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
