package cn.limc.androidcharts.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;

import java.util.List;

import cn.limc.androidcharts.common.IFlexableGrid;
import cn.limc.androidcharts.entity.DateValueEntity;
import cn.limc.androidcharts.entity.LineEntity;

/**
 * Created by treycc on 2016/6/8.
 */
public class MinutesChart extends SlipAreaChart {

    private List<LineEntity<DateValueEntity>> maLines;

    public MinutesChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MinutesChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MinutesChart(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void drawData(Canvas canvas) {
        super.drawData(canvas);
        drawMaLines(canvas);
    }

    private void drawMaLines(Canvas canvas) {
        if (null == this.maLines) {
            return;
        }
        if (0 == this.maLines.size()) {
            return;
        }
        // distance between two points
        float lineLength;
        // start point‘s X
        float startX;

        // draw lines
        for (int i = 0; i < maLines.size(); i++) {
            LineEntity<DateValueEntity> line = (LineEntity<DateValueEntity>) maLines
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
            mPaint.setAntiAlias(true);
            // start point
            PointF ptFirst = null;
//			if (axisY.getPosition() == IAxis.AXIS_Y_POSITION_LEFT) {
            if (lineAlignType == IFlexableGrid.ALIGN_TYPE_CENTER) {
                lineLength = (dataQuadrant.getPaddingWidth() / getDataDisplayNumber());
                startX = dataQuadrant.getPaddingStartX() + lineLength / 2;
            } else {
                lineLength = (dataQuadrant.getPaddingWidth() / (getDataDisplayNumber() - 1));
                startX = dataQuadrant.getPaddingStartX();
            }

            for (int j = getDisplayFrom(); j < getDisplayTo(); j++) {
                float value = lineData.get(j).getValue();
                if (isNoneDisplayValue(value)) {
                    //无需显示
                } else {
                    // calculate Y
                    float valueY = (float) ((1f - (value - minValue)
                            / (maxValue - minValue)) * dataQuadrant.getPaddingHeight())
                            + dataQuadrant.getPaddingStartY();

                    // if is not last point connect to previous point
                    if (j > getDisplayFrom() && ptFirst != null) {
                        canvas.drawLine(ptFirst.x, ptFirst.y, startX, valueY,
                                mPaint);
                    }
                    // reset
                    ptFirst = new PointF(startX, valueY);
                }
                startX = startX + lineLength;

            }
        }
    }

    public void setMaLinesData(List<LineEntity<DateValueEntity>> maLines) {
        if (null == maLines) {
            return;
        }
        if (0 == maLines.size()) {
            return;
        }
        LineEntity<DateValueEntity> line = (LineEntity<DateValueEntity>) maLines
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

        this.maLines = maLines;

        if (dataCursor.getMinDisplayNumber() >= datasize) {
            dataCursor.setMaxDisplayNumber(datasize);
            dataCursor.setDisplayFrom(0);
            dataCursor.setDisplayNumber(datasize);
        }else{
            dataCursor.setMaxDisplayNumber(datasize);
            //右侧显示
            dataCursor.setDisplayFrom(datasize - getDisplayNumber());
        }
    }
}
