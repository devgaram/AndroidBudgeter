package org.androidtown.mybudgeter.data.budget;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BudgetDao {

    @Insert
    void insert(BudgetEntity budget);

    @Update
    void update(BudgetEntity budget);

    @Delete
    void delete(BudgetEntity budget);

    @Transaction
    @Query("SELECT a.*, b.id as category_id, b.name as category_name, sum(c.amount) as remainAmount FROM budget_table a"
            +" INNER JOIN category_table b ON a.categoryId = b.id "
            +" LEFT OUTER JOIN expenditure_table as c ON c.budetId = a.id"
            +" WHERE DATE(a.endDate/1000, 'unixepoch') >= DATE(datetime('now','localtime'))"
            +" GROUP BY a.id, a.categoryId, a.startDate, a.endDate, a.period, a.amount, b.id, b.name")
    LiveData<List<BudgetWithCategory>> getAllBudgetWithCategory();

    @Query("SELECT a.*, b.id as category_id, b.name as category_name, sum(c.amount) as remainAmount FROM budget_table a"
            +" INNER JOIN category_table b ON a.categoryId = b.id "
            +" LEFT OUTER JOIN expenditure_table as c ON a.id = c.budetId"
            +" WHERE a.id=:id")
    LiveData<BudgetWithCategory> getBudgetWithCategoryForId(int id);

    @Insert
    void insertAll(BudgetEntity... budgets);

}
