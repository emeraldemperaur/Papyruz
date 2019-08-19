package iot.empiaurhouse.papyruz.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class PapyruzRepository {

    private CategoryDAO categoryDAO;
    private CodexDAO codexDAO;
    private LiveData<List<Category>> categories;
    private LiveData<List<Codex>> codex;


    public PapyruzRepository(Application application) {

        PapyruzDatabase papyruzDatabase = PapyruzDatabase.getInstance(application);
        categoryDAO = papyruzDatabase.categoryDAO();
        codexDAO = papyruzDatabase.codexDAO();


    }

    public LiveData<List<Category>> getCategories() {
        return categoryDAO.getAllCategories();
    }


    public LiveData<List<Codex>> getCodex(int categoryId) {
        return codexDAO.getCodex(categoryId);
    }



    public void insertCategory(Category category){

        new InsertCategoryAsyncTask(categoryDAO).execute(category);

    }




    private static class InsertCategoryAsyncTask extends AsyncTask<Category,Void,Void> {

        private CategoryDAO categoryDAO;

        public InsertCategoryAsyncTask(CategoryDAO categoryDAO) {
            this.categoryDAO = categoryDAO;
        }

        @Override
        protected Void doInBackground(Category... categories) {

            categoryDAO.insert(categories[0]);

            return null;
        }
    }




    public void insertCodex(Codex codex){

        new InsertCodexAsyncTask(codexDAO).execute(codex);

    }




    private static class InsertCodexAsyncTask extends AsyncTask<Codex,Void,Void>{

        private CodexDAO codexDAO;

        public InsertCodexAsyncTask(CodexDAO codexDAO) {
            this.codexDAO = codexDAO;
        }

        @Override
        protected Void doInBackground(Codex... codex) {

            codexDAO.insert(codex[0]);

            return null;
        }
    }





    public void deleteCategory(Category category){

        new DeleteCategoryAsyncTask(categoryDAO).execute(category);

    }




    private static class DeleteCategoryAsyncTask extends AsyncTask<Category,Void,Void>{

        private CategoryDAO categoryDAO;

        public DeleteCategoryAsyncTask(CategoryDAO categoryDAO) {
            this.categoryDAO = categoryDAO;
        }

        @Override
        protected Void doInBackground(Category... categories) {

            categoryDAO.delete(categories[0]);

            return null;
        }
    }




    public void deleteCodex(Codex codex){

        new DeleteCodexAsyncTask(codexDAO).execute(codex);

    }




    private static class DeleteCodexAsyncTask extends AsyncTask<Codex,Void,Void>{

        private CodexDAO codexDAO;

        public DeleteCodexAsyncTask(CodexDAO codexDAO) {
            this.codexDAO = codexDAO;
        }

        @Override
        protected Void doInBackground(Codex... books) {

            codexDAO.delete(books[0]);

            return null;
        }
    }



    public void updateCategory(Category category){

        new UpdateCategoryAsyncTask(categoryDAO).execute(category);

    }




    private static class UpdateCategoryAsyncTask extends AsyncTask<Category,Void,Void>{

        private CategoryDAO categoryDAO;

        public UpdateCategoryAsyncTask(CategoryDAO categoryDAO) {
            this.categoryDAO = categoryDAO;
        }

        @Override
        protected Void doInBackground(Category... categories) {

            categoryDAO.update(categories[0]);

            return null;
        }
    }




    public void updateCodex(Codex codex){

        new UpdateCodexAsyncTask(codexDAO).execute(codex);

    }




    private static class UpdateCodexAsyncTask extends AsyncTask<Codex,Void,Void>{

        private CodexDAO codexDAO;

        public UpdateCodexAsyncTask(CodexDAO codexDAO) {
            this.codexDAO = codexDAO;
        }

        @Override
        protected Void doInBackground(Codex... codex) {

            codexDAO.update(codex[0]);

            return null;
        }
    }



}
