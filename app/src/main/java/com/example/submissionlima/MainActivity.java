package com.example.submissionlima;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        if (savedInstanceState== null){
            MovieFragment movieFragment = new MovieFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, movieFragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment;
        int id = item.getItemId();

        if (id == R.id.nav_movie) {
            fragment = new MovieFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
        else if (id == R.id.nav_search){
            fragment = new SearchFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_layout,fragment,fragment.getClass().getSimpleName())
                    .commit();
        }
        else if (id == R.id.nav_tvmovie) {
            fragment = new TvMovieFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                    .commit();

        } else if (id == R.id.nav_slideshow) {
            Intent movie_favoriteIntent = new Intent(this, MovieFavorite.class);
            this.startActivity(movie_favoriteIntent);

        }
        else if (id == R.id.nav_tools) {
            Intent tv_movie_favoriteIntent = new Intent(this, TvMovieFavorite.class);
            this.startActivity(tv_movie_favoriteIntent);
        }
        else if (id == R.id.nav_notification){
            Intent notificationIntent = new Intent(this, Notification.class);
            this.startActivity(notificationIntent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
