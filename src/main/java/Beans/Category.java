package Beans;

/** Category enum used in the coupons' category param. */
public enum Category {
    Food,
    Xtreme,
    Cars,
    Vacation,
    Tattoos;

   /** This field is to get the enum value that corresponds to the sql category id. */
    public final int value = 1 + ordinal();

    /**
     * Getter that matches sql category id to the matching enum value.
     * @param category_id id in sql.
     * @return returns the matching Category.
     * */
    public static Category getCategoryById (int category_id){
//       Category returnCategory = null;
//        for (Category item : Category.values()){
//            if (item.value == category_id){
//                returnCategory = item;
//                break;
//            }
//
//        }
        if (Category.values()[category_id -1] == null) {return null;}
        return Category.values()[category_id - 1];
    }
}
