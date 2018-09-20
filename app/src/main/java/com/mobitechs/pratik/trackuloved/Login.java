package com.mobitechs.pratik.trackuloved;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.mobitechs.pratik.trackuloved.sessionManager.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText txtUserEmailMobile;
    private EditText txtPassword;
    // private TextView txtForgotPassword;
    private Button btnSignIn;
    TextView btnRegister;
    private ProgressDialog progressDialog;

    SessionManager sessionManager;
    private String userId,userPassword,DeviceName,manufacturer,model,token,deviceId,method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        HashMap<String, String> userToken = sessionManager.getUserFirebaseNotificationToken();
        token = userToken.get(SessionManager.KEY_TOKEN);
        deviceId = userToken.get(SessionManager.KEY_DEVICEID);
        manufacturer = userToken.get(SessionManager.KEY_MANUFACTURER);
        model = userToken.get(SessionManager.KEY_MODEL);

        txtUserEmailMobile = (EditText) findViewById(R.id.txtUserEmailMobile);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
//        txtForgotPasswordgotPassword = (TextView) findViewById(R.id.lblforgetpassword);
        btnRegister = (TextView) findViewById(R.id.btnRegister);
        btnSignIn = (Button) findViewById(R.id.btnlogin);

        //txtForgotPassword.setVisibility(View.GONE);
        btnSignIn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
//        txtForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnlogin) {
            userId = txtUserEmailMobile.getText().toString();
            userPassword = txtPassword.getText().toString();

            if (txtUserEmailMobile.getText().toString().isEmpty()) {
                Toast.makeText(Login.this, "Please Enter Your User Id.", Toast.LENGTH_LONG).show();
            } else if (txtPassword.getText().toString().isEmpty()) {
                Toast.makeText(Login.this, "Please Enter Password.", Toast.LENGTH_LONG).show();
            } else {
                progressDialog = new ProgressDialog(Login.this);
                progressDialog.setMessage("Logging In.");
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);

                LoginRequest();
            }
        }
        else if (v.getId() == R.id.btnlogin) {
            Intent gotoRegister = new Intent(this,Register.class);
            startActivity(gotoRegister);

        }

    }

    private void LoginRequest() {
        method = "Login";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("EmailMobile", userId);
            jsonObject.put("Password", userPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = getString(R.string.url) + method;

        AndroidNetworking.post(url)
                .addJSONObjectBody(jsonObject)
                .setTag(method)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressDialog.dismiss();
                            String res = response.getString("responseDetails");
                            if (res.equals("Invalid UserName & Password")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                builder.setTitle("Result");
                                builder.setMessage("Invalid UserName or Password");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface alert, int which) {
                                        // TODO Auto-generated method stub
                                        //Do something
                                        alert.dismiss();
                                    }
                                });
                                AlertDialog alert1 = builder.create();
                                alert1.show();
                            } else {
                                sessionManager = new SessionManager(Login.this);
                                try {
                                    JSONArray jArr = response.getJSONArray("responseDetails");
                                    for (int count = 0; count < jArr.length(); count++) {
                                        JSONObject obj = jArr.getJSONObject(count);

                                        AddDeviceDetails();
                                        Intent gotoOTP = new Intent(Login.this, MainActivity.class);
                                        gotoOTP.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


                                    }
                                } catch (JSONException ex) {
                                    ex.printStackTrace();
                                }
                            }

                        } catch (Exception e) {
                            progressDialog.dismiss();
                            e.getMessage();
                            Toast.makeText(Login.this, "Exception" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        progressDialog.dismiss();
                        error.getErrorDetail();
                        Toast.makeText(Login.this, "Error" + error.getErrorDetail(), Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void AddDeviceDetails() {
        method ="Add_User_Device_Details";
        DeviceName = manufacturer +" "+ model;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("User_id", userId);
            jsonObject.put("TokenNo", token);
            jsonObject.put("Device_id", deviceId);
            jsonObject.put("DeviceName", DeviceName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = getString(R.string.url) + method;

        AndroidNetworking.post(url)
                .addJSONObjectBody(jsonObject)
                .setTag(method)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //do nothing
                    }

                    @Override
                    public void onError(ANError error) {
                        AddDeviceDetails();
                    }
                });
    }

}
