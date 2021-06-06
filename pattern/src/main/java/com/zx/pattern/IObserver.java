package com.zx.pattern;

public interface IObserver <T> {
    void onChanged(T value);
}