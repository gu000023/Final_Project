package com.example.finalproject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
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

class TheGuardianQuery extends AsyncTask<String, Integer, String> {

    private List<TheGuardianArticle> articles = new ArrayList<>();
    private URL url;
    private HttpURLConnection urlConnection;
    private InputStream response;
    private ProgressBar progressBar;
    private Context context;
    private ListView listView;

    public TheGuardianQuery (Context context, ListView listView, ProgressBar progressBar) {
        super();
        this.context = context;
        this.listView = listView;
        this.progressBar = progressBar;
    }

    public List<TheGuardianArticle> getArticles(){
        return articles;
    }

    @Override
    protected String doInBackground(String... args) {

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
                getArticles().add(new TheGuardianArticle(publicationDate, title, webUrl, i));
                TimeUnit.MILLISECONDS.sleep(2);
                publishProgress(i*100/results.length());
            }
            publishProgress(100);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }

        return "Done";
    }

    protected void onProgressUpdate(Integer ... args)
    {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(args[0]);
    }

    protected void onPostExecute(String fromDoInBackground)
    {
        Log.i("HTTP", fromDoInBackground);
        ArticlesListAdapter myArticleListAdapter = new ArticlesListAdapter(context);
        for (TheGuardianArticle element : getArticles()) {
            myArticleListAdapter.getElements().add(element);
        }
        listView.setAdapter(myArticleListAdapter);
        progressBar.setVisibility(View.INVISIBLE);
    }

}