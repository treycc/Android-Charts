package cn.limc.androidcharts.entity;

/**
 * Created by CUIMENGQI on 2016/6/6.
 */
public class MAEntity implements IHasMA {

    private double ma5;
    private double ma10;
    private double ma20;
    private double ma30;

    public MAEntity(double ma5, double ma10, double ma20, double ma30) {
        this.ma5 = ma5;
        this.ma10 = ma10;
        this.ma20 = ma20;
        this.ma30 = ma30;
    }

    public double getMa5() {
        return ma5;
    }

    public double getMa10() {
        return ma10;
    }

    public double getMa20() {
        return ma20;
    }

    public double getMa30() {
        return ma30;
    }

    public void setMa5(double ma5) {
        this.ma5 = ma5;
    }

    public void setMa10(double ma10) {
        this.ma10 = ma10;
    }

    public void setMa20(double ma20) {
        this.ma20 = ma20;
    }

    public void setMa30(double ma30) {
        this.ma30 = ma30;
    }
}
