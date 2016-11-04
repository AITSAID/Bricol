package daisousoft.app.com.bricol;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import daisousoft.app.com.bricol.DAO.myDBHandler;
import daisousoft.app.com.bricol.Fragments.BricoleurFragment;
import daisousoft.app.com.bricol.Models.Account;
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
    private static final String FORM_VIEW = "FORM_VIEW_MARKER";
    myDBHandler mydb ;
    ArrayList<Account> bricoList;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
          //      .findFragmentById(R.id.infoWindowMap);
        //Synchronize fetcher = new Synchronize(this.getApplicationContext());
        //fetcher.execute();



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
                ExecuteRequest();

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
        ExecuteRequest();

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

    public void ExecuteRequest(){
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
/**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setBuildingsEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng me = new LatLng(33.605099,-7.48496);
        mMap.addMarker(new MarkerOptions().position(me).title("Marouane Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(me, 17.0f));

    }*/
}
