package daisousoft.app.com.bricol.Support;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import daisousoft.app.com.bricol.Models.MyItem;

/**
 * Created by HABABONGA on 16/11/2016.
 */
public class OwnIconRender extends DefaultClusterRenderer<MyItem> {
    public OwnIconRender(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<MyItem> cluster) {
        //start clustering if at least 2 items overlap
        return cluster.getSize() > 1;
    }
}
