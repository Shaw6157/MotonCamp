package com.ais.mnc.view.campsite;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.ais.mnc.R;
import com.ais.mnc.db.bean.CampBean;
import com.ais.mnc.db.dao.CampsiteDao;
import com.ais.mnc.db.daoimp.CampsiteDaoImp;
import com.ais.mnc.util.MncUtilities;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

public class CsMapActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = "CsMapActivity >>> ";

    private GoogleMap mMap;

    FusedLocationProviderClient mClient;
    LocationCallback mCallback;
    LocationRequest mRequest;

    CampsiteDao mCampsiteDao;
    List<CampBean> mCpList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campsite_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Log.d(TAG, "================  start mapppp : ");

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Log.d(TAG, "================  list got");
                            buildLocationRequest();
                            buildLocationCallBack();

                            mClient = LocationServices.getFusedLocationProviderClient(CsMapActivity.this);

                            //update location
                            if (ActivityCompat.checkSelfPermission(
                                    CsMapActivity.this,
                                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                    && ActivityCompat.checkSelfPermission(
                                    CsMapActivity.this,
                                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            mClient.requestLocationUpdates(mRequest, mCallback, Looper.myLooper());

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        MncUtilities.toastMessage(CsMapActivity.this, "Permission Denied, plz check.");
                    }
                }).check();

        mCampsiteDao = new CampsiteDaoImp(this);

    }

    private void buildLocationRequest() {
        mRequest = new LocationRequest();
        mRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mRequest.setInterval(5000);
        mRequest.setFastestInterval(3000);
        mRequest.setSmallestDisplacement(10f);
    }

    private void buildLocationCallBack() {
        mCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                Log.d(TAG, "================  call bak started");
                //multi thread to init Markers on GG map
//                Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.d(TAG, "================  threading... ");
//                        getCampsiteAround();
//                    }
//                });
//                thread.start();

                Log.d(TAG, "================  my location here");
                // Add a marker in Auckland
//                LatLng auckland = new LatLng(-36.8, 174.8);
                //-36.848460, 174.762100
                LatLng userLocation = new LatLng(locationResult.getLastLocation().getLatitude(),
                        locationResult.getLastLocation().getLongitude());
                mMap.addMarker(new MarkerOptions().position(userLocation).title("You are here."));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 13.0f));

                getCampsiteAround();
            }
        };
    }

    private void getCampsiteAround() {
        if (MncUtilities.currentCampList != null) {
            mCpList = MncUtilities.currentCampList;
        } else {
            mCpList = mCampsiteDao.findAll();
        }

        for (CampBean cp : mCpList) {
            LatLng cpLocation = new LatLng(cp.getLAT(), cp.getLNG());
            Log.d(TAG, "reading ...  " + cp.getCname());

            mMap.addMarker(
                    new MarkerOptions()
                            .position(cpLocation)
                            .title(cp.getCname())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_cplocation)));
        }
    }

    @Override
    protected void onPause() {
        if (mCallback != null) {
            mClient.removeLocationUpdates(mCallback);
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCallback != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mClient.requestLocationUpdates(mRequest, mCallback, Looper.myLooper());
        }
    }

    @Override
    protected void onStop() {
        mClient.removeLocationUpdates(mCallback);
        super.onStop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}
