package com.allfeature;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.allfeature.helper.SQLiteHandler;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener{

    private TextInputLayout mEditTextFullname, mEditTextEmail, mEditTextPassword, mEditTextReTypePassword;
    private SQLiteHandler databaseHelper;
    private User user;
    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mEditTextFullname = view.findViewById(R.id.fullname);
        mEditTextEmail = view.findViewById(R.id.email);
        mEditTextPassword = view.findViewById(R.id.passwordRegister);
        mEditTextReTypePassword = view.findViewById(R.id.confirmPasswordRegister);
        Button buttonRegister = view.findViewById(R.id.button2);
        buttonRegister.setOnClickListener(this);

        initObject();
        // Inflate the layout for this fragment
        return view;
    }

    private void initObject(){
        databaseHelper = new SQLiteHandler(SignUpFragment.this);
        user = new User();
    }


    public void registerUser() {
        Boolean isFormError = true;


        Log.d("####### Fullname", "registerUser: " + mEditTextFullname.getEditText().getText().toString());
        Log.d("####### Email", "registerUser: " + mEditTextEmail.getEditText().getText().toString());
        Log.d("####### Password", "registerUser: " + mEditTextPassword.getEditText().getText().toString());

        if(!isEmptyField(mEditTextFullname.getEditText().getText().toString())){
            Toast.makeText(getActivity(), "Full name is empty",Toast.LENGTH_LONG).show();
            isFormError = true;
        }else if(!isValidateEmail(mEditTextEmail.getEditText().getText().toString())){
            Toast.makeText(getActivity(), "Email is empty or wrong",Toast.LENGTH_LONG).show();
            isFormError = true;
        }else if(!isEmptyField(mEditTextPassword.getEditText().getText().toString())){
            Toast.makeText(getActivity(), "Password is empty",Toast.LENGTH_LONG).show();
            isFormError = true;
        }else if(!isEmptyField(mEditTextReTypePassword.getEditText().getText().toString())){
            Toast.makeText(getActivity(), "Confirm password is empty",Toast.LENGTH_LONG).show();
            isFormError = true;
        }else if(!isMatch(mEditTextPassword.getEditText().getText().toString(),mEditTextReTypePassword.getEditText().getText().toString())){
            Toast.makeText(getActivity(), "Password incorrect",Toast.LENGTH_LONG).show();
            isFormError = true;
        }
        else {
            isFormError = false;
        }

        if (!isFormError) {

            // Tag used to cancel the request
            String tag_json_obj = "json_obj_req";

            String url = "http://45.77.171.22:3030/useraccount/register/";

            Map<String, String> parameterRegister = new HashMap();
            parameterRegister.put("full_name", mEditTextFullname.getEditText().getText().toString());
            parameterRegister.put("email", mEditTextEmail.getEditText().getText().toString());
            parameterRegister.put("password", mEditTextPassword.getEditText().getText().toString());

            final ProgressDialog pDialog = new ProgressDialog(getActivity());
            pDialog.setCancelable(true);

            pDialog.setMessage("Loading...");
            pDialog.show();

            JsonObjectRequest RegisterRequest = new JsonObjectRequest(Request.Method.POST,
                    url, new JSONObject(parameterRegister),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Result", response.toString());
                            user.setName(mEditTextFullname.getEditText().getText().toString().trim());
                            user.setEmail(mEditTextEmail.getEditText().getText().toString().trim());
                            user.setPassword(mEditTextPassword.getEditText().getText().toString().trim());
                            databaseHelper.addUser(user);

                            try {
                                JSONObject userData = response.getJSONObject("data");
                                Log.d("####", "full_name: " + userData.getString("full_name"));
                                String token = userData.getString("access_token");
                                Toast.makeText(getActivity(), "Access token: " + token, Toast.LENGTH_SHORT).show();
                                pDialog.dismiss();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                if (response.getBoolean("success")) {
                                    Toast.makeText(getActivity(), response.getString("message"), Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getActivity(), Login.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    getActivity().finish();
                                } else {
                                    Toast.makeText(getActivity(), response.getString("message"), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            pDialog.hide();
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Result", "Error: " + error.getMessage());
                    // hide the progress dialog
                    pDialog.hide();
                }
            });

            Volley.newRequestQueue(getActivity()).add(RegisterRequest);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button2:
                registerUser();
                break;
        }
    }


}
