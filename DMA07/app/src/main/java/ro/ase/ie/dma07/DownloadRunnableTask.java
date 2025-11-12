package ro.ase.ie.dma07;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadRunnableTask implements Runnable{

    ImageView imageView;
    String movieUrl;
    public DownloadRunnableTask(String movieUrl, ImageView ivRunnable) {
        this.movieUrl = movieUrl;
        this.imageView = ivRunnable;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(movieUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//            imageView.setImageBitmap(bitmap); //--not possible

            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });


            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
