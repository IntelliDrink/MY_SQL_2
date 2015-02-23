package com.intellidrink.my_sql_2;

/**
 * Created by David on 1/27/2015.
 */
public class Customer {
    protected String userName;
    protected String RFIDCode;
    protected Float balance;

    /*
        Set Functions
    */
    protected void setUserName(String passedInUserName){
        userName = passedInUserName;
    }

    protected void setRFIDCode(String passedInRFIDCode){
        RFIDCode = passedInRFIDCode;
    }
    protected void setBalance(Float passedInBalance){
        balance = passedInBalance;
    }
    /*
        Get Functions
    */
    protected String getUserName(){
        return RFIDCode;
    }

    protected String getRFIDCode(){
        return RFIDCode;
    }

    protected String getBalance(){
        return RFIDCode;
    }

}
