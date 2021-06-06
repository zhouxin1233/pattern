package com.zx.pattern;

public interface Observable<T> {
    void addObserver(IObserver<T> observer);

    void removeObserver(IObserver<T> observer);

    void update(Setter<T> setter);
}
