package org.androidtown.mybudgeter.data.category;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.androidtown.mybudgeter.data.category.CategoryEntity;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert
    void insert(CategoryEntity category);

    @Update
    void update(CategoryEntity category);

    @Delete
    void delete(CategoryEntity category);

    @Query("SELECT * FROM category_table")
    LiveData<List<CategoryEntity>> getAllCategories();

    @Insert
    void insertAll(CategoryEntity... categories);
}
