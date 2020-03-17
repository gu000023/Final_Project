package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//Author: Lilia Ramalho Martins
//Student # 040952491
public class TheGuardianActivity extends AppCompatActivity {

    private URL url;
    private HttpURLConnection urlConnection;
    private InputStream response;
    private ListView listView;
    private ProgressBar progressBar;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_guardian);

        editText = (EditText)findViewById(R.id.editText_term);
        button = (Button)findViewById(R.id.search_term_button);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        listView = (ListView)findViewById(R.id.list_results);

        class TheGuardianQuery extends AsyncTask<String, Integer, String> {

            private List<TheGuardianArticle> articles = new ArrayList<>();

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
                        articles.add(new TheGuardianArticle(publicationDate, title, webUrl, i));
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

                class ArticlesListAdapter extends BaseAdapter {

                    @Override
                    public int getCount() {
                        return articles.size();
                    }

                    @Override
                    public Object getItem(int position) {
                        return articles.get(position);
                    }

                    @Override
                    public long getItemId(int position) {
                        return articles.get(position).getId();
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        LayoutInflater inflater = getLayoutInflater();
                        View newView;
                        TheGuardianArticle itemArticle = (TheGuardianArticle) getItem(position);
                        //newView = inflater.inflate(R.layout.row_layout, parent, false );
                        newView = inflater.inflate(R.layout.)
                        TextView articleTitleView = newView.findViewById(R.id.articleTitle);
                        articleTitleView.setText(itemArticle.getTitle());
                        TextView articleDateView = newView.findViewById(R.id.articleDate);
                        articleTitleView.setText(itemArticle.getDate());
                        return newView;
                    }

                }

                progressBar.setVisibility(View.INVISIBLE);
            }

        }

         button.setOnClickListener((click) -> {
             String term = String.valueOf(editText.getText());
             term.trim();
             TheGuardianQuery req = new TheGuardianQuery();
             req.execute(term);
         });




    }
}
