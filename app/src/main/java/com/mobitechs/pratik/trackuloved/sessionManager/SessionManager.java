package com.mobitechs.pratik.trackuloved.sessionManager;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.mobitechs.pratik.trackuloved.Login;
import com.mobitechs.pratik.trackuloved.MainActivity;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;      // Shared pref mode
    SessionManager sessionManager;

    // Sharedpref file name
    private static final String PREF_NAME = "Preference";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_OTP_CHECK = "IsOTPCheck";
    private static final String IS_DATASYNCH = "IsDataSynch";

    // Email address (make variable public to access from outside)
    public static final String KEY_USERID = "userId";
    public static final String KEY_USERTYPE = "userType";
    public static final String KEY_NAME = "name";
    public static final String KEY_CONTACTNO = "password";
    public static final String KEY_USEREMAIL = "userEmail";
    public static final String KEY_USERDOB = "Dob";
    public static final String KEY_STANDARD = "standard";
    public static final String KEY_DIVISION = "division";

    public static final String KEY_USERADDRESS = "userAddress";
    public static final String KEY_USERBLOODGROUP = "userBloodGroup";
    public static final String KEY_USERGRNO = "userGrNo";
    public static final String KEY_USERSWIPECARDNO = "userSwipeCard";
    public static final String KEY_USERROLLNO = "userRollNo";
    public static final String KEY_USERR_LOGO = "userPhoto";

    public static final String KEY_SCHOOLID = "schoolId";
    public static final String KEY_SCHOOLLOGO = "schoolLogo";
    public static final String KEY_SCHOOLNAME = "schoolName";
    public static final String KEY_SCHOOLCONTACTNO = "schoolContactNo";
    public static final String KEY_SCHOOLADDRESS = "schoolAddress";
    public static final String KEY_SCHOOLWEBSITE = "schoolWebsite";
    public static final String KEY_SCHOOLEMAIL = "schoolEmail";
    public static final String KEY_OTP = "otp";
    public static final String KEY_PIN = "pin";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_DEVICEID = "deviceId";

    public static final String KEY_MANUFACTURER = "manufacturer";
    public static final String KEY_MODEL = "model";
    public static final String KEY_FINGERPRINT = "fingerPrint";

    public static final String KEY_ISTEACHING_STAFF = "isTeachingStaff";
    public static final String KEY_SCHOOL_LAT = "latitude";
    public static final String KEY_SCHOOL_LONGI = "longitude";
    public static final String KEY_ISCLASS_TEACHER = "isClassTeacher";
    public static final String KEY_GPS_OUTWORK_COUNTER = "counter";
    String userType;

    public static final String smsListCount = "smsListCount";
    public static final String homeworkCount = "homeworkCount";
    public static final String newsCount = "newsCount";
    public static final String noticeCount = "noticeCount";

    public static final String smsListRead = "smsListRead";
    public static final String homeworkRead = "homeworkRead";
    public static final String newsRead = "newsRead";
    public static final String noticeRead = "noticeRead";

    public SessionManager(Context c) {
        this.context = c;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createUserLoginSession(String saveUserId, String saveUserType, String userFullName, String standard,String division, String userContactNo, String userEmail, String userDob, String userAddress, String userBloodGroup, String userGrNo, String userSwipeCard, String userRollNo, String userPhoto, String saveSchooId, String schoolLogo, String schoolName, String schoolContactNo, String schoolAddress, String schoolWebsite, String schoolEmail,String isTeachingStaff,String schoolLat,String schoolLongi, String isClassTeacher, String pin) {
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();

        editor.putString(KEY_USERID, saveUserId);
        editor.putString(KEY_NAME, userFullName);
        editor.putString(KEY_STANDARD, standard);
        editor.putString(KEY_DIVISION, division);
        editor.putString(KEY_USERTYPE, saveUserType);
        editor.putString(KEY_CONTACTNO, userContactNo);
        editor.putString(KEY_USEREMAIL, userEmail);
        editor.putString(KEY_USERDOB, userDob);
        editor.putString(KEY_USERADDRESS, userAddress);
        editor.putString(KEY_USERBLOODGROUP, userBloodGroup);
        editor.putString(KEY_USERGRNO, userGrNo);
        editor.putString(KEY_USERSWIPECARDNO, userSwipeCard);
        editor.putString(KEY_USERROLLNO, userRollNo);
        editor.putString(KEY_USERR_LOGO, userPhoto);
        editor.putString(KEY_SCHOOLID, saveSchooId);
        editor.putString(KEY_SCHOOLLOGO, schoolLogo);
        editor.putString(KEY_SCHOOLNAME, schoolName);
        editor.putString(KEY_SCHOOLCONTACTNO, schoolContactNo);
        editor.putString(KEY_SCHOOLADDRESS, schoolAddress);
        editor.putString(KEY_SCHOOLWEBSITE, schoolWebsite);
        editor.putString(KEY_SCHOOLEMAIL, schoolEmail);
        editor.putString(KEY_ISTEACHING_STAFF, isTeachingStaff);
        editor.putString(KEY_SCHOOL_LAT, schoolLat);
        editor.putString(KEY_SCHOOL_LONGI, schoolLongi);
        editor.putString(KEY_ISCLASS_TEACHER, isClassTeacher);
        editor.putString(KEY_PIN, pin);
       // editor.putString(KEY_OTP, otp);

//         commit changes
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_USERID, pref.getString(KEY_USERID, null));
        user.put(KEY_USERTYPE, pref.getString(KEY_USERTYPE, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_STANDARD, pref.getString(KEY_STANDARD, null));
        user.put(KEY_DIVISION, pref.getString(KEY_DIVISION, null));
        user.put(KEY_CONTACTNO, pref.getString(KEY_CONTACTNO, null));
        user.put(KEY_USEREMAIL, pref.getString(KEY_USEREMAIL, null));
        user.put(KEY_USERDOB, pref.getString(KEY_USERDOB, null));
        user.put(KEY_USERADDRESS, pref.getString(KEY_USERADDRESS, null));
        user.put(KEY_USERBLOODGROUP, pref.getString(KEY_USERBLOODGROUP, null));
        user.put(KEY_USERGRNO, pref.getString(KEY_USERGRNO, null));
        user.put(KEY_USERSWIPECARDNO, pref.getString(KEY_USERSWIPECARDNO, null));
        user.put(KEY_USERROLLNO, pref.getString(KEY_USERROLLNO, null));
        user.put(KEY_USERR_LOGO, pref.getString(KEY_USERR_LOGO, null));
        user.put(KEY_SCHOOLID, pref.getString(KEY_SCHOOLID, null));
        user.put(KEY_SCHOOLLOGO, pref.getString(KEY_SCHOOLLOGO, null));
        user.put(KEY_SCHOOLNAME, pref.getString(KEY_SCHOOLNAME, null));
        user.put(KEY_SCHOOLCONTACTNO, pref.getString(KEY_SCHOOLCONTACTNO, null));
        user.put(KEY_SCHOOLADDRESS, pref.getString(KEY_SCHOOLADDRESS, null));
        user.put(KEY_SCHOOLWEBSITE, pref.getString(KEY_SCHOOLWEBSITE, null));
        user.put(KEY_SCHOOLEMAIL, pref.getString(KEY_SCHOOLEMAIL, null));
        user.put(KEY_ISTEACHING_STAFF, pref.getString(KEY_ISTEACHING_STAFF, null));
        user.put(KEY_SCHOOL_LAT, pref.getString(KEY_SCHOOL_LAT, null));
        user.put(KEY_SCHOOL_LONGI, pref.getString(KEY_SCHOOL_LONGI, null));
        user.put(KEY_ISCLASS_TEACHER, pref.getString(KEY_ISCLASS_TEACHER, null));
        user.put(KEY_PIN, pref.getString(KEY_PIN, null));
       // user.put(KEY_OTP, pref.getString(KEY_OTP, null));

        // return user
        return user;
    }






    public void checkLogin() {
        HashMap<String, String> staffDetails = this.getUserDetails();
        String teachingStaff = staffDetails.get(KEY_ISTEACHING_STAFF);
        String classTeacher = staffDetails.get(KEY_ISCLASS_TEACHER);
        String is_std = staffDetails.get(KEY_STANDARD);
        String is_div = staffDetails.get(KEY_DIVISION);
        String pin = staffDetails.get(KEY_PIN);
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        } else {

                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
        }
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, Login.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        context.startActivity(i);
    }


    public void createUserFirebaseNotificationToken(String token, String deviceId,String manufacturer, String model,String fingerPrint) {
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_DEVICEID, deviceId);
        editor.putString(KEY_DEVICEID, manufacturer);
        editor.putString(KEY_DEVICEID, fingerPrint);
        editor.putString(KEY_DEVICEID, fingerPrint);
        editor.commit();
    }

    public HashMap<String, String> getUserFirebaseNotificationToken() {
        HashMap<String, String> user = new HashMap<String, String>();


        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        user.put(KEY_DEVICEID, pref.getString(KEY_DEVICEID, null));
        user.put(KEY_MANUFACTURER, pref.getString(KEY_MANUFACTURER, null));
        user.put(KEY_MODEL, pref.getString(KEY_MODEL, null));
        user.put(KEY_FINGERPRINT, pref.getString(KEY_FINGERPRINT, null));

        return user;
    }



}
