package ro.ase.ie.dma07;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpConnectionService {

    private static final String TAG = HttpConnectionService.class.getName();
    private String recipeJsonUrl;

    public HttpConnectionService(String recipeJson) {

        this.recipeJsonUrl = recipeJson;
    }

    public String getData() {
        StringBuilder jsonFile = new StringBuilder();
        HttpURLConnection connection = null;
        try {
            URL url = new URL(recipeJsonUrl);
            connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonFile.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        Log.d(TAG, "Stop:"+jsonFile.toString());
        return jsonFile.toString();
    }

    public String postData(String jsonArray) {
        HttpURLConnection connection = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(recipeJsonUrl);

            HttpURLConnection.setFollowRedirects(false);

            connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            byte[] out = jsonArray.getBytes(StandardCharsets.UTF_8);
            connection.setFixedLengthStreamingMode(out.length);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(out);
            }

            int code = connection.getResponseCode();
            InputStream inputStream = (code >= 200 && code < 300)
                    ? connection.getInputStream()
                    : connection.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            Log.d("HTTP", "Response code: " + code);
            Log.d("HTTP", "Response: " + response.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            connection.disconnect();
        }
        return result.toString();
    }
}

