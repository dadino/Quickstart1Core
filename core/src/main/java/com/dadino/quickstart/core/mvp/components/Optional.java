package com.dadino.quickstart.core.mvp.components;

import androidx.annotation.Nullable;

public class Optional<T> {

	@Nullable
	private final T element;

	public Optional(@Nullable T element) {
		this.element = element;
	}

	@Nullable
	public T getElement() {
		return element;
	}
}
