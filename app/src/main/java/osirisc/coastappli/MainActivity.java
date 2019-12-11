package osirisc.coastappli;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;

import com.google.android.material.navigation.NavigationView;

import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import osirisc.coastappli.Database.DatabaseAssistant;
import osirisc.coastappli.Database.Marker;

import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.step;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.expressions.Expression.zoom;


public class MainActivity extends AppCompatActivity implements PermissionsListener,MapboxMap.OnMapClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private MapboxMap mapBoxMap;
    private PermissionsManager permissionsManager;
    private DatabaseAssistant databaseAssistant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_map, R.id.nav_history, R.id.nav_badges,
                R.id.nav_manage, R.id.nav_log_out, R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Place to add the markers
        databaseAssistant = new DatabaseAssistant(this);
    }

    public void createMapLocation(Bundle savedInstanceState){

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, "pk.eyJ1IjoicGF1bC1kcm9pZCIsImEiOiJjazNlbnJsMmowMDZrM2VtbmR1MWpjbHpoIn0.GeyDIGrew2ZOKRaYxwtC3w");

        // Create supportMapFragment
        final SupportMapFragment mapFragment;
        if (savedInstanceState == null) {

        // Create fragment
            final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Build a Mapbox map
            MapboxMapOptions options = MapboxMapOptions.createFromAttributes(this, null);
            options.camera(new CameraPosition.Builder()
                    .target(new LatLng(38.899895, -77.03401))
                    .zoom(9)
                    .build());

        // Create map fragment
            mapFragment = SupportMapFragment.newInstance(options);

        // Add map fragment to parent container
            transaction.add(R.id.mapView, mapFragment, "com.mapbox.map");
            transaction.commit();
        } else {
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentByTag("com.mapbox.map");
        }

        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull MapboxMap mapboxMap) {
                    mapBoxMap = mapboxMap;
                    mapboxMap.setStyle(Style.OUTDOORS, new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {
                            mapBoxMap.addOnMapClickListener(MainActivity.this);
                            enableLocationComponent(style);
                            style.addImage("custom_marker.png", BitmapFactory.decodeResource(
                                    MainActivity.this.getResources(), R.drawable.custom_marker));
                            style.addImage("custom_marker_small.png", BitmapFactory.decodeResource(
                                    MainActivity.this.getResources(), R.drawable.custom_marker_small));
                            displayMarkers(style);
                        }
                    });
                }
            });
        }
        }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

        // Get an instance of the LocationComponent.
            LocationComponent locationComponent = mapBoxMap.getLocationComponent();

        // Activate the LocationComponent
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());

        // Enable the LocationComponent so that it's actually visible on the map
            locationComponent.setLocationComponentEnabled(true);

        // Set the LocationComponent's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING_GPS);

        // Set the LocationComponent's render mode
            locationComponent.setRenderMode(RenderMode.NORMAL);

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    public void centerMapOnMyLocation() {
        mapBoxMap.setStyle(Style.OUTDOORS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                mapBoxMap.addOnMapClickListener(MainActivity.this);
                enableLocationComponent(style);
                style.addImage("custom_marker.png", BitmapFactory.decodeResource(
                        MainActivity.this.getResources(), R.drawable.custom_marker));
                style.addImage("custom_marker_small.png", BitmapFactory.decodeResource(
                        MainActivity.this.getResources(), R.drawable.custom_marker_small));
                displayMarkers(style);
            }
        });
    }

    private void displayMarkers(@NonNull Style loadedMapStyle) {

        List<Feature> features = new ArrayList<>();
        ArrayList<Marker> listMarker = databaseAssistant.loadMarker();
        Log.i("ListMarker",listMarker.toString());
        for (int i = 0; i < listMarker.size(); ++i){
            features.add(Feature.fromGeometry(Point.fromLngLat(listMarker.get(i).getLongitude(), listMarker.get(i).getLatitude())));

        }
        /* Source: A data source specifies the geographic coordinate where the image marker gets placed. */
        loadedMapStyle.addSource(new GeoJsonSource("pk.eyJ1IjoicGF1bC1kcm9pZCIsImEiOiJjazNlbnJsMmowMDZrM2VtbmR1MWpjbHpoIn0.GeyDIGrew2ZOKRaYxwtC3w", FeatureCollection.fromFeatures(features)));

        /* Style layer: A style layer ties together the source and image and specifies how they are displayed on the map. */
        loadedMapStyle.addLayer(new SymbolLayer("custom_markers", "pk.eyJ1IjoicGF1bC1kcm9pZCIsImEiOiJjazNlbnJsMmowMDZrM2VtbmR1MWpjbHpoIn0.GeyDIGrew2ZOKRaYxwtC3w")
                .withProperties(
                        PropertyFactory.iconAllowOverlap(true),
                        PropertyFactory.iconIgnorePlacement(true),
                        PropertyFactory.iconImage(step(zoom(),literal("custom_marker_small.png"),stop(14,"custom_marker.png")))
                ));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapBoxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        int zoom = (int)mapBoxMap.getCameraPosition().zoom;
        ArrayList<Marker> listMarker = databaseAssistant.loadMarker();
        Double Latitude = point.getLatitude();
        Double Longitude = point.getLongitude();
        //Depending on the zoom, we adapt the error the user can have when clicking on the marker
        //---PEUT-ÊTRE AJUSTER UN PEU LA LISTE DES ERREURS---
        Double[] errorList = new Double[]{1.,1.,1.,1.,0.1,0.1,0.1,0.01,0.01,0.01,0.01,0.001,0.001,0.001,0.001,0.0001,0.0001,0.0001,0.0001,0.0001,0.0001,0.0001,0.00001};
        for (int i = 0; i < listMarker.size(); ++i){
            if (listMarker.get(i).getLatitude() >= Latitude-errorList[zoom] && listMarker.get(i).getLatitude() <= Latitude+errorList[zoom]
                    && listMarker.get(i).getLongitude() >= Longitude-errorList[zoom] && listMarker.get(i).getLongitude() <= Longitude+errorList[zoom]){
                Marker marker = databaseAssistant.findMarker(listMarker.get(i).getLatitude(), listMarker.get(i).getLongitude());
                Intent myIntent= new Intent(this, PlaceMainActivity.class);
                myIntent.putExtra("markerLatitude", marker.getLatitude());
                myIntent.putExtra("markerLongitude", marker.getLongitude());
                myIntent.putExtra("nameBeach", marker.getNameBeach());
                myIntent.putExtra("nameTown", marker.getNameTown());
                myIntent.putExtra("coastType", marker.getCoastType());
                myIntent.putExtra("INEC", marker.getINEC());
                myIntent.putExtra("erosionDistanceMesureBool", marker.getErosionDistanceMesureBool());
                MainActivity.this.startActivity(myIntent);}
        }
        return true;
    }
}