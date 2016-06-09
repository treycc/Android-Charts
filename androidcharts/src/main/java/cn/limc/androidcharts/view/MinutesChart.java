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
    }

}
