package ro.ase.ie.dma07;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView ivAsync;

    public DownloadAsyncTask(ImageView imageView)
    {
        ivAsync = imageView;
    }
    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap = null;

        try {
            URL url = new URL(strings[0]);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        ivAsync.setImageBitmap(bitmap);
    }
}
