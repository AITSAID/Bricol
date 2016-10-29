package daisousoft.app.com.bricol;

import android.app.Activity;
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

import daisousoft.app.com.bricol.DAO.myDBHandler;
import daisousoft.app.com.bricol.Models.Account;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class BricolleurActivity extends Activity {

    Button j1,j2,j3,j4,j5,j6,j7,c1,c2,c3;
    EditText namebricp,phonebrico;
    private TrackMe gps;
    private double latitude,longitude;
    String IdDevice,PhoneNumber;
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
        phonebrico = (EditText) findViewById(R.id.phonebrico);
        namebricp = (EditText) findViewById(R.id.namebrico) ;
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
        //PhoneNumber = tMgr.getSimSerialNumber();

        //phonebrico.setText(PhoneNumber);
        //DB HANDLER
        mydb = new myDBHandler(getApplicationContext());

    }

    public void ChooseJob(View view) {
        //test DATABASE
        //mydb.addAccount(new Account("hdjahdadz5454", "MAROUANE", "0672145149", 33.121, 7.1245, 1));
        //phonebrico.setText(mydb.getAllAccounts().size()+"");


        //
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
        if(view.getTag().equals(j1.getId())){
            j1.setVisibility(View.VISIBLE);
            j1.setBackgroundResource(R.drawable.job1);
            view.setTag(view.getId());
            view.setBackgroundResource(R.drawable.anonymejob);
        }
        if(view.getTag().equals(j2.getId())){
            j2.setVisibility(View.VISIBLE);
            view.setTag(view.getId());
            view.setBackgroundResource(R.drawable.anonymejob);
        }
        if(view.getTag().equals(j3.getId())){
            j3.setVisibility(View.VISIBLE);
            view.setTag(view.getId());
            view.setBackgroundResource(R.drawable.anonymejob);
        }
        if(view.getTag().equals(j4.getId())){
            j4.setVisibility(View.VISIBLE);
            view.setTag(view.getId());
            view.setBackgroundResource(R.drawable.anonymejob);
        }
        if(view.getTag().equals(j5.getId())){
            j5.setVisibility(View.VISIBLE);
            view.setTag(view.getId());
            view.setBackgroundResource(R.drawable.anonymejob);
        }
        if(view.getTag().equals(j6.getId())){
            j6.setVisibility(View.VISIBLE);
            view.setTag(view.getId());
            view.setBackgroundResource(R.drawable.anonymejob);
        }
        if(view.getTag().equals(j7.getId())){
            j7.setVisibility(View.VISIBLE);
            view.setTag(view.getId());
            view.setBackgroundResource(R.drawable.anonymejob);
        }
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
        mydb.addAccount(account);

    }
}
