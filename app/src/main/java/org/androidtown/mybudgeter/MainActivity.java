package org.androidtown.mybudgeter;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    AnimationDrawable emotionAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        BudgetFragment budgetFragment = (BudgetFragment) fragmentManager.findFragmentById(R.id.budget_fragment);
        //SpendListFragment spendListFragment = (SpendListFragment) fragmentManager.findFragmentById(R.id.spend_list_fragment);
        ImageView emotionImage = (ImageView) findViewById(R.id.emotion_mouse);
        emotionAnimation = (AnimationDrawable) emotionImage.getDrawable();

        emotionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emotionAnimation.start();
            }
        });
    }
}
