package main.java.org.cytoscape.pokemeow.internal.SampleUsage;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.Animator;
import main.java.org.cytoscape.pokemeow.internal.SampleUsage.Demo;
import main.java.org.cytoscape.pokemeow.internal.SampleUsage.debugDraw;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL2ES2.GL_SHADING_LANGUAGE_VERSION;
/**
 * Created by ZhangMenghe on 2017/6/19.
 */
public class debugDrawLauncher implements GLEventListener, KeyListener{
    private final Demo demo;
    private final Runnable onExitHook;
    public debugDrawLauncher(Demo demo, Runnable onExitHook) {
        this.demo = demo;
        this.onExitHook = onExitHook;
    }

    public static void main(String[] args) {
        final GLProfile glProfile = GLProfile.get(GLProfile.GL4);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        final GLWindow glWindow = GLWindow.create(glCapabilities);

        glWindow.setSize(800, 800);
        glWindow.setTitle("gl-scene");
        glWindow.setFullscreen(false);
        glWindow.setVisible(true);

        final Animator animator = new Animator(glWindow);

        final Runnable onExitHook = () -> {
            animator.remove(glWindow);
            glWindow.destroy();
        };

        final Demo demo = new debugDraw();

        final debugDrawLauncher newtLauncher = new debugDrawLauncher(demo, onExitHook);
        glWindow.addGLEventListener(newtLauncher);
        glWindow.addKeyListener(newtLauncher);

        animator.start();
    }

    private static GL4 getGL4(GLAutoDrawable drawable) {
        return drawable.getGL().getGL4();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            onExitHook.run();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void init(GLAutoDrawable drawable) {
        final GL4 gl4 = getGL4(drawable);
        demo.create(gl4);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        demo.dispose(getGL4(drawable));
        System.exit(0);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL4 gl4 = getGL4(drawable);
        gl4.glClearColor(0.4f, 0.4f, 0.4f, 1.0f);
        gl4.glClear(GL_COLOR_BUFFER_BIT);
        demo.render(gl4);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL4 gl4 = getGL4(drawable);
        gl4.glViewport(x, y, width, height);
        demo.resize(gl4,x, y, width, height);
    }
}
