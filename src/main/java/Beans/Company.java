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
/** Data bean representing a company*/
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

    /** Override's lomboks @Data so password could not be changed. */
    private void setPassword(String password){
    }

    /** uses the default constructor and updates coupons. */
    public Company(String name, String email, String password, ArrayList<Coupon> coupons) {
        this(name , email , password);
        this.coupons = coupons;
    }

    /** Default constructor.*/
    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    /** Override's lomboks @Data so id could not be changed. */
    private void setId(long id) {
        this.id = id;
    }
    /** Override's lomboks @Data so name could not be changed. */
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
