package com.yopox;

import com.badlogic.gdx.Game;
import com.yopox.screens.PropositionScreen;
import com.yopox.screens.TitleScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. Listens to user input. */
public class Devinupes extends Game {
	TitleScreen title;
	PropositionScreen propositions;

	@Override
	public void create() {
		title = new TitleScreen(this);
		propositions = new PropositionScreen(this);
		setScreen(title);
	}

	public void startGame() {
		propositions.reset();
		setScreen(propositions);
	}
}