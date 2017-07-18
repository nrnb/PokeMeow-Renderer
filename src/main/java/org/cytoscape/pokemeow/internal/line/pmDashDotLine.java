package main.java.org.cytoscape.pokemeow.internal.line;

import com.jogamp.opengl.GL4;

/**
 * Created by ZhangMenghe on 2017/7/10.
 */
public class pmDashDotLine extends pmLineVisual{
    private int lineSegments = 50;
    private int numOfPoints;
    private float base;

    public pmDashDotLine(GL4 gl4){
        super(gl4);
        numOfPoints = 3*(lineSegments+1);
        float[] pos = new float[numOfPoints];
        base = 2.0f / lineSegments;
        pos[0] = -1.0f; pos[1] = .0f; pos[2] = .0f;
        base /= 2;
        for(int i=3, n = 1;i<numOfPoints;i+=3,n++){
            if(n%4==1)
                pos[i] = pos[i-3] + 5*base;
            else
                pos[i] = pos[i-3] + base;
            pos[i+1] = .0f;
            pos[i+2] = .0f;
        }
        connectMethod = CONNECT_SEGMENTS;
        initLineVisual(gl4, pos);
    }
}