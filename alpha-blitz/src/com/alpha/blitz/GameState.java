package com.alpha.blitz;

public enum GameState {

	SPLASH (0),
	MENU (1),
	GAME (2),
	POSTGAME (3),
	OPTIONS (4);
	
	public final int index;
	
	private GameState(int index)
	{
		this.index = index;
	}
}
