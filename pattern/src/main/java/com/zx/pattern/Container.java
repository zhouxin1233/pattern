package com.zx.pattern;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 承载boards && 控制消息的下发
 */
public abstract class Container implements Handler.Callback {
    private final Context mContext;
    private final Handler msgHandler;
    private final Handler uiHandler;
    private final List<Board> mBoards = new ArrayList<>();
    private final SparseArray<List<Board>> classifiedBlocks = new SparseArray<>();
    private ViewGroup containerView;
    private final IProvider mProvider = new Provider();
    private Map<Observable, FilterObservable> retainedObservables = new HashMap<>();

    public Container(Context context) {
        this.mContext = context;
        setup();
        HandlerThread handlerThread = new HandlerThread("MessageThread");
        handlerThread.start();
        msgHandler = new Handler(handlerThread.getLooper(), this);
        uiHandler = new Handler(Looper.getMainLooper());
    }

    public IProvider getProvider() {
        return mProvider;
    }

    public void provide(Object object) {
        if (getProvider() != null ) {
            getProvider().provide(object);
        }
    }

    @Nullable
    public <T> T acquire(Class<T> clz) {
        try {
            if (getProvider() == null) {
                return null;
            }
            return getProvider().acquire(clz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> void remove(Class<T> clz) {
        if (getProvider() != null) {
            getProvider().remove(clz);
        }
    }

    public void clear() {
        getProvider().clear();
    }

    /**
     * Container observe的可以不用释放
     */
    public <T> Observable<T> observe(Class<T> clz) {
        if (getProvider() != null) {
            Observable<T> observable = getProvider().observe(clz);
            FilterObservable<T> filterObservable = retainedObservables.get(observable);
            if (filterObservable == null) {
                filterObservable = new FilterObservable<>(observable);
                retainedObservables.put(observable, filterObservable);
            }
            return filterObservable;
        }
        return null;
    }

    public void runOnUIThread(Runnable runnable) {
        uiHandler.post(runnable);
    }

    private void setup() {
        for (Class<? extends Board> clz : boards()) {
            try {
                Board board = clz.getConstructor(Container.class).newInstance(this);
                mBoards.add(board);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 重新创建所有Block
     */
    public void reset() {
        for (Board board : mBoards) {
            board.onDestroy();
        }
        msgHandler.removeCallbacksAndMessages(null);
        uiHandler.removeCallbacksAndMessages(null);
        mBoards.clear();
        msgHandler.post(classifiedBlocks::clear);
        containerView.removeAllViews();
        setup();
        setupBoard(containerView);
        onCreate();
    }

    protected abstract List<Class<? extends Board>> boards();


    public Context getContext() {
        return mContext;
    }

    public ViewGroup getContainerView() {
        return containerView;
    }

    public void onCreate() {
        for (Board board : mBoards) {
            board.onCreate();
        }
    }

    public void setupBoard(@NonNull ViewGroup view) {
        containerView = view;
        for (Board board : mBoards) {
            board.callSetup(view);
        }
    }

    public void onResume() {
        for (Board board : mBoards) {
            board.onResume();
        }
    }

    public void onPause() {
        for (Board board : mBoards) {
            board.onPause();
        }
    }

    public void onStart() {
        for (Board board : mBoards) {
            board.onStart();
        }
    }

    public void onStop() {
        for (Board board : mBoards) {
            board.onStop();
        }
    }

    public void onDestroy() {
        for (Board board : mBoards) {
            board.onDestroy();
        }
        msgHandler.getLooper().quitSafely();
        for (FilterObservable filterObservable : retainedObservables.values()) {
            filterObservable.onDestroy();
        }
    }

    public void dispatchMessage(int msgType) {
        dispatchMessage(msgType, null);
    }

    public void dispatchMessage(int msgType, Object msg) {
        Message.obtain(msgHandler, msgType, msg).sendToTarget();
    }


    @Override
    public boolean handleMessage(Message msg) {
        int msgType = msg.what;
        Object msgEntity = msg.obj;
        List<Board> messagingBoards = classifiedBlocks.get(msgType);
        if (messagingBoards == null) {
            messagingBoards = new ArrayList<>();
            for (Board board : mBoards) {
                if (board.canHandleMessage(msgType)) {
                    messagingBoards.add(board);
                }
            }
            classifiedBlocks.put(msgType, messagingBoards);
        }
        for (Board board : messagingBoards) {
            board.onReceiveMessage(msgType, msgEntity);
        }
        return true;
    }

    public void finish() {
        if (getContext() instanceof Activity) {
            ((Activity) getContext()).finish();
        }
    }

    /**
     * 自动释放的Observable
     */
    private static class FilterObservable<M> implements Observable<M> {
        private Observable<M> observable;
        private Set<IObserver<M>> observers = new HashSet<>();

        private FilterObservable(Observable<M> observable) {
            this.observable = observable;
        }

        @Override
        public void addObserver(IObserver<M> observer) {
            this.observable.addObserver(observer);
            observers.add(observer);
        }

        @Override
        public void removeObserver(IObserver<M> observer) {
            this.observable.removeObserver(observer);
            observers.remove(observer);
        }

        @Override
        public void update(Setter<M> setter) {
            this.observable.update(setter);
        }

        public void onDestroy() {
            for (IObserver<M> observer : observers) {
                observable.removeObserver(observer);
            }
        }
    }
}
