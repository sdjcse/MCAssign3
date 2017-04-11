package com.example.vamsikrishnag.mcassign3;

/**
 * Created by sdj on 4/3/17.
 */

public class Constants {
    public static int INPUT_DIMENSION_SIZE = 150;
    public static String TRAINING_TABLE = "Training";
    public static String TEST_TABLE = "Test";
    public static String TRAIN_FIRST_STRING = "Please train before testing!";
    public static String INSUFFICIENT_DATA = "Data Insufficient to train!";
    public static String ACTIVITY_PERFORMED = "Activity performed is ";
    public static String TRAINING_COMPLETED = "Training Completed Successfully";
    public static String SQL_TRAINING_SELECT = "SELECT * FROM " + Constants.TRAINING_TABLE;
    public static String SQL_TRUNCATE_TEST_TABLE = "TRUNCATE " + Constants.TEST_TABLE;
    public static String SQL_TEST_SELECT = "SELECT * FROM " + Constants.TEST_TABLE + " LIMIT 10";
    public static int INPUT_ROWS_TRAINING = 60;
    public static int TEST_ROWS_LIMIT = 5;
    public static String EXCEPTION_SVM_SERVICE = "Exception occured in SVM Service class! Please take a look at logs";
    public static String dbName="Assignment3_Group10.db";
    public static String FILE_PATH_PHONE = "/data/data/com.example.vamsikrishnag.mcassign3/";
    public static String inputFileName = "/data.csv";
    public static String testFileName = "/test.csv";
    public static int NR_FOLD_CROSS_VALID = 4;
    public static String TRAIN_BEFORE_ABOUT = "Please train before checking accuracy in About Page";
}

