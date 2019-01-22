package org.androidtown.mybudgeter;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class ListFragment extends Fragment implements View.OnTouchListener {

    private ListViewModel mViewModel;
    private ViewGroup mListSceneRoot;
    private Scene mListScene1;
    private Scene mListScene2;
    private TransitionManager mTransitionManager;
    private View containerScene1;

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
        containerScene1 = mListSceneRoot.findViewById(R.id.list_container);
        containerScene1.setOnTouchListener(this);

        mListScene2 = Scene.getSceneForLayout(mListSceneRoot, R.layout.list_scene_2, getActivity());





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

        switch (action) {
            case MotionEvent.ACTION_DOWN :
                Log.i("drag","start");
                return true;
            case MotionEvent.ACTION_MOVE :
                Log.i("drag",event.getX() + "," + event.getY());
                return true;
            case MotionEvent.ACTION_UP :
                Log.i("drag","end");
                TransitionManager.go(mListScene2);
                return false;


        }
        return false;
    }
}
