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

    private void setPassword(String password){
    }

    public Company(String name, String email, String password, ArrayList<Coupon> coupons) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }
}
