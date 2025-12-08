package ro.ase.ie.g1087_s04.networking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadTask implements Runnable {

    Context context;
    String url;
    ImageView poster;

    public DownloadTask(Context context, String url, ImageView poster) {
        this.context = context;
        this.url = url;
        this.poster = poster;
    }



    @Override
    public void run() {
        try {
            URL url = new URL(this.url);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.connect();
            InputStream inputStream = httpConn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//            poster.setImageBitmap(bitmap);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    poster.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
