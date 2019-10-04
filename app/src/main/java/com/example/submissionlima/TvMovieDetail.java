package com.example.submissionlima;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.submissionlima.db.DatabaseMovieContract;

import java.util.ArrayList;

public class TvMovieDetail extends AppCompatActivity implements View.OnClickListener {
    private Button btnFav1, btnFav2;
    public static final String EXTRA_NOTE = "extra_note";
    public static final String EXTRA_POSITION = "extra_position";
    private boolean isEdit = false;
    private TvMovie movieItemDatabase, movie;
    private int position;
    private TvMovieHelper tvMovieHelper;

    private ArrayList<TvMovie> movies = new ArrayList<>();
    private Context context;
    ImageView imgPhoto;
    TextView tvName, tvRemarks, tvRemarks2, tvDescription;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_movie_detail);

        loading = findViewById(R.id.progressBar);
        btnFav1 = findViewById(R.id.btn_item_favorite1);
        btnFav2 = findViewById(R.id.btn_item_favorite2);
        tvName = findViewById(R.id.tv_item_name);
        imgPhoto = findViewById(R.id.img_item_photo);
        tvRemarks = findViewById(R.id.tv_item_remarks);
        tvRemarks2 = findViewById(R.id.tv_item_remarks2);
        tvDescription = findViewById(R.id.tv_item_description);
        loading.setVisibility(View.VISIBLE);
        imgPhoto.setVisibility(View.GONE);
        btnFav1.setVisibility(View.GONE);
        btnFav2.setVisibility(View.GONE);
        tvName.setVisibility(View.GONE);
        tvRemarks.setVisibility(View.GONE);
        tvRemarks2.setVisibility(View.GONE);
        tvDescription.setVisibility(View.GONE);

        if (savedInstanceState == null){
            loading.setVisibility(View.GONE);
            imgPhoto.setVisibility(View.VISIBLE);
            btnFav1.setVisibility(View.VISIBLE);
            btnFav2.setVisibility(View.GONE);
            tvName.setVisibility(View.VISIBLE);
            tvRemarks.setVisibility(View.VISIBLE);
            tvRemarks2.setVisibility(View.VISIBLE);
            tvDescription.setVisibility(View.VISIBLE);
        } else {
            loading.setVisibility(View.GONE);
            imgPhoto.setVisibility(View.VISIBLE);
            btnFav1.setVisibility(View.VISIBLE);
            btnFav2.setVisibility(View.GONE);
            tvName.setVisibility(View.VISIBLE);
            tvRemarks.setVisibility(View.VISIBLE);
            tvRemarks2.setVisibility(View.VISIBLE);
            tvDescription.setVisibility(View.VISIBLE);
        }

        movie = getIntent().getParcelableExtra("KEY_EXTRA");
//        String title = movie.getOriginalTitle();
//        tvName.setText(title);
        String name = movie.getOriginalName();
        tvName.setText(name);
//        String release_date = movie.getReleaseDate();
//        tvRemarks2.setText(String.valueOf(release_date));
        String first_date = movie.getFirstAirDate();
        tvRemarks.setText(first_date);
        String overview = movie.getOverview();
        tvDescription.setText(overview);
        btnFav1.setOnClickListener(this);
        btnFav2.setOnClickListener(this);

        String poster_path = "https://image.tmdb.org/t/p/w154" + movie.getPosterPath();
        Glide.with(this).load(poster_path).apply(new RequestOptions().error(R.drawable.ic_camera_alt_black_24dp)).into(imgPhoto);

////Kirim data
        tvMovieHelper = TvMovieHelper.getInstance(getApplicationContext());
        tvMovieHelper.open();
    }
    private void getDataIntent(){
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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



    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_item_favorite1) {
            movieItemDatabase = new TvMovie();
//            movieItemDatabase.setTitle(movie.getTitle());
            movieItemDatabase.setOriginalName(movie.getOriginalName());
            movieItemDatabase.setOverview(movie.getOverview());
            movieItemDatabase.setFirstAirDate(movie.getFirstAirDate());
            movieItemDatabase.setPosterPath(movie.getPosterPath());
//            movieItemDatabase.setReleaseDate(movie.getReleaseDate());
        }

        Cursor cek2 = tvMovieHelper.cekData2(movie.getOriginalName());
        if (cek2.getCount()>0){
            cek2.moveToFirst();
            btnFav1.setVisibility(View.VISIBLE);
            btnFav2.setVisibility(View.GONE);
            tvMovieHelper.deleteNote(cek2.getInt(cek2.getColumnIndex(DatabaseMovieContract.NoteColumns._ID)));
            Toast.makeText(TvMovieDetail.this, "Data berhasil dihapus dari favorite", Toast.LENGTH_SHORT).show();
        }
        else {
            btnFav1.setVisibility(View.GONE);
            btnFav2.setVisibility(View.VISIBLE);
            Long insert = tvMovieHelper.insertNote(movieItemDatabase);
//            movieHelper.insertNote(movieItemDatabase);
            if (insert>0){
                Toast.makeText(TvMovieDetail.this, "Data berhasil ditambahan ke favorite", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
