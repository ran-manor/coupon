package Beans;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
//todo: try to remove allargsconstructor (check where builder is used)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon {
    private long id;
    private long companyId;
    private Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;

    /** Constructor without setting id. */
    public Coupon(long companyId, Category category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
        this.companyId = companyId;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }
    /** overrides lombok's setter so id couldnt be set directly.*/
    private void setId(long id) {
        this.id = id;
    }
    /** overrides lombok's setter so companyid couldnt be set directly.*/
    private void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}

