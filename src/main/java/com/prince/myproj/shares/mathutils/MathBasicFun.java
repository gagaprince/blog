package com.prince.myproj.shares.mathutils;

import java.util.List;

/**
 * Created by zidong.wang on 2016/8/1.
 */
public class MathBasicFun {

    private float mean;
    private float variance;
    private float range;

    private List<Float> nums;
    public MathBasicFun(List<Float> nums){
        this.nums = nums;
    }
    public void excute(){
        float max=-1000;
        float min=1000;
        float sum = 0;
        int size = nums.size();
        for(int i=0;i<size;i++){
            float num = nums.get(i);
            if(num>max){
                max=num;
            }
            if(num<min){
                min=num;
            }
            sum+=num;
        }
        mean = sum/size;
        range = max-min;
        float sumV = 0;
        for(int i=0;i<size;i++){
            float num = nums.get(i);
            float v = (num-mean)*(num-mean);
            sumV+=v;
        }
        variance = sumV/size;
    }

    public float getMean() {
        return mean;
    }

    public float getRange() {
        return range;
    }

    public float getVariance() {
        return variance;
    }
}
