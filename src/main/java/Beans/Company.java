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
}
