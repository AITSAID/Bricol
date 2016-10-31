package daisousoft.app.com.bricol.DAO;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import daisousoft.app.com.bricol.Models.Account;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HABABONGA on 31/10/2016.
 */
public class Synchronize extends AsyncTask<Void, Void, String> {
    private static final String TAG = "PostFetcher";

    private Context context;

    public Synchronize(Context context){
        this.context=context;
    }


    @Override
    protected String doInBackground(Void... params) {
        myDBHandler mydb = new myDBHandler(context);
        mydb.deleteAllAccounts();

        try {
            //Create an HTTP client
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://bricolapp-daisousoft.rhcloud.com/getallbrico")
                    .build();
            Response responses = client.newCall(request).execute();
            String jsonData = responses.body().string();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Account>>(){}.getType();
            List<Account> listaccounts = (List<Account>) gson.fromJson(jsonData, listType);
            for(Account li :listaccounts) {
                mydb.addAccount(li);
            }

        } catch(Exception ex) {
            Log.e(TAG, "Failed to send HTTP POST request due to: " + ex);
        }
        return null;
    }
}