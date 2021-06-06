package com.zx.pattern;

import androidx.annotation.Nullable;

public interface Setter <T> {
    T update(@Nullable T t);
}
