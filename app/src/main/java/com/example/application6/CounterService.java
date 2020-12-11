package com.example.application6;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import static java.lang.Thread.sleep;

public class CounterService extends Service {
    RandomTask task;

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        task = new RandomTask();
        startWork();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        task.stop();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    void startWork() {
        Thread thread = new Thread(task, "T1");
        thread.start();
    }

    private class RandomTask implements Runnable {

        private boolean exit;

        RandomTask() {
        }

        @Override
        public void run() {
            send(new Intent(ServiceReceiver.COUNTER_ACTION), ServiceReceiver.COUNTER_START);

            exit = false;

            final int VERY_MUCH = 100000;
            for (int i = 0; i < VERY_MUCH && !exit; i++) {

                int randomVal = (int) (Math.random() * i);

                send(new Intent(ServiceReceiver.COUNTER_ACTION).putExtra(ServiceReceiver.COUNTER_ANSWER_KEY, randomVal),
                        ServiceReceiver.COUNTER_ANSWER);

                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }

            send(new Intent(ServiceReceiver.COUNTER_ACTION), ServiceReceiver.COUNTER_FINISH);
            stop();
        }

        void stop() {
            exit = true;
        }

        void send(Intent intent, int code) {
            intent.putExtra(ServiceReceiver.REQUEST_CODE_KEY, code);
            sendBroadcast(intent);
        }
    }
}
