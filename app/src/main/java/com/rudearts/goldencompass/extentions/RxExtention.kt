package com.rudearts.goldencompass.extentions

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.threadToAndroid(): Observable<T> {
    return this
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.threadToIO(): Observable<T> {
    return this
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
}