package com.zx.pattern;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Nullable;

public interface IProvider {
    void provide(@NonNull Object object);

    @Nullable
    <T> T acquire(Class<T> clz);

    <T> void remove(Class<T> clz);

    void clear();

    <T> Observable<T> observe(Class<T> clz);
}
