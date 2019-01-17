package com.rajatv.surajv.roshank.sac.NavDrawerActivities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

import com.rajatv.surajv.roshank.sac.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

        LatLng SAC_building = new LatLng(25.6193583, 85.1720750);
        LatLng Cafeteria = new LatLng(25.6191857, 85.1720269);
        LatLng MainBuilding = new LatLng(25.6207500, 85.1719484);
        LatLng CivilDepartment = new LatLng(25.6212939, 85.1722132);
        LatLng Computercentre = new LatLng(25.6213556, 85.1727258);
        LatLng TennisCourt = new LatLng(25.6209605, 85.1726816);
        LatLng Director_bungalow = new LatLng(25.6213756, 85.1732707);
        LatLng GuestHouse = new LatLng(25.6210010, 85.1728988);
        LatLng mechanicalWorkshop = new LatLng(25.6206688, 85.1730678);
        LatLng GangaGirlsHostel = new LatLng(25.6210509, 85.1740468);
        LatLng CSEdepartment = new LatLng(25.6205914, 85.1742185);
        LatLng kosi_hostel = new LatLng(25.6206004, 85.1745043);
        LatLng CWRS = new LatLng(25.6194965, 85.1739721);
        LatLng library = new LatLng(25.6205615, 85.1715333);
        LatLng CanteenGopalJi = new LatLng(25.6204324, 85.1716841);
        LatLng CanteenShuklaJi = new LatLng(25.6210593, 85.1717103);
        LatLng ElectricalDepartment = new LatLng(25.6210615, 85.1706243);
        LatLng MechanicalDepartment = new LatLng(25.6212746, 85.1706508);
        LatLng NewElectricalDepartment = new LatLng(25.6206310, 85.1710297);
        LatLng ElectronicsDepartment = new LatLng(25.6204114, 85.1736354);
        LatLng PhysicsDepartment = new LatLng(25.6204414, 85.1734081);
        LatLng NITP_MainGround = new LatLng(25.6200502, 85.1726082);
        LatLng SonA_hostel = new LatLng(25.6181565, 85.1719772);
        LatLng sonB_hostel = new LatLng(25.6185166, 85.1719802);


        mMap.addMarker(new MarkerOptions().position(SAC_building).title("Student Activity Centre, NIT Patna"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(SAC_building));

        mMap.addMarker(new MarkerOptions().position(Cafeteria).title("Cafeteria"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(Cafeteria));


        mMap.addMarker(new MarkerOptions().position(CivilDepartment).title("Civil Department"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(CivilDepartment));

        mMap.addMarker(new MarkerOptions().position(Computercentre).title("Computer Centre"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(Computercentre));

        mMap.addMarker(new MarkerOptions().position(TennisCourt).title("Tennis Court"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(TennisCourt));

        mMap.addMarker(new MarkerOptions().position(Director_bungalow).title("Director's Bungalow"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(Cafeteria));

        mMap.addMarker(new MarkerOptions().position(GuestHouse).title("Guest House"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(GuestHouse));

        mMap.addMarker(new MarkerOptions().position(mechanicalWorkshop).title("Mechanical Workshop"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(mechanicalWorkshop));

        mMap.addMarker(new MarkerOptions().position(GangaGirlsHostel).title("Ganga Girls Hostel"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(GangaGirlsHostel));

        mMap.addMarker(new MarkerOptions().position(CSEdepartment).title("CSE Department"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(CSEdepartment));

        mMap.addMarker(new MarkerOptions().position(kosi_hostel).title("Kosi Hostel"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(kosi_hostel));

        mMap.addMarker(new MarkerOptions().position(CWRS).title("CWRS Building"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(CWRS));

        mMap.addMarker(new MarkerOptions().position(library).title("Library"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(library));

        mMap.addMarker(new MarkerOptions().position(CanteenGopalJi).title("Gopal Ji Canteen"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(CanteenGopalJi));

        mMap.addMarker(new MarkerOptions().position(CanteenShuklaJi).title("Shukla Ji Canteen"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(CanteenShuklaJi));

        mMap.addMarker(new MarkerOptions().position(ElectricalDepartment).title("Electrical Department"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(ElectricalDepartment));

        mMap.addMarker(new MarkerOptions().position(MechanicalDepartment).title("Mechanical Department"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(MechanicalDepartment));

        mMap.addMarker(new MarkerOptions().position(NewElectricalDepartment).title("New Electrical Department"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(NewElectricalDepartment));

        mMap.addMarker(new MarkerOptions().position(ElectronicsDepartment).title("Electronics Department"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(ElectronicsDepartment));

        mMap.addMarker(new MarkerOptions().position(PhysicsDepartment).title("Physics Department"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(PhysicsDepartment));

        mMap.addMarker(new MarkerOptions().position(NITP_MainGround).title("NITP Main Ground"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(NITP_MainGround));

        mMap.addMarker(new MarkerOptions().position(SonA_hostel).title("Sone A Hostel"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(SonA_hostel));

        mMap.addMarker(new MarkerOptions().position(sonB_hostel).title("Sone B Hostel"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sonB_hostel));

        mMap.addMarker(new MarkerOptions().position(MainBuilding).title("Main Building")).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN) );
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(MainBuilding));

        //CameraPosition cameraPosition = new CameraPosition.Builder().target(MainBuilding).zoom(18).build();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MainBuilding, 18.0f));
        // mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
    }


}
