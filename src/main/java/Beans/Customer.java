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


    /**
     * Overrides default constructor so id could not be set through it
     * also, sets his coupons.
     * @param firstName customer's first name.
     * @param lastName customer's last name.
     * @param email customer's email.
     * @param password customer's password.
     * @param coupons customer's coupons.
     */
    public Customer(String firstName, String lastName, String email, String password, List<Coupon> coupons) {
        this(firstName, lastName, email, password);
        this.coupons = coupons;
    }

    /**
     * Overrides default constructor so id could not be set through it.
     * @param firstName customer's first name.
     * @param lastName customer's last name.
     * @param email customer's email.
     * @param password customer's password.
     */
    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /**
     * Sets the customer's first name.
     * allows update inline.
     * @param firstName customer's first name to be set.
     * @return this customer after the update.
     */
    public Customer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;

    }

    /**
     * Sets the customer's last name.
     * allows update inline.
     * @param lastName customer's last name to be set.
     * @return this customer after the update.
     */
    public Customer setLastName(String lastName) {
        this.lastName = lastName;
        return this;

    }

    /**
     * Sets the customer's email.
     * allows update inline.
     * @param email customer's email to be set.
     * @return this customer after the update.
     */
    public Customer setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Override's lomboks @Data password setter from public to private.
     * by so, password could not be changed.
     * @param password the customer's password.
     */
    private void setPassword(String password) {
        this.password = password;
    }

    /**
     * Override's lombok's @Data id setter from public to private.
     * by so, id could not be changed.
     * @param id the customer's id.
     */
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
