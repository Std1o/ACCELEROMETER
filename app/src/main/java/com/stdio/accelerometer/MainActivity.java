package com.stdio.accelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView textAccel;//создание текстового поля для вывода данных
    SensorManager sensorManager;//SensorManager позволяет получить доступ к датчикам устройства
    Sensor sensorAccel;//Создаем сенсор с названием sensorAccel (в этой переменной мы будем хранить параметры нашего акселерометра)
    Timer timer;//создаем таймер

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textAccel = (TextView) findViewById(R.id.textAccel);//находим текстовое поле для записи текста в разметке
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAccel =
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//назначаем созданному сенсору тип Акселерометр
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(listener, sensorAccel,
                SensorManager.SENSOR_DELAY_NORMAL);//создаем слушатель для нашего сенсора
        timer = new Timer();
        TimerTask task = new TimerTask() {//запускаем таймер, который каждые 500 мсек будет отображать значения с акселерометра
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textAccel.setText("Accelerometer: " + valuesAccel[0] + "; " +
                                +valuesAccel[1] + "; " + valuesAccel[2]);//вывод данных в текстовое поле tvText
                    }
                });
            }
        };
        timer.schedule(task, 0, 500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listener);
        timer.cancel();
    }

    float[] valuesAccel = new float[3];
    SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            for (int i = 0; i < 3; i++) {
                valuesAccel[i] = event.values[i];//получаем значения с акселерометра и записываем их в массив
            }
        }
    };
}