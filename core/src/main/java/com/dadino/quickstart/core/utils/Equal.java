package com.dadino.quickstart.core.utils;


public class Equal {

	public static boolean equals(Object a, Object b) {
		return (a == b) || (a != null && a.equals(b));
	}
}
