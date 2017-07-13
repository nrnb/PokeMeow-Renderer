package main.java.org.cytoscape.pokemeow.internal.nodeshape;

import com.jogamp.opengl.GL4;
import main.java.org.cytoscape.pokemeow.internal.algebra.Vector4;

/**
 * Created by ZhangMenghe on 2017/6/21.
 */
public class pmTriangleNodeShape extends pmBasicNodeShape{
    private float[] mvertices = {
               .0f,  0.25f, .0f,.0f, .0f, .0f, 1.0f,
            -0.25f, -0.25f, .0f,.0f, .0f, .0f, 1.0f,
             0.25f, -0.25f, .0f,.0f, .0f, .0f, 1.0f
    };
    private int[] colorIndices = {3, 10, 17};

    public pmTriangleNodeShape(GL4 gl4){
        super();
        vertices = mvertices;
        numOfVertices = 3;
        xMin = -0.25f;
        xMax = 0.25f;
        yMin = -0.25f;
        yMax = 0.25f;
        gsthForDraw.initBuiffer(gl4, numOfVertices, vertices);
    }
    @Override
    public void setColor(GL4 gl4, float[] new_color){
        for(int i:colorIndices){
            vertices[i] = new_color[0];
            vertices[i+1] = new_color[1];
            vertices[i+2] = new_color[2];
            vertices[i+3] = new_color[3];
        }
        gsthForDraw.initBuiffer(gl4, numOfVertices, vertices);
    }
    @Override
    public void setColor(GL4 gl4, Vector4 new_color){
        for(int i:colorIndices){
            vertices[i] = new_color.x;
            vertices[i+1] = new_color.y;
            vertices[i+2] = new_color.z;
            vertices[i+3] = new_color.w;
        }
        gsthForDraw.initBuiffer(gl4, numOfVertices, vertices);
    }
    @Override
    public void setColor(GL4 gl4, Vector4 [] colorList){
        int len = colorList.length;
         for(int i:colorIndices){
             int idx = Math.floorDiv(i,7);
             if(idx >=len)
                 idx = 0;
             vertices[i] = colorList[idx].x;
             vertices[i+1] = colorList[idx].y;
             vertices[i+2] = colorList[idx].z;
             vertices[i+3] = colorList[idx].w;
        }
        gsthForDraw.initBuiffer(gl4, numOfVertices, vertices);
    }
    @Override
    public void setDefaultTexcoord(GL4 gl4){
        useTexture = true;
        Vector4 [] coordList = {new Vector4(.0f,.0f,.0f,-1.0f),
                new Vector4(.0f,1.0f,.0f,-1.0f),
                new Vector4(1.0f,.0f,.0f,-1.0f),
                new Vector4(1.0f,1.0f,.0f,-1.0f)
        };
        setColor(gl4,coordList);
    }
    @Override
    public void setZorder(GL4 gl4, int new_z) {
        zorder = new_z;
        for(int i:colorIndices)
            vertices[i-1] = new_z;
        gsthForDraw.initBuiffer(gl4, numOfVertices, vertices);
    }
}
