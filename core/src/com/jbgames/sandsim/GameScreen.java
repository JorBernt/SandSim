package com.jbgames.sandsim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreen implements Screen {
    ShapeRenderer sr;
    OrthographicCamera cam;
    List<Sand> sandList;
    List<Water> waterList;
    List<Dirt> dirtList;
    BitmapFont font;
    SpriteBatch batch;
    boolean rain;
    boolean[][] map;
    Random random;

    private enum Mode {
        SAND, WATER, DIRT
    }

    public Mode mode;

    public GameScreen() {
        cam = new OrthographicCamera();
        cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sr = new ShapeRenderer();
        sr.setProjectionMatrix(cam.combined);
        sandList = new ArrayList<>();
        waterList = new ArrayList<>();
        dirtList = new ArrayList<>();
        map = new boolean[360][640];
        font = new BitmapFont();
        batch = new SpriteBatch();
        mode = Mode.SAND;
        rain = false;
        random = new Random();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float mx = Gdx.input.getX();
        float my = Gdx.input.getY();

        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            switch (mode) {
                case DIRT:
                    mode = Mode.SAND;
                    break;
                case SAND:
                    mode = Mode.WATER;
                    break;
                case WATER:
                    mode = Mode.DIRT;
                    break;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            sandList.clear();
            waterList.clear();
            dirtList.clear();
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    map[i][j] = false;
                }
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            rain = !rain;
        }

        if (rain) {
            for (int i = 0; i < map[0].length; i++) {
                if (random.nextInt(100) == 1) {
                    waterList.add(new Water(i * 2, 0));
                }
            }
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            int[] coord = screenCoordToArrayCoord(my, mx);
            switch (mode) {
                case SAND: {
                    for (int i = Math.max(0, coord[0] - 2); i < Math.min(map.length, coord[0] + 2); i++) {
                        for (int j = Math.max(0, coord[1] - 2); j < Math.min(map[0].length, coord[1] + 2); j++) {
                            if (!map[i][j]) {
                                sandList.add(new Sand(j * 2, i * 2));
                                map[i][j] = true;
                            }
                        }
                    }
                }
                case WATER: {
                    for (int i = Math.max(0, coord[0] - 2); i < Math.min(map.length, coord[0] + 2); i++) {
                        for (int j = Math.max(0, coord[1] - 2); j < Math.min(map[0].length, coord[1] + 2); j++) {
                            if (!map[i][j]) {
                                waterList.add(new Water(j * 2, i * 2));
                                map[i][j] = true;
                            }
                        }
                    }
                    break;
                }
                case DIRT: {
                    for (int i = Math.max(0, coord[0] - 2); i < Math.min(map.length, coord[0] + 2); i++) {
                        for (int j = Math.max(0, coord[1] - 2); j < Math.min(map[0].length, coord[1] + 2); j++) {
                            if (!map[i][j]) {
                                dirtList.add(new Dirt(j * 2, i * 2));
                                map[i][j] = true;
                            }
                        }
                    }
                    break;
                }
            }
            map[coord[0]][coord[1]] = true;
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            int[] mcoord = screenCoordToArrayCoord(my, mx);

            /*for(Sand s : sandList) {
                int[] coord = screenCoordToArrayCoord(s.y, s.x);
                if(coord[0] == mcoord[0] && coord[1] == mcoord[1]) {
                    sandList.remove(s);
                    map[coord[0]][coord[1]] = false;
                    break;
                }
            }
            for(Water w : waterList) {
                int[] coord = screenCoordToArrayCoord(s.y, s.x);
                if(coord[0] == mcoord[0] && coord[1] == mcoord[1]) {
                    sandList.remove(s);
                    map[coord[0]][coord[1]] = false;
                    break;
                }
            }*/


            for (int i = Math.max(0, mcoord[0] - 2); i < Math.min(map.length, mcoord[0] + 2); i++) {
                for (int j = Math.max(0, mcoord[1] - 2); j < Math.min(map[0].length, mcoord[1] + 2); j++) {
                    for (Dirt d : dirtList) {
                        int[] coord = screenCoordToArrayCoord(d.y, d.x);
                        if (coord[0] == i && coord[1] == j) {
                            dirtList.remove(d);
                            map[coord[0]][coord[1]] = false;
                            break;
                        }
                    }
                }
            }
        }

        for (Sand s : sandList) {
            s.update(map, delta);
        }
        for (Water w : waterList) {
            w.update(map, delta);
        }

        for (Dirt d : dirtList) {
            d.update(map, delta);
        }


        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        for (Sand s : sandList) {
            sr.rect(s.x, s.y, 2, 2);
        }
        sr.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        for (Water w : waterList) {
            sr.rect(w.x, w.y, 2, 2);
        }
        sr.setColor(50 / 255.0f, 20 / 255.0f, 10 / 255.0f, 1);
        for (Dirt d : dirtList) {
            sr.rect(d.x, d.y, 2, 2);
        }
        sr.end();


        batch.begin();
        font.draw(batch, mode.name(), 100, 700);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    public int[] screenCoordToArrayCoord(float y, float x) {
        return new int[]{(int) (y / 2), (int) (x / 2)};
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
