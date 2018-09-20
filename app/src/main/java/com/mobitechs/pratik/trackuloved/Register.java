package com.mobitechs.pratik.trackuloved;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class Register extends AppCompatActivity  implements View.OnClickListener {

    private EditText txtUserName,txtUserEmail,txtMobileNo,txtCity,txtPassword,txtConfPassword;

    private Button btnRegister;
    TextView btnlogin;
    private ProgressDialog progressDialog;

    SessionManager sessionManager;
    private String userName,userEmail,userMobile,userCity,userPassword,userConfPassword,DeviceName,manufacturer,model,token,deviceId,method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        HashMap<String, String> userToken = sessionManager.getUserFirebaseNotificationToken();
        token = userToken.get(SessionManager.KEY_TOKEN);
        deviceId = userToken.get(SessionManager.KEY_DEVICEID);
        manufacturer = userToken.get(SessionManager.KEY_MANUFACTURER);
        model = userToken.get(SessionManager.KEY_MODEL);
        
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtMobileNo = (EditText) findViewById(R.id.txtMobileNo);
        txtUserEmail = (EditText) findViewById(R.id.txtUserEmail);
        txtCity = (EditText) findViewById(R.id.txtCity);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfPassword = (EditText) findViewById(R.id.txtConfPassword);
        btnlogin = (TextView) findViewById(R.id.btnlogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);


        btnlogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegister) {
            userName = txtUserName.getText().toString();
            userEmail = txtUserEmail.getText().toString();
            userMobile = txtMobileNo.getText().toString();
            userCity = txtCity.getText().toString();
            userPassword = txtPassword.getText().toString();
            userConfPassword = txtConfPassword.getText().toString();

            if (txtUserEmail.getText().toString().isEmpty()) {
                Toast.makeText(Register.this, "Please Enter Your Email.", Toast.LENGTH_LONG).show();
            }
            else if (txtPassword.getText().toString().isEmpty()) {
                Toast.makeText(Register.this, "Please Enter Password.", Toast.LENGTH_LONG).show();
            }
            else if (txtUserName.getText().toString().isEmpty()) {
                Toast.makeText(Register.this, "Please Enter UserName.", Toast.LENGTH_LONG).show();
            }
            else if (txtMobileNo.getText().toString().isEmpty()) {
                Toast.makeText(Register.this, "Please Enter MobileNo.", Toast.LENGTH_LONG).show();
            }
            else if (txtCity.getText().toString().isEmpty()) {
                Toast.makeText(Register.this, "Please Enter City.", Toast.LENGTH_LONG).show();
            }
            else if (userPassword.equals(userConfPassword)) {
                Toast.makeText(Register.this, "Password not matting", Toast.LENGTH_LONG).show();
            }
            else {
                progressDialog = new ProgressDialog(Register.this);
                progressDialog.setMessage("Registering.Please Wait.");
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);

                RegisterRequest();
            }
        }
        else if (v.getId() == R.id.btnlogin) {
            Intent gotoRegister = new Intent(this,Login.class);
            startActivity(gotoRegister);

        }

    }

    private void RegisterRequest() {
        method = "Register";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName", userName);
            jsonObject.put("email", userEmail);
            jsonObject.put("mobile", userMobile);
            jsonObject.put("city", userCity);
            jsonObject.put("password", userPassword);
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
                            if (res.equals("Not Register")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                builder.setTitle("Result");
                                builder.setMessage("You are not registered please try again.");
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
                                sessionManager = new SessionManager(Register.this);
                                try {
                                    JSONArray jArr = response.getJSONArray("responseDetails");
                                    for (int count = 0; count < jArr.length(); count++) {
                                        JSONObject obj = jArr.getJSONObject(count);
                                        
                                        Intent gotoOTP = new Intent(Register.this, MainActivity.class);
                                        gotoOTP.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                    }
                                } catch (JSONException ex) {
                                    ex.printStackTrace();
                                }
                            }

                        } catch (Exception e) {
                            progressDialog.dismiss();
                            e.getMessage();
                            Toast.makeText(Register.this, "Exception" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        progressDialog.dismiss();
                        error.getErrorDetail();
                        Toast.makeText(Register.this, "Error" + error.getErrorDetail(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
