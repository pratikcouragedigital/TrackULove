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

public class User_Add extends AppCompatActivity implements View.OnClickListener{

    private EditText txtUserEmailMobile;
    private Button btnSubmit;
    private ProgressDialog progressDialog;
    String userId, emailOrMobile,method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_add);

        SessionManager sessionManagerNgo = new SessionManager(this);
        HashMap<String, String> typeOfUser = sessionManagerNgo.getUserDetails();
        userId = typeOfUser.get(SessionManager.KEY_USERID);
        
        btnSubmit = (Button) findViewById(R.id.btnlogin);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnlogin) {
            emailOrMobile = txtUserEmailMobile.getText().toString();
           
            if (txtUserEmailMobile.getText().toString().isEmpty()) {
                Toast.makeText(User_Add.this, "Please Enter Mobile No Or Email.", Toast.LENGTH_LONG).show();
            } 
            else {
                progressDialog = new ProgressDialog(User_Add.this);
                progressDialog.setMessage("Searching.");
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);

                User_AddRequest();
            }
        }
    }

    private void User_AddRequest() {
        method = "User_Add";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("EmailMobile", emailOrMobile);
            jsonObject.put("userId", userId);
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
                            if (res.equals("User Not Valid")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(User_Add.this);
                                builder.setTitle("Result");
                                builder.setMessage("User Not Available");
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
                                
                                try {
                                    JSONArray jArr = response.getJSONArray("responseDetails");
                                    for (int count = 0; count < jArr.length(); count++) {
                                        JSONObject obj = jArr.getJSONObject(count);

                                        AddDeviceDetails();

                                    }
                                } catch (JSONException ex) {
                                    ex.printStackTrace();
                                }
                            }

                        } catch (Exception e) {
                            progressDialog.dismiss();
                            e.getMessage();
                            Toast.makeText(User_Add.this, "Exception" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        progressDialog.dismiss();
                        error.getErrorDetail();
                        Toast.makeText(User_Add.this, "Error" + error.getErrorDetail(), Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void AddDeviceDetails() {
        method ="Add_User_Device_Details";

    }
}
