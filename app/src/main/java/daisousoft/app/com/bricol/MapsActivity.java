package daisousoft.app.com.bricol;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appolica.interactiveinfowindow.InfoWindow;
import com.appolica.interactiveinfowindow.InfoWindowManager;
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnBackPressListener;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import daisousoft.app.com.bricol.DAO.myDBHandler;
import daisousoft.app.com.bricol.Fragments.BricoleurFragment;
import daisousoft.app.com.bricol.Models.Account;
import daisousoft.app.com.bricol.Models.Jobs;
import daisousoft.app.com.bricol.Models.JobsObject;
import daisousoft.app.com.bricol.Support.CustomAdapter;
import daisousoft.app.com.bricol.Support.PlayGifView;
import daisousoft.app.com.bricol.Support.TrackMe;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapsActivity extends FragmentActivity implements InfoWindowManager.WindowShowListener ,View.OnClickListener {

    private ShowcaseView showcaseView;
    private int counter = 0;
    public static GoogleMap mMap;
    private TrackMe gps;
    double latitude;
    double longitude;
    Button myProfil,toMe;
    PlayGifView pGif;
    ImageView selectedjob;
    myDBHandler mydb ;
    ArrayList<Account> bricoList;
    Bundle bundle = new Bundle();
    DialogPlus dialogPlus,dialogLangue;
    Integer[] listJobs = {2131624106,2131624107,2131624108,2131624109,2131624110,2131624111,2131624112};
    int itemSelected;
    TextView lookingFor;
    String langueSelected;
    Locale myLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
          //      .findFragmentById(R.id.infoWindowMap);
        //Synchronize fetcher = new Synchronize(this.getApplicationContext());
        //fetcher.execute();
        //setLocale("ar");

        mydb = new myDBHandler(getApplicationContext());

        toMe = (Button) findViewById(R.id.toMe);
        //ScrollView verticalScrollView = (ScrollView) findViewById(R.id.vertical_scroll_view);
        //OverScrollDecoratorHelper.setUpOverScroll(verticalScrollView);
        myProfil = (Button) findViewById(R.id.profil);
        selectedjob = (ImageView) findViewById(R.id.selectedjob);
        selectedjob.setVisibility(View.GONE);
        lookingFor = (TextView) findViewById(R.id.lookingFor);
        pGif = (PlayGifView) findViewById(R.id.viewGif);
        pGif.setImageResource(R.drawable.radar);
        gps = new TrackMe(MapsActivity.this);
        if(gps.canGetLocation()){
            latitude = gps .getLatitude();
            longitude = gps.getLongitude();
        }
        else
        {
            gps.showSettingsAlert();
        }

        final MapInfoWindowFragment mapInfoWindowFragment =
                (MapInfoWindowFragment) getSupportFragmentManager().findFragmentById(R.id.infoWindowMap);

        final InfoWindowManager infoWindowManager = mapInfoWindowFragment.infoWindowManager();
        infoWindowManager.setHideOnFling(true);

        mapInfoWindowFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                bricoList = mydb.getAllAccounts();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 15));
                getAllAccounts();
                getAllJobs();

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

                        final InfoWindow.MarkerSpecification markerSpec =
                                new InfoWindow.MarkerSpecification(20, 90);

                        Fragment fragment = null;
                        fragment = new BricoleurFragment();
                        bundle.putString("ID",marker.getSnippet());
                        fragment.setArguments(bundle);

                        if (fragment != null) {
                            final InfoWindow infoWindow = new InfoWindow(marker, markerSpec, fragment);
                            infoWindowManager.toggle(infoWindow, true);
                        }


                        return true;
                    }
                });
            }
        });

        infoWindowManager.setWindowShowListener(new InfoWindowManager.WindowShowListener() {
            @Override
            public void onWindowShowStarted(@NonNull InfoWindow infoWindow) {

            }

            @Override
            public void onWindowShown(@NonNull InfoWindow infoWindow) {

            }

            @Override
            public void onWindowHideStarted(@NonNull InfoWindow infoWindow) {

            }

            @Override
            public void onWindowHidden(@NonNull InfoWindow infoWindow) {

            }
        });
    }

    @Override
    public void onWindowShowStarted(@NonNull InfoWindow infoWindow) {
//        Log.d("debug", "onWindowShowStarted: " + infoWindow);
    }

    @Override
    public void onWindowShown(@NonNull InfoWindow infoWindow) {
//        Log.d("debug", "onWindowShown: " + infoWindow);
    }

    @Override
    public void onWindowHideStarted(@NonNull InfoWindow infoWindow) {
//        Log.d("debug", "onWindowHideStarted: " + infoWindow);
    }

    @Override
    public void onWindowHidden(@NonNull InfoWindow infoWindow) {
//        Log.d("debug", "onWindowHidden: " + infoWindow);
    }

    public void goProfil(View view){
        Intent i = new Intent(this , BricolleurActivity.class);
        startActivity(i);
    }

    public void goToMyLocation(View view){
        getAllAccounts();
        getAllJobs();

        gps = new TrackMe(MapsActivity.this);
        if(gps.canGetLocation()){
            latitude = gps .getLatitude();
            longitude = gps.getLongitude();
        }
        else
        {
            gps.showSettingsAlert();
        }
        CameraUpdate mylocation = CameraUpdateFactory.newLatLngZoom(
                new LatLng(latitude,longitude), 15);
        mMap.animateCamera(mylocation);
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 15));
    }


    public void mChooseJob(View view){

    }

    public void getAllAccounts(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://bricolapp-daisousoft.rhcloud.com/getallbrico")
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
                                mMap.clear();
                                mydb.deleteAllAccounts();
                                String jsonData = res;
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<Account>>(){}.getType();
                                List<Account> listaccounts = (List<Account>) gson.fromJson(jsonData, listType);
                                for(Account ac :listaccounts) {
                                    mydb.addAccount(ac);
                                    if(ac!=null) {
                                        mMap.addMarker(new MarkerOptions().position(new LatLng(ac.get_lat(), ac.get_long())).snippet(ac.get_id()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                                    }
                                }
                            }
                        });

                    }
                });
    }

    public void getAllJobs(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://bricolapp-daisousoft.rhcloud.com/getalljobs")
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
                        mydb.deleteAllJobs();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String jsonData = res;
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<JobsObject>>(){}.getType();
                                List<JobsObject> listjobs = (List<JobsObject>) gson.fromJson(jsonData, listType);
                                for(JobsObject jb :listjobs) {
                                    mydb.addJob(new Jobs(jb.get_idAccount(),jb.getIdjob()));
                                }
                            }
                        });

                    }
                });

    }
    public void FilterJobs(View view){
        dialogPlus = DialogPlus.newDialog(this)
                .setAdapter(new CustomAdapter(this, listJobs))
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setExpanded(true, 400)
                .setHeader(R.layout.header)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        itemSelected =(Integer)item;
                        LookingForJobs(itemSelected);
                        //lookingFor.setText(itemSelected+"");
                        ArrayList<String> listAccouts = mydb.getAccountbyJob(itemSelected);
                        mMap.clear();
                        for (String act : listAccouts){
                            Account cc = mydb.getAccountByID(act);
                            mMap.addMarker(new MarkerOptions().position(new LatLng(cc.get_lat(), cc.get_long())).snippet(cc.get_id()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                        }
                        dialogPlus.dismiss();
                    }
                })
                .setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogPlus dialog) {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel(DialogPlus dialog) {

                    }
                })
                .setOnBackPressListener(new OnBackPressListener() {
                    @Override
                    public void onBackPressed(DialogPlus dialogPlus) {

                    }
                })
                .create();


        dialogPlus.show();
    }

    public void LookingForJobs(int singleJob){
        if(2131624106==singleJob){
            lookingFor.setText(getResources().getString(R.string.job1));
            selectedjob.setImageResource(R.drawable.job1);
            selectedjob.setVisibility(View.VISIBLE);
        }
        if(2131624107==singleJob){
            lookingFor.setText(getResources().getString(R.string.job2));
            selectedjob.setImageResource(R.drawable.job2);
            selectedjob.setVisibility(View.VISIBLE);
        }
        if(2131624108==singleJob){
            lookingFor.setText("job3");
            selectedjob.setImageResource(R.drawable.job3);
            selectedjob.setVisibility(View.VISIBLE);
        }
        if(2131624109==singleJob){
            lookingFor.setText("job4");
            selectedjob.setImageResource(R.drawable.job4);
            selectedjob.setVisibility(View.VISIBLE);
        }
        if(2131624110==singleJob){
            lookingFor.setText("job5");
            selectedjob.setImageResource(R.drawable.job5);
            selectedjob.setVisibility(View.VISIBLE);
        }
        if(2131624111==singleJob){
            lookingFor.setText("job6");
            selectedjob.setImageResource(R.drawable.job6);
            selectedjob.setVisibility(View.VISIBLE);
        }
        if(2131624112==singleJob){
            lookingFor.setText("job7");
            selectedjob.setImageResource(R.drawable.job7);
            selectedjob.setVisibility(View.VISIBLE);
        }
    }


    private void setAlpha(float alpha, View... views) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            for (View view : views) {
                view.setAlpha(alpha);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (counter) {
            case 0:
                showcaseView.setShowcase(new ViewTarget(toMe), true);
                break;

            case 1:
                showcaseView.setShowcase(new ViewTarget(pGif), true);
                break;

            case 2:
                showcaseView.setTarget(Target.NONE);
                showcaseView.setContentTitle("Check it out");
                showcaseView.setContentText("You don't always need a target to showcase");
                showcaseView.setButtonText("Close");
                setAlpha(0.4f, myProfil, toMe, pGif);
                break;

            case 3:
                showcaseView.hide();
                setAlpha(1.0f, myProfil, toMe, pGif);
                break;
        }
        counter++;
    }

    public void Showhow(View view){
        counter=0;
        showcaseView = new ShowcaseView.Builder(this)
                .setTarget(new ViewTarget(findViewById(R.id.profil)))
                .setOnClickListener(this)
                .build();
        showcaseView.setButtonText("Next");

    }

    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
        Intent refresh = new Intent(this, MapsActivity.class);
        startActivity(refresh);

    }

    public void SelectLanguage(View view){
        String[] languages = {"العربية","Français","English","にほん"};
        dialogPlus = DialogPlus.newDialog(this)
                .setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,languages))
                .setCancelable(true)
                .setGravity(Gravity.TOP)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        langueSelected =(String)item;
                        if(position==0){
                        setLocale("ar");}
                        if(position==1){
                            setLocale("fr");}
                        if(position==2){
                            setLocale("en");}
                        if(position==3){
                            setLocale("ja");}
                        dialogPlus.dismiss();
                    }
                })
                .setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogPlus dialog) {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel(DialogPlus dialog) {

                    }
                })
                .setOnBackPressListener(new OnBackPressListener() {
                    @Override
                    public void onBackPressed(DialogPlus dialogPlus) {

                    }
                })
                .create();


        dialogPlus.show();


    }


}
