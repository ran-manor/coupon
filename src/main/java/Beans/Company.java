package Beans;

import DBDAO.CompaniesDBDAO;
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
/**
 * Data bean representing a company.
 */
public class Company {
    private long id;
    private String name;
    private String email;
    private String password;
    private ArrayList<Coupon> coupons = new ArrayList<>();

    /** Sets the company email and returns this company after the update, allows updates inline.
     * @return returns this to use inline updates.*/
    public Company setEmail(String email){
        this.email = email;
        return this;
    }

    /**
     * Override's lomboks @Data password setter from public to private.
     * by so, password could not be changed.
     * @param password the company's password.
     */
    private void setPassword(String password){
    }

    /**
     * constructor that uses the default constructor and setting company's coupons.
     * @param name company's name.
     * @param email company's email.
     * @param password company's password.
     * @param coupons company's coupons.
     */
    public Company(String name, String email, String password, ArrayList<Coupon> coupons) {
        this(name , email , password);
        this.coupons = coupons;
    }

    /**
     * Default Company constructor.
     * @param name company's name.
     * @param email company's email.
     * @param password company's password.
     */
    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Override's lomboks @Data id setter from public to private.
     * by so, id could not be changed.
     * @param id company's id.
     */
    private void setId(long id) {
        this.id = id;
    }

    /**
     * Override's lomboks @Data name setter from public to private.
     * by so, name could not be changed.
     * @param name compny's name.
     */
    private void setName(String name) {
        this.name = name;
    }
    /** Sets the company coupons and returns this company after the update, allows updates inline. */
    public Company setCoupons(ArrayList<Coupon> coupons){
        this.coupons = coupons;
        return this;
    }

    /**
     * overides default toString() method to be more readable.
     * @return the finalized string.
     */
    @Override
    public String toString() {
        return ArtUtils.ANSI_CYAN+ "Company: " +
                "\nid: " + id +
                "\nname:'" + name + '\'' +
                "\nemail:" + email + '\'' +
                "\npassword: " + password + ArtUtils.ANSI_RESET +
                "\ncoupons:\n" + couponPrinter() +

                "\n===========================================================================================\n";
    }

    /**
     * readable format to read all the company's coupons.
     * @return the finalized string.
     */
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
