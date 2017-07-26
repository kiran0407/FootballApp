package kiran541.ench.com.footballapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static kiran541.ench.com.footballapp.R.id.username;

public class SigninActivity extends AppCompatActivity implements ProgressGenerator.OnCompleteListener {
    Button signin;
    TextView signuplink;
    EditText username1,pswd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        //signin=(Button)findViewById(R.id.signin);
        signuplink=(TextView)findViewById(R.id.signuplink);
        username1=(EditText)findViewById(username);
        pswd1=(EditText)findViewById(R.id.pswrd);
         final ProgressGenerator progressGenerator = new ProgressGenerator(this);
         final SubmitProcessButton signin = (SubmitProcessButton) findViewById(R.id.signin);
        signuplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SigninActivity.this,Signup.class));
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user1=username1.getText().toString();
                final String password1=pswd1.getText().toString();//String p=password1;
                if(user1.matches("")){
                    //Toast.makeText(getApplicationContext(), "user name must not be empty", Toast.LENGTH_SHORT).show();
                    TastyToast.makeText(getApplicationContext(), " user name must not be empty", TastyToast.LENGTH_LONG,
                            TastyToast.ERROR);
                }
                else if(password1.matches("")){
                    //Toast.makeText(getApplicationContext(), "please enter password", Toast.LENGTH_SHORT).show();
                    TastyToast.makeText(getApplicationContext(), " please enter password", TastyToast.LENGTH_LONG,
                            TastyToast.ERROR);
                }
               else {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            insert_service(user1,password1);
                        }
                    }, 5000);

                    progressGenerator.start(signin);
                    signin.setEnabled(false);



                }
            }
        });

    }

    @Override
    public void onComplete() {
       /* TastyToast.makeText(getApplicationContext(), "Deleted Successful !", TastyToast.LENGTH_LONG,
                TastyToast.SUCCESS);*/
    }
    private void insert_service(final String user1, final String password1) {

        StringRequest stringreqs = new StringRequest(Request.Method.POST, inputurl.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                     boolean abc = jObj.getBoolean("exits");
                    if (abc)
                    {
                        JSONObject users = jObj.getJSONObject("user_det");
                        String name = users.getString("username");
                        //Toast.makeText(getApplicationContext(), "User Exists already with  mobile number!", Toast.LENGTH_SHORT).show();
                        String maill = users.getString("password");
                      /*  String msg="hello";
                        Intent i=new Intent(SigninActivity.this,SplashActivity.class);
                        i.putExtra("btn",msg);
                        startActivity(i);*/


                        startActivity(new Intent(SigninActivity.this, TeamSelection.class));
                    }
                    else
                    {
                        String msg=jObj.getString("messeade");
                        //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        TastyToast.makeText(getApplicationContext(), msg, TastyToast.LENGTH_LONG,
                                TastyToast.WARNING);
                    }
                    /*Intent intent = new Intent(SignUp_Screen.this, Home_Screen.class);
                    intent.putExtra("name", name);
                    intent.putExtra("mail", mail);
                    intent.putExtra("uuidq", uuidq);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Welcome" + name, Toast.LENGTH_SHORT).show();
*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                uandme.put("username2", user1);
                uandme.put("password2", password1);
                return uandme;
            }
        };
        AppController.getInstance().addToRequestQueue(stringreqs);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
