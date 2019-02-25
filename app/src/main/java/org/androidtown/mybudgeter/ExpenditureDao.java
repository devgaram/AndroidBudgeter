package org.androidtown.mybudgeter;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ExpenditureDao {

    @Insert
    void insert(Expenditure expenditure);

    @Update
    void update(Expenditure expenditure);

    @Delete
    void delete(Expenditure expenditure);

    @Query("SELECT * FROM expenditure_table WHERE budgetId=:budgetId")
    LiveData<List<Expenditure>> getExpendituresForBudget(int budgetId);

    @Query("SELECT * FROM expenditure_table")
    LiveData<List<Expenditure>> getAllExpenditures();
}
