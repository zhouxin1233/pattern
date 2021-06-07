package com.zx.pattern.sample

import com.zx.pattern.IProvider
import com.zx.pattern.Observable
import com.zx.pattern.Provider
import java.lang.ref.WeakReference

class ZxDriver private constructor() : IProvider {
    companion object {
        val ins: ZxDriver = ZxDriverHolder.holder

    }
    private object ZxDriverHolder{
        val holder = ZxDriver()
    }


    private val mProvider = Provider()
    override fun provide(obj: Any) {
        mProvider.provide(obj)
    }

    override fun <T : Any?> acquire(clz: Class<T>?): T? {
        return mProvider.acquire(clz)
    }

    override fun <T : Any?> remove(clz: Class<T>?) {
        mProvider.remove(clz)
    }

    override fun clear() {
        mProvider.clear()
    }

    override fun <T : Any?> observe(clz: Class<T>?): Observable<T> {
        return mProvider.observe(clz)
    }

    private var containerWeakRefs = WeakReference<ZxContainer>(null)

    var container: ZxContainer?
        get() = containerWeakRefs.get()
        set(container) = if (container == null) {
            containerWeakRefs.clear()
        } else {
            containerWeakRefs = WeakReference(container)
        }



}