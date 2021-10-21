package main;

import frame.GameFrame;

public class StrikersApp {

	public StrikersApp() {
		init();
	}

	private void init() {
		new GameFrame();
	}

	public static void main(String[] args) {
		new StrikersApp();
	}

}
