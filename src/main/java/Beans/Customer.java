package Beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.ArtUtils;

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

    /** Override's lomboks @Data so password could not be changed. */
    private void setPassword(String password) {
    }
    /** Overrides default constructor so id could not be set through it and also sets coupons. */
    public Customer(String firstName, String lastName, String email, String password, List<Coupon> coupons) {
        this(firstName, lastName, email, password);
        this.coupons = coupons;
    }

    /** Constructor without setting id. */
    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /** Sets the customer first name and returns this customer after the update, allows updates inline. */
    public Customer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;

    }

    /** Sets the customer last name and returns this customer after the update, allows updates inline. */
    public Customer setLastName(String lastName) {
        this.lastName = lastName;
        return this;

    }


    /**
     * Sets the customer email and returns this customer after the update, allows updates inline.
     * @param email
     * @return
     */
    public Customer setEmail(String email) {
        this.email = email;
        return this;
    }


    /** Override's lomboks @Data so id could not be changed. */
    private void setId(long id) {
        this.id = id;
    }

    /**
     * overides default toString() method to be more readable.
     * @return the finalized string.
     */
    @Override
    public String toString() {
        return ArtUtils.ANSI_YELLOW + "Customer: " +
                "\nid: " + id +
                "\nfirstName: " + firstName +
                "\nlastName: " + lastName +
                "\nemail: " + email +
                "\npassword: " + password +
                ArtUtils.ANSI_RESET + "\ncoupons: \n" + couponPrinter() +
                '}' +
                "\n===========================================================================================\n";
    }

    /**
     * makes a more organized string representing a coupon.
     * @return the finalized string.
     */
    private String couponPrinter() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (!coupons.isEmpty()) {
            for (Coupon coupon : coupons) {
                sb.append(coupon + "\n");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
