package com.quickblox.sample.videochat.java.constants;

public class ApiConstants {


//  public static String BASE_URL="http://18.188.72.6";
//   public static String BASE_URL="http://3.14.133.206";   //testing server
//  public static String BASE_URL="http://113.193.29.67:8001";  //Dt local server
//   public static String BASE_URL="http://15.207.240.65:8001";//new local
   public static String BASE_URL="http://signable.live";
//  public static String BASE_URL="http://15.207.240.65";
//   public static String BASE_URL="http://15.207.240.65:8001";
//   public static String BASE_URL="http://signable.live";

   public static String CREATE_ACCOUNT="/createAccount/";
   public static String UPDATE_PROFILE="/updateuserdetails/id/";
   public static String GET_TOKEN = "/api-token-auth/";
   public static String GET_LANGUAGE = "/getLanguages/";
   public static String DEVICE_CHECK = "/isdeviceregistered/";
   public static String DEVICE_REGISTRATION = "/registerendpoint/";
   public static String GET_NOTIFICATION = "/getrecentnotification";

   public static String LOGIN_USER="/login/";
   public static String CHECK_OTP="/checkOTP/";
   public static String GET_OTP="/getOTP/";
   public static String HAVE_CALL="/haveCall";
   public static String ACCEPT_CALL="/acceptCall/";
   public static String GET_STATUS="/getStatus/";
   public static String START_CALL="/startCall/";
   public static String START_LISTENING="/startListening/";
   public static String STOP_LISTENING="/stopListening/";
   public static String VIDEO_CALLING_ROOM="/startVideoCall/";
   public static String END_CALL="/endCall/";
   public static String SEND_DURATION="/sendDuration/";
   public static String Contact_UNAVAILABLE="/contact_unavailable/";
   public static String interpretername="/interpretername/";
   //New added code
   public static final String SERVICE_DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";

}
