package com.jbgames.sandsim;

import com.badlogic.gdx.math.Rectangle;

public class Water extends Rectangle {

    int dir = 0;
    float velocity;

    public Water(float x, float y) {
        x = x;
        y = y;
        velocity = 0.001f;
        this.x = x;
        this.y = y;
        width = 1;
        height = 1;
    }

    public void update(boolean[][] map, float delta) {
        int[] coord = screenCoordToArrayCoord(y,x);
        boolean moved = false;
        if(coord[0]+1 < map.length) {
            if(!map[coord[0]+1][coord[1]]) {
                y+=2;
                moved = true;
                map[coord[0]][coord[1]] = false;
                map[coord[0]+1][coord[1]] = true;
                dir=1;
                velocity = 0.001f;
            }
            else if(coord[1]-1 >= 0 && !map[coord[0]+1][coord[1]-1]) {
                y+=2;
                x-=2;
                moved = true;
                map[coord[0]][coord[1]] = false;
                map[coord[0]+1][coord[1]-1] = true;
                dir = 2;
                velocity = 0.001f;
            }
            else if(coord[1]+1 < map[0].length && !map[coord[0]+1][coord[1]+1]) {
                y+=2;
                x+=2;
                moved = true;
                map[coord[0]][coord[1]] = false;
                map[coord[0]+1][coord[1]+1] = true;
                dir = 0;
                velocity = 0.001f;
            }
            else if(coord[1]-1 >= 0 && !map[coord[0]][coord[1]-1]) {
                x-=2;
                map[coord[0]][coord[1]] = false;
                map[coord[0]][coord[1]-1] = true;
            }
            else if(coord[1]+1 < map[0].length && !map[coord[0]][coord[1]+1]) {
                x+=2;
                map[coord[0]][coord[1]] = false;
                map[coord[0]][coord[1]+1] = true;
            }


        }
    }

    public int[] screenCoordToArrayCoord(float y, float x) {
        return new int[]{(int)(y/2), (int)(x/2)};
    }

}
