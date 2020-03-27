package echang.pxd.netease.ui.splash;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description
 * @Author 彭孝东
 * @QQ 932056657
 */
public class JumpView extends View {
    private Paint mCirclePaint;
    private Paint mTextPaint;

    private float radius;
    private float width = 3;
    private float sweepAngle ;

    private RectF mRect;
    private Timer mTimer;

    private String text = "跳转";
    private float yPosition;
    private float xPosition;

    private OnJumpButtonClickListener onJumpClickListener;

    public JumpView(Context context) {
        this(context,null);
    }

    public JumpView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public JumpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setColor(Color.RED);
        mCirclePaint.setStrokeWidth(dpToPixel(width));

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(36);
        mTextPaint.setStyle(Paint.Style.FILL);
    }


    private float dpToPixel(float px){
        return getResources().getDisplayMetrics().density*px;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (getWidth() >= getHeight()) {
            radius = (getHeight()-2*dpToPixel(width))/2;
        }else{
            radius = (getWidth()-2*dpToPixel(width))/2;
        }
        float padding = dpToPixel(width);
        mRect = new RectF(padding,padding,getWidth()-padding,getHeight()-padding);

        if (mTimer == null){
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    sweepAngle += 360/(3000/200);
                    if (sweepAngle <= 360){
                        invalidate();
                    }else{
                        if (onJumpClickListener != null){
                            onJumpClickListener.onTimeOver();
                        }
                        mTimer.cancel();
                    }
                }
            },0,200);
        }

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float height = fontMetrics.bottom-fontMetrics.top;

        //计算文本y坐标
        yPosition = height/2 - fontMetrics.bottom +getHeight()/2;
        //计算文本x坐标
        xPosition = (getWidth()-mTextPaint.measureText(text))/2;
        System.out.println("y:"+yPosition);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.addArc(mRect,-90,sweepAngle);
        canvas.drawPath(path,mCirclePaint);

        canvas.drawText(this.text,xPosition,yPosition,mTextPaint);
    }

    public interface OnJumpButtonClickListener{
        void onJumpClicked();
        void onTimeOver();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            mTimer.cancel();
            if (onJumpClickListener != null){
                onJumpClickListener.onJumpClicked();
            }
        }
        return true;
    }

    public OnJumpButtonClickListener getOnJumpClickListener() {
        return onJumpClickListener;
    }

    public void setOnJumpClickListener(OnJumpButtonClickListener onJumpClickListener) {
        this.onJumpClickListener = onJumpClickListener;
    }
}
