package org.androidtown.mybudgeter;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

public class ListFragment extends Fragment implements View.OnTouchListener {

    private ListViewModel mViewModel;
    private ViewGroup mListSceneRoot;
    private Scene mListScene1;
    private Scene mListScene2;
    private TransitionManager mTransitionManager;
    Transition mFadeTransition;
    Transition mUpTransition;
    Transition mDownTransition;

    // 리스트 터치이벤트 필요 변수
    float pointY;
    float firstPointY;

    public static ListFragment newInstance() {
        return new ListFragment();
    }
    public ListFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        assert view != null; // 조건식이 false면 AssertionError 발생
        /* 레이아웃 전환 기본 셋팅*/
        view.bringToFront();
        mListSceneRoot = (ViewGroup) view.findViewById(R.id.list_scene_root);
        mListScene1 = new Scene(mListSceneRoot, (View) mListSceneRoot.findViewById(R.id.container));
        mListScene2 = Scene.getSceneForLayout(mListSceneRoot, R.layout.list_scene_2, getActivity());
        mFadeTransition = TransitionInflater.from(getActivity()).inflateTransition(R.transition.fade_transition);
        mUpTransition = TransitionInflater.from(getActivity()).inflateTransition(R.transition.up_transition);
        mDownTransition = TransitionInflater.from(getActivity()).inflateTransition(R.transition.down_transition);
        mListScene1.getSceneRoot().findViewById(R.id.list_container_1).setOnTouchListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        // TODO: Use the ViewModel
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float eventY = 0;
        int id = v.getId();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN :
                if (id == R.id.list_container_1) {
                    TranslateAnimation animate = new TranslateAnimation(0, 0, v.getHeight(), 0);
                    animate.setDuration(300);
                    animate.setBackgroundColor(Color.WHITE);
                    animate.setFillAfter(true);
                    v.startAnimation(animate);
                    firstPointY = event.getRawY();
                    pointY = event.getRawY();
                } else if (id == R.id.list_container_2) {
                    firstPointY = event.getRawY();
                    pointY = event.getRawY();
                } else {
                }
                break;
            case MotionEvent.ACTION_MOVE :
                if (id == R.id.list_container_1) {
                    if (firstPointY-pointY > 50) {
                        /*FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) v.getLayoutParams();
                        eventY = event.getRawY();
                        layoutParams.rightMargin -= (pointY - eventY) / 3;
                        layoutParams.leftMargin -= (pointY - eventY) / 3;
                        layoutParams.height += pointY - eventY;
                        layoutParams.bottomMargin = 10;
                        v.setLayoutParams(layoutParams);*/

                        TransitionManager.go(mListScene2, mUpTransition);
                        mListScene2.getSceneRoot().findViewById(R.id.list_container_2).setOnTouchListener(this);
                        mListScene2.getSceneRoot().findViewById(R.id.list_container_2).onTouchEvent(event);

                    }
                    pointY = event.getRawY();
                } else if (id == R.id.list_container_2) {
                   if (pointY - firstPointY > 50) {

                       TransitionManager.go(mListScene1, mDownTransition);
                       mListScene1.getSceneRoot().findViewById(R.id.list_container_1).setOnTouchListener(this);
                   }
                    pointY = event.getRawY();
                } else {
                }
                break;
            case MotionEvent.ACTION_UP :
                if (id == R.id.list_container_1) {
                    if (firstPointY-event.getRawY() > 50) {
                        TransitionManager.go(mListScene2, mUpTransition);
                        mListScene2.getSceneRoot().findViewById(R.id.list_container_2).setOnTouchListener(this);
                        break;
                    }
                } else if (id == R.id.list_container_2) {
                } else {
                }
                break;
        }
        return true;
    }

}
