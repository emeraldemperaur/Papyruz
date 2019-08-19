package iot.empiaurhouse.papyruz.view;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.LinearLayout;

import iot.empiaurhouse.papyruz.R;

public class LaunchActivity extends AppCompatActivity {

    private ImageView ehlogo_bw;
    private ImageView ehlogo_wb;
    private LinearLayout papyruz_launcheractivity;
    private Animation animationFadeIn;
    private Animation animationFadeInB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ehlogo_bw = findViewById(R.id.empiaurhouse_BWlogo);
        ehlogo_wb = findViewById(R.id.empiaurhouse_WBlogo);
        papyruz_launcheractivity = findViewById(R.id.launcher_activity);


        EHAnimator();







    }



    private void PapyruzLoaderIntent(){
        Intent intent = new Intent(this, LoaderActivity.class);
        startActivity(intent);


    }


    private void EHAnimator(){
        //animation var initialization
        animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.faderone);
        animationFadeInB = AnimationUtils.loadAnimation(this, R.anim.fadertwo);




        Runnable _activitycoloranimationRunnable = new Runnable() {
            public void run() {
                //animate activity background and ui elements

                ehlogo_bw.setVisibility(View.INVISIBLE);

                int colorFrom = getResources().getColor(R.color.white);
                int colorTo = getResources().getColor(R.color.colorAccent);
                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                colorAnimation.setDuration(750);
                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        papyruz_launcheractivity.setBackgroundColor((int) animator.getAnimatedValue());
                    }

                });
                colorAnimation.start();
                NavShader();




                ehlogo_wb.setVisibility(View.VISIBLE);
                ehlogo_wb.startAnimation(animationFadeInB);

            }
        };
        Handler _animHandler = new Handler();
        _animHandler.postDelayed(_activitycoloranimationRunnable, 3215);


        Runnable _LaunchIntentRunnable = new Runnable() {
            public void run() {


                PapyruzLoaderIntent();



            }
        };
        Handler _LoaderIntentHandler = new Handler();
        _LoaderIntentHandler.postDelayed(_LaunchIntentRunnable, 10440);




    }


    private void NavShader(){

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setNavigationBarColor(ContextCompat.getColor(this, R.color.colorAccent));


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




    @Override
    protected void onResume()
    {
        super.onResume();


    }




    @Override
    protected void onPause()
    {
        super.onPause();
        ehlogo_wb.clearAnimation();
        ehlogo_bw.clearAnimation();



    }








}
