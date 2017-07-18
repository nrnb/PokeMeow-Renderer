package main.java.org.cytoscape.pokemeow.internal.nodeshape;

import com.jogamp.opengl.GL4;
import main.java.org.cytoscape.pokemeow.internal.algebra.Vector3;
import main.java.org.cytoscape.pokemeow.internal.algebra.Vector4;

/**
 * Created by ZhangMenghe on 2017/6/22.
 */
public class pmRectangleNodeShape extends pmBasicNodeShape{
    public float[] mvertices = {
        0.25f,  0.25f, .0f,.0f, .0f, .0f, 1.0f, // Top-left
        0.25f, -0.25f, .0f,.0f, .0f, .0f, 1.0f, // Top-right
        -0.25f, -0.25f, .0f,.0f, .0f, .0f, 1.0f, // Bottom-right
        -0.25f,  0.25f, .0f,.0f, .0f, .0f, 1.0f  // Bottom-left
    };
    public int []elements = {
            3, 0, 1,
            3, 1, 2
    };

    public pmRectangleNodeShape(GL4 gl4){
        super();
        xMinOri = -0.25f;xMaxOri = 0.25f;yMinOri = -0.25f;yMaxOri = 0.25f;
        xMin= xMinOri;xMax = xMaxOri;yMin = yMinOri;yMax = yMaxOri;

        vertices = mvertices;
        numOfVertices = 4;
        gsthForDraw.initBuiffer(gl4, numOfVertices, vertices, elements);
    }

    public pmRectangleNodeShape(){
        super();
        xMinOri = -0.25f;xMaxOri = 0.25f;yMinOri = -0.25f;yMaxOri = 0.25f;
        xMin= xMinOri;xMax = xMaxOri;yMin = yMinOri;yMax = yMaxOri;
        vertices = mvertices;
        numOfVertices = 4;
    }

    @Override
    public boolean isHit(float posx, float posy) {
        if (posx<xMin || posx>xMax || posy<yMin || posy>yMax)
            return false;
        return super.isHit(posx,posy);
    }
}
