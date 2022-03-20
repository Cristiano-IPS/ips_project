package cm.app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

import cm.app.db.Status;
import cm.app.utils.BitmapUtils;
import cm.app.utils.PermissionUtils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnInfoWindowClickListener {
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static String helptype= "";
    private boolean mPermissionDenied = false;
    private GoogleMap mMap;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
        if (useDarkTheme) {
            setTheme(R.style.Theme_AppCompat_Custom);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navView2);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.help), Toast.LENGTH_LONG).show();
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        enableMyLocation();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(MainActivity.this, ButtonMapActivity.class);
        intent.putExtra("title", marker.getTitle());
        intent.putExtra("snippet", marker.getSnippet());
        intent.putExtra("tag", marker.getTag().toString());
        startActivity(intent);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "LAT: " + location.getLatitude() + "\nLON: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }
        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            enableMyLocation();
        } else {
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog.newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
            BitmapDescriptor icon = BitmapUtils.bitmapDescriptorFromVector(this, R.drawable.pin, 128, 128);
            BitmapDescriptor wave = BitmapUtils.bitmapDescriptorFromVector(this, R.drawable.wave, 128, 128);
            BitmapDescriptor fire = BitmapUtils.bitmapDescriptorFromVector(this, R.drawable.fire, 128, 128);
            BitmapDescriptor earth = BitmapUtils.bitmapDescriptorFromVector(this, R.drawable.earth, 128, 128);
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            //get our current location
            Location last = null;
            double distance = 0;
            if (locationManager != null) {
                for (String provider : locationManager.getProviders(true)) {
                    Location loc = locationManager.getLastKnownLocation(provider);
                    if (loc != null && (last == null || loc.getTime() > last.getTime())) {
                        last = loc;
                    }
                }
                Location endPoint = new Location("location1");
                endPoint.setLatitude(38.53760);
                endPoint.setLongitude(-8.88856);
                //calc diff between 2 points
                distance = (last != null ? last.distanceTo(endPoint) : 0) / 1000;
            }
            //GERAR OS CARRINHOS E MOSTRAR AS INFORMAÇÕES
            mMap.addMarker(new MarkerOptions().position(new LatLng(38.58427906448935, -8.981059590049233)).title("teste").icon(icon)
                    .snippet(String.format(Locale.getDefault(), "%.02f", distance) + getResources().getString(R.string.km_to)))
                    .setTag("Nº 5\nStatus: " + Status.AVAILABLE + "\nEmpresa: UNICEF\n");
            mMap.addMarker(new MarkerOptions().position(new LatLng(41.139038170411425, -8.567853473666773)).title("Inundação").icon(wave)
                    .snippet(String.format(Locale.getDefault(), "%.02f", distance) + getResources().getString(R.string.km_to)))
                    .setTag("Nº 12\nStatus: Activo \nEmpresa: UNICEF\n");
            mMap.addMarker(new MarkerOptions().position(new LatLng(40.34092670401685, -7.357334153919241)).title("Incendio").icon(fire)
                    .snippet(String.format(Locale.getDefault(), "%.02f", distance) + getResources().getString(R.string.km_to)))
                    .setTag("Nº 7\nStatus: Activo \nEmpresa: CARE\n");
            mMap.addMarker(new MarkerOptions().position(new LatLng(37.229745012357505, -8.177926579832123)).title("Terramoto").icon(earth)
                    .snippet(String.format(Locale.getDefault(), "%.02f", distance) + getResources().getString(R.string.km_to)))
                    .setTag("Nº 21\nStatus: Activo \nEmpresa: CARE\n");

            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 14));
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            };
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            assert locationManager != null;
            locationManager.requestSingleUpdate(criteria, locationListener, null);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_item_one: {
                Intent profileActivity = new Intent(MainActivity.this, ProfileActivity.class);
                profileActivity.putExtra("string", getResources().getText(R.string.profile));
                startActivity(profileActivity);
                break;
            }
            case R.id.nav_item_two: {
                Toast.makeText(this, getResources().getText(R.string.current), Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.nav_item_three: {
                Intent historyActivity = new Intent(MainActivity.this, FreshNewsActivity.class);
                startActivity(historyActivity);
                break;
            }
            case R.id.nav_item_four: {
                Intent commentActivity = new Intent(MainActivity.this, DonationsActivity.class);
                startActivity(commentActivity);
                break;
            }
            case R.id.nav_item_five: {
                Intent settingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsActivity);
                break;
            }
            case R.id.nav_item_six: {
                Toast.makeText(this, getResources().getString(R.string.logout), Toast.LENGTH_SHORT).show();
                onDestroy();
                Intent loginActivity = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginActivity);
                break;
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

}
