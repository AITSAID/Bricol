package daisousoft.app.com.bricol;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import daisousoft.app.com.bricol.DAO.myDBHandler;
import daisousoft.app.com.bricol.Models.Account;
import daisousoft.app.com.bricol.Models.Jobs;
import daisousoft.app.com.bricol.Support.TrackMe;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BricolleurActivity extends Activity {

    Button j1,j2,j3,j4,j5,j6,j7,c1,c2,c3;
    EditText namebricp,phonebrico;
    private TrackMe gps;
    private double latitude,longitude;
    String IdDevice,PhoneNumber;
    ProgressDialog progress;

    myDBHandler mydb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bricolleur);
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontal_scroll_view);
        OverScrollDecoratorHelper.setUpOverScroll(horizontalScrollView);

        j1 = (Button)findViewById(R.id.job1);
        j2 = (Button)findViewById(R.id.job2);
        j3 = (Button)findViewById(R.id.job3);
        j4 = (Button)findViewById(R.id.job4);
        j5 = (Button)findViewById(R.id.job5);
        j6 = (Button)findViewById(R.id.job6);
        j7 = (Button)findViewById(R.id.job7);
        namebricp = (EditText) findViewById(R.id.namebrico) ;
        phonebrico = (EditText) findViewById(R.id.phonebrico);

        c1 = (Button) findViewById(R.id.choice1);
        c1.setTag(c1.getId());
        c2 = (Button) findViewById(R.id.choice2);
        c2.setTag(c2.getId());
        c3 = (Button) findViewById(R.id.choice3);
        c3.setTag(c3.getId());

        //
        TelephonyManager tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);

        // Initiate IdDevice and PhoneNumber
        IdDevice = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        GetAccount(IdDevice);
        GetAllJobs(IdDevice);
        //PhoneNumber = tMgr.getSimSerialNumber();

        //phonebrico.setText(PhoneNumber);
        //DB HANDLER
        //mydb = new myDBHandler(getApplicationContext());
        //Account myAccount = mydb.getAccountByID(IdDevice);
        //namebricp.setText(myAccount.get_name());
        //phonebrico.setText(myAccount.get_phonenumber());

    }

    public void ChooseJob(View view) {
        //test DATABASE
        //mydb.addAccount(new Account("hdjahdadz5454", "MAROUANE", "0672145149", 33.121, 7.1245, 1));
        //phonebrico.setText(mydb.getAllAccounts().size()+"");
        int choose = checkEmptyChoice();
        if(choose == 1 ){
            c1.setBackground(view.getBackground());
            c1.setTag(view.getId());
            view.setVisibility(View.GONE);

        }else{
            if(choose == 2){
                c2.setBackground(view.getBackground());
                c2.setTag(view.getId());
                view.setVisibility(View.GONE);

            }else{
                if(choose == 3){
                    c3.setBackground(view.getBackground());
                    c3.setTag(view.getId());
                    view.setVisibility(View.GONE);

                }else{
                    Toast.makeText(this, "You can choose only 3 Jobs", Toast.LENGTH_LONG).show();

                }
            }
        }

        if(choose!=0){
            Jobs job =new Jobs(IdDevice,view.getId());
            Gson gson = new Gson();
            String json = gson.toJson(job);
            ExecuteRequest("ajouterjobs?job",json);

        }

    }

    public void EnableJobs(int idjob) {
        //test DATABASE
        //mydb.addAccount(new Account("hdjahdadz5454", "MAROUANE", "0672145149", 33.121, 7.1245, 1));
        //phonebrico.setText(mydb.getAllAccounts().size()+"");
        int choose = checkEmptyChoice();

        if(j1.getId()==idjob){
            if(choose == 1 ){
                c1.setBackground(j1.getBackground());
                c1.setTag(j1.getId());
                j1.setVisibility(View.GONE);

            }else{
                if(choose == 2){
                    c2.setBackground(j1.getBackground());
                    c2.setTag(j1.getId());
                    j1.setVisibility(View.GONE);

                }else{
                    if(choose == 3){
                        c3.setBackground(j1.getBackground());
                        c3.setTag(j1.getId());
                        j1.setVisibility(View.GONE);

                    }else{
                        Toast.makeText(this, "You can choose only 3 Jobs", Toast.LENGTH_LONG).show();

                    }
                }
            }
        }
        if(j2.getId()==idjob){
            if(choose == 1 ){
                c1.setBackground(j2.getBackground());
                c1.setTag(j2.getId());
                j2.setVisibility(View.GONE);

            }else{
                if(choose == 2){
                    c2.setBackground(j2.getBackground());
                    c2.setTag(j2.getId());
                    j2.setVisibility(View.GONE);

                }else{
                    if(choose == 3){
                        c3.setBackground(j2.getBackground());
                        c3.setTag(j2.getId());
                        j2.setVisibility(View.GONE);

                    }else{
                        Toast.makeText(this, "You can choose only 3 Jobs", Toast.LENGTH_LONG).show();

                    }
                }
            }

        }
        if(j3.getId()==idjob){
            if(choose == 1 ){
                c1.setBackground(j3.getBackground());
                c1.setTag(j3.getId());
                j3.setVisibility(View.GONE);

            }else{
                if(choose == 2){
                    c2.setBackground(j3.getBackground());
                    c2.setTag(j3.getId());
                    j3.setVisibility(View.GONE);

                }else{
                    if(choose == 3){
                        c3.setBackground(j3.getBackground());
                        c3.setTag(j3.getId());
                        j3.setVisibility(View.GONE);

                    }else{
                        Toast.makeText(this, "You can choose only 3 Jobs", Toast.LENGTH_LONG).show();

                    }
                }
            }

        }
        if(j4.getId()==idjob){
            if(choose == 1 ){
                c1.setBackground(j4.getBackground());
                c1.setTag(j4.getId());
                j4.setVisibility(View.GONE);

            }else{
                if(choose == 2){
                    c2.setBackground(j4.getBackground());
                    c2.setTag(j4.getId());
                    j4.setVisibility(View.GONE);

                }else{
                    if(choose == 3){
                        c3.setBackground(j4.getBackground());
                        c3.setTag(j4.getId());
                        j4.setVisibility(View.GONE);

                    }else{
                        Toast.makeText(this, "You can choose only 3 Jobs", Toast.LENGTH_LONG).show();

                    }
                }
            }
        }
        if(j5.getId()==idjob){
            if(choose == 1 ){
                c1.setBackground(j5.getBackground());
                c1.setTag(j5.getId());
                j5.setVisibility(View.GONE);

            }else{
                if(choose == 2){
                    c2.setBackground(j5.getBackground());
                    c2.setTag(j5.getId());
                    j5.setVisibility(View.GONE);

                }else{
                    if(choose == 3){
                        c3.setBackground(j5.getBackground());
                        c3.setTag(j5.getId());
                        j5.setVisibility(View.GONE);

                    }else{
                        Toast.makeText(this, "You can choose only 3 Jobs", Toast.LENGTH_LONG).show();

                    }
                }
            }
        }
        if(j6.getId()==idjob){
            if(choose == 1 ){
                c1.setBackground(j6.getBackground());
                c1.setTag(j6.getId());
                j6.setVisibility(View.GONE);

            }else{
                if(choose == 2){
                    c2.setBackground(j6.getBackground());
                    c2.setTag(j6.getId());
                    j6.setVisibility(View.GONE);

                }else{
                    if(choose == 3){
                        c3.setBackground(j6.getBackground());
                        c3.setTag(j6.getId());
                        j6.setVisibility(View.GONE);

                    }else{
                        Toast.makeText(this, "You can choose only 3 Jobs", Toast.LENGTH_LONG).show();

                    }
                }
            }

        }
        if(j7.getId()==idjob){

            if(choose == 1 ){
                c1.setBackground(j7.getBackground());
                c1.setTag(j7.getId());
                j1.setVisibility(View.GONE);

            }else{
                if(choose == 2){
                    c2.setBackground(j7.getBackground());
                    c2.setTag(j1.getId());
                    j7.setVisibility(View.GONE);

                }else{
                    if(choose == 3){
                        c3.setBackground(j7.getBackground());
                        c3.setTag(j7.getId());
                        j7.setVisibility(View.GONE);

                    }else{
                        Toast.makeText(this, "You can choose only 3 Jobs", Toast.LENGTH_LONG).show();

                    }
                }
            }
        }



    }

    public int checkEmptyChoice() {
        if (c1.getTag().equals(c1.getId())) {
            return 1;
        } else {
            if (c2.getTag().equals(c2.getId())) {
                return 2;
            } else {
                if (c3.getTag().equals(c3.getId())) {
                    return 3;
                }
            }
        }

        return 0;
    }

    public void UnCheckJob(View view){
        int id = 0;

        if(view.getTag().equals(j1.getId())){
            j1.setVisibility(View.VISIBLE);
            j1.setBackgroundResource(R.drawable.job1);
            view.setTag(view.getId());
            view.setBackgroundResource(R.drawable.anonymejob);
            id =j1.getId();
        }
        if(view.getTag().equals(j2.getId())){
            j2.setVisibility(View.VISIBLE);
            view.setTag(view.getId());
            view.setBackgroundResource(R.drawable.anonymejob);
            id =j2.getId();
        }
        if(view.getTag().equals(j3.getId())){
            j3.setVisibility(View.VISIBLE);
            view.setTag(view.getId());
            view.setBackgroundResource(R.drawable.anonymejob);
            id =j3.getId();
        }
        if(view.getTag().equals(j4.getId())){
            j4.setVisibility(View.VISIBLE);
            view.setTag(view.getId());
            view.setBackgroundResource(R.drawable.anonymejob);
            id =j4.getId();
        }
        if(view.getTag().equals(j5.getId())){
            j5.setVisibility(View.VISIBLE);
            view.setTag(view.getId());
            view.setBackgroundResource(R.drawable.anonymejob);
            id =j5.getId();
        }
        if(view.getTag().equals(j6.getId())){
            j6.setVisibility(View.VISIBLE);
            view.setTag(view.getId());
            view.setBackgroundResource(R.drawable.anonymejob);
            id =j6.getId();
        }
        if(view.getTag().equals(j7.getId())){
            j7.setVisibility(View.VISIBLE);
            view.setTag(view.getId());
            view.setBackgroundResource(R.drawable.anonymejob);
            id =j7.getId();
        }
        Jobs job = new Jobs(IdDevice,id);
        Gson gson = new Gson();
        String json = gson.toJson(job);
        ExecuteRequest("deletejobs?job",json);

    }

    public void SaveAccount(View view){
        gps = new TrackMe(BricolleurActivity.this);
        if(gps.canGetLocation()){
            latitude = gps .getLatitude();
            longitude = gps.getLongitude();
        }
        else
        {
            gps.showSettingsAlert();
        }
        Account account = new Account(IdDevice,namebricp.getText().toString(),phonebrico.getText().toString(),latitude,longitude,1) ;
        //mydb.addAccount(account);
        Gson gson = new Gson();
        String json = gson.toJson(account);
        Toast.makeText(getApplicationContext(),"Working on it "+json,Toast.LENGTH_LONG).show();
        addAccount(json);

    }

    public void addAccount(final String jsonValue){

        ExecuteRequest("register?accountUpdate",jsonValue);
    }

    public void ExecuteRequest(String method, String value){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://bricolapp-daisousoft.rhcloud.com/"+method+"="+value)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        // Error

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // For the example, you can show an error dialog or a toast
                                // on the main UI thread
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();

                        // Do something with the response
                    }
                });
    }

    public void GetAccount(String ID){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://bricolapp-daisousoft.rhcloud.com/getbricobyid?idAccount="+ID)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        // Error

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // For the example, you can show an error dialog or a toast
                                // on the main UI thread
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        final String res = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String jsonData = res;
                                Gson gson = new Gson();
                                Type listType = new TypeToken<Account>(){}.getType();
                                Account account = (Account) gson.fromJson(jsonData, listType);
                                phonebrico.setText(account.get_phonenumber());
                                namebricp.setText(account.get_name());
                            }
                        });

                    }
                });
    }

    public void GetAllJobs(String ID){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://bricolapp-daisousoft.rhcloud.com/getbricojobs?idAccount="+ID)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        // Error

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // For the example, you can show an error dialog or a toast
                                // on the main UI thread
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        final String res = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String jsonData = res;
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<Jobs>>(){}.getType();
                                List<Jobs> listaccounts = (List<Jobs>) gson.fromJson(jsonData, listType);
                                for(Jobs jb :listaccounts) {
                                    EnableJobs(jb.getIdjob());
                                }
                            }
                        });

                    }
                });

    }
}
