package kiran541.ench.com.footballapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dd.processbutton.iml.SubmitProcessButton;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity implements ProgressGenerator.OnCompleteListener{
    EditText user,email,pass,cpass;
    Button signup;
    String uname,email1,pswd,cpswd;
    TextView signinlink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        user=(EditText)findViewById(R.id.username);
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.pswrd);
        cpass=(EditText)findViewById(R.id.cnfrmpswrd);
        //signup=(Button)findViewById(R.id.signup);
        signinlink=(TextView)findViewById(R.id.signinlink);
        final ProgressGenerator progressGenerator = new ProgressGenerator(this);
        final SubmitProcessButton signup = (SubmitProcessButton) findViewById(R.id.signup);
        signinlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this,SigninActivity.class));
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname=user.getText().toString();
                email1=email.getText().toString();
                pswd=pass.getText().toString();
                cpswd=cpass.getText().toString();

                if(uname.isEmpty()){
                    //Toast.makeText(getApplicationContext(), "user name must not be empty", Toast.LENGTH_SHORT).show();
                    TastyToast.makeText(getApplicationContext(), " user name must not be empty", TastyToast.LENGTH_LONG,
                            TastyToast.ERROR);
                }
                else if(email1.isEmpty()){
                   // Toast.makeText(getApplicationContext(), "please enter email", Toast.LENGTH_SHORT).show();
                    TastyToast.makeText(getApplicationContext(), " please enter email", TastyToast.LENGTH_LONG,
                            TastyToast.ERROR);
                }
                else if(email1.matches("@gmail.com")){
                   // Toast.makeText(getApplicationContext(), "please enter valid email", Toast.LENGTH_SHORT).show();
                    TastyToast.makeText(getApplicationContext(), " please enter valid email", TastyToast.LENGTH_LONG,
                            TastyToast.ERROR);
                }
                else if(pswd.isEmpty()){
                    //Toast.makeText(getApplicationContext(), "please enter password", Toast.LENGTH_SHORT).show();
                    TastyToast.makeText(getApplicationContext(), " please enter password", TastyToast.LENGTH_LONG,
                            TastyToast.ERROR);
                }
                else if(pswd.length()<6){
                   // Toast.makeText(getApplicationContext(), " password is not less than 6 characters", Toast.LENGTH_SHORT).show();
                    TastyToast.makeText(getApplicationContext(), " password is not less than 6 characters", TastyToast.LENGTH_LONG,
                            TastyToast.ERROR);
                }
                else if(!cpswd.equals(pswd)){
                  //  Toast.makeText(getApplicationContext(), "confirm password is not correct please check", Toast.LENGTH_SHORT).show();
                    TastyToast.makeText(getApplicationContext(), " confirm password is not correct please check", TastyToast.LENGTH_LONG,
                            TastyToast.WARNING);
                }
                else{

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            insert_service(uname,email1,pswd,cpswd);
                            TastyToast.makeText(getApplicationContext(), "Registration Successful", TastyToast.LENGTH_LONG,
                                    TastyToast.INFO);
                            startActivity(new Intent(Signup.this,TeamSelection.class));
                        }
                    }, 5000);

                    progressGenerator.start(signup);
                    signup.setEnabled(false);




                }

            }
        });





    }



    private void insert_service(final String uname, final String email1, final String pswd,final String cpswd) {

        StringRequest stringreqs = new StringRequest(Request.Method.POST, MyGlobal_Url.MYBASIC_SIGNUP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                try {
//                    JSONObject jObj = new JSONObject(response);
//                    boolean abc = jObj.getBoolean("exits");
//                    if (abc) {
//
//                        Toast.makeText(getApplicationContext(), "User Exists already with  mobile number!", Toast.LENGTH_SHORT).show();
//
//                    }
//                    JSONObject users = jObj.getJSONObject("user_det");
//                    String name = users.getString("name");
//                    String mail = users.getString("email");
//                    String maill = users.getString("password");
//                    String uuidq = users.getString("cpassword");
//
//                    Intent intent = new Intent(SignUp_Screen.this, Home_Screen.class);
//                    intent.putExtra("name", name);
//                    intent.putExtra("mail", mail);
//                    intent.putExtra("uuidq", uuidq);
//                    startActivity(intent);
//                    Toast.makeText(getApplicationContext(), "Welcome" + name, Toast.LENGTH_SHORT).show();
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
           }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(getApplicationContext(), "INTERNET CONNECTION NOT AVAILABLE", Toast.LENGTH_SHORT).show();
                TastyToast.makeText(getApplicationContext(), "INTERNET CONNECTION NOT AVAILABLE", TastyToast.LENGTH_LONG,
                        TastyToast.ERROR);
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> uandme = new HashMap<String, String>();
                uandme.put("username", uname);
                uandme.put("email", email1);
                uandme.put("password", pswd);
                uandme.put("cpassword", cpswd);
                return uandme;
            }
        };
        AppController.getInstance().addToRequestQueue(stringreqs);

    }

    @Override
    public void onComplete() {

    }
}
