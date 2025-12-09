package ro.ase.ie.g1107_s04.networking;

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

public class DownloadPoster implements Runnable{

    private String posterUrl;
    private ImageView poster;

    public DownloadPoster(ImageView poster, String url) {
        this.poster = poster;
        this.posterUrl = url;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(posterUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    poster.setImageBitmap(bitmap);
                }
            });

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
