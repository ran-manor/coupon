package Beans;

import DBDAO.CompaniesDBDAO;
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
public class Company {
    private long id;
    private String name;
    private String email;
    private String password;
    private ArrayList<Coupon> coupons = new ArrayList<>();

    public Company setEmail(String email){
        this.email = email;
        return this;
    }

    private void setPassword(String password){
    }

    public Company(String name, String email, String password, ArrayList<Coupon> coupons) {
        this(name , email , password);
        this.coupons = coupons;
    }
    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    private void setId(long id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    public Company setCoupons(ArrayList<Coupon> coupons){
        this.coupons = coupons;
        return this;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", \ncoupons:\n" + couponPrinter() +
                '}'+
                "\n===========================================================================================";
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
