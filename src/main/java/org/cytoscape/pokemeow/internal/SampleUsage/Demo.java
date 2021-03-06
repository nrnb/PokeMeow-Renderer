package main.java.org.cytoscape.pokemeow.internal.SampleUsage;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.Animator;
import main.java.org.cytoscape.pokemeow.internal.algebra.Matrix4;
import main.java.org.cytoscape.pokemeow.internal.algebra.Vector2;
import main.java.org.cytoscape.pokemeow.internal.algebra.Vector3;
import main.java.org.cytoscape.pokemeow.internal.commonUtil;
import main.java.org.cytoscape.pokemeow.internal.rendering.pmShaderParams;

import java.io.IOException;

/**
 * Created by ZhangMenghe on 2017/7/18.
 */
public class Demo implements GLEventListener, MouseListener, KeyListener {
    public Matrix4 viewMatrix = Matrix4.identity();
    public Matrix4 zoomMatrix = Matrix4.identity();
    protected Matrix4 lastViewMatrix = Matrix4.identity();
    protected GL4 gl4;
    protected int numOfItems;
    protected pmShaderParams gshaderParam;
    protected int program;
    protected Vector2 lastMousePosition = new Vector2(.0f, .0f);
    protected int times = 0;
    public Vector2 viewportSize = new Vector2(600,600);

    @Override
    public void init(GLAutoDrawable drawable){
        gl4 = drawable.getGL().getGL4();
    }
    @Override
    public void display(GLAutoDrawable drawable){
        gl4.glUseProgram(program);
        gl4.glClear(GL4.GL_DEPTH_BUFFER_BIT | GL4.GL_COLOR_BUFFER_BIT);

    }
    @Override
    public void dispose(GLAutoDrawable drawable){}

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        viewportSize.x = width;
        viewportSize.y = height;
        viewMatrix = Matrix4.scale(new Vector3(commonUtil.DEMO_VIEWPORT_SIZE.x/viewportSize.x,commonUtil.DEMO_VIEWPORT_SIZE.y/viewportSize.y,1.0f));
        lastViewMatrix = Matrix4.identity();
        setReshapeMatrix();
        gl4.glClearColor(0.2f, 0.2f, 0.2f,1.0f);
        gl4.glViewport(x, y, width, height);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseEvent e){}

    @Override
    public void mouseDragged(MouseEvent e){}

    @Override
    public void mouseMoved(MouseEvent e){}

    @Override
    public void keyPressed(KeyEvent e){}

    @Override
    public void keyReleased(KeyEvent e){}

    public void reSetMatrix(boolean viewChanged){
        if(viewChanged){
            viewMatrix = Matrix4.mult(lastViewMatrix, viewMatrix);
            lastViewMatrix = viewMatrix;
        }
    }

    public void setReshapeMatrix(){}

    public static void main(String[] args) throws IOException {
        final GLProfile glProfile = GLProfile.get(GLProfile.GL4);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        final GLWindow glWindow = GLWindow.create(glCapabilities);
        final Animator animator = new Animator(glWindow);

        glWindow.setSize(600, 600);
        glWindow.setTitle("Demo");
        glWindow.setFullscreen(false);
        glWindow.setVisible(true);

//        final mousePickupDemo demo = new mousePickupDemo();
//        final simpleTriangleDemo demo = new simpleTriangleDemo();
        final drawNodesDemo demo = new drawNodesDemo();
//        final drawArrowDemo demo = new drawArrowDemo();
//        final drawCurveDemo demo = new drawCurveDemo();
//        final drawLineDemo demo = new drawLineDemo();
//        final renderToTextureDemo demo = new renderToTextureDemo();
//        final TextRendererDemo demo = new TextRendererDemo();
//        final drawEdgeDemo demo = new drawEdgeDemo();
//        final edgeHitDemo demo = new edgeHitDemo();
//        final drawNodeAndEdgeDemo demo = new drawNodeAndEdgeDemo();
//        final drawSimpleNodesDemo demo = new drawSimpleNodesDemo();
        glWindow.addGLEventListener(demo);
        glWindow.addMouseListener(demo);
        glWindow.addKeyListener(demo);
        animator.start();
    }

}
