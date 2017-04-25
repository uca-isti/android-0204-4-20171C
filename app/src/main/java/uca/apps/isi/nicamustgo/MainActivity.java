package uca.apps.isi.nicamustgo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uca.apps.isi.nicamustgo.api.Api;
import uca.apps.isi.nicamustgo.api.ApiInterface;
import uca.apps.isi.nicamustgo.fragments.CentrosCulturalesFragment;
import uca.apps.isi.nicamustgo.fragments.ConfigFragment;
import uca.apps.isi.nicamustgo.fragments.EntretenimientoFragment;
import uca.apps.isi.nicamustgo.fragments.FolkloreFragment;
import uca.apps.isi.nicamustgo.fragments.HomeFragment;
import uca.apps.isi.nicamustgo.fragments.NosFragment;
import uca.apps.isi.nicamustgo.fragments.ServiciosFragment;
import uca.apps.isi.nicamustgo.fragments.SitiosNaturalesFragment;
import uca.apps.isi.nicamustgo.models.Categoria;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        private final static String TAG ="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();



            }
        });

        //xf{gl
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.getBase())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface=retrofit.create(ApiInterface.class);

                /*Categoria categoria = new Categoria();
                categoria.setNombre("Hospedaje2");
                Call<Categoria> categoriaCall=ApiInterface.createCategoria(categoria)*/

        //Call<List<Categoria>> call = ApiInterface.getCategorias();
        Call<List<Categoria>> call = Api.instance().getCategorias();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if(response!=null)
                {
                    for(Categoria categoria:response.body())
                    {
                        addItem(categoria);
                        Log.i(TAG,categoria.getNombre());
                    }
                }
                else
                {
                    Log.i(TAG,"response es nulo");
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                Log.i(TAG,t.getMessage());
            }
        });

        //sdjfoisj

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
private void addItem(Categoria categoriaItem) {
    Realm realm =Realm.getDefaultInstance();
    realm.beginTransaction();
    Categoria categoria =realm.createObject(Categoria.class);
    categoria.setNombre(categoriaItem.getNombre());

    realm.commitTransaction();
}
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;
        Class fragmentClass = null;

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragmentClass = HomeFragment.class;
        } else if (id == R.id.nav_naturales) {
            fragmentClass = SitiosNaturalesFragment.class;
        } else if (id == R.id.nav_culturales) {
            fragmentClass = CentrosCulturalesFragment.class;
        } else if (id == R.id.nav_folklore) {
            fragmentClass = FolkloreFragment.class;
        } else if (id == R.id.nav_entretenimiento) {
            fragmentClass = EntretenimientoFragment.class;
        } else if (id == R.id.nav_servicios) {
            fragmentClass = ServiciosFragment.class;
        } else if (id == R.id.nav_nosotros) {
            fragmentClass = NosFragment.class;
        } else if (id == R.id.nav_configuracion) {
            fragmentClass = ConfigFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
