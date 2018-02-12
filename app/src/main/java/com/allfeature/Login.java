package com.allfeature;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mEditTextEmail = (EditText) findViewById(R.id.username);
        mEditTextPassword = (EditText) findViewById(R.id.password);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);

        FragmentPagerAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void loginUser(View view) {
        Boolean isFormError = true;
        TextInputLayout usernameLayout = (TextInputLayout) findViewById(R.id.username);
        TextInputLayout passwordLayout = (TextInputLayout) findViewById(R.id.password);

        Log.d("####### Username", "loginUser: " + usernameLayout.getEditText().getText().toString());
        Log.d("####### Password", "loginUser: " + passwordLayout.getEditText().getText().toString());

        if(!isValidateEmail(usernameLayout.getEditText().getText().toString())){
            Toast.makeText(this, "Email is empty or wrong",Toast.LENGTH_LONG).show();
            isFormError = true;
        }else if(!isEmptyField(passwordLayout.getEditText().getText().toString())){
            Toast.makeText(this, "Password is empty",Toast.LENGTH_LONG).show();
            isFormError = true;
        } else {
            isFormError = false;
        }

        if (!isFormError) {

            // Tag used to cancel the request
            String tag_json_obj = "json_obj_req";

            String url = "http://45.77.171.22:3030/useraccount/login";

            Map<String, String> parameterLogin = new HashMap();
            parameterLogin.put("email", usernameLayout.getEditText().getText().toString());
            parameterLogin.put("password", passwordLayout.getEditText().getText().toString());

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(true);

            progressDialog.setMessage("Loading...");
            progressDialog.show();

            JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST,
                    url, new JSONObject(parameterLogin),
                        new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Result", response.toString());

                            try {
                                JSONObject userData = response.getJSONObject("data");

                                Log.d("####", "full_name: " + userData.getString("full_name"));
                            } catch (JSONException e) {
                                  e.printStackTrace();
                            }
                            try {
                                if (response.getBoolean("success")) {
                                    Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getApplicationContext(), HomepageActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Result", "Error: " + error.getMessage());
                    // hide the progress dialog
                    //pDialog.hide();
                }
            });

            Volley.newRequestQueue(this).add(loginRequest);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                login();
                break;
        }
    }

        /**
     * method ini dipanggil saat kita menekan button login
     */
    private void login(){
        String email = mEditTextEmail.getText().toString();
        String password = mEditTextPassword.getText().toString();

        if(!isValidateEmail(email)){
            Toast.makeText(this, "Email is empty or wrong",Toast.LENGTH_LONG).show();
        }else if(!isEmptyField(password)){
            Toast.makeText(this, "Password is empty",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Login success",Toast.LENGTH_LONG).show();
        }

    }

    /**
     *
     * @param email
     * Method dibawah ini untuk validasi email kosong atau salah
     */
    private boolean isValidateEmail(String email){
        return !TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     *
     * @param yourField
     * Method ini digunakan untuk validasi field kosong atau tidak
     */
    private boolean isEmptyField(String yourField){
        return !TextUtils.isEmpty(yourField);
    }

    /**
     *
     * @param password
     * @param retypePassword
     * method ini digunakan untuk mencocokan password dengan retype password
     */
    private boolean isMatch(String password, String retypePassword){
        return password.equals(retypePassword);
    }

}