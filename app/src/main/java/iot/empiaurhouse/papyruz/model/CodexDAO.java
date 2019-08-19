package iot.empiaurhouse.papyruz.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CodexDAO {


    @Insert
    void insert(Codex codex);

    @Update
    void update(Codex codex);

    @Delete
    void delete(Codex codex);

    @Query("SELECT * FROM codex_table")
    LiveData<List<Codex>> getAllCodex();

    @Query("SELECT * FROM codex_table WHERE category_id==:categoryId")
    LiveData<List<Codex>> getCodex(int categoryId);




}
