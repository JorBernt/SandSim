package com.jbgames.sandsim;

import com.badlogic.gdx.math.Rectangle;

public class Dirt extends Rectangle {

    public Dirt(float x, float y) {
        x = x;
        y = y;
        this.x = x;
        this.y = y;
        width = 1;
        height = 1;
    }

    public void update(boolean[][] map, float delta) {

    }

    public int[] screenCoordToArrayCoord(float y, float x) {
        return new int[]{(int)(y/2), (int)(x/2)};
    }

}
