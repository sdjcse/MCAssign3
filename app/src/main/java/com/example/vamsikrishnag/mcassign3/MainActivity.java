package com.example.vamsikrishnag.mcassign3;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;
import android.view.View;
import android.database.Cursor;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase dbCon;
    private SensorManager AcclManager;// = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    private Sensor Accelerometer;// = AcclManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    String tableName;
    boolean flag=true;

    private int activity_label; //0- walking, 1- running,  2 - eating
    private int columnSize=0;
    private String rowToBeInserted="";

    ProgressDialog progress;
    private String dbPath= "Assignment3_test2.db";
    String activityToBeRecorded;
    long previousTime=0;
    private SensorEventListener acclListener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent acclEvent) {
            Sensor AcclSensor = acclEvent.sensor;
            if (AcclSensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = acclEvent.values[0];
                float y = acclEvent.values[1];
                float z = acclEvent.values[2];
                long currentTime = System.currentTimeMillis();
//                String msg=Long.toString(currentTime)+","+Float.toString(x)+","+Float.toString(y)+","+Float.toString(z);
                String msg=Float.toString(x)+","+Float.toString(y)+","+Float.toString(z);
                if ((currentTime-previousTime)>=90)
                {
                    Log.d("Current Time:",Integer.toString(columnSize)+","+Long.toString(currentTime-previousTime));
                    if (columnSize<50)
                    {
                        rowToBeInserted=rowToBeInserted+","+msg;
                        previousTime=currentTime;
                        columnSize++;
                    }
                    if (columnSize==50)
                    {
                        rowToBeInserted=Long.toString(currentTime)+rowToBeInserted+","+Integer.toString(activity_label);
                        try {
                            dbCon.execSQL("INSERT INTO " + tableName + " VALUES (" + rowToBeInserted + ");");
                            progress.dismiss();
                            Toast.makeText(getApplicationContext()," Row Inserted ",Toast.LENGTH_LONG).show();
                            Log.d(" Row insert successful:", rowToBeInserted);
                        }
                        catch (Exception e)
                        {
                            Log.d(e.getMessage()," at - Insert part "+rowToBeInserted);
                        }
                        rowToBeInserted="";
                        columnSize=0;
                        AcclManager.unregisterListener(acclListener);
                        dbCon.close();
    //                    finish();
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.d("Sensor Accuracy changed","Sensor Accuracy Changed");
        }
    };

    // Register Sensor to start recording data
    private void registerAcclListener(String activity)
    {
        progress= ProgressDialog.show(this, "", "Recording Accelerometer data. Continue "+activity+"...", true);
        AcclManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Accelerometer = AcclManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        AcclManager.registerListener(acclListener,Accelerometer,100000/*0.1 secondsSensorManager.SENSOR_DELAY_NORMAL*/);
        Log.d("Registered Listener","Registered Listener");
    }

    private void createTable(String t_name,SQLiteDatabase connection)
    {
        Log.d(t_name,t_name);
        String createTableName="CREATE TABLE " + t_name + " (ID REAL";
        for (int i=0;i<50;i++)
        {
            String x_attr="Accel_X_"+Integer.toString(i+1)+" REAL";
            String y_attr="Accel_Y_"+Integer.toString(i+1)+" REAL";
            String z_attr="Accel_Z_"+Integer.toString(i+1)+" REAL";
            createTableName=createTableName+", "+x_attr+", "+y_attr+", "+z_attr;
        }
        createTableName=createTableName+", Activity_Label INTEGER);"; //0- walking, 1- running,  2 - eating
        try {
//            connection.execSQL("CREATE TABLE " + t_name + " (Time_Stamp REAL, X_Value REAL, Y_Value REAL, Z_Value REAL);");
            connection.execSQL(createTableName);
            Log.d("Table Created ", t_name);
        }
        catch (Exception e)
        {
            Log.d("Table Already exists: ",t_name);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button insertRecord= (Button) findViewById(R.id.record);
        insertRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                dbCon=openOrCreateDatabase(dbPath,MODE_PRIVATE,null);
                RadioButton run= (RadioButton) findViewById(R.id.run);
                if (run.isChecked()) {
                    activityToBeRecorded = "Running";
                    activity_label= 1;
                }
                RadioButton walk= (RadioButton) findViewById(R.id.walk);
                if (walk.isChecked()) {
                    activityToBeRecorded = "Walking";
                    activity_label= 0;
                }
                RadioButton eat= (RadioButton) findViewById(R.id.Eat);
                if(eat.isChecked()) {
                    activityToBeRecorded = "Eating";
                    activity_label= 2;
                }
                tableName= "Training";
                createTable(tableName, dbCon);
                registerAcclListener(activityToBeRecorded);
            }
        });

        Button trainActivity= (Button) findViewById(R.id.train);
        trainActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                dbCon= openOrCreateDatabase(dbPath,MODE_PRIVATE,null);
//                String selectQuery="SELECT * FROM " + tableName + " ORDER BY ID DESC LIMIT 1;";
                String selectQuery= "SELECT * FROM " + tableName+";";
                Cursor sel = dbCon.rawQuery(selectQuery, null);
                sel.moveToFirst();
                int c=0;
                do{
                    int noOfColumns= sel.getColumnCount();
                    String row="";
                    row= row+ Integer.toString(sel.getInt(0));
                    for(int i=1;i<noOfColumns-1;i++)
                    {
                        row= row+","+Float.toString(sel.getFloat(i));
                    }
                    row= row +","+ Integer.toString(sel.getInt(noOfColumns-1));
                    Log.d("Row No. "+Integer.toString(c), row);
                    c++;
                }while (sel.moveToNext());
                dbCon.close();
          }
        });

        Button testActivity= (Button) findViewById(R.id.test);
        testActivity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                activityToBeRecorded= "performing the activity";
////                tableName= "Test";
//                dbCon= openOrCreateDatabase(dbPath,MODE_PRIVATE,null);
//                activity_label = -1;
//                createTable(tableName, dbCon);
//                registerAcclListener(activityToBeRecorded);
//                predictActivity(); Open DB; Query Test table; Use SVM, predict and display the class label
            }
        });
    }
}
