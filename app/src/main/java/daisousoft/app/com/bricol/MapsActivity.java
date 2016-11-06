package daisousoft.app.com.bricol;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.appolica.interactiveinfowindow.InfoWindow;
import com.appolica.interactiveinfowindow.InfoWindowManager;
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;
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

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

public class MapsActivity extends FragmentActivity implements InfoWindowManager.WindowShowListener {

    public static GoogleMap mMap;
    private TrackMe gps;
    double latitude;
    double longitude;
    Button myProfil;
    PlayGifView pGif;
    myDBHandler mydb ;
    ArrayList<Account> bricoList;
    Bundle bundle = new Bundle();
    DialogPlus dialogPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
          //      .findFragmentById(R.id.infoWindowMap);
        //Synchronize fetcher = new Synchronize(this.getApplicationContext());
        //fetcher.execute();

        Integer[] listJobs = {2131624106,2131624107,2131624108,2131624109,2131624110,2131624111,2131624112};

        dialogPlus = DialogPlus.newDialog(this)
                .setAdapter(new CustomAdapter(this, listJobs))
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setExpanded(true, 400)
                .setHeader(R.layout.header)
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




        mydb = new myDBHandler(getApplicationContext());

        //ScrollView verticalScrollView = (ScrollView) findViewById(R.id.vertical_scroll_view);
        //OverScrollDecoratorHelper.setUpOverScroll(verticalScrollView);
        myProfil = (Button) findViewById(R.id.profil);
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
        dialogPlus.show();
    }
}
