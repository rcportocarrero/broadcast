package com.mov.tracker;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class TrackerService extends Service {

    private final static int INTERVAL = 1000 * 60 * 1;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        try {
            new DoBackgroundTask().execute(new URL(
                    "http://www.google.com/imagen1"), new URL(
                    "http://www.google.com/imagen2"), new URL(
                    "http://www.google.com/imagen3"), new URL(
                    "http://www.google.com/imagen4"));
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

        return START_STICKY;
    }

    private int DownloadFile(URL url) {
        try {
            // Simulamos la descarga de un fichero
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        // Retornamos un numero que puede ser el tamanio del archivo
        return 100;
    }

    private class DoBackgroundTask extends AsyncTask<URL, Integer, Long> {
        boolean timeExpired = false;
        
        @Override
        protected Long doInBackground(URL... urls) {
            
            Timer t =new Timer();
            TimerTask tk = new TimerTask() {
                @Override
                public void run() {
                    timeExpired = true;
                }
            };

            t.schedule(tk, 500);

            while(!timeExpired){
                Toast.makeText(getApplicationContext(), "Trabajando...", Toast.LENGTH_SHORT).show();
            }
            
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
            Log.d("Descargando Ficheros", String.valueOf(values[0])
                    + "% descargado");
            Toast.makeText(getBaseContext(), values[0] + "% descargado",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Long result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            Toast.makeText(getBaseContext(),
                    "Descargados " + result + " Kbytes", Toast.LENGTH_SHORT)
                    .show();
            stopSelf();
        }

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Toast.makeText(getBaseContext(), "Servicio Detenido",
                Toast.LENGTH_SHORT).show();
    }
}