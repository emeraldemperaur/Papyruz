package iot.empiaurhouse.papyruz.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
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
import android.widget.TextView;
import android.widget.Toast;

import iot.empiaurhouse.papyruz.R;
import iot.empiaurhouse.papyruz.databinding.ActivityAddAndEditBinding;
import iot.empiaurhouse.papyruz.model.Codex;

public class AddAndEditActivity extends AppCompatActivity {

    private TextView editorTitle;
    private Button editorButton;
    private Animation fadeIn;


    private Codex codex;
    public static final String CODEX_ID="codexId";
    public static final String CODEX_NAME="codexName";
    public static final String CODEX_TEXT="codexText";
    public static final String UNIT_PRICE="unitPrice";
    public static final String UNIT_VALUE ="unitValue";

    private ActivityAddAndEditBinding activityAddAndEditBinding;
    private AddAndEditActivityClickHandlers addAndEditActivityClickHandlers;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_add_and_edit);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        NavShader();
        codex = new Codex();
        activityAddAndEditBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_and_edit);
        activityAddAndEditBinding.setCodex(codex);

        addAndEditActivityClickHandlers = new AddAndEditActivityClickHandlers(this);
        activityAddAndEditBinding.setClickHandler(addAndEditActivityClickHandlers);
        editorTitle = activityAddAndEditBinding.EditorTitle;
        editorButton = activityAddAndEditBinding.codexButton;

        Intent intent = getIntent();
        if (intent.hasExtra(CODEX_ID)){

            editorTitle.setText(getText(R.string.edit_codex));
            editorButton.setText(getText(R.string.update_codex));
            codex.setCodexName(intent.getStringExtra(CODEX_NAME));
            codex.setUnitPrice(intent.getStringExtra(UNIT_PRICE));
            codex.setUnitValue(intent.getStringExtra(UNIT_VALUE));
            codex.setCodexText(intent.getStringExtra(CODEX_TEXT));
        }else {

            editorTitle.setText(getText(R.string.add_codex));
            editorButton.setText(getText(R.string.submit_codex));

        }



    }




    public class AddAndEditActivityClickHandlers{

        Context context;

        public AddAndEditActivityClickHandlers(Context context) {
            this.context = context;
        }


        public void onSubmitButtonClicked(View view){
            if(codex.getCodexName() == null){

                Toast.makeText(context, "Name Field cannot be empty",Toast.LENGTH_LONG).show();

            }else {

                Intent intent = new Intent();
                intent.putExtra(CODEX_NAME,codex.getCodexName());
                intent.putExtra(CODEX_TEXT,codex.getCodexText());
                intent.putExtra(UNIT_VALUE,codex.getUnitValue());
                intent.putExtra(UNIT_PRICE,codex.getUnitPrice());
                setResult(RESULT_OK,intent);
                finish();



            }



        }


    }




    @Override
    protected void onResume()
    {
        super.onResume();
        TitleAnimator();


    }




    @Override
    protected void onPause()
    {
        super.onPause();



    }



    private void NavShader(){

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));


    }



    private void TitleAnimator(){


        editorTitle.startAnimation(fadeIn);
        editorTitle.setVisibility(View.VISIBLE);


    }














}
