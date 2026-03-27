package com.example.cinefast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView btnToday = findViewById(R.id.btnToday);
        TextView btnTomorrow = findViewById(R.id.btnTomorrow);

        btnToday.setOnClickListener(v -> {
            btnToday.setBackgroundResource(R.drawable.date_selected_bg);
            btnToday.setTextColor(getResources().getColor(R.color.white));
            btnTomorrow.setBackgroundResource(R.drawable.date_unselected_bg);
            btnTomorrow.setTextColor(getResources().getColor(R.color.light_grey));
        });

        btnTomorrow.setOnClickListener(v -> {
            btnTomorrow.setBackgroundResource(R.drawable.date_selected_bg);
            btnTomorrow.setTextColor(getResources().getColor(R.color.white));
            btnToday.setBackgroundResource(R.drawable.date_unselected_bg);
            btnToday.setTextColor(getResources().getColor(R.color.light_grey));
        });

        // Trailer Buttons - Implicit Intents
        ImageView btnTrailerDarkKnight = findViewById(R.id.btnTrailerDarkKnight);
        btnTrailerDarkKnight.setOnClickListener(v -> openYouTube("https://www.youtube.com/watch?v=EXeTwQWaywY"));

        ImageView btnTrailerInception = findViewById(R.id.btnTrailerInception);
        btnTrailerInception.setOnClickListener(v -> openYouTube("https://www.youtube.com/watch?v=YoHD9XEInc0"));

        ImageView btnTrailerInterstellar = findViewById(R.id.btnTrailerInterstellar);
        btnTrailerInterstellar.setOnClickListener(v -> openYouTube("https://www.youtube.com/watch?v=zSWdZVtXT7E"));

        // Booking Buttons
        Button btnBookDarkKnight = findViewById(R.id.btnBookDarkKnight);
        btnBookDarkKnight.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SeatSelectionActivity.class);
            intent.putExtra("MOVIE_NAME", "The Dark Knight");
            intent.putExtra("MOVIE_POSTER_RES_ID", R.drawable.dnk);
            startActivity(intent);
        });

        Button btnBookInception = findViewById(R.id.btnBookInception);
        btnBookInception.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SeatSelectionActivity.class);
            intent.putExtra("MOVIE_NAME", "Inception");
            intent.putExtra("MOVIE_POSTER_RES_ID", R.drawable.ic);
            startActivity(intent);
        });

        Button btnBookInterstellar = findViewById(R.id.btnBookInterstellar);
        btnBookInterstellar.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SeatSelectionActivity.class);
            intent.putExtra("MOVIE_NAME", "Interstellar");
            intent.putExtra("MOVIE_POSTER_RES_ID", R.drawable.is);
            startActivity(intent);
        });
    }

    private void openYouTube(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
