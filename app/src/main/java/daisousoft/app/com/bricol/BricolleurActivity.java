package daisousoft.app.com.bricol;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

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

public class BricolleurActivity extends Activity implements View.OnClickListener {

    Button j1,j2,j3,j4,j5,j6,j7,c1,c2,c3,save,delete;
    EditText namebricp,phonebrico;
    private TrackMe gps;
    private Double latitude,longitude;
    String IdDevice,PhoneNumber;
    //ACProgressFlower progress;
    ArrayList<Jobs> myjobs;
    private ShowcaseView showcaseView;
    private HorizontalScrollView horizontal_scroll_view;
    private int counter = 0;
    myDBHandler mydb ;
    Account myaccount ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bricolleur);
        horizontal_scroll_view = (HorizontalScrollView) findViewById(R.id.horizontal_scroll_view);
        OverScrollDecoratorHelper.setUpOverScroll(horizontal_scroll_view);
        save = (Button) findViewById(R.id.save);
        delete = (Button) findViewById(R.id.delete);
        j1 = (Button)findViewById(R.id.job1);
        j1.setTag(111);
        j2 = (Button)findViewById(R.id.job2);
        j2.setTag(222);
        j3 = (Button)findViewById(R.id.job3);
        j3.setTag(333);
        j4 = (Button)findViewById(R.id.job4);
        j4.setTag(444);
        j5 = (Button)findViewById(R.id.job5);
        j5.setTag(555);
        j6 = (Button)findViewById(R.id.job6);
        j6.setTag(666);
        j7 = (Button)findViewById(R.id.job7);
        j7.setTag(777);

        namebricp = (EditText) findViewById(R.id.namebrico) ;
        phonebrico = (EditText) findViewById(R.id.phonebrico);

        c1 = (Button) findViewById(R.id.choice1);
        c1.setTag(c1.getId());
        c2 = (Button) findViewById(R.id.choice2);
        c2.setTag(c2.getId());
        c3 = (Button) findViewById(R.id.choice3);
        c3.setTag(c3.getId());

        //
        //TelephonyManager tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);

        // Initiate IdDevice and PhoneNumber
        IdDevice = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        /*progress = new ACProgressFlower.Builder(BricolleurActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.BLUE).build();
        progress.show();*/
        mydb = new myDBHandler(getApplicationContext());
        myaccount = mydb.getAccountByID(IdDevice);
        phonebrico.setText(myaccount.get_phonenumber());
        namebricp.setText(myaccount.get_name());
        myjobs = mydb.getAllJobs(IdDevice);
        for(Jobs jb:myjobs){
            EnableJobs(jb.getIdjob());
        }
        //GetAccount(IdDevice);
        //GetAllJobs(IdDevice);
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
            c1.setTag(view.getTag());
            view.setVisibility(View.GONE);

        }else{
            if(choose == 2){
                c2.setBackground(view.getBackground());
                c2.setTag(view.getTag());
                view.setVisibility(View.GONE);

            }else{
                if(choose == 3){
                    c3.setBackground(view.getBackground());
                    c3.setTag(view.getTag());
                    view.setVisibility(View.GONE);

                }else{
                    Toast.makeText(this, "You can choose only 3 Jobs", Toast.LENGTH_LONG).show();

                }
            }
        }

        if(choose!=0){
            Jobs job =new Jobs(IdDevice,(int)view.getTag());
            Gson gson = new Gson();
            String json = gson.toJson(job);
            ExecuteRequest("ajouterjobs?job",json);
            mydb.addJob(job);

        }

    }

    public void EnableJobs(int idjob) {
        //test DATABASE
        //mydb.addAccount(new Account("hdjahdadz5454", "MAROUANE", "0672145149", 33.121, 7.1245, 1));
        //phonebrico.setText(mydb.getAllAccounts().size()+"");
        int choose = checkEmptyChoice();

        if((int)j1.getTag()==idjob){
            if(choose == 1 ){
                c1.setBackground(j1.getBackground());
                c1.setTag(j1.getTag());
                j1.setVisibility(View.GONE);

            }else{
                if(choose == 2){
                    c2.setBackground(j1.getBackground());
                    c2.setTag(j1.getTag());
                    j1.setVisibility(View.GONE);

                }else{
                    if(choose == 3){
                        c3.setBackground(j1.getBackground());
                        c3.setTag(j1.getTag());
                        j1.setVisibility(View.GONE);

                    }else{
                        Toast.makeText(this, "You can choose only 3 Jobs", Toast.LENGTH_LONG).show();

                    }
                }
            }
        }
        if((int)j2.getTag()==idjob){
            if(choose == 1 ){
                c1.setBackground(j2.getBackground());
                c1.setTag(j2.getTag());
                j2.setVisibility(View.GONE);

            }else{
                if(choose == 2){
                    c2.setBackground(j2.getBackground());
                    c2.setTag(j2.getTag());
                    j2.setVisibility(View.GONE);

                }else{
                    if(choose == 3){
                        c3.setBackground(j2.getBackground());
                        c3.setTag(j2.getTag());
                        j2.setVisibility(View.GONE);

                    }else{
                        Toast.makeText(this, "You can choose only 3 Jobs", Toast.LENGTH_LONG).show();

                    }
                }
            }

        }
        if((int)j3.getTag()==idjob){
            if(choose == 1 ){
                c1.setBackground(j3.getBackground());
                c1.setTag(j3.getTag());
                j3.setVisibility(View.GONE);

            }else{
                if(choose == 2){
                    c2.setBackground(j3.getBackground());
                    c2.setTag(j3.getTag());
                    j3.setVisibility(View.GONE);

                }else{
                    if(choose == 3){
                        c3.setBackground(j3.getBackground());
                        c3.setTag(j3.getTag());
                        j3.setVisibility(View.GONE);

                    }else{
                        Toast.makeText(this, "You can choose only 3 Jobs", Toast.LENGTH_LONG).show();

                    }
                }
            }

        }
        if((int)j4.getTag()==idjob){
            if(choose == 1 ){
                c1.setBackground(j4.getBackground());
                c1.setTag(j4.getTag());
                j4.setVisibility(View.GONE);

            }else{
                if(choose == 2){
                    c2.setBackground(j4.getBackground());
                    c2.setTag(j4.getTag());
                    j4.setVisibility(View.GONE);

                }else{
                    if(choose == 3){
                        c3.setBackground(j4.getBackground());
                        c3.setTag(j4.getTag());
                        j4.setVisibility(View.GONE);

                    }else{
                        Toast.makeText(this, "You can choose only 3 Jobs", Toast.LENGTH_LONG).show();

                    }
                }
            }
        }
        if((int)j5.getTag()==idjob){
            if(choose == 1 ){
                c1.setBackground(j5.getBackground());
                c1.setTag(j5.getTag());
                j5.setVisibility(View.GONE);

            }else{
                if(choose == 2){
                    c2.setBackground(j5.getBackground());
                    c2.setTag(j5.getTag());
                    j5.setVisibility(View.GONE);

                }else{
                    if(choose == 3){
                        c3.setBackground(j5.getBackground());
                        c3.setTag(j5.getTag());
                        j5.setVisibility(View.GONE);

                    }else{
                        Toast.makeText(this, "You can choose only 3 Jobs", Toast.LENGTH_LONG).show();

                    }
                }
            }
        }
        if((int)j6.getTag()==idjob){
            if(choose == 1 ){
                c1.setBackground(j6.getBackground());
                c1.setTag(j6.getTag());
                j6.setVisibility(View.GONE);

            }else{
                if(choose == 2){
                    c2.setBackground(j6.getBackground());
                    c2.setTag(j6.getTag());
                    j6.setVisibility(View.GONE);

                }else{
                    if(choose == 3){
                        c3.setBackground(j6.getBackground());
                        c3.setTag(j6.getTag());
                        j6.setVisibility(View.GONE);

                    }else{
                        Toast.makeText(this, "You can choose only 3 Jobs", Toast.LENGTH_LONG).show();

                    }
                }
            }

        }
        if((int)j7.getTag()==idjob){

            if(choose == 1 ){
                c1.setBackground(j7.getBackground());
                c1.setTag(j7.getTag());
                j7.setVisibility(View.GONE);

            }else{
                if(choose == 2){
                    c2.setBackground(j7.getBackground());
                    c2.setTag(j7.getTag());
                    j7.setVisibility(View.GONE);

                }else{
                    if(choose == 3){
                        c3.setBackground(j7.getBackground());
                        c3.setTag(j7.getTag());
                        j7.setVisibility(View.GONE);

                    }else{
                        Toast.makeText(this, "You can choose only 3 Jobs", Toast.LENGTH_LONG).show();

                    }
                }
            }
        }



    }


    public int checkEmptyChoice() {
        if ((int)c1.getTag()==c1.getId()) {
            return 1;
        } else {
            if ((int)c2.getTag()== c2.getId()) {
                return 2;
            } else {
                if ((int)c3.getTag()==c3.getId()) {
                    return 3;
                }
            }
        }

        return 0;
    }
    public void UnCheckJob(View view){
        int id = 0;

        if((int)view.getTag()== (int)j1.getTag()){
            j1.setVisibility(View.VISIBLE);
            view.setTag(view.getId());
            id=111;
            view.setBackgroundResource(R.drawable.anonymejob);
        }
        if((int) view.getTag()==(int)j2.getTag()){
            j2.setVisibility(View.VISIBLE);
            view.setTag(view.getId());
            id=222;
            view.setBackgroundResource(R.drawable.anonymejob);
        }
        if((int)view.getTag()== (int)j3.getTag()){
            j3.setVisibility(View.VISIBLE);
            view.setTag(view.getId());
            id=333;
            view.setBackgroundResource(R.drawable.anonymejob);
        }
        if((int)view.getTag()==(int)j4.getTag()){
            j4.setVisibility(View.VISIBLE);
            view.setTag(view.getId());
            id=444;
            view.setBackgroundResource(R.drawable.anonymejob);
        }
        if((int)view.getTag()== (int)j5.getTag()){
            j5.setVisibility(View.VISIBLE);
            view.setTag(view.getId());
            id=555;
            view.setBackgroundResource(R.drawable.anonymejob);
        }
        if((int)view.getTag()== (int)j6.getTag()){
            j6.setVisibility(View.VISIBLE);
            view.setTag(view.getId());
            id=666;
            view.setBackgroundResource(R.drawable.anonymejob);
        }
        if((int)view.getTag()== (int)j7.getTag()){
            j7.setVisibility(View.VISIBLE);
            view.setTag(view.getId());
            view.setBackgroundResource(R.drawable.anonymejob);
            id=777;
        }
        Jobs job = new Jobs(IdDevice,id);
        Gson gson = new Gson();
        String json = gson.toJson(job);
        ExecuteRequest("deletejobs?job",json);
        mydb.deleteJobById(IdDevice,id);

    }

    public void SaveAccount(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(
                BricolleurActivity.this);
        alert.setTitle("Alert!!");
        alert.setMessage("Are you sure to locate Yourself");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
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
                //Toast.makeText(getApplicationContext(),"Working on it "+json,Toast.LENGTH_LONG).show();
                addAccount(json);
                dialog.dismiss();

            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();

    }

    public void addAccount(final String jsonValue){
        if(latitude == null || longitude == null){
            Toast.makeText(BricolleurActivity.this, "Please retry when your GPS is activated", Toast.LENGTH_LONG).show();
        }else {
            ExecuteRequest("register?accountUpdate", jsonValue);
            Toast.makeText(BricolleurActivity.this, "Operation executed Successfully", Toast.LENGTH_LONG).show();
        }
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

                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();


                    }
                });
    }



    public void UnSaveAccount(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(
                BricolleurActivity.this);
        alert.setTitle("Alert!!");
        alert.setMessage("Are you sure to Dislocate Yourself");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                myaccount.set_statut(0);
                Gson gson = new Gson();
                String json = gson.toJson(myaccount);
                ExecuteRequest("register?accountUpdate", json);
                dialog.dismiss();

            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();

    }

    public void ShowhowCase(View view){
        counter=0;
        showcaseView = new ShowcaseView.Builder(this)
                .setTarget(new ViewTarget(findViewById(R.id.namebrico)))
                .setOnClickListener(this)
                .build();
        showcaseView.setButtonText("Next");
    }

    @Override
    public void onClick(View v) {
        switch (counter) {
            case 0:
                showcaseView.setShowcase(new ViewTarget(phonebrico), true);
                break;
            case 1:
                showcaseView.setShowcase(new ViewTarget(c1), true);
                break;
            case 2:
                showcaseView.setShowcase(new ViewTarget(save), true);
                break;
            case 3:
                showcaseView.setShowcase(new ViewTarget(delete), true);
                showcaseView.setButtonText("Close");
                break;
            case 4:
                showcaseView.hide();
                break;


        }
        counter++;
    }


}
