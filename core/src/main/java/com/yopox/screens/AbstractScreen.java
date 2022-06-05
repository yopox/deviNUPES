package com.yopox.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.yopox.DeviNUPES;
import com.yopox.Util;
import com.yopox.ui.Colors;

public abstract class AbstractScreen implements InputProcessor, Screen {
    final DeviNUPES myGame;
    final SpriteBatch myBatch = new SpriteBatch();
    final OrthographicCamera myCamera = new OrthographicCamera();
    final FitViewport myViewport = new FitViewport(Util.WIDTH, Util.HEIGHT, myCamera);

    public AbstractScreen(DeviNUPES game) {
        myGame = game;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        myBatch.setProjectionMatrix(myCamera.combined);
        ScreenUtils.clear(Colors.DEFAULT_BG);
    }

    @Override
    public void resize(int width, int height) {
        myViewport.update(width, height, true);
        myCamera.update();
    }

    @Override
    public void dispose() {
        myBatch.dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
