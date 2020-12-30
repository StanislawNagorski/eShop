package com.ecommerce.eshop.utils.exepctions;

public class ExceptionUtils {

    public static final String PRODUCT_CANNOT_SAVE = "Cannot save product with id: %d, it already exists";
    public static final String PRODUCT_CANNOT_FIND = "Cannot find product with id: %d";
    public static final String PRODUCT_CANNOT_DEACTIVATE = "Product of id: %d was already deactivated";
    public static final String CATEGORY_CANNOT_SAVE_DUPLICATE = "Category named: %s already exists in DB";
    public static final String CATEGORY_NOT_FOUND_BY_NAME = "Could not found category named: %s";
    public static final String CATEGORY_NOT_FOUND_BY_ID = "Could not found category with id: %d";

}
