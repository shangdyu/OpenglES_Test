package com.shangdyu.cuijia.another;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by cuiji on 2015/9/6.
 */
public class MyTDView extends GLSurfaceView {

    final float ANGLE_SPAN = 0.375f;
    RotateThread rthread;
    SceneRenderer mRenderer;
    public MyTDView(Context context) {
        super(context);
        this.setEGLContextClientVersion(2);
        mRenderer = new SceneRenderer();
        this.setRenderer(mRenderer);
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    private class SceneRenderer implements GLSurfaceView.Renderer
    {
        Triangle tle;

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES20.glClearColor(0,0,0,1.0f);
            tle = new Triangle(MyTDView.this);
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            rthread = new RotateThread();
            rthread.start();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0, 0, width, height);
            float ratio = (float) width / height;
            Matrix.frustumM(Triangle.mMMatrix, 0, -ratio, ratio, 01, 1, 1, 10);
            Matrix.setLookAtM(Triangle.mVMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f ,1.0f, 0.0f);
        }

        public void onDrawFrame(GL10 gl){
            GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            tle.drawSelf();
        }
    }

    public class RotateThread extends Thread
    {
        public boolean flag = true;
        @Override
        public void run(){
            while(flag){
                mRenderer.tle.xAngle = mRenderer.tle.xAngle + ANGLE_SPAN;
                try {
                    Thread.sleep(20);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
