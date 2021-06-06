package com.zx.pattern;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class GroupBoard extends Board {

    private List<Board> mChildren = new ArrayList<>();

    public GroupBoard(Container container) {
        super(container);
        for (Class<? extends Board> clz : boards()) {
            try {
                Board board = clz.getConstructor(Container.class).newInstance(container);
                mChildren.add(board);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract List<Class<? extends Board>> boards();

    @Override
    protected void callSetup(ViewGroup root) {
        super.callSetup(root);
        ViewGroup viewGroup = getGroupView();
        if (viewGroup == null) {
            viewGroup = root;
        }
        for (Board child : mChildren) {
            child.callSetup(viewGroup);
        }
    }

    protected ViewGroup getGroupView() {
        return null;
    }

    @Override
    public boolean canHandleMessage(int msgType) {
        for (Board child : mChildren) {
            if (child.canHandleMessage(msgType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onReceiveMessage(int msgType, Object msg) {
        for (Board child : mChildren) {
            if (child.canHandleMessage(msgType)) {
                child.onReceiveMessage(msgType, msg);
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        for (Board child : mChildren) {
            child.onCreate();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (Board child : mChildren) {
            child.onDestroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        for (Board child : mChildren) {
            child.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        for (Board child : mChildren) {
            child.onPause();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        for (Board child : mChildren) {
            child.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        for (Board child : mChildren) {
            child.onStop();
        }
    }
}
