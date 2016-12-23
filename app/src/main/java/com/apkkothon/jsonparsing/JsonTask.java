package com.apkkothon.jsonparsing;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joy on 12/22/2016.
 */

public class JsonTask extends AsyncTask<Void,Void,Void> {
    private StringBuffer buffer;
    private HttpURLConnection connection;
    private BufferedReader reader;
    private List<PostModel>postModelList;
    private Context context;
   // ProgressDialog pd;
   private RecyclerView recyclerView;


    public JsonTask(RecyclerView recyclerView,Context context) {
        this.recyclerView = recyclerView;
        this.context = context;
       // pd=new ProgressDialog(context);
       // pd.setMessage("looooo");

    }

    @Override
    protected void onPreExecute() {
        //pd.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... strings) {
        try {

            postModelList=new ArrayList<>();


            URL url = new URL("http://apkkothon.net/jg/get_posts");
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson=buffer.toString();


            // JSON Start.....
            JSONObject parentObject= new JSONObject(finalJson);
            JSONArray parentArray =parentObject.getJSONArray("posts");


            //StringBuffer finalBuffer=new StringBuffer();


            for(int i=0;i<parentArray.length();i++) {
                PostModel postModel=new PostModel();
                JSONObject finalObject = parentArray.getJSONObject(i);
                postModel.setPost(finalObject.getString("title"));
                postModel.setExcerpt(finalObject.getString("date"));
                postModel.setImage(finalObject.getString("thumbnail"));

                postModelList.add(postModel);
                //System.out.println("Title"+postModel.image);
            }


            //String finalResult =post+"\n"+image;
            //finalResult=finalResult.replaceAll("(\n)", "<br/>");



        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    @Override
    protected void onPostExecute(Void s) {
        //pd.dismiss();
        MyAdapter adapter=new MyAdapter(context,postModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        super.onPostExecute(s);
    }
}

