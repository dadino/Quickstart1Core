package com.dadino.quickstart.core.mvp.components;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;


public class ObRandom {

	public static final int MIN = 1;
	public static final int MAX = 5;

	public static <T> Observable<T> time(Observable<T> ob) {
		return time(ob, MIN, MAX);
	}

	public static <T> Observable<T> time(Observable<T> ob, int min, int max) {
		int randomNum = new Random().nextInt((max - min) + 1) + min;
		return Observable.timer(randomNum, TimeUnit.SECONDS)
		                 .flatMap(a -> ob);
	}
}
