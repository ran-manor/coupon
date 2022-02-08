package Beans;

import lombok.*;

import java.util.Date;

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

    /**
     * Coupon Constructor without setting an id.
     * @param companyId coupon's companyID.
     * @param category coupon's category.
     * @param title coupon's title.
     * @param description coupon's description.
     * @param startDate coupon's start date.
     * @param endDate coupon's expiry date.
     * @param amount coupon's amount.
     * @param price coupon's price.
     * @param image coupon's image.
     */
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
    /**
     * Override's lombok's @Data id setter from public to private.
     * by so, coupon's id could not be changed.
     * @param id the coupon's id.
     */
    private void setId(long id) {
        this.id = id;
    }
    /**
     * Override's lombok's @Data companyID setter from public to private.
     * by so, coupon's companyID could not be changed.
     * @param companyId the coupon's companyID.
     */
    private void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}

