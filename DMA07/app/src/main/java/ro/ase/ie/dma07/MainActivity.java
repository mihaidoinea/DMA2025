package ro.ase.ie.dma07;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private String movieUrl = "https://www.joblo.com/wp-content/uploads/2020/09/enola-review-face.jpg";
    private static final String RECIPE_GET_JSON = "https://jsonkeeper.com/b/OCIE";
    private static final String RECIPE_POST_JSON = "https://crudcrud.com/api/12ed287260404cc7b25add34a858b357/dma2025";


    private Button btnAsync;
    private Button btnCallable;
    private Button btnThread;
    private Button btnRunnable;
    private Button btnGetJson;

    private ImageView ivAsync;
    private ImageView ivCallable;
    private ImageView ivRunnable;
    private ImageView ivThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        trustEveryone();

        ivAsync = findViewById(R.id.ivAsync);
        ivCallable = findViewById(R.id.ivCallable);
        ivRunnable = findViewById(R.id.ivRunnable);
        ivThread = findViewById(R.id.ivThread);

        btnAsync = findViewById(R.id.btnAsync);
        btnCallable = findViewById(R.id.btnCallable);
        btnRunnable = findViewById(R.id.btnRunnable);
        btnThread = findViewById(R.id.btnThreads);
        btnGetJson = findViewById(R.id.btGetRecipe);

        btnGetJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpConnectionService httpConnectionService = new HttpConnectionService(RECIPE_GET_JSON);
                        String jsonObject = httpConnectionService.getData();
                        List<Recipe> recipes = RecipeJsonParser.fromJson(jsonObject);

                        httpConnectionService = new HttpConnectionService(RECIPE_POST_JSON);
                        for(Recipe recipe:recipes)
                        {
                            Log.d("MainActivity", recipe.toString());
                            try {
                                JSONObject jObject = RecipeJsonParser.writeRecipe(recipe);
                                httpConnectionService.postData(jObject.toString());
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }


                    }
                });
                thread.start();
            }
        });


        btnAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService executorService = Executors.newFixedThreadPool(1);
                DownloadAsyncTask dat = new DownloadAsyncTask(ivAsync);
//                dat.execute(movieUrl);
                dat.executeOnExecutor(executorService, movieUrl);
            }
        });

        btnCallable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService executorService = Executors.newFixedThreadPool(3);
                Future<Bitmap> future = executorService.submit(new DownloadCallableTask(movieUrl));
                try {
                    ivCallable.setImageBitmap(future.get());
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        btnRunnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadRunnableTask drt = new DownloadRunnableTask(movieUrl, ivRunnable);
                Thread thread = new Thread(drt);
                thread.start();
            }
        });
    }



    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }});
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }}}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }
}