package org.androidtown.mybudgeter.category;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import org.androidtown.mybudgeter.data.category.CategoryEntity;
import org.androidtown.mybudgeter.data.category.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository categoryRepository;
    private LiveData<List<CategoryEntity>> allCategories;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
        allCategories = categoryRepository.getAllCategories();
    }

    public LiveData<List<CategoryEntity>> getAllCategories() { return allCategories;}
}
