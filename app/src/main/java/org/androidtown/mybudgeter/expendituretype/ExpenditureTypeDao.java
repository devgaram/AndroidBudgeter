package org.androidtown.mybudgeter.expendituretype;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface ExpenditureTypeDao {

    @Insert
    void insert(ExpenditureType expenditureType);

    @Update
    void update(ExpenditureType expenditureType);

    @Delete
    void delete(ExpenditureType expenditureType);

    @Query("SELECT * FROM expenditure_type_table")
    LiveData<List<ExpenditureType>> getAllExpenditureTypes();
}
