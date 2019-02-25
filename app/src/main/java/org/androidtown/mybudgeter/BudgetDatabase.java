package org.androidtown.mybudgeter;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Budget.class}, version = 1, exportSchema = false)
public abstract class BudgetDatabase extends RoomDatabase {

    private  static BudgetDatabase instance;

    public abstract BudgetDao budgetDao();

    public static synchronized BudgetDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BudgetDatabase.class, "budget_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private BudgetDao budgetDao;

        private PopulateDbAsyncTask(BudgetDatabase db) {
            budgetDao = db.budgetDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            budgetDao.insert(new Budget("식비",100000, "2019-02-25", "2019-03-25",28));
            return null;
        }
    }





}
