package org.androidtown.mybudgeter.data.expenditure;

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
    void insert(ExpenditureEntity expenditureEntity);

    @Update
    void update(ExpenditureEntity expenditureEntity);

    @Delete
    void delete(ExpenditureEntity expenditureEntity);

    @Query("SELECT * FROM expenditure_table WHERE budetId=:budgetId")
    LiveData<List<ExpenditureEntity>> getExpenditureForBudget(int budgetId);

    @Query("select DATE(date) as edate, sum(amount) as daysum from expenditure_table where budetId=:budgetId group by date")
    LiveData<List<ExpenditureStatics>> getExpenditureStaticsForBudget(int budgetId);

    @Insert
    void insertAll(ExpenditureEntity... expenditureEntities);

    @Query("SELECT SUM(amount) FROM expenditure_table WHERE budetId=:budgetId")
    long getExpenditureSumForBudget(int budgetId);
}
