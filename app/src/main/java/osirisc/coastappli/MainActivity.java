package osirisc.coastappli;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
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

/**
 *  This is the main activity launched at the start of the app
 *  It launches the map and has all the functions which interact with it
 */
public class MainActivity extends AppCompatActivity implements PermissionsListener,MapboxMap.OnMapClickListener {

    // The MapboxMap is the object containing all the map information
    private MapboxMap mapBoxMap;

    private AppBarConfiguration mAppBarConfiguration;
    private PermissionsManager permissionsManager;
    private DatabaseAssistant databaseAssistant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If the activity is launched as the app is just starting
        // we launch the splashscreen, hiding the loading time
        if (savedInstanceState==null){
            Intent i = new Intent(MainActivity.this,
                    SplashScreen.class);
            startActivity(i);
        }

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

        // We recover the database
        databaseAssistant = new DatabaseAssistant(this);
    }


    /**
     * Function in which the MapboxMap is created with all its characteristics
     * @param savedInstanceState
     */
    public void createMapLocation(Bundle savedInstanceState){

        // Mapbox access token is configured here.
        // This needs to be called either in the application in order to access Mapbox data
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
                            displayMarkers(style);
                        }
                    });
                }
            });
        }
    }

    /**
     * Function that enables location if the access is granted by the device
     * It then sets the camera to a tracking mode
     * @param loadedMapStyle
     */
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

        // If permission to access location is not granted, it is asked to grant it
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    /**
     * This function adds the marker's layer on the map
     * The markers have to be in the database to appear in this layer
     * @param loadedMapStyle
     */
    private void displayMarkers(@NonNull Style loadedMapStyle) {
        List<Feature> features = new ArrayList<>();

        // We retrieve all the markers form the database
        ArrayList<Marker> listMarker = databaseAssistant.loadMarker();

        // We add them to the marker's list
        for (int i = 0; i < listMarker.size(); ++i){
            features.add(Feature.fromGeometry(Point.fromLngLat(listMarker.get(i).getLongitude(), listMarker.get(i).getLatitude())));
        }

        // We add the source: A data source specifies the geographic coordinate where the image marker gets placed
        loadedMapStyle.addSource(new GeoJsonSource("pk.eyJ1IjoicGF1bC1kcm9pZCIsImEiOiJjazNlbnJsMmowMDZrM2VtbmR1MWpjbHpoIn0.GeyDIGrew2ZOKRaYxwtC3w", FeatureCollection.fromFeatures(features)));

        // We add the style layer: A style layer ties together the source and image and specifies how they are displayed on the map
        // The layer's properties can be changed here
        loadedMapStyle.addLayer(new SymbolLayer("custom_markers", "pk.eyJ1IjoicGF1bC1kcm9pZCIsImEiOiJjazNlbnJsMmowMDZrM2VtbmR1MWpjbHpoIn0.GeyDIGrew2ZOKRaYxwtC3w")
                .withProperties(
                        PropertyFactory.iconAllowOverlap(true),
                        PropertyFactory.iconIgnorePlacement(true),
                        PropertyFactory.iconImage(literal("custom_marker.png")))
                );
    }

    /**
     * Function that retrieves the result from the location permission question
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Function that makes a message pop up if granting the location is not possible on the device
     * It explains that the usage of location is impossible
     * @param permissionsToExplain
     */
    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_localisation_permission_explanation, Toast.LENGTH_LONG).show();
    }

    /**
     * Function that restarts the enableLocation function if location is granted by the user
     * If it's not granted, the app shows a message explaining that the location is not granted
     * @param granted
     */
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
            Toast.makeText(this, R.string.user_localisation_permission_not_granted, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * We override the base method onSupportNavigateUp in order to work with our configuration
     * @return
     */
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * This is the function launched by clicking on the location button
     * It makes the map center on the user's location
     */
    public void centerMapOnMyLocation() {
        // In order to center the map on the user's location, we just reload the mapBoxMap style
        mapBoxMap.setStyle(Style.OUTDOORS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                mapBoxMap.addOnMapClickListener(MainActivity.this);
                enableLocationComponent(style);
                style.addImage("custom_marker.png", BitmapFactory.decodeResource(
                        MainActivity.this.getResources(), R.drawable.custom_marker));
                displayMarkers(style);
            }
        });
    }

    /**
     * This is the funtion launched when the map is clicked on
     * @param point
     * @return
     */
    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        // We retrieve the zoom from the map
        int zoom = (int)mapBoxMap.getCameraPosition().zoom;
        // We retrieve all the markers from the database
        ArrayList<Marker> listMarker = databaseAssistant.loadMarker();

        // We recover the latitude and longitude from the point where the user clicked
        Double Latitude = point.getLatitude();
        Double Longitude = point.getLongitude();

        // Depending on the zoom, we adapt the error the user can have when clicking on the marker
        // Each number in this list is the error for each level of zoom, the first one being the biggest and the last one the smallest
        Double[] errorList = new Double[]{1.,1.,1.,1.,0.1,0.1,0.1,0.01,0.01,0.01,0.01,0.001,0.001,0.001,0.001,0.0001,0.0001,0.0001,0.0001,0.0001,0.0001,0.0001,0.00001};

        // We then see if the user clicked on the square in which a marker is depending on the current zoom
        for (int i = 0; i < listMarker.size(); ++i){
            if (listMarker.get(i).getLatitude() >= Latitude-errorList[zoom] && listMarker.get(i).getLatitude() <= Latitude+errorList[zoom]
                    && listMarker.get(i).getLongitude() >= Longitude-errorList[zoom] && listMarker.get(i).getLongitude() <= Longitude+errorList[zoom]){

                // If the user actually clicked on a marker, we recover the marker's data from the database
                final Marker marker = databaseAssistant.findMarker(listMarker.get(i).getLatitude(), listMarker.get(i).getLongitude());

                // We then create a button with the marker name on it with the name of the town
                final Button buttonInformation = findViewById(R.id.informationButton);
                buttonInformation.setText(marker.getNameBeach()+ " / "+marker.getNameTown());
                buttonInformation.setTextColor(getResources().getColor(R.color.white));

                // We animate it to come from the bottom of the screen
                buttonInformation.animate().translationY(-50);

                // We add an onClickListener which opens the locationMainActivity with the right marker's information
                buttonInformation.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v)
                    {
                        Intent myIntent= new Intent(MainActivity.this, LocationMainActivity.class);

                        // We put in the extras part of the intent all of the markers data (appart from photos, they are to big for the intent)
                        myIntent.putExtra("markerLatitude", marker.getLatitude());
                        myIntent.putExtra("markerLongitude", marker.getLongitude());
                        myIntent.putExtra("nameBeach", marker.getNameBeach());
                        myIntent.putExtra("nameTown", marker.getNameTown());
                        myIntent.putExtra("coastType", marker.getCoastType());
                        myIntent.putExtra("INEC", marker.getINEC());
                        myIntent.putExtra("erosionPhotoCaptureBool", marker.getErosionDistanceMesureBool());

                        // We start the activity new activity
                        MainActivity.this.startActivity(myIntent);

                    // We animate the button to disappear at the bottom of the screen
                    buttonInformation.animate().translationY(130);
                    }
                });
            }
            }
        return true;
    }
}