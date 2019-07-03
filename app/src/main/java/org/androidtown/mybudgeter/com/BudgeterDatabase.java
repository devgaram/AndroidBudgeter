package org.androidtown.mybudgeter.com;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import org.androidtown.mybudgeter.budget.Budget;
import org.androidtown.mybudgeter.budget.BudgetDao;
import org.androidtown.mybudgeter.category.Category;
import org.androidtown.mybudgeter.category.CategoryDao;
import org.androidtown.mybudgeter.expenditure.Expenditure;
import org.androidtown.mybudgeter.expenditure.ExpenditureDao;

@Database(entities = {Budget.class, Expenditure.class, Category.class}, version = 1, exportSchema = false)
public abstract class BudgeterDatabase extends RoomDatabase {

    private  static BudgeterDatabase instance;

    public abstract BudgetDao budgetDao();
    public abstract ExpenditureDao expenditureDao();
    public abstract CategoryDao categoryDao();

    public static synchronized BudgeterDatabase getInstance(Context context) {
        if (instance == null) {
            Log.i("chk","instance is null");
            instance = Room.databaseBuilder(context,
                    BudgeterDatabase.class, "budget_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        } else {
            Log.i("chk","instance is not null");
        }
        return instance;
    }

    public static BudgeterDatabase getDB(){
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i("chk","pre-populate..");
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private BudgetDao budgetDao;
        private ExpenditureDao expenditureDao;
        private CategoryDao categoryDao;

        private PopulateDbAsyncTask(BudgeterDatabase db) {
            budgetDao = db.budgetDao();
            expenditureDao = db.expenditureDao();
            categoryDao = db.categoryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            budgetDao.insert(new Budget("식비",100000, "2019-02-23", "2019-03-23",28));
            budgetDao.insert(new Budget("의류비",50000, "2019-02-23", "2019-03-23",28));
            budgetDao.insert(new Budget("데이트비용",300000, "2019-02-23", "2019-03-23",28));
            budgetDao.insert(new Budget("생활비",150000, "2019-02-23", "2019-03-23",28));
            expenditureDao.insert(new Expenditure(1, 1, "점심다연", 8000, "2019-03-06"));
            expenditureDao.insert(new Expenditure(1, 1, "점심2", 8000, "2019-03-06"));
            expenditureDao.insert(new Expenditure(1, 1, "점심3", 8000, "2019-03-07"));
            expenditureDao.insert(new Expenditure(1, 1, "점심4", 8000, "2019-03-08"));
            expenditureDao.insert(new Expenditure(2, 1, "점심5", 8000, "2019-03-08"));
            expenditureDao.insert(new Expenditure(2, 1, "점심6", 8000, "2019-03-09"));
            categoryDao.insert(new Category(1, "식비"));
            categoryDao.insert(new Category(2, "의류비"));
            categoryDao.insert(new Category(3, "데이트비용"));
            categoryDao.insert(new Category(4, "생활비"));
            return null;
        }
    }





}
