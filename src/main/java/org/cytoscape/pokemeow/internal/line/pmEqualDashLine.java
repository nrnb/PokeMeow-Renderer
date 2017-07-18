package main.java.org.cytoscape.pokemeow.internal.line;

import com.jogamp.opengl.GL4;

/**
 * Created by ZhangMenghe on 2017/7/10.
 */
public class pmEqualDashLine extends pmLineVisual{
    private int lineSegments = 50;
    private int numOfPoints;
    private float base;
    public pmEqualDashLine(GL4 gl4){
        super(gl4);
        numOfPoints = 3*(lineSegments+1);
        float[] pos = new float[numOfPoints];
        base = 2.0f/lineSegments;

        for(int i=0, n = 0;i<numOfPoints;i+=3,n++){
            pos[i] = -1.0f + base*n;
            pos[i+1] = .0f;
            pos[i+2] = .0f;
        }
        connectMethod = CONNECT_SEGMENTS;
        initLineVisual(gl4, pos);
    }
}