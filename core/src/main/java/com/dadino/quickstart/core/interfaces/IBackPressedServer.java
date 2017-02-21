package com.dadino.quickstart.core.interfaces;

public interface IBackPressedServer {

	void addBackPressedClient(IBackPressedClient client);
	void removeBackPressedClient(IBackPressedClient client);
}
