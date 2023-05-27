package ru.mirea.zhurin.d.r.yandexmaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CompositeIcon;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.RotationType;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

import ru.mirea.zhurin.d.r.yandexmaps.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements
        UserLocationObjectListener{
    private MapView mapView;
    private UserLocationLayer userLocationLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.mapview);
        mapView.getMap().move(
                new CameraPosition(new Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    public void onObjectAdded(UserLocationView userLocationView) {
        userLocationLayer.setAnchor(
                new PointF((float)(mapView.getWidth() * 0.5),
                        (float)(mapView.getHeight() * 0.5)),
                new PointF((float)(mapView.getWidth() * 0.5),
                        (float)(mapView.getHeight() * 0.83)));
        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
                this, android.R.drawable.arrow_up_float));
        
        CompositeIcon pinIcon = userLocationView.getPin().useCompositeIcon();
        pinIcon.setIcon(
                "pin",
                ImageProvider.fromResource(this, R.drawable.ic_launcher_foreground),
                new IconStyle().setAnchor(new PointF(0.5f, 0.5f))
                        .setRotationType(RotationType.ROTATE)
                        .setZIndex(1f)
                        .setScale(0.5f)
        );

        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE & 0x99ffffff);
    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {
    }
    
    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView,
                                @NonNull ObjectEvent objectEvent) {
    }

    private void loadUserLocationLayer(){
        MapKit mapKit = MapKitFactory.getInstance();
        mapKit.resetLocationManagerToDefault();
        userLocationLayer = mapKit.createUserLocationLayer(mapView.getMapWindow());
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);
        userLocationLayer.setObjectListener(this);
    }
}
