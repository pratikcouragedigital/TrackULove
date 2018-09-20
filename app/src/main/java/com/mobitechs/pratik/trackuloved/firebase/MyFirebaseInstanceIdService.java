package com.mobitechs.pratik.trackuloved.firebase;

import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.mobitechs.pratik.trackuloved.sessionManager.SessionManager;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String REG_TOKEN ="REG_TOKEN";
    SessionManager sessionManager;
    String deviceId;
    String token;
    String manufacturer,model,fingerPrint,device,id;

    @Override
    public void onTokenRefresh() {
        token = FirebaseInstanceId.getInstance().getToken();
        manufacturer = Build.MANUFACTURER;
        fingerPrint = Build.FINGERPRINT;
//        device = Build.DEVICE;
//        id = Build.ID;
        model = Build.MODEL;

        Log.d(REG_TOKEN,"Your Token No:-"+ token);

        createDeviceId();
    }

    public void createDeviceId() {
        deviceId = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);

        sessionManager = new SessionManager(this);
        sessionManager.createUserFirebaseNotificationToken(token, deviceId,manufacturer,model,fingerPrint);
    }
}
