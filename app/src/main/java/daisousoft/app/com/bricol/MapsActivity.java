package daisousoft.app.com.bricol;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.interactiveinfowindow.InfoWindow;
import com.appolica.interactiveinfowindow.InfoWindowManager;
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.maps.android.clustering.ClusterManager;
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
import daisousoft.app.com.bricol.Models.MyItem;
import daisousoft.app.com.bricol.Support.ConnectivityReceiver;
import daisousoft.app.com.bricol.Support.CustomAdapter;
import daisousoft.app.com.bricol.Support.MyApplication;
import daisousoft.app.com.bricol.Support.OwnIconRender;
import daisousoft.app.com.bricol.Support.PlayGifView;
import daisousoft.app.com.bricol.Support.TrackMe;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapsActivity extends FragmentActivity implements InfoWindowManager.WindowShowListener, View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private ShowcaseView showcaseView;
    private int counter = 0;
    public static GoogleMap mMap;
    private TrackMe gps;
    double latitude;
    double longitude;
    Button myProfil, languagebutton, explain, btnalljobs;
    PlayGifView pGif;
    ImageView selectedjob;
    myDBHandler mydb;
    Bundle bundle = new Bundle();
    DialogPlus dialogPlus;
    Integer[] listJobs = {111, 222, 333, 444, 555, 666, 777};
    int itemSelected = 36;
    TextView lookingFor;
    String langueSelected;
    Locale myLocale;
    ClusterManager<MyItem> mClusterManager;
    List<MyItem> MyItemsList = new ArrayList<MyItem>();
    private MyItem clickedClusterItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        //      .findFragmentById(R.id.infoWindowMap);

        //setLocale("ar");

        mydb = new myDBHandler(getApplicationContext());

        //ScrollView verticalScrollView = (ScrollView) findViewById(R.id.vertical_scroll_view);
        //OverScrollDecoratorHelper.setUpOverScroll(verticalScrollView);
        myProfil = (Button) findViewById(R.id.profil);
        explain = (Button) findViewById(R.id.explain);
        languagebutton = (Button) findViewById(R.id.languagebutton);
        btnalljobs = (Button) findViewById(R.id.btnalljobs);
        btnalljobs.setVisibility(View.INVISIBLE);
        selectedjob = (ImageView) findViewById(R.id.selectedjob);
        selectedjob.setVisibility(View.GONE);
        lookingFor = (TextView) findViewById(R.id.lookingFor);
        pGif = (PlayGifView) findViewById(R.id.viewGif);
        pGif.setImageResource(R.drawable.radar);
        gps = new TrackMe(MapsActivity.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        } else {
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
                mClusterManager = new ClusterManager<MyItem>(getApplicationContext(), mMap);
                mClusterManager.setRenderer(new OwnIconRender(getApplicationContext(), mMap, mClusterManager));
                mMap.setOnCameraChangeListener(mClusterManager);

                MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.style_json);
                mMap.setMapStyle(style);
                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);

                } else {
                    Toast.makeText(MapsActivity.this, "You have to accept to enjoy all app's services!", Toast.LENGTH_LONG).show();
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }
                }
                /*CameraUpdate mylocation = CameraUpdateFactory.newLatLngZoom(
                        new LatLng(latitude,longitude), 13);
                mMap.animateCamera(mylocation);*/
                getAllAccounts();
                getAllJobs();
                //MarkerManager mMarkerManager =null;
                //mMap.setOnMarkerClickListener(mMarkerManager);
                /*mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
                    @Override
                    public boolean onClusterItemClick(MyItem item) {
                        clickedClusterItem = item;
                        final InfoWindow.MarkerSpecification markerSpec =
                                new InfoWindow.MarkerSpecification(20, 90);

                        Marker marker = null;
                        marker.setPosition(item.getPosition());
                        marker.setSnippet(item.getSnippet());
                        Fragment fragment = null;
                        fragment = new BricoleurFragment();
                        bundle.putString("ID",marker.getSnippet());
                        fragment.setArguments(bundle);

                        if (fragment != null) {
                            final InfoWindow infoWindow = new InfoWindow(marker, markerSpec, fragment);
                            infoWindowManager.toggle(infoWindow, true);
                        }
                        return false;
                    }
                });*/
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

                        final InfoWindow.MarkerSpecification markerSpec =
                                new InfoWindow.MarkerSpecification(20, 90);


                        Fragment fragment = null;
                        fragment = new BricoleurFragment();
                        bundle.putString("ID", getRightMarker(marker.getPosition().latitude, marker.getPosition().longitude));
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

    public void goProfil(View view) {
        Intent i = new Intent(this, BricolleurActivity.class);
        startActivity(i);
    }

    /*public void goToMyLocation(View view){
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
                new LatLng(latitude,longitude), 13);
        mMap.animateCamera(mylocation);
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 15));
    }*/
    public String getRightMarker(double lat, double lang) {

        for (MyItem i : MyItemsList) {
            if (i.getPosition().latitude == lat && i.getPosition().longitude == lang) {
                return i.getSnippet();
            }
        }
        return "";
    }


    public void mChooseJob(View view) {

    }

    public void getAllAccounts() {
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
                                mClusterManager.clearItems();
                                MyItemsList.clear();
                                String jsonData = res;
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<Account>>() {
                                }.getType();
                                List<Account> listaccounts = (List<Account>) gson.fromJson(jsonData, listType);
                                //IconGenerator  mIcon = new IconGenerator(getApplicationContext());
                                //MyItemsList.clear();
                                for (Account ac : listaccounts) {
                                    mydb.addAccount(ac);
                                    //Bitmap iconBitMap = mIcon.makeIcon(ac.get_name());
                                    if (ac != null && ac.get_statut() == 1 && itemSelected == 36) {
                                        //mMap.addMarker(new MarkerOptions().position(new LatLng(ac.get_lat(), ac.get_long())).snippet(ac.get_id()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                                        //mClusterManager.getMarkerCollection().addMarker(new MarkerOptions().position(new LatLng(ac.get_lat(), ac.get_long())).snippet(ac.get_id()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                                        MyItem offsetItem = new MyItem(ac.get_lat(), ac.get_long(), ac.get_id());
                                        MyItemsList.add(offsetItem);
                                        mClusterManager.addItem(offsetItem);
                                    }
                                }
                                if (itemSelected != 36) {
                                    getFiltredJobs(itemSelected);
                                }
                                mClusterManager.cluster();

                            }
                        });

                    }
                });
    }

    public void getAllJobs() {
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mydb.deleteAllJobs();
                                String jsonData = res;
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<JobsObject>>() {
                                }.getType();
                                List<JobsObject> listjobs = (List<JobsObject>) gson.fromJson(jsonData, listType);
                                for (JobsObject jb : listjobs) {
                                    mydb.addJob(new Jobs(jb.get_idAccount(), jb.getIdjob()));
                                }
                            }
                        });

                    }
                });

    }

    public void FilterJobs(View view) {
        dialogPlus = DialogPlus.newDialog(this)
                .setAdapter(new CustomAdapter(this, listJobs))
                .setCancelable(true)
                .setGravity(Gravity.BOTTOM)
                .setHeader(R.layout.header)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        itemSelected = (Integer) item;
                        getFiltredJobs(itemSelected);
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


    public void getFiltredJobs(int itemid) {
        LookingForJobs(itemid);
        //lookingFor.setText(itemSelected+"");
        ArrayList<String> listAccouts = mydb.getAccountbyJob(itemid);
        mMap.clear();
        MyItemsList.clear();
        mClusterManager.clearItems();
        for (String act : listAccouts) {
            Account cc = mydb.getAccountByID(act);
            if (cc.get_statut() == 1) {
                MyItem offsetItem = new MyItem(cc.get_lat(), cc.get_long(), cc.get_id());
                MyItemsList.add(offsetItem);
                mClusterManager.addItem(offsetItem);
            }
            //mMap.addMarker(new MarkerOptions().position(new LatLng(cc.get_lat(), cc.get_long())).snippet(cc.get_id()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }
        mClusterManager.cluster();
    }


    public void LookingForJobs(int singleJob) {
        if (111 == singleJob) {
            lookingFor.setText(getResources().getString(R.string.Job1));
            selectedjob.setImageResource(R.drawable.job1);
            selectedjob.setVisibility(View.VISIBLE);
            btnalljobs.setVisibility(View.VISIBLE);
        }
        if (222 == singleJob) {
            lookingFor.setText(getResources().getString(R.string.Job2));
            selectedjob.setImageResource(R.drawable.job2);
            selectedjob.setVisibility(View.VISIBLE);
            btnalljobs.setVisibility(View.VISIBLE);

        }
        if (333 == singleJob) {
            lookingFor.setText(getResources().getString(R.string.Job3));
            selectedjob.setImageResource(R.drawable.job3);
            selectedjob.setVisibility(View.VISIBLE);
            btnalljobs.setVisibility(View.VISIBLE);
        }
        if (444 == singleJob) {
            lookingFor.setText(getResources().getString(R.string.Job4));
            selectedjob.setImageResource(R.drawable.job4);
            selectedjob.setVisibility(View.VISIBLE);
            btnalljobs.setVisibility(View.VISIBLE);
        }
        if (555 == singleJob) {
            lookingFor.setText(getResources().getString(R.string.Job5));
            selectedjob.setImageResource(R.drawable.job5);
            selectedjob.setVisibility(View.VISIBLE);
            btnalljobs.setVisibility(View.VISIBLE);
        }
        if (666 == singleJob) {
            lookingFor.setText(getResources().getString(R.string.Job6));
            selectedjob.setImageResource(R.drawable.job6);
            selectedjob.setVisibility(View.VISIBLE);
            btnalljobs.setVisibility(View.VISIBLE);
        }
        if (777 == singleJob) {
            lookingFor.setText(getResources().getString(R.string.Job7));
            selectedjob.setImageResource(R.drawable.job7);
            selectedjob.setVisibility(View.VISIBLE);
            btnalljobs.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (counter) {
            case 0:
                showcaseView.setShowcase(new ViewTarget(explain), true);
                break;

            case 1:
                showcaseView.setShowcase(new ViewTarget(pGif), true);
                break;

            case 2:
                showcaseView.setShowcase(new ViewTarget(languagebutton), true);
                showcaseView.setButtonText("Close");
                break;
            case 3:
                showcaseView.hide();
                break;


        }
        counter++;
    }

    public void Showhow(View view) {
        counter = 0;
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
        refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(refresh);

    }

    public void SelectLanguage(View view) {
        String[] languages = {"العربية", "Français", "English", "にほん", "Español", "Deutsch", "русский"};
        dialogPlus = DialogPlus.newDialog(this)
                .setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, languages))
                .setCancelable(true)
                .setGravity(Gravity.TOP)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        langueSelected = (String) item;
                        if (position == 0) {
                            setLocale("ar");
                        }
                        if (position == 1) {
                            setLocale("fr");
                        }
                        if (position == 2) {
                            setLocale("en");
                        }
                        if (position == 3) {
                            setLocale("ja");
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

    public void displayAllJobs(View view) {
        lookingFor.setText(getResources().getString(R.string.All_jobs));
        getAllAccounts();
        getAllJobs();
        selectedjob.setVisibility(View.INVISIBLE);
        btnalljobs.setVisibility(View.INVISIBLE);
        itemSelected = 36;


    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllAccounts();
        getAllJobs();
        /*lookingFor.setText("All Jobs");
        selectedjob.setVisibility(View.INVISIBLE);
        btnalljobs.setVisibility(View.INVISIBLE);*/
        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
        } else {
            message = "Sorry! Not connected to internet";
        }
        Toast.makeText(MapsActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
