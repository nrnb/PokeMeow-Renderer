package main.java.org.cytoscape.pokemeow.internal.rendering;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by ZhangMenghe on 2017/8/4.
 */
public class pmEdgeBuffer {
    public int VAO = 0;
    public int VBO = 1;
    public int EBO = 2;//index buffer, maybe no use
    public int[] objects = new int[3];
    public int numOfVertices;
    public int numOfIndices;
//    public FloatBuffer data_buff;
    public int capacity = 4000;
    public int capacityIdx = 4000;
    //-1: no need to resize
    //0: should double vbo
    //1: should double ebo
    public int shouldBeResize = -1;
    public int dataOffset;
    public int indexOffset;
    public int dataOffsetPre = 0; //indicate the head of VBO
    public int indexOffsetPre = 0; //indicate the head of EBO
    public pmEdgeBuffer(GL4 gl4){
        gl4.glGenVertexArrays(1,objects,VAO);
        gl4.glGenBuffers(1,objects,VBO);
        gl4.glGenBuffers(1,objects,EBO);

        gl4.glBindVertexArray(objects[VAO]);
        gl4.glBindBuffer(GL.GL_ARRAY_BUFFER, objects[VBO]);
        gl4.glBufferData(GL.GL_ARRAY_BUFFER, capacity, null, GL.GL_DYNAMIC_DRAW);

        gl4.glEnableVertexAttribArray(0);
        gl4.glVertexAttribPointer(0, 3, GL.GL_FLOAT,false, 3*Float.BYTES, 0);

        gl4.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER,objects[EBO]);
        gl4.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, capacityIdx,null, GL.GL_DYNAMIC_DRAW);

        gl4.glBindBuffer(GL.GL_ARRAY_BUFFER,0);
        gl4.glBindVertexArray(0);
    }
    public void doubleVBOSize(GL4 gl4){
        gl4.glBindVertexArray(objects[VAO]);
        gl4.glBindBuffer(GL.GL_ARRAY_BUFFER, objects[VBO]);

        int[] t_object = new int[1];
        gl4.glGenBuffers(1,t_object,0);
        gl4.glBindBuffer(GL4.GL_COPY_READ_BUFFER, t_object[0]);
        gl4.glBufferData(GL4.GL_COPY_READ_BUFFER, capacity, null, GL.GL_STATIC_DRAW);
        gl4.glCopyBufferSubData(GL4.GL_ARRAY_BUFFER,GL4.GL_COPY_READ_BUFFER,0,0,capacity);

        gl4.glBindBuffer(GL.GL_ARRAY_BUFFER,  objects[VBO]);
        gl4.glBufferData(GL.GL_ARRAY_BUFFER, capacity*2, null, GL.GL_STATIC_DRAW);
        gl4.glCopyBufferSubData(GL4.GL_COPY_READ_BUFFER, GL.GL_ARRAY_BUFFER, 0, 0, capacity);
//        FloatBuffer tmp = Buffers.newDirectFloatBuffer(new float[capacity]);
//        gl4.glGetBufferSubData(GL4.GL_ARRAY_BUFFER, 0, capacity, tmp);
//        float [] debug = new float[capacity];
//        tmp.get(debug);
        capacity*=2;
    }

    public void doubleEBOSize(GL4 gl4){
        gl4.glBindVertexArray(objects[VAO]);
        gl4.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, objects[EBO]);

        int[] t_object = new int[1];
        gl4.glGenBuffers(1, t_object,0);
        gl4.glBindBuffer(GL4.GL_COPY_READ_BUFFER, t_object[0]);
        gl4.glBufferData(GL4.GL_COPY_READ_BUFFER, capacityIdx, null, GL.GL_STATIC_DRAW);
        gl4.glCopyBufferSubData(GL4.GL_ELEMENT_ARRAY_BUFFER,GL4.GL_COPY_READ_BUFFER,0,0,capacityIdx);

        gl4.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER,  objects[EBO]);
        gl4.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, capacityIdx*2, null, GL.GL_STATIC_DRAW);
        gl4.glCopyBufferSubData(GL4.GL_COPY_READ_BUFFER, GL.GL_ELEMENT_ARRAY_BUFFER, 0, 0, capacityIdx);
        capacityIdx*=2;
    }
}
