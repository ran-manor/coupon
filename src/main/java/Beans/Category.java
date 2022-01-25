package Beans;

public enum Category {
    Food,
    Xtreme,
    Cars,
    Vacation,
    Tattoos;

    public final int value = 1 + ordinal();

    public static Category getCategoryById (int category_id){
       Category returnCategory = null;
        for (Category item : Category.values()){
            if (item.value == category_id){
                returnCategory = item;
                break;
            }
        }
        return returnCategory;
    }
}
