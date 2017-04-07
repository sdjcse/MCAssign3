package com.example.vamsikrishnag.mcassign3;

/**
 * Created by sdj on 4/3/17.
 */

import com.example.vamsikrishnag.mcassign3.csvutil.CSVUtil;
import com.example.vamsikrishnag.mcassign3.svmwrapperlibrary.ActivityType;
import com.example.vamsikrishnag.mcassign3.svmwrapperlibrary.DataBean;
import com.example.vamsikrishnag.mcassign3.svmwrapperlibrary.SvmWrapper;

import java.util.ArrayList;
import java.util.List;

import libsvm.svm_model;

public class SVMService {
    svm_model modelToTrain = null;
    SvmWrapper wrapperStub = null;

    public SVMService(){
        wrapperStub = new SvmWrapper();
    }

    public void train(String fileName){
        try {
            List<DataBean> trainingSet = CSVUtil.readFromFile(Constants.inputFileName);
            modelToTrain = wrapperStub.trainer(trainingSet);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public ActivityType test(String fileName,ActivityType actType){
        List<DataBean> testSet;
        ActivityType returnType = null;
        double predictedArr[];
        try {
            testSet = CSVUtil.readSpecColumn(fileName,actType);
            predictedArr = wrapperStub.predictFromSetOfInputs(testSet,modelToTrain);
            returnType = returnMajorityClass(predictedArr);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return returnType;
    }

    public ActivityType returnMajorityClass(double [] predictedArr){
        ActivityType retType = ActivityType.EATING;
        int eatingCount = 0,walkingCount =0,runningCount = 0;
        for (int i = 0; i < predictedArr.length; i++) {
            switch ((int) predictedArr[i]){
                case 0:
                    walkingCount++;
                    break;
                case 1:
                    runningCount++;
                    break;
                case 2:
                    eatingCount++;
            }
        }
        if(walkingCount > runningCount && walkingCount > eatingCount){
            return ActivityType.WALKING;
        }else if(runningCount > walkingCount && runningCount > eatingCount){
            return ActivityType.RUNNING;
        }
        return retType;
    }

    public List<DataBean> packer(String fileName)throws Exception{
        List<DataBean> returnObject = new ArrayList<>();
        // Yet to code assume that the input will have one databean in each line with the label in the end
        return returnObject;
    }
}
