package com.example.adview.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CanvasView extends View {

    private Paint mPaint;

    public CanvasView(Context context) {
        super(context);
        initPaint();
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint(){
        mPaint = new Paint();
        //画笔颜色
        mPaint.setColor(Color.RED);
        //填充样式 FILL 填充满  FILL_AND_STROKE 描边以及填充  STROKE指代边线上用多粗的线去画
        mPaint.setStyle(Paint.Style.STROKE);
        //画笔宽度
        mPaint.setStrokeWidth(5);
    }

    //在其中不能创建对象，因为此函数是会经常调用的
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        drawRegion(canvas);

        /**
         * 水平方向和竖直方向平移的距离，向下以及向右为正方向
         * 在平移之前所画的图像并不会随着一起变动，相当于移动当前的坐标系吧
         */
        canvas.translate(100,100);
    }

    private void drawNormal(Canvas canvas){
        //画直线，对应起止位置坐标 ， 线粗细只与画笔粗细有关与Style无关
        canvas.drawLine(20,20,50,50,mPaint);

        //画点，点的大小与画笔粗细有关
        canvas.drawPoint(50,50,mPaint);

        //画矩形 矩形工具类：Rect and RectF，参数区别，int和float,参数 左、上、右、下
        canvas.drawRect(new Rect(1,1,100,100),mPaint);

        //画圆
        canvas.drawCircle(190, 200, 150, mPaint);

        //填充背景颜色 也可以为：0xAARRGGBB
        canvas.drawColor(Color.RED);
        canvas.drawColor(0xFFFFFFFF);
        //4个0x00～0xFF的数，颜色深度和颜色
        canvas.drawARGB(0xFF,0xFF,0xFF,0xFF);
        //同上 Alpha=255
        canvas.drawRGB(0xFF,0xFF,0xFF);

        //返回一个int的color值
        Color.argb(0xFF,0xFF,0xFF,0xFF);
        //不带透明度
        Color.rgb(0xFF,0xFF,0xFF);
        //提取颜色int值中的a，r，g，b四个值
        int alpha = Color.alpha(0xFF);
        Color.red(0xFF);
        Color.green(0xFF);
        Color.blue(0xFF);
    }

    private void drawPath(Canvas canvas){
        Path path = new Path();
        //设置起始点坐标
        path.moveTo(10,10);
        //画笔移动到
        path.lineTo(10,10);
        //闭环
        path.close();
        canvas.drawPath(path,mPaint);
    }


    //画弧线路径,
    private void drawAct(Canvas canvas){
        Path path = new Path();
        path.moveTo(10,10);
        RectF rectF = new RectF(100,10,200,100);
        //还可以添加参数forceMoveTo，表示是否强制改变其实点
        path.arcTo(rectF,0,90);
        canvas.drawPath(path,mPaint);
//        canvas.drawRect(rectF,paint);
    }

    private void drawRegion(Canvas canvas){
        Region region = new Region(70,70,80,500);
        region.union(new Rect(60,60,400,100));
        Path path = new Path();
        path.addOval(new RectF(50,50,200,500), Path.Direction.CCW);
        region.setPath(path,region);
        RegionIterator iterator = new RegionIterator(region);
        Rect rect = new Rect();
        while (iterator.next(rect)) {
            canvas.drawRect(rect,mPaint);
        } 
    }

    /**
     * 裁减画布，为不可逆操作
     */
    @SuppressLint("NewApi")
    public void clipCanvas(Canvas canvas){
        Path path = new Path();
        canvas.clipOutPath(path);
        canvas.clipOutRect(new Rect());
    }

    /**
     * 因为基本上对于画布的操作都是不可逆的
     * 所以我们可以对画布状态进行保存，方便操作之后的回退
     * @param canvas
     */
    public void canvasStack(Canvas canvas){
        //每次调用此函数，都会保存当前画布的状态，然后放入特定的栈中
        canvas.save();
        //把栈顶层的画布状态取出，恢复到当前状态上
        canvas.restore();
    }
}
