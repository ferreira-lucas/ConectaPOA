package thiagocury.eti.br.conectapoa;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Wifi> wf;
    private RecyclerView rvWifi;
    private WifiAdapter adapter;
    private Toolbar toolbar;
    private Drawer result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        toolbar.setTitle(getResources().getString(R.string.redes_wifi));

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.banner)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(getResources().getString(R.string.redes_wifi)).withIdentifier(0).withIcon(R.drawable.ic_wifi_black_24dp),
                        new PrimaryDrawerItem().withName(getResources().getString(R.string.mapa)).withIdentifier(1).withIcon(R.drawable.ic_place_black_24dp),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(getResources().getString(R.string.sobre)).withIdentifier(2).withIcon(R.drawable.ic_info_black_24dp),
                        new PrimaryDrawerItem().withName(getResources().getString(R.string.sair)).withIdentifier(3).withIcon(R.drawable.ic_exit_to_app)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        switch ((int)drawerItem.getIdentifier()){
                            case 0:

                                break;

                            case 1:
                                Intent it = new Intent(MainActivity.this,MapsActivity.class);
                                startActivity(it);
                                break;

                            case 2:
                                Intent sobre = new Intent(MainActivity.this,SobreActivity.class);
                                startActivity(sobre);
                                break;
                            case 3:
                                finish();
                        }
                        return false;
                    }
                }).build();
        result.setSelection(0);

        rvWifi = findViewById(R.id.ma_rv_wifi);

        adapter = new WifiAdapter(MainActivity.this,new ArrayList<Wifi>(0));
        rvWifi.setAdapter(adapter);
        rvWifi.setHasFixedSize(true);
        rvWifi.setLayoutManager(new LinearLayoutManager(this));

        Gson g = new GsonBuilder().registerTypeAdapter(
                Wifi.class,
                new WifiDeserializer()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIRetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(g))
                .build();

        final APIRetrofitService service = retrofit.create(APIRetrofitService.class);

        Call<List<Wifi>> wifiCall = service.getWifiCall();

        wifiCall.enqueue(new Callback<List<Wifi>>() {
            @Override
            public void onResponse(Call<List<Wifi>> call, Response<List<Wifi>> response) {
                List<Wifi> wifiAux = response.body();

                wf = new ArrayList<>();

                for (Wifi wifi : wifiAux){
                    wf.add(wifi);
                }

                adapter.setWf(wf);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Wifi>> call, Throwable t) {
                Toast.makeText(
                        getBaseContext(),
                        getResources().getString(R.string.erro)+" "+t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }

        });

        adapter.setOnItemClickListener(new WifiAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        result.setSelection(0);
        if(getIntent().getBooleanExtra("SAIR",false)){
            finish();
        }
    }
}
