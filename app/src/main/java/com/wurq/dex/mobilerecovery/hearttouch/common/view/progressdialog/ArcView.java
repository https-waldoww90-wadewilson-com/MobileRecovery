package com.wurq.dex.mobilerecovery.hearttouch.common.view.progressdialog;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.wurq.dex.mobilerecovery.hearttouch.common.util.ResourcesUtil;
import com.wurq.dex.mobilerecovery.R;

/**
 * Created b
 */
public class ArcView extends View {
  static String TAG = "ArcView";
    //圆心位置
    int cx = 0;
    int cy = 0;

    int cxx = 0;
    int cyy = 0;
    int Rr = 0;
    double pointX;
    double pointY;
    float a;

    //进度
    int progress;

    /**
     * 画笔对象的引用
     */
    private Paint paint;

    public ArcView(Context context) {
        this(context, null);
    }

    public ArcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.i(TAG, "draw: ");
        paint.setStrokeWidth(70);
        paint.setColor(ResourcesUtil.getColor(R.color.white));
        paint.setAntiAlias(true);
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        RectF oval = new RectF(cx,cy,cxx,cyy);//根据两个坐标点画一个矩形
        canvas.drawArc(oval,-90, (float) -(360-progress*360/100),false,paint);

        paint.setStrokeWidth(0);
        paint.setColor(ResourcesUtil.getColor(R.color.black));
        paint.setTextSize(75);
        paint.setShader(null);
        paint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
        int percent = (int) (((float) progress / (float) 100) * 100);  //中间的进度百分比，先转换成float在进行除法运算，不然都为0
        float textWidth = paint.measureText(percent + "%");   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间

        if (percent != 0&&percent<=100) {
            canvas.drawText(percent + "%", (cx+cxx)/2 - textWidth / 2, (cy+cyy)/2 + 75 / 2, paint); //画出进度百分比
        }


        pointX =  (cx+cxx)/2+Rr* Math.sin(Math.PI/180*progress/100*360);
        pointY =  ((cy+cyy)/2 -Rr* Math.cos(Math.PI/180*progress/100*360));
        Shader mShader=new LinearGradient((float) pointX-10,(float) pointY-10,(float) pointX+10,(float) pointY+10,
                new int[]{ResourcesUtil.getColor(R.color.loading_ball_color_1),ResourcesUtil.getColor(R.color.loading_ball_color_2),ResourcesUtil.getColor(R.color.loading_ball_color_3)},
                null, Shader.TileMode.CLAMP);
        paint.setShader(mShader);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle((float) pointX,(float) pointY,10,paint);
    }



    public synchronized void setProgress(int progress){
        this.progress = progress;
        if (progress<=100){
            postInvalidate();
        }
    }

    public synchronized void setCx(int x){
        this.cx = x;
    }

    public synchronized void setCy(int y){
        this.cy = y;
    }

    public synchronized void setCxx(int x){
        this.cxx = x;
    }

    public synchronized void setCyy(int y){
        this.cyy = y;
    }

    public synchronized void setR(int y){
        this.Rr = y;
    }



}
