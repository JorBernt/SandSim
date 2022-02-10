package com.jbgames.sandsim;

import com.badlogic.gdx.Game;

public class SandSimGame extends Game {

	@Override
	public void create () {
		setScreen(new GameScreen());
	}


	
	@Override
	public void dispose () {
		super.dispose();
	}
}
