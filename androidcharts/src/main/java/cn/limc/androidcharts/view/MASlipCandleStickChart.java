/*
 * MASlipCandleStickChart.java
 * Android-Charts
 *
 * Created by limc on 2014.
 *
 * Copyright 2011 limc.cn All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.limc.androidcharts.view;

import java.util.ArrayList;
import java.util.List;

import cn.limc.androidcharts.entity.DateValueEntity;
import cn.limc.androidcharts.entity.LineEntity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

/**
 * <p>
 * en
 * </p>
 * <p>
 * jp
 * </p>
 * <p>
 * cn
 * </p>
 *
 * @author limc
 * @version v1.0 2014/01/21 12:03:25
 */
public class MASlipCandleStickChart extends SlipCandleStickChart {

    /**
     * <p>
     * data to draw lines
     * </p>
     * <p>
     * ラインを書く用データ
     * </p>
     * <p>
     * 绘制线条用的数据
     * </p>
     */
    private List<LineEntity<DateValueEntity>> linesData;

    /**
     * <p>
     * Constructor of MASlipCandleStickChart
     * </p>
     * <p>
     * MASlipCandleStickChart类对象的构造函数
     * </p>
     * <p>
     * MASlipCandleStickChartのコンストラクター
     * </p>
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public MASlipCandleStickChart(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    /**
     * <p>
     * Constructor of MASlipCandleStickChart
     * </p>
     * <p>
     * MASlipCandleStickChart类对象的构造函数
     * </p>
     * <p>
     * MASlipCandleStickChartのコンストラクター
     * </p>
     *
     * @param context
     * @param attrs
     */
    public MASlipCandleStickChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    /**
     * <p>
     * Constructor of MASlipCandleStickChart
     * </p>
     * <p>
     * MASlipCandleStickChart类对象的构造函数
     * </p>
     * <p>
     * MASlipCandleStickChartのコンストラクター
     * </p>
     *
     * @param context
     */
    public MASlipCandleStickChart(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<String> calcAxisXGraduate() {
        List<LineEntity<DateValueEntity>> linesData = getLinesData();
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < linesData.size(); i++) {
            List<DateValueEntity> lineData1 = linesData.get(i).getLineData();
            float value = lineData1.get(getSelectedIndex()).getValue();
            switch (i) {
                case 0:
                    stringList.add("MA5:" + value);
                    break;
                case 1:
                    stringList.add("MA10:" + value);
                    break;
                case 2:
                    stringList.add("MA25:" + value);
                    break;
                default:
                    break;
            }
        }
        stringList.add(linesData.get(0).getLineData().get(getSelectedIndex()).getDate() + "");


        return stringList;

    }

    @Override
    protected void calcDataValueRange() {
        super.calcDataValueRange();

        double maxValue = this.maxValue;
        double minValue = this.minValue;
        // 逐条输出MA线
        for (int i = 0; i < this.linesData.size(); i++) {
            LineEntity<DateValueEntity> line = this.linesData.get(i);
            if (line != null && line.getLineData() != null && line.getLineData().size() > 0) {
                // 判断显示为方柱或显示为线条
                for (int j = getDisplayFrom(); j < getDisplayTo(); j++) {
                    DateValueEntity lineData = line.getLineData().get(j);
                    if (isNoneDisplayValue(lineData.getValue())) {
                    } else {
                        if (lineData.getValue() < minValue) {
                            minValue = lineData.getValue();
                        }

                        if (lineData.getValue() > maxValue) {
                            maxValue = lineData.getValue();
                        }
                    }
                }
            }
        }
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    /*
     * (non-Javadoc)
     *
     * <p>Called when is going to draw this chart<p> <p>チャートを書く前、メソッドを呼ぶ<p>
     * <p>绘制图表时调用<p>
     *
     * @param canvas
     *
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void drawData(Canvas canvas) {
        super.drawData(canvas);
        if (getDisplayNumber() > displayStickAsLineNumber) {

        } else {
            drawLines(canvas);
        }
    }


    /**
     * <p>
     * draw lines
     * </p>
     * <p>
     * ラインを書く
     * </p>
     * <p>
     * 绘制线条
     * </p>
     *
     * @param canvas
     */
    protected void drawLines(Canvas canvas) {
        if (null == linesData) {
            return;
        }
        if (linesData.size() <= 0) {
            return;
        }
        // distance between two points
        float lineLength = dataQuadrant.getPaddingWidth() / getDataDisplayNumber() - stickSpacing;
        // start point‘s X
        float startX;

        // draw MA lines
        for (int i = 0; i < linesData.size(); i++) {
            LineEntity<DateValueEntity> line = (LineEntity<DateValueEntity>) linesData
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
            // set start point’s X
            startX = dataQuadrant.getPaddingStartX() + lineLength / 2;
            // start point
            PointF ptFirst = null;
            for (int j = super.getDisplayFrom(); j < super.getDisplayFrom()
                    + super.getDisplayNumber(); j++) {
                float value = lineData.get(j).getValue();
                // calculate Y
                float valueY = (float) ((1f - (value - minValue)
                        / (maxValue - minValue)) * dataQuadrant.getPaddingHeight())
                        + dataQuadrant.getPaddingStartY();

                // if is not last point connect to previous point
                if (j > super.getDisplayFrom()) {
                    canvas.drawLine(ptFirst.x, ptFirst.y, startX, valueY,
                            mPaint);
                }
                // reset
                ptFirst = new PointF(startX, valueY);
                startX = startX + stickSpacing + lineLength;
            }
        }
    }

    /**
     * @return the linesData
     */
    public List<LineEntity<DateValueEntity>> getLinesData() {
        return linesData;
    }

    /**
     * @param linesData the linesData to set
     */
    public void setLinesData(List<LineEntity<DateValueEntity>> linesData) {
        this.linesData = linesData;
    }

}
