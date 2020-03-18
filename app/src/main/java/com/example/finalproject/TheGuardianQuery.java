package com.example.finalproject;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

class TheGuardianQuery extends AsyncTask<String, Integer, List<TheGuardianArticle>> {

    private List<TheGuardianArticle> articles = new ArrayList<>();
    private URL url;
    private HttpURLConnection urlConnection;
    private InputStream response;
    private ProgressBar progressBar;

    public TheGuardianQuery (ProgressBar progressBar) {
        super();
        this.progressBar = progressBar;
    }

    @Override
    protected List<TheGuardianArticle> doInBackground(String... args) {
        List<TheGuardianArticle> backArticles = new ArrayList<>();
        try {

            String searchUrl = "https://content.guardianapis.com/search?api-key=1fb36b70-1588-4259-b703-2570ea1fac6a&q=" + args[0];
            url = new URL(searchUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            response = urlConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            String fullResult = sb.toString();
            JSONObject TGresult = new JSONObject(fullResult);
            JSONObject TGresponse = TGresult.getJSONObject("response");
            JSONArray results = TGresponse.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject anObject = results.getJSONObject(i);
                String publicationDate = anObject.getString("webPublicationDate");
                String title = anObject.getString("webTitle");
                String webUrl = anObject.getString("webUrl");
                backArticles.add(new TheGuardianArticle(publicationDate, title, webUrl, i));
                TimeUnit.MILLISECONDS.sleep(2);
                publishProgress(i*100/results.length());
            }
            publishProgress(100);

        } catch (Exception e) {
            Log.e("Error message: ", e.getMessage());
        }

        return backArticles;
    }

    protected void onProgressUpdate(Integer ... args)
    {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(args[0]);
    }

    protected void onPostExecute(List<TheGuardianArticle> backArticles)
    {
        for (TheGuardianArticle element : backArticles) {
            articles.add(element);
        }
        progressBar.setVisibility(View.INVISIBLE);
    }

}