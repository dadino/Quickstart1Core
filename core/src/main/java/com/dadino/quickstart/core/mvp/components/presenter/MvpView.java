package com.dadino.quickstart.core.mvp.components.presenter;


import com.dadino.quickstart.core.interfaces.ICompleted;
import com.dadino.quickstart.core.interfaces.IError;
import com.dadino.quickstart.core.interfaces.ILoad;
import com.dadino.quickstart.core.interfaces.INext;
import com.dadino.quickstart.core.interfaces.ISub;

public class MvpView<ITEM> {

	public final INext<ITEM> next;
	public final ICompleted  completed;
	public final IError      error;
	public final ILoad       load;
	public final ISub        sub;

	public MvpView(INext<ITEM> next, ICompleted completed, IError error, ILoad load, ISub sub) {
		this.next = next;
		this.completed = completed;
		this.error = error;
		this.load = load;
		this.sub = sub;
	}

	public MvpView(ICompleted completed, IError error, ILoad load, ISub sub) {
		this.next = null;
		this.completed = completed;
		this.error = error;
		this.load = load;
		this.sub = sub;
	}

	public MvpView(INext<ITEM> next, IError error, ILoad load, ISub sub) {
		this.next = next;
		this.completed = null;
		this.error = error;
		this.load = load;
		this.sub = sub;
	}


	public MvpView(IError error, ILoad load, ISub sub) {
		this.next = null;
		this.completed = null;
		this.error = error;
		this.load = load;
		this.sub = sub;
	}

	public MvpView(INext<ITEM> next, ISub sub) {
		this.next = next;
		this.completed = null;
		this.error = null;
		this.load = null;
		this.sub = sub;
	}

	public MvpView(INext<ITEM> next) {
		this.next = next;
		this.completed = null;
		this.error = null;
		this.load = null;
		this.sub = null;
	}

	public MvpView(INext<ITEM> next, IError error) {
		this.next = next;
		this.error = error;
		this.completed = null;
		this.load = null;
		this.sub = null;
	}

	public MvpView(INext<ITEM> next, IError error, ISub sub) {
		this.next = next;
		this.error = error;
		this.completed = null;
		this.load = null;
		this.sub = sub;
	}

	public MvpView(IError error) {
		this.next = null;
		this.completed = null;
		this.error = error;
		this.load = null;
		this.sub = null;
	}

	public MvpView(IError error, ISub sub) {
		this.next = null;
		this.completed = null;
		this.error = error;
		this.load = null;
		this.sub = sub;
	}

	public MvpView(INext<ITEM> next, ILoad load) {
		this.next = next;
		this.error = null;
		this.completed = null;
		this.load = load;
		this.sub = null;
	}

	public MvpView(ILoad load) {
		this.next = null;
		this.error = null;
		this.completed = null;
		this.load = load;
		this.sub = null;
	}

	public MvpView(INext<ITEM> next, IError error, ILoad load) {
		this.next = next;
		this.error = error;
		this.completed = null;
		this.load = load;
		this.sub = null;
	}
}
