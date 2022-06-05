package com.yopox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SFX {
    public static final Sound KEY = Gdx.audio.newSound(Gdx.files.internal("sfx/1_key.ogg"));
    public static final Sound TIME_OUT = Gdx.audio.newSound(Gdx.files.internal("sfx/2.1_time_out.ogg"));
    public static final Sound OK = Gdx.audio.newSound(Gdx.files.internal("sfx/2.2_ok.ogg"));
    public static final Sound GREEN = Gdx.audio.newSound(Gdx.files.internal("sfx/3.1_green.ogg"));
    public static final Sound ORANGE = Gdx.audio.newSound(Gdx.files.internal("sfx/3.2_orange.ogg"));
    public static final Sound RED = Gdx.audio.newSound(Gdx.files.internal("sfx/3.3_red.ogg"));
    public static final Sound WIN = Gdx.audio.newSound(Gdx.files.internal("sfx/4_win.ogg"));
    public static final float VOLUME = 0.25f;
}
