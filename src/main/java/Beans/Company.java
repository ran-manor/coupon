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
public class Company {
    private int id;
    private String name;
    private String email;
    private String password;
    private List<Coupon> coupons = new ArrayList<>();

    private void setPassword(String password){
    }
}
