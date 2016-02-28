package vishal.vaf.dce;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import vishal.vaf.dce.fragments.CaesarFragment;
import vishal.vaf.dce.fragments.HomeFragment;
import vishal.vaf.dce.fragments.RSAFragment;
import vishal.vaf.dce.fragments.VignereFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    CaesarFragment caesarFragment;
    VignereFragment vignereFragment;
    RSAFragment rsaFragment;
    CoordinatorLayout coordinatorLayout;

    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpHomeFragment();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        item.setChecked(false);

        if (id == R.id.nav_caesar) {
            caesarFragment = new CaesarFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, caesarFragment)
                    .commit();
            setTitle("Caesar Cipher");
        } else if (id == R.id.nav_vignere) {
            vignereFragment = new VignereFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, vignereFragment)
                    .commit();
            setTitle("Vignere Cipher");
        } else if (id == R.id.home) {
            setUpHomeFragment();
            setTitle("DCE");
        } else if (id == R.id.nav_rsa) {
            rsaFragment = new RSAFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, rsaFragment)
                    .commit();
            setTitle("RSA Algorithm");
        } /*else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setUpHomeFragment() {
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, homeFragment)
                .commit();
    }

    @Override
    public void onStart() {
        super.onStart();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cl);
        if (flag == 0) {
            Snackbar.make(coordinatorLayout, "Welcome to Data Compression and Encryption", Snackbar.LENGTH_LONG)
                    .show();
            flag++;
        }
    }
}
