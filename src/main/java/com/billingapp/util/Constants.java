package com.billingapp.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Constants {
    public static final String USER_NOT_FOUND="UserDetails not present in system!!";
    public static final String CROP_DETAILS_NOT_FOUND="Crop details not present in system!!";
    public static final String CROP_SETTING_NOT_FOUND="Crop setting not present in system!!";
    public static final String INVALID_OLD_PASSWORD="Invalid old password, please provide correct password!!";
    public static final String USER_NOT_FOUND_WITH_USE_NAME="User not found with username";
    public static final String CUSTOMER_DETAILS_NOT_FOUND="Customer details not present in system!!";
    public static final String AGENT_DETAILS_NOT_FOUND="Agent details not present in system!!";
    public static final String DISCOUNT_DETAILS_NOT_FOUND="Discount details not present in system!!";
    public static final String CUSTOMER_DETAILS_NOT_SAVE="Customer details not saved in system!!";
    public static final String CUSTOMER_PAYMENT_DETAILS_NOT_FOUND="Customer payment details not present in system!!";
    public static final String CUSTOMER_PAYMENT_ALREADY_DONE="Customer payment already paid!!";
    public static final Boolean ACTIVE_STATUS=true;
    public static final Boolean DI_ACTIVE_STATUS=false;
    public static final String ENABLED="Enabled";
    public static final String DISABLED="Disabled";
    public static final String DASH="-";
    public static final Integer ADMIN_TYPE=21;
    public static final Integer PERCENTAGE=1;
    public static final Integer RUPEES=2;
    public static final Integer UNPAID=1;
    public static final Integer PARTIALLY_PAID=2;
    public static final Integer PAID=3;
    public static final String UNPAID_STATUS="Unpaid";
    public static final String PARTIALLY_PAID_STATUS="Partially Paid";
    public static final String PAID_STATUS="Paid";
    public static final String PERCENTAGE_VALUE="Percentage(%)";
    public static final String RUPEES_VALUE="Rupees(Rs)";

    public static final String formatedDate(String inputDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("dd MMMM yyyy");
        String convertedDate = null;
        try {
            Date date = format.parse(inputDate);
            convertedDate=format2.format(date);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }
    public static final String setStatus(Boolean value){
        if(value.equals(ACTIVE_STATUS)){
            return ENABLED;
        }else if(value.equals(DI_ACTIVE_STATUS)){
            return DISABLED;
        }else{
            return DASH;
        }
    }
    public static final String setDiscountType(Integer value){
        if(value.equals(PERCENTAGE)){
            return PERCENTAGE_VALUE;
        }else if(value.equals(RUPEES)){
            return RUPEES_VALUE;
        }else{
            return DASH;
        }
    }
    public static final String setPaymentStatus(Integer value){
        if(value.equals(UNPAID)){
            return UNPAID_STATUS;
        }else if(value.equals(PARTIALLY_PAID)){
            return PARTIALLY_PAID_STATUS;
        }else if(value.equals(PAID)){
            return PAID_STATUS;
        }else{
            return DASH;
        }
    }
}
