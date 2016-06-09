package cn.limc.androidcharts.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.List;

import cn.limc.androidcharts.common.IFlexableGrid;
import cn.limc.androidcharts.entity.DateValueEntity;
import cn.limc.androidcharts.entity.LineEntity;
import cn.limc.androidcharts.event.IGestureDetector;
import cn.limc.androidcharts.event.ISlipable;
import cn.limc.androidcharts.event.LongPressSlipGestureDetector;
import cn.limc.androidcharts.event.OnSlipGestureListener;

/**
 * Created by treycc on 2016/6/8.
 */
public class Minutes2chart extends LineChart implements ISlipable {


    private List<LineEntity<DateValueEntity>> areas;


    public Minutes2chart(Context context) {
        super(context);
    }

    public Minutes2chart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public Minutes2chart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void drawData(Canvas canvas) {
        super.drawData(canvas);
        drawAreas(canvas);
    }

    protected OnSlipGestureListener onSlipGestureListener = new OnSlipGestureListener();

    protected IGestureDetector slipGestureDetector = new LongPressSlipGestureDetector<ISlipable>(this);

    protected boolean detectSlipEvent = true;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isValidTouchPoint(event.getX(), event.getY())) {
            return false;
        }
        if (null == linesData || linesData.size() == 0) {
            return false;
        }
        if (detectSlipEvent) {
            return slipGestureDetector.onTouchEvent(event);
        }else{
            return true;
        }
    }

    protected void drawAreas(Canvas canvas) {
        if (null == areas) {
            return;
        }
        if (0 == areas.size()) {
            return;
        }
        // distance between two points
        float lineLength;
        // start point‘s X
        float startX;

        // draw lines
        for (int i = 0; i < areas.size(); i++) {
            LineEntity<DateValueEntity> line = (LineEntity<DateValueEntity>) areas
                    .get(i);
            if (line == null) {
                continue;
            }
            if (line.isDisplay() == false) {
                continue;
            }
            List<DateValueEntity> lineData = line.getLineData();
            if (lineData == null) {
                continue;
            }

            Paint mPaint = new Paint();
            mPaint.setColor(line.getLineColor());
            mPaint.setAlpha(70);
            mPaint.setAntiAlias(true);

            // set start point’s X
            if (lineAlignType == IFlexableGrid.ALIGN_TYPE_CENTER) {
                lineLength = (dataQuadrant.getPaddingWidth() / getDisplayNumber());
                startX = dataQuadrant.getPaddingStartX() + lineLength / 2;
            } else {
                lineLength = (dataQuadrant.getPaddingWidth() / (getDisplayNumber() - 1));
                startX = dataQuadrant.getPaddingStartX();
            }

            Path linePath = new Path();
            for (int j = getDisplayFrom(); j < getDisplayTo(); j++) {
                float value = lineData.get(j).getValue();
                // calculate Y
                float valueY = (float) ((1f - (value - minValue)
                        / (maxValue - minValue)) * dataQuadrant.getPaddingHeight())
                        + dataQuadrant.getPaddingStartY();

                // if is not last point connect to previous point
                if (j == getDisplayFrom()) {
                    linePath.moveTo(startX, dataQuadrant.getPaddingEndY());
                    linePath.lineTo(startX, valueY);
                } else if (j == getDisplayTo() - 1) {
                    linePath.lineTo(startX, valueY);
                    linePath.lineTo(startX, dataQuadrant.getPaddingEndY());
                } else {
                    linePath.lineTo(startX, valueY);
                }
                startX = startX + lineLength;
            }
            linePath.close();
            canvas.drawPath(linePath, mPaint);
        }
    }

    public void setMaLinesData(List<LineEntity<DateValueEntity>> areas) {
        if (null == areas) {
            return;
        }
        if (0 == areas.size()) {
            return;
        }
        LineEntity<DateValueEntity> line = (LineEntity<DateValueEntity>) areas
                .get(0);
        if (line == null) {
            return;
        }
        if (line.isDisplay() == false) {
            return;
        }
        List<DateValueEntity> lineData = line.getLineData();
        if (lineData == null) {
            return;
        }

        int datasize = lineData.size();

        this.areas = areas;

        if (dataCursor.getMinDisplayNumber() >= datasize) {
            dataCursor.setMaxDisplayNumber(datasize);
            dataCursor.setDisplayFrom(0);
            dataCursor.setDisplayNumber(datasize);
        } else {
            dataCursor.setMaxDisplayNumber(datasize);
            //右侧显示
            dataCursor.setDisplayFrom(datasize - getDisplayNumber());
        }
    }

    @Override
    public void moveLeft() {

    }

    @Override
    public void moveRight() {

    }

    @Override
    public void setOnSlipGestureListener(OnSlipGestureListener listener) {
        this.onSlipGestureListener = listener;
    }

    @Override
    public OnSlipGestureListener getOnSlipGestureListener() {
        return onSlipGestureListener;
    }
}
