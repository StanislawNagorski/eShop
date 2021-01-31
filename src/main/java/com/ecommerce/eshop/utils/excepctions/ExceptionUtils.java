package com.ecommerce.eshop.utils.excepctions;

public class ExceptionUtils {

    public static final String PRODUCT_CANNOT_SAVE = "Cannot save product with id: %d, it already exists";
    public static final String PRODUCT_CANNOT_FIND = "Cannot find product with id: %d";
    public static final String PRODUCT_CANNOT_DEACTIVATE = "Product of id: %d was already deactivated";
    public static final String CATEGORY_CANNOT_SAVE_DUPLICATE = "Category named: %s already exists in DB";
    public static final String CATEGORY_NOT_FOUND_BY_NAME = "Could not found category named: %s";
    public static final String CATEGORY_NOT_FOUND_BY_ID = "Could not found category with id: %d";
    public static final String ORDER_CANNOT_SAVE = "Cannot save order with id: %d, it already exists, use update instead";
    public static final String ORDER_WITHOUT_ANY_PRODUCTS = "Cannot save order without any products";
    public static final String ORDER_NOT_FOUND = "Cannot find order with id: %d";
    public static final String ORDER_CONTAINING_PRODUCT_NOT_FOUND = "Cannot find order that contain product with id: %d";
    public static final String ORDER_DATES_INCORRECT_FORMAT = "Date format is incorrect, plz use yyyy-mm-dd";
    public static final String ORDER_STATUS_NOT_FOUND = "Status of: %s do not exist";
    public static final String USER_CANNOT_SAVE = "Cannot save user with login: %s, it already exists";
    public static final String USER_NOT_FOUND = "Cannot find user: %s";

}
