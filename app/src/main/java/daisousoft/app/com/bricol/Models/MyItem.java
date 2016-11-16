package daisousoft.app.com.bricol.Models;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by HABABONGA on 12/11/2016.
 */
public class MyItem implements ClusterItem {
    private final LatLng mPosition;
    private final String mSnippet;

    public MyItem(double lat, double lng,String s) {
        mPosition = new LatLng(lat, lng);
        mSnippet=s;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public String getSnippet(){
        return mSnippet;
    }
}