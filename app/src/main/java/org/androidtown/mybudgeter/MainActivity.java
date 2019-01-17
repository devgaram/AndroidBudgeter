package org.androidtown.mybudgeter;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    AnimationDrawable emotionAnimation;
    ImageView emotionImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        BudgetFragment budgetFragment = (BudgetFragment) fragmentManager.findFragmentById(R.id.budget_fragment);


            ImageView imageView = (ImageView) findViewById(R.id.emotion_mouse);
            Drawable drawable = imageView.getDrawable();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (drawable instanceof AnimatedVectorDrawable) {
                    AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) drawable;
                    animatedVectorDrawable.start();
                }
            } else {
                if (drawable instanceof AnimatedVectorDrawableCompat) {
                    AnimatedVectorDrawableCompat animatedVectorDrawableCompat = (AnimatedVectorDrawableCompat) drawable;
                    animatedVectorDrawableCompat.start();
                }
            }

        //SpendListFragment spendListFragment = (SpendListFragment) fragmentManager.findFragmentById(R.id.spend_list_fragment);
        //emotionImage = (ImageView) findViewById(R.id.emotion_mouse);
        //emotionImage.setImageResource(R.drawable.ani_emotion_mouth);
        //emotionAnimation = (AnimationDrawable) emotionImage.getDrawable();
        //emotionAnimation.setEnterFadeDuration(1000);
        //emotionAnimation.setExitFadeDuration(1000);
        //emotionAnimation.start();
        /*
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        emotionImage.setAnimation(animation);
        animation.start();
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Animation fadeOut = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out);
                emotionImage.startAnimation(fadeOut);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
*/

    }
}
