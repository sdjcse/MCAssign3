package com.example.vamsikrishnag.mcassign3.svmwrapperlibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdj on 4/3/17.
 *
 * Bean Wrapper for SVM data feeding
 */

public class DataBean {
    private List<Double> accleromterData = null;
    private ActivityType actType;

    public List<Double> getAccleromterData() {
        return accleromterData;
    }

    public ActivityType getActType() {
        return actType;
    }

    DataBean(){
        accleromterData = new ArrayList<>();
    }
    DataBean(ActivityType actType,List<Double> data){
        this.accleromterData = data;
        this.actType = actType;
    }

}
