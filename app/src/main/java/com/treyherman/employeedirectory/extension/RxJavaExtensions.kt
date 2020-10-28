package com.treyherman.employeedirectory.extension

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.subscribeOnIO(): Single<T> {
    return this.subscribeOn(Schedulers.io())
}

fun <T> Single<T>.subscribeOnComputation(): Single<T> {
    return this.subscribeOn(Schedulers.computation())
}

fun <T> Single<T>.observeOnMain(): Single<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.subscribeOnIO(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
}

fun <T> Observable<T>.observeOnMain(): Observable<T> {
    return this.observeOn(AndroidSchedulers.mainThread())
}
