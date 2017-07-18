package main.java.org.cytoscape.pokemeow.internal.nodeshape;
import com.jogamp.opengl.GL4;
import main.java.org.cytoscape.pokemeow.internal.algebra.Vector4;

/**
 * Created by ZhangMenghe on 2017/6/22.
 */
public class pmDiamondNodeShape extends pmRectangleNodeShape{
    public pmDiamondNodeShape(GL4 gl4){
        super(gl4);
        setRotation((float) Math.PI/4);
    }
}
