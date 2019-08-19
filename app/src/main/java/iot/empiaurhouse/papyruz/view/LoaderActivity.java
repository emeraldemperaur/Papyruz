package iot.empiaurhouse.papyruz.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import iot.empiaurhouse.papyruz.R;

public class LoaderActivity extends AppCompatActivity {

    SharedPreferences PapyruzRAM;
    private String DisplayName;
    private String Greeting;
    private String Salutation;
    private TextView Greeter;

    private Animation FadeIn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_loader);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        NavShader();

        PapyruzRAM = getApplicationContext().getSharedPreferences("PAPYRUZ_PREFERENCES",0);
        DisplayName = PapyruzRAM.getString("DisplayName",null);
        Greeter = findViewById(R.id.greeter_text);
        LoadDirector();
        Animator();
        ExitDirector();





    }


    private void Animator(){

        FadeIn = AnimationUtils.loadAnimation(this, R.anim.faderone);
        Greeter.startAnimation(FadeIn);
        Greeter.setVisibility(View.VISIBLE);


    }


    private void NavShader(){

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));


    }




    private void ExitDirector(){

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                ReturningUserValidator();

            }
        }, 4000);



    }


    private void LoadDirector(){
        if(DisplayName != null){

            Salutation = "Hello, " + DisplayName + "!";
            Greeter.setText(Salutation);



        }else {

            PapyruzGreeter();

        }



    }




    private void PapyruzGreeter(){


        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        Greeting = null;
        if(hour>= 12 && hour < 17){
            Greeting = "Good Afternoon";
            Greeter.setText(Greeting);

        } else if(hour >= 17 && hour < 21){
            Greeting = "Good Evening";
            Greeter.setText(Greeting);

        } else if(hour >= 21 && hour < 24){
            Greeting = "Carpe Noctem";
            Greeter.setText(Greeting);

        } else {
            Greeting = "Good Morning";
            Greeter.setText(Greeting);

        }



    }





    private void ReturningUserValidator(){

        if(DisplayName == null){

            Intent signin_intent = new Intent(this, SignInActivity.class);
            startActivity(signin_intent);
            finish();


        }else  {

            Intent main_intent = new Intent(this, MainActivity.class);
            startActivity(main_intent);
            finish();

        }

    }






    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }









}
