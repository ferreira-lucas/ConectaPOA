package thiagocury.eti.br.conectapoa;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by QI on 29/12/2017.
 */

public class CustomClusterRenderer extends DefaultClusterRenderer<Wifi> {

    private final Context mContext;

    public CustomClusterRenderer(Context context, GoogleMap map,
                                 ClusterManager<Wifi> clusterManager) {
        super(context, map, clusterManager);

        mContext = context;
    }

    @Override
    protected void onBeforeClusterItemRendered(Wifi item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);

        if(item.getSetor().equals("Poder Público")) {
            final BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_wifi_livre);
            markerOptions.icon(markerDescriptor).snippet(item.getNomeRede());
        }else if(item.getSetor().equals("Rede Privada")){
            final BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_wifi_cadeado);
            markerOptions.icon(markerDescriptor).snippet(item.getNomeRede());

        }
    }
}
