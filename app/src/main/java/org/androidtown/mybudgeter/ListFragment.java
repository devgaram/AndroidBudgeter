package org.androidtown.mybudgeter;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class ListFragment extends Fragment implements View.OnTouchListener {

    private ListViewModel mViewModel;
    private ViewGroup mListSceneRoot;
    private Scene mListScene1;
    private Scene mListScene2;
    private TransitionManager mTransitionManager;
    Transition mFadeTransition;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    public ListFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        assert view != null; // 조건식이 false면 AssertionError 발생
        /* 레이아웃 전환 기본 셋팅*/
        mListSceneRoot = (ViewGroup) view.findViewById(R.id.list_scene_root);
        mListScene1 = new Scene(mListSceneRoot, (View) mListSceneRoot.findViewById(R.id.container));
        mListSceneRoot.findViewById(R.id.list_container).setOnTouchListener(this);
        mListScene2 = Scene.getSceneForLayout(mListSceneRoot, R.layout.list_scene_2, getActivity());
        mFadeTransition = TransitionInflater.from(getActivity()).inflateTransition(R.transition.fade_transition);
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
        final int action = event.getAction();
        int pointY = 0;
        int eventY = 0;
        int height = 0;
        int width = 0;

        switch (action) {
            case MotionEvent.ACTION_DOWN :
                pointY = Math.round(event.getY());
                height =v.getHeight();
                width = v.getWidth();
                return true;
            case MotionEvent.ACTION_MOVE :
                eventY = Math.round(event.getY());
                height+=pointY-eventY;
                width +=pointY-eventY;
                Log.i("chk",height+"");
                v.setLayoutParams(new FrameLayout.LayoutParams(width,height));
                return true;
            case MotionEvent.ACTION_UP :
                TransitionManager.go(mListScene2,mFadeTransition);
                return false;
        }
        return false;
    }

}
