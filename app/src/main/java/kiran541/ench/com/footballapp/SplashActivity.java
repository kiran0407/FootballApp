package kiran541.ench.com.footballapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private final int SPLASH_DISPLA = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        final SharedPreferences settings=getSharedPreferences("prefs",0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean firstRun=settings.getBoolean("firstRun",false);
                Intent intent = getIntent();
                String inp = intent.getStringExtra("btn");
                //Toast.makeText(getApplicationContext(),inp,Toast.LENGTH_LONG).show();
                String ver = "hello";
                if(ver.equals(inp)) {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("firstRun", true);
                    editor.apply();
                    Intent i = new Intent(SplashActivity.this, TeamSelection.class);
                    startActivity(i);

                }else {



                                Intent mainIntent = new Intent(SplashActivity.this, SigninActivity.class);
                                /*mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
                                startActivity(mainIntent);


                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
