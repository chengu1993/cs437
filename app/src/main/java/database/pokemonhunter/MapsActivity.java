package database.pokemonhunter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnInfoWindowClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private boolean mPermissionDenied = false;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        List<LatLng> positions = new LinkedList<>();
//        positions.add(new LatLng(41.31, -72.93));
//        positions.add(new LatLng(41.34, -72.91));
//        positions.add(new LatLng(41.27, -72.99));
////
//        for(int i=0; i<positions.size(); i++){
//            mMap.addMarker(new MarkerOptions().position(positions.get(i))
//                    .title(new Date().toString())
//                    .icon(BitmapDescriptorFactory.fromBitmap(IconUtils.resizeMapIcons(this, "pokemon00"+(i+1), 128, 128))));
//
//        }


        mMap.setOnInfoWindowClickListener(this);


//        mMap.moveCamera(CameraUpdateFactory.newLatLng(newHaven));

        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();

        DatabaseConnector databaseConnector = new DatabaseConnector(this);
        databaseConnector.execute("info.php");


    }

    private static final String TAG = "DatabaseUtils";
    private static final String BASE_URL = "http://172.27.157.75/";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "guchenji";



    protected void showPokemon(String jsonString){
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray pokemons = jsonObject.getJSONArray("pokemon");
            for(int i=0; i<pokemons.length(); i++){
                JSONObject pokemon = pokemons.getJSONObject(i);
                int pokemon_id = pokemon.getInt("pokemon_id");
                double latitude = pokemon.getDouble("latitude");
                double longitude = pokemon.getDouble("longitude");
                String time = pokemon.getString("time");
                mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
                        .title(time)
                        .snippet(String.valueOf(pokemon_id))
                        .icon(BitmapDescriptorFactory.fromBitmap(IconUtils.resizeMapIcons(this, String.format(Locale.US, "pokemon%03d", pokemon_id)))));


            }
        } catch (JSONException e){
            Log.e("JSON Parse", "Error parsing data"+ e.toString()) ;
        }


    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    android.Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent attribute = new Intent(this, Atrribute.class);
        startActivity(attribute);
    }
}
