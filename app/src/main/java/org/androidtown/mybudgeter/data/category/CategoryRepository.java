package org.androidtown.mybudgeter.data.category;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import org.androidtown.mybudgeter.com.Database;

import java.util.List;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private LiveData<List<CategoryEntity>> categories;

    public CategoryRepository(Application application) {
        Database database = Database.getInstance(application);
        categoryDao = database.categoryDao();
        categories = categoryDao.getAllCategories();
    }

    public LiveData<List<CategoryEntity>> getAllCategories() {
        return categories;
    }

    public void insert(CategoryEntity category) {new InsertCategoryAsyncTask(categoryDao).execute(category);}

    public static class InsertCategoryAsyncTask extends AsyncTask<CategoryEntity, Void, Void>  {
        private CategoryDao categoryDao;

        public InsertCategoryAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(CategoryEntity... categories) {
            categoryDao.insertAll(categories);
            return null;
        }
    }
}
