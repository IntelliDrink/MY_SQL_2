package com.intellidrink.my_sql_2;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by David on 1/22/2015.
 */
public class Kiosk {
    protected static final String PREFERENCE_FILE = "com.intellidrink.my_sql_2.app" ;
    protected static final String KEY_KIOSK_NAME = "KIOSK_NAME";
    protected static final String KEY_KIOSK_PASSWORD = "KIOSK_PASSWORD";

    protected static final String BASE_URL = "http://192.168.1.4/IntelliDrink/";
    protected static final String UPDATE_USER_BALANCE = "update_customer_balance.php";
    protected static final String GET_CUSTOMER_INFO = "get_user_info.php";
    protected static final String UPDATE_METRIC_LOG = "update_metric_log.php";
    protected static final String GET_RECIPES = "get_recipes.php";
    protected static final String SELECT_QUERY = "select_query.php";
    protected static final String DELETE_QUERY = "delete_query.php";
    protected static final String INSERT_QUERY = "insert_query.php";
    protected static final String UPDATE_QUERY = "update_query.php";
    protected static final String CONFIGURE_KIOSK = "configure_kiosk_database.php";
    protected static final String URL_CONFIGURE_KIOSK = "customer_info.php";

    protected static final String TAG_SUCCESS = "success";

    public static final String TAG_AVAILABLE = "Available";

    // Common Table Column Names (Tags)
    public static final String TAG_ID = "ID";
    protected static final String TAG_CUSTOMER_NAME = "CustomerName";
    protected static final String TAG_CUSTOMER_DOB = "CustomerDOB";
    protected static final String TAG_CUSTOMER_RFID = "CustomerRFID";
    protected static final String TAG_RECIPE_ID = "RecipeID";
    protected static final String TAG_INGREDIENT_ID = "IngredientID";
    protected static final String TAG_PRICE = "Price";
    protected static final String TAG_DT_PURCHASED = "DT_Purchased";
    protected static final String TAG_LITERAL_NAME = "LiteralName";

    // Table ActiveTransactions
    protected static final String TABLE_ACTIVE_TRANSACTIONS = "ActiveTransactions";
    protected static final String TAG_CUSTOMER_ID = "CustomerID";

    //Table RecipeNeeds
    protected static final String TABLE_RECIPE_NEEDS = "RecipeNeeds";
    protected static final String TAG_UNITS = "Units";

    //Table Metrics
    protected static final String TABLE_METRICS = "Metrics";
    protected static final String TAG_Number = "Number";

    // Table Recipes
    protected static final String TABLE_RECIPES = "Recipes";
    protected static final String TAG_RECIPE_NAME = "RecipeName";
    protected static final String TAG_DESCRIPTION = "Description";

    //Table Customers
    protected static final String TABLE_CUSTOMERS = "Customers";
    protected static final String TAG_EMAIL = "EMAIL";
    protected static final String TAG_LAST_VISIT = "LAST_VISIT";
    protected static final String TAG_BALANCE = "BALANCE";
    protected static final String TAG_COOL_DOWN = "CoolDown";

    //Table GIngredient
    protected static final String TABLE_ACTIVE_GENERIC_INGREDIENT = "GIngredient";
    protected static final String TAG_GENERIC_NAME = "GenericName";
    protected static final String TAG_CUSTOM = "Custom";

    //Table LIngredient
    protected static final String TABLE_LITERAL_INGREDIENT = "LIngredient";
    protected static final String TAG_GENERIC_ID_NUMBER = "GenericIDNumber";

    //Table Kiosks
    protected static final String TABLE_KIOSKS = "Kiosks";
    protected static final String TAG_KIOSK_SLOT1 = "Slot1";
    protected static final String TAG_KIOSK_SLOT2 = "Slot2";
    protected static final String TAG_KIOSK_SLOT3 = "Slot3";
    protected static final String TAG_KIOSK_SLOT4 = "Slot4";
    protected static final String TAG_KIOSK_SLOT5 = "Slot5";
    protected static final String TAG_KIOSK_SLOT6 = "Slot6";
    protected static final String TAG_KIOSK_SLOT7 = "Slot7";
    protected static final String TAG_KIOSK_SLOT8 = "Slot8";

    protected String KNAME;
    protected String KPASSWD;
    private ArrayList<String> inventorySlot;

    /*
        Set Functions
    */

    protected void setKNAME(String passedInParam){
        KNAME = passedInParam;
    }
    protected void setKPASSWD(String passedInParam){
        KPASSWD = passedInParam;
    }



    /*
        Get Functions
    */

    protected String getKNAME(){
        return KNAME;
    }

    protected String getKPASSWD(){
        return KPASSWD;
    }

    /*
        Inventory Slot Methods
    */

    protected void addInventorySlot(String ingredient){
        inventorySlot.add(ingredient);
    }

    protected int inventorySlotSize(){
        return inventorySlot.size();
    }

    protected String getInventorySlot(int position){
        return inventorySlot.get(position);
    }

    protected void deleteInventorySlot(int position){
        inventorySlot.remove(position);
    }

    protected void resetInventory(){
        for(int i = 0; i < inventorySlotSize(); i++ ){
            inventorySlot.remove(i);
        }
    }

}
