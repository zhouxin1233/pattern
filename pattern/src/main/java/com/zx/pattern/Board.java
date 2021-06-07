package com.zx.pattern;


import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 功能分区
 */
public abstract class Board implements Serializable {
    private final Context mContext;
    private final WeakReference<Container> containerRef;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public void register(Disposable subscription) {
        mCompositeDisposable.add(subscription);
    }

    public Board(Container container) {
        this.mContext = container.getContext();
        this.containerRef = new WeakReference<>(container);
    }

    @Nullable
    public Container getContainer() {
        return containerRef.get();
    }

//    public void dispatchMessage(int msgType) {
//        dispatchMessage(msgType, null);
//    }
//
//    public void dispatchMessage(int msgType, Object message) {
//        Container container = getContainer();
//        if (container != null) {
//            container.dispatchMessage(msgType, message);
//        }
//    }

    protected void callSetup(ViewGroup root) {
        setup(root);
    }

    public void provide(Object object) {
        if (getContainer() != null) {
            getContainer().provide(object);
        }
    }

    @Nullable
    public <T> T acquire(Class<T> clz) {
        if (getContainer() != null) {
            return getContainer().acquire(clz);
        } else {
            return null;
        }
    }

    public <T> void remove(Class<T> clz) {
        if (getContainer() != null) {
            getContainer().remove(clz);
        }
    }

    public void clear() {
        if (getContainer() != null) {
            getContainer().clear();
        }
    }

    @Nullable
    public <T> Observable<T> observe(Class<T> clz) {
        if (getContainer() != null) {
            return getContainer().observe(clz);
        }
        return null;
    }

    /**
     * 在这个方法里组装View，布局等
     *
     * @param root 父布局
     */
    protected abstract void setup(ViewGroup root);

    public Context getContext() {
        return mContext;
    }

    public void onCreate() {
    }

    public void onDestroy() {
        mCompositeDisposable.clear();
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onStart() {
    }

    public void onStop() {
    }

    /**
     * @param msgType 消息类型
     * @return 是否需要接收该消息
     */
    public abstract boolean canHandleMessage(int msgType);

    /**
     * @param msgType 消息类型
     * @param msg     接收消息
     */
    public abstract void onReceiveMessage(int msgType, Object msg);

    public void runOnUIThread(Runnable runnable) {
        if (getContainer() != null) {
            getContainer().runOnUIThread(runnable);
        }
    }

    public void finish() {
        if (getContainer() != null) {
            getContainer().finish();
        }
    }
}
