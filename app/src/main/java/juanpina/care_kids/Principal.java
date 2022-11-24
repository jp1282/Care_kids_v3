package juanpina.care_kids;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.ArrayList;
import java.util.List;

import juanpina.care_kids.R;


public class Principal extends AppCompatActivity {
    Button button6, btnubicacion;

    private MapView mapView;
    String nmbct1, nmbct2, nroct1, nroct2;
    String mensaje, nromensaje;
    CheckBox check1, check2;

    String[] contactos = {"Llamame", "S.O.S", "Saliendo del Metro", "Bajando de la Micro", "Ayuda"};
    List<listadoelementos> elements;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_principal);
        Intent in = getIntent();
        String usu = in.getStringExtra("usuario");

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.73581, -73.99155), 12), 12000);
                    }
                });



            }
        });


        //Spinner
        Spinner Contactos = (Spinner) findViewById(R.id.spinnercontacto);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, contactos);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Contactos.setAdapter(aa);

        init(usu);

        check1 = (CheckBox) findViewById(R.id.chekNro1);
        check2 = (CheckBox) findViewById(R.id.chekNro2);

        button6 = findViewById(R.id.btnEnviar);
        button6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mensaje = Contactos.getSelectedItem().toString();
                validar();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                String uri = "whatsapp://send?phone=" + nromensaje + "&text=" + mensaje;
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });

        /*  Ubicacion   */

        btnubicacion = findViewById(R.id.btngps);
        btnubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Principal.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Principal.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Principal.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
                } else {

                    //public void locationStart() {
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        Criteria criteria = new Criteria();

                        if (ActivityCompat.checkSelfPermission(Principal.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Principal.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

                        double latitud = location.getLatitude();
                        double longitud = location.getLongitude();


                        Toast.makeText(Principal.this,"ubicacion." + latitud + longitud,Toast.LENGTH_LONG).show();

                        mapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                                    @Override
                                    public void onStyleLoaded(@NonNull Style style) {

                                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                                        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 15), 12000);
                                    }
                                });



                            }
                        });

                }


            }
        });

    }




   public void init(String usuario){
    try{
    SQLiteDatabase db = openOrCreateDatabase("BD_Usuarios", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS usuario(nombre VARCHAR,clave VARCHAR, nmbContacto1 VARCHAR, nroContacto1 VARCHAR, nmbContacto2 VARCHAR, nroContacto2 VARCHAR)");
        final Cursor c = db.rawQuery("select * from usuario where nombre=?", new String[]{String.valueOf(usuario)});
        c.moveToLast();

    int nombrect1 = c.getColumnIndex("nmbContacto1");
    int nombrect2 = c.getColumnIndex("nmbContacto2");
    int numeroct1 = c.getColumnIndex("nroContacto1");
    int numeroct2 = c.getColumnIndex("nroContacto2");

    nmbct1=c.getString(nombrect1).toString();
    nmbct2=c.getString(nombrect2).toString();
    nroct1=c.getString(numeroct1).toString();
    nroct2=c.getString(numeroct2).toString();

    elements = new ArrayList<>();
    elements.add(new listadoelementos("black",nmbct1,nroct1));
    elements.add(new listadoelementos("black",nmbct2,nroct2));


    listadoadaptador listado = new listadoadaptador(elements, this);
    RecyclerView recycler = findViewById(R.id.recycler1);
    recycler.setHasFixedSize(true);
    recycler.setLayoutManager(new LinearLayoutManager(this));
    recycler.setAdapter(listado);
        }catch (Exception ex){
                Toast.makeText(this,"No se encontraron los datos.",Toast.LENGTH_LONG).show();
            }
        }

        private void validar(){
        if (check1.isChecked()){
        nromensaje=nroct1;
        }
        if (check2.isChecked()){
        nromensaje=nroct2;
        }
    }

        @Override
        protected void onStart() {
            super.onStart();
            mapView.onStart();

        }

        @Override
        protected void onResume() {
            super.onResume();
            mapView.onResume();
        }

        @Override
        protected void onPause() {
            super.onPause();
            mapView.onPause();
        }

        @Override
        protected void onStop() {
            super.onStop();
            mapView.onStop();
        }

        @Override
        protected void onSaveInstanceState(@NonNull Bundle outState) {
            super.onSaveInstanceState(outState);
            mapView.onSaveInstanceState(outState);
        }

        @Override
        public void onLowMemory() {
            super.onLowMemory();
            mapView.onLowMemory();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            mapView.onDestroy();
        }
}


