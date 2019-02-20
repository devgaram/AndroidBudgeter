package org.androidtown.mybudgeter;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    AnimationDrawable emotionAnimation;
    ImageView emotionImage;
    Toolbar myToolbar;
    BottomAppBar bottomAppBar;
    Menu bottomAppBarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


}
