package com.yopox;

import com.badlogic.gdx.Game;
import com.yopox.screens.PropositionScreen;
import com.yopox.screens.ResultsScreen;
import com.yopox.screens.SeedScreen;
import com.yopox.screens.TitleScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. Listens to user input.
 */
public class DeviNUPES extends Game {
	TitleScreen title;
	SeedScreen seedScreen;
	PropositionScreen propositions;
	ResultsScreen resultsScreen;

	@Override
	public void create() {
		title = new TitleScreen(this);
		seedScreen = new SeedScreen(this);
		propositions = new PropositionScreen(this);
		resultsScreen = new ResultsScreen(this);
		setScreen(title);
	}

	public void startGame(Util.Mode mode) {
		propositions.reset(mode);
		setScreen(propositions);
	}

	public void showResults(int guesses, Chrono chrono) {
		resultsScreen.reset(guesses, chrono);
		setScreen(resultsScreen);
	}

	public void showResults(int guessed, int guesses) {
		resultsScreen.reset(guessed, guesses);
		setScreen(resultsScreen);
	}

	public void chooseSeed() {
		seedScreen.reset();
		setScreen(seedScreen);
	}

	public void showTitle() {
		setScreen(title);
	}
}