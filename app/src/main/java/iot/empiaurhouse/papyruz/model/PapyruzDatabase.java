package iot.empiaurhouse.papyruz.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Category.class, Codex.class}, version = 1)
public abstract class PapyruzDatabase extends RoomDatabase {

    public abstract CategoryDAO categoryDAO();
    public abstract CodexDAO codexDAO();

    private static PapyruzDatabase instance;


    public static synchronized PapyruzDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PapyruzDatabase.class,"papyruz_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();


        }

        return instance;
    }


    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new InitialDataAsyncTask(instance).execute();
        }

    };



    private static class InitialDataAsyncTask extends AsyncTask<Void,Void,Void> {

        private CategoryDAO categoryDAO;
        private CodexDAO codexDAO;

        public InitialDataAsyncTask(PapyruzDatabase papyruzDatabase) {

            categoryDAO = papyruzDatabase.categoryDAO();
            codexDAO = papyruzDatabase.codexDAO();


        }


        @Override
        protected Void doInBackground(Void... voids) {
            Category category1 = new Category();
            category1.setCategoryName("Alpha");
            category1.setCategoryDescription("Alpha Codex");


            Category category2 = new Category();
            category2.setCategoryName("Beta");
            category2.setCategoryDescription("Beta Codex");


            Category category3 = new Category();
            category3.setCategoryName("Gamma");
            category3.setCategoryDescription("Gamma Codex");

            Category category4 = new Category();
            category4.setCategoryName("Delta");
            category4.setCategoryDescription("Delta Codex");

            Category category5 = new Category();
            category5.setCategoryName("Epsilon");
            category5.setCategoryDescription("Epsilon Codex");

            categoryDAO.insert(category1);
            categoryDAO.insert(category2);
            categoryDAO.insert(category3);
            categoryDAO.insert(category4);
            categoryDAO.insert(category5);


            Codex codex1 = new Codex();
            codex1.setCodexName("Alpha Codex Sample");
            codex1.setUnitPrice("$200");
            codex1.setUnitValue("200");
            codex1.setCodexText("Enter Codex Content Information Here");
            codex1.setCategoryId(1);


            Codex codex2 = new Codex();
            codex2.setCodexName("Beta Codex Sample");
            codex2.setUnitPrice("$100");
            codex2.setUnitValue("100");
            codex2.setCodexText("Enter Codex Content Information Here");
            codex2.setCategoryId(2);


            Codex codex3 = new Codex();
            codex3.setCodexName("Gamma Codex Sample");
            codex3.setUnitPrice("$150");
            codex3.setUnitValue("150");
            codex3.setCodexText("Enter Codex Content Information Here");
            codex3.setCategoryId(3);

            Codex codex4 = new Codex();
            codex4.setCodexName("Delta Codex Sample");
            codex4.setUnitPrice("$220");
            codex4.setUnitValue("220");
            codex4.setCodexText("Enter Codex Content Information Here");
            codex4.setCategoryId(4);


            Codex codex5 = new Codex();
            codex5.setCodexName("Epsilon Codex Sample");
            codex5.setUnitPrice("$150");
            codex5.setUnitValue("150");
            codex5.setCodexText("Enter Codex Content Information Here");
            codex5.setCategoryId(5);


            codexDAO.insert(codex1);
            codexDAO.insert(codex2);
            codexDAO.insert(codex3);
            codexDAO.insert(codex4);
            codexDAO.insert(codex5);


            return null;

        }


    }










}
