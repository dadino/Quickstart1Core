package com.dadino.quickstart.core.utils;

import java.util.Random;

public class RandomProvider {

	public static int random(int min, int max) {
		return new Random().nextInt(max - min) + min;
	}
}
