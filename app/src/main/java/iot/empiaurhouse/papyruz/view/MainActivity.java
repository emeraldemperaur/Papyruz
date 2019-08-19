package iot.empiaurhouse.papyruz.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import iot.empiaurhouse.papyruz.R;
import iot.empiaurhouse.papyruz.adapter.CodexAdapter;
import iot.empiaurhouse.papyruz.databinding.ActivityMainBinding;
import iot.empiaurhouse.papyruz.model.Category;
import iot.empiaurhouse.papyruz.model.Codex;
import iot.empiaurhouse.papyruz.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mainActivityViewModel;
    private ArrayList<Category> categoriesList;
    private ArrayList<Codex> codices;
    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandlers handlers;

    private Category selectedCategory;
    private RecyclerView codexRecyclerView;
    private CodexAdapter codexAdapter;
    private int selectedCodexId;


    SharedPreferences PapyruzRAM;
    private TextView codexUserTitle;
    private String codexUser;
    private Animation slideDown;
    private ImageView codexIcon;
    private Drawable codexPNG;



    public static final int ADD_CODEX_REQUEST_CODE = 1;
    public static final int EDIT_CODEX_REQUEST_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.faderone, R.anim.fadeout);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        NavShader();
        codexIcon = findViewById(R.id.codexIcon);
        slideDown = AnimationUtils.loadAnimation(this, R.anim.slidedown);
        PapyruzRAM = getApplicationContext().getSharedPreferences("PAPYRUZ_PREFERENCES",0);
        codexUser = PapyruzRAM.getString("DisplayName",null);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        handlers = new MainActivityClickHandlers();
        activityMainBinding.setClickHandlers(handlers);

        codexUserTitle = activityMainBinding.secondaryLayout.CodexTitle;

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                categoriesList = (ArrayList<Category>) categories;

                for (Category c:categories){

                    Log.i("PapyruzTAG", c.getCategoryName());

                }

                showOnSpinner();




            }
        });

    }


    private void showOnSpinner(){

        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<Category>(this,R.layout.spinner_item, categoriesList);
        categoryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityMainBinding.setSpinnerAdapter(categoryArrayAdapter);


    }




    private void loadCodexArrayList(int categoryId){

        mainActivityViewModel.getCodexofASelectedCategory(categoryId).observe(this, new Observer<List<Codex>>() {
            @Override
            public void onChanged(@Nullable List<Codex> codex) {

                codices = (ArrayList<Codex>) codex;
                loadRecyclerView();


            }
        });


    }





    private void NavShader(){

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));


    }



    private void TitleAnimator(){

        String codexTitle = codexUser + "'s" + " Papyruz";
        codexUserTitle.setText(codexTitle);
        codexUserTitle.startAnimation(slideDown);
        codexUserTitle.setVisibility(View.VISIBLE);


    }








    private void loadRecyclerView(){

        codexRecyclerView = activityMainBinding.secondaryLayout.rvCodex;
        codexRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        codexRecyclerView.setHasFixedSize(true);

        codexAdapter = new CodexAdapter();
        codexRecyclerView.setAdapter(codexAdapter);
        codexAdapter.setCodex(codices);
        codexAdapter.setListener(new CodexAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Codex codex) {

                selectedCodexId= codex.getCodexId();
                Intent intent = new Intent(MainActivity.this,AddAndEditActivity.class);
                intent.putExtra(AddAndEditActivity.CODEX_ID,selectedCodexId);
                intent.putExtra(AddAndEditActivity.CODEX_NAME,codex.getCodexName());
                intent.putExtra(AddAndEditActivity.CODEX_TEXT,codex.getCodexText());
                intent.putExtra(AddAndEditActivity.UNIT_VALUE,codex.getUnitValue());
                intent.putExtra(AddAndEditActivity.UNIT_PRICE,codex.getUnitPrice());
                startActivityForResult(intent, EDIT_CODEX_REQUEST_CODE);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                Codex codexToDelete = codices.get(viewHolder.getAdapterPosition());
                mainActivityViewModel.deleteCodex(codexToDelete);

            }
        }).attachToRecyclerView(codexRecyclerView);





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
        codexUserTitle.clearAnimation();



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








    public class MainActivityClickHandlers{

        public void onFABClicked(View view){

            Intent intent = new Intent(MainActivity.this,AddAndEditActivity.class);
            startActivityForResult(intent,ADD_CODEX_REQUEST_CODE);



            //Toast.makeText(getApplicationContext(), "FAB Clicked", Toast.LENGTH_SHORT).show();

        }


        public void onSelectItem(AdapterView<?> parent, View view, int pos, long id){

            selectedCategory = (Category) parent.getItemAtPosition(pos);
            String message = " id is " + selectedCategory.getId() + "\n name is " + selectedCategory.getCategoryName();

            // Toast.makeText(parent.getContext(),message, Toast.LENGTH_LONG).show();

            loadCodexArrayList(selectedCategory.getId());




        }


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int selectedCategoryId = selectedCategory.getId();
        if(requestCode == ADD_CODEX_REQUEST_CODE && resultCode == RESULT_OK){

            Codex codex = new Codex();
            codex.setCategoryId(selectedCategoryId);
            codex.setCodexName(data.getStringExtra(AddAndEditActivity.CODEX_NAME));
            codex.setCodexText(data.getStringExtra(AddAndEditActivity.CODEX_TEXT));
            codex.setUnitValue(data.getStringExtra(AddAndEditActivity.UNIT_VALUE));
            codex.setUnitPrice(data.getStringExtra(AddAndEditActivity.UNIT_PRICE));
            mainActivityViewModel.addNewCodex(codex);



        }else if(requestCode == EDIT_CODEX_REQUEST_CODE && resultCode == RESULT_OK){

            Codex codex = new Codex();
            codex.setCategoryId(selectedCategoryId);
            codex.setCodexName(data.getStringExtra(AddAndEditActivity.CODEX_NAME));
            codex.setCodexText(data.getStringExtra(AddAndEditActivity.CODEX_TEXT));
            codex.setUnitPrice(data.getStringExtra(AddAndEditActivity.UNIT_PRICE));
            codex.setUnitValue(data.getStringExtra(AddAndEditActivity.UNIT_VALUE));
            codex.setCodexId(selectedCodexId);
            mainActivityViewModel.updateCodex(codex);


        }


    }









}
