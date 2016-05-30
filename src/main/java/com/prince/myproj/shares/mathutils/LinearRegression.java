package com.prince.myproj.shares.mathutils;

import org.apache.log4j.Logger;

/**
 * Created by zidong.wang on 2016/5/25.
 */
public class LinearRegression {
    public static final Logger logger = Logger.getLogger(LinearRegression.class);

    double[] x;
    double[] y;
    double a;
    double b;

    public LinearRegression(double[] x,double[] y){
        if(x.length!=y.length){
            logger.error("传入的x和y长度不一致");
            return;
        }
        this.x = x;
        this.y = y;
        regression();
    }
    //拟合
    private void regression(){
        int len = x.length;
        double sumx=0;
        double sumy=0;
        double sumxy=0;
        double sumx2=0;

        for(int i=0;i<len;i++){
            double xitem = x[i];
            double yitem = y[i];
            sumx += xitem;
            sumy += yitem;
            sumxy += xitem*yitem;
            sumx2 += xitem*xitem;
        }

        a = (len*sumxy - sumx*sumy)/(len*sumx2-sumx*sumx);
        b = sumy/len - a*sumx/len;
    }

    //计算函数值
    public double lineY(double x){
        return a*x+b;
    }
    public double lineX(double y){
        return (y-b)/a;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setY(double[] y) {
        this.y = y;
    }

    public void setX(double[] x) {
        this.x = x;
    }
}
