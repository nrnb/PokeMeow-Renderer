package main.java.org.cytoscape.pokemeow.internal;

/**
 * Created by ZhangMenghe on 2017/6/18.
 */

import main.java.org.cytoscape.pokemeow.internal.algebra.*;

public final class commonUtil {
    public static final float CAM_ZOOM_RATE = 1.25F;
    public static final Vector2 CAM_CLIP_RANGE = new Vector2(1e-2f, 1e6f);
    public static final Vector2 CAM_VIEWPORT_SIZE = new Vector2(100, 100);
    public static final Vector2 DEMO_VIEWPORT_SIZE = new Vector2(600, 600);
    public static Vector2 getAbsolutePos(float x, float y){
        return new Vector2(DEMO_VIEWPORT_SIZE.x*x, DEMO_VIEWPORT_SIZE.y*y);
    }

    public static Vector2 getRelativePos(float x, float y ){
        return new Vector2(x/DEMO_VIEWPORT_SIZE.x, y/DEMO_VIEWPORT_SIZE.y);
    }
}
