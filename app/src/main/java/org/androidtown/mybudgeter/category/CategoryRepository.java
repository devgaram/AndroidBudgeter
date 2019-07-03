package org.androidtown.mybudgeter.category;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import org.androidtown.mybudgeter.com.BudgeterDatabase;

import java.util.List;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private LiveData<List<Category>> categories;

    public CategoryRepository(Application application) {
        BudgeterDatabase database = BudgeterDatabase.getInstance(application);
        categoryDao = database.categoryDao();
        categories = categoryDao.getAllCategories();
    }

    public LiveData<List<Category>> getAllCategories() {
        return categories;
    }

    public void insert(Category category) {new InsertCategoryAsyncTask(categoryDao).execute(category);}

    public static class InsertCategoryAsyncTask extends AsyncTask<Category, Void, Void>  {
        private CategoryDao categoryDao;

        public InsertCategoryAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.insertAll(categories);
            return null;
        }
    }
}
