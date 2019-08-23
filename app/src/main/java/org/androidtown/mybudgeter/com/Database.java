package org.androidtown.mybudgeter.com;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import org.androidtown.mybudgeter.data.expenditure.ExpenditureDao;
import org.androidtown.mybudgeter.data.expenditure.ExpenditureEntity;
import org.androidtown.mybudgeter.data.budget.BudgetDao;
import org.androidtown.mybudgeter.data.budget.BudgetEntity;
import org.androidtown.mybudgeter.data.category.CategoryDao;
import org.androidtown.mybudgeter.data.category.CategoryEntity;

import java.util.Calendar;
import java.util.Date;

@android.arch.persistence.room.Database(entities = {BudgetEntity.class, CategoryEntity.class, ExpenditureEntity.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    private  static Database instance;

    public abstract BudgetDao budgetDao();
    public abstract CategoryDao categoryDao();
    public abstract ExpenditureDao expenditureDao();

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            Log.i("chk","instance is null");
            instance = Room.databaseBuilder(context,
                    Database.class, "database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        } else {
            Log.i("chk","instance is not null");
        }
        return instance;
    }

    public static Database getDB(){
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i("chk","pre-populate..");
            new Database.PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private BudgetDao budgetDao;
        private CategoryDao categoryDao;
        private ExpenditureDao expenditureDao;

        private PopulateDbAsyncTask(Database db) {
            budgetDao = db.budgetDao();
            categoryDao = db.categoryDao();
            expenditureDao = db.expenditureDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Calendar cal = Calendar.getInstance();
            cal.set(2019, 03, 01);
            Date date = cal.getTime();
            categoryDao.insert(new CategoryEntity(1, "식비"));
            categoryDao.insert(new CategoryEntity(2, "의류비"));
            categoryDao.insert(new CategoryEntity(3, "데이트비용"));
            categoryDao.insert(new CategoryEntity(4, "생활비"));

            return null;
        }
    }





}