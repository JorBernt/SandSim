package com.jbgames.sandsim;

import com.badlogic.gdx.math.Rectangle;

public class Sand extends Rectangle {

    public Sand(float x, float y) {
        this.x = x;
        this.y = y;
        width = 1;
        height = 1;
    }

    public void update(boolean[][] map, float delta) {
        int[] coord = screenCoordToArrayCoord(y,x);
        if(coord[0]+1 < map.length) {
            if(!map[coord[0]+1][coord[1]]) {
                y+=2;
                map[coord[0]][coord[1]] = false;
                map[coord[0]+1][coord[1]] = true;
            }
            else if(coord[1]-1 >= 0 && !map[coord[0]+1][coord[1]-1]) {
                y+=2;
                x-=2;
                map[coord[0]][coord[1]] = false;
                map[coord[0]+1][coord[1]-1] = true;
            }
            else if(coord[1]-1 >= 0 && !map[coord[0]+1][coord[1]+1]) {
                y+=2;
                x+=2;
                map[coord[0]][coord[1]] = false;
                map[coord[0]+1][coord[1]+1] = true;
            }
        }
    }

    public int[] screenCoordToArrayCoord(float y, float x) {
        return new int[]{(int)(y/2), (int)(x/2)};
    }

}
