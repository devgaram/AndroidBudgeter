package org.androidtown.mybudgeter;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BudgetDao {

    @Insert
    void insert(Budget budget);

    @Update
    void update(Budget budget);

    @Delete
    void delete(Budget budget);

    @Query("SELECT * FROM budget_table")
    LiveData<List<Budget>> getAllbudgets();

    @Query("SELECT * FROM budget_table WHERE id=:id")
    LiveData<Budget> getBudgetForId(int id);

}
