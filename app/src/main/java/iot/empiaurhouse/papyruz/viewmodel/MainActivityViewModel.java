package iot.empiaurhouse.papyruz.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import iot.empiaurhouse.papyruz.model.Category;
import iot.empiaurhouse.papyruz.model.Codex;
import iot.empiaurhouse.papyruz.model.PapyruzDatabase;
import iot.empiaurhouse.papyruz.model.PapyruzRepository;

public class MainActivityViewModel extends AndroidViewModel {


    private PapyruzRepository papyruzRepository;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<Codex>> codexofASelectedCategory;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        papyruzRepository = new PapyruzRepository(application);

    }


    public LiveData<List<Category>> getAllCategories() {

        allCategories = papyruzRepository.getCategories();

        return allCategories;
    }

    public LiveData<List<Codex>> getCodexofASelectedCategory(int categoryId) {

        codexofASelectedCategory = papyruzRepository.getCodex(categoryId);

        return codexofASelectedCategory;
    }



    public void addNewCodex(Codex codex){

        papyruzRepository.insertCodex(codex);
    }


    public void updateCodex(Codex codex){
        papyruzRepository.updateCodex(codex);

    }


    public void deleteCodex(Codex codex){
        papyruzRepository.deleteCodex(codex);

    }












}
