package iot.empiaurhouse.papyruz.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import iot.empiaurhouse.papyruz.R;
import iot.empiaurhouse.papyruz.databinding.ActivitySignInBinding;
import iot.empiaurhouse.papyruz.model.LoginFields;
import iot.empiaurhouse.papyruz.viewmodel.SignInViewModel;

public class SignInActivity extends AppCompatActivity {

    SharedPreferences PapyruzRAM;
    private SignInViewModel viewModel;

    private TextView SignInTitle;
    private Button Continue;
    private Animation fadeIn;

    private EditText displayName;
    private EditText email;
    private String DisplayName;
    private String Email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_sign_in);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        NavShader();
        setupBindings(savedInstanceState);
        SignInTitle = findViewById(R.id.SignInTitle);
        displayName = findViewById(R.id.SignIn_Fullname);
        email = findViewById(R.id.SignIn_Email);
        Continue = findViewById(R.id.continue_button);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        TitleAnimator();
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Validator();

            }
        });


    }



    private void setupBindings(Bundle savedInstanceState){
        ActivitySignInBinding activitySignInBinding = DataBindingUtil.setContentView(this,R.layout.activity_sign_in);
        viewModel = ViewModelProviders.of(this).get(SignInViewModel.class);
        if (savedInstanceState == null){

            viewModel.init();

        }
        activitySignInBinding.setLoginmodel(viewModel);
        setupButtonClick();


    }




    private void setupButtonClick(){
        viewModel.getContinueButtonClick().observe(this, new Observer<LoginFields>() {
            @Override
            public void onChanged(@Nullable LoginFields loginFields) {

                Toast.makeText(SignInActivity.this,
                        "Email " + loginFields.getEmail() + ", Password" + loginFields.getName(),
                        Toast.LENGTH_LONG).show();

            }
        });



    }






    private void TitleAnimator(){

        SignInTitle.startAnimation(fadeIn);
        SignInTitle.setVisibility(View.VISIBLE);


    }


    private void Validator(){

        DisplayName = displayName.getText().toString();
        Email = email.getText().toString();
        if (!isValidName(DisplayName)){
            displayName.setError(getString(R.string.NameError));
        }
        if (!isValidEmail(Email)){
            email.setError(getString(R.string.EmailError));

        }

        if (isValidName(DisplayName) && isValidEmail(Email)){

            PapyruzRAM = getApplicationContext().getSharedPreferences("PAPYRUZ_PREFERENCES",0);
            SharedPreferences.Editor PapyruzRAM_IO = PapyruzRAM.edit();
            PapyruzRAM_IO.putString("DisplayName", DisplayName);
            PapyruzRAM_IO.putString("EmailAddress",Email);
            PapyruzRAM_IO.apply();
            MainIntent();

        }


    }




    private void NavShader(){

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));


    }


    //  email text view validator
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // name text view validator
    private boolean isValidName(String name) {
        if (name != null && !name.isEmpty()) {
            return true;
        }
        return false;
    }




    @Override
    protected void onResume()
    {
        super.onResume();

        SignInTitle.startAnimation(fadeIn);

    }




    @Override
    protected void onPause()
    {
        super.onPause();
        SignInTitle.clearAnimation();



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






    private void MainIntent(){
        Intent main_intent = new Intent(SignInActivity.this, MainActivity.class);
        SignInActivity.this.startActivity(main_intent);
        finish();

    }




}
