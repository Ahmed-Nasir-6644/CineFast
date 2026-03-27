package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SeatSelectionActivity extends AppCompatActivity {

    private String movieName;
    private int moviePosterResId;
    private int selectedSeatsCount = 0;
    private final int SEAT_PRICE = 16;
    private TextView tvMovieName;
    private Button btnToSnacks, btnBookDirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        movieName = getIntent().getStringExtra("MOVIE_NAME");
        moviePosterResId = getIntent().getIntExtra("MOVIE_POSTER_RES_ID", 0);
        if (movieName == null) movieName = "Oppenheimer";

        tvMovieName = findViewById(R.id.tvMovieName);
        tvMovieName.setText(movieName);

        btnToSnacks = findViewById(R.id.btnToSnacks);
        btnBookDirect = findViewById(R.id.btnBookDirect);
        GridLayout seatGrid = findViewById(R.id.seatGrid);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Create the grid: 9 rows, 7 columns (3 left, 1 gap, 3 right)
        int rows = 9;
        int cols = 7;
        seatGrid.setRowCount(rows);
        seatGrid.setColumnCount(cols);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (c == 3) { // Gap column
                    View gap = new View(this);
                    GridLayout.LayoutParams gapParams = new GridLayout.LayoutParams();
                    gapParams.width = 40;
                    gapParams.height = 40;
                    gap.setLayoutParams(gapParams);
                    seatGrid.addView(gap);
                    continue;
                }

                View seat = new View(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 70; // Larger seats to match Figma
                params.height = 70;
                params.setMargins(10, 10, 10, 10);
                seat.setLayoutParams(params);

                // Simulate Figma's booked seats
                boolean isBooked = false;
                if ((r == 1 && c == 0) || (r == 4 && c == 1) || (r == 4 && c == 2) ||
                    (r == 5 && c == 1) || (r == 1 && c == 5) || (r == 6 && c == 5) || (r == 6 && c == 6)) {
                    isBooked = true;
                }

                if (isBooked) {
                    seat.setBackground(ContextCompat.getDrawable(this, R.drawable.seat_booked_new));
                    seat.setEnabled(false);
                } else {
                    seat.setBackground(ContextCompat.getDrawable(this, R.drawable.seat_available_new));
                    seat.setOnClickListener(new View.OnClickListener() {
                        boolean isSelected = false;
                        @Override
                        public void onClick(View v) {
                            if (isSelected) {
                                v.setBackground(ContextCompat.getDrawable(SeatSelectionActivity.this, R.drawable.seat_available_new));
                                selectedSeatsCount--;
                            } else {
                                v.setBackground(ContextCompat.getDrawable(SeatSelectionActivity.this, R.drawable.seat_selected_new));
                                selectedSeatsCount++;
                            }
                            isSelected = !isSelected;
                            updateButtons();
                        }
                    });
                }
                seatGrid.addView(seat);
            }
        }

        btnToSnacks.setOnClickListener(v -> {
            if (selectedSeatsCount > 0) {
                Intent intent = new Intent(SeatSelectionActivity.this, SnacksActivity.class);
                intent.putExtra("MOVIE_NAME", movieName);
                intent.putExtra("MOVIE_POSTER_RES_ID", moviePosterResId);
                intent.putExtra("SEATS_COUNT", selectedSeatsCount);
                intent.putExtra("TICKET_PRICE", selectedSeatsCount * SEAT_PRICE);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please select at least one seat", Toast.LENGTH_SHORT).show();
            }
        });

        btnBookDirect.setOnClickListener(v -> {
            if (selectedSeatsCount > 0) {
                Intent intent = new Intent(SeatSelectionActivity.this, TicketSummaryActivity.class);
                intent.putExtra("MOVIE_NAME", movieName);
                intent.putExtra("MOVIE_POSTER_RES_ID", moviePosterResId);
                intent.putExtra("SEATS_COUNT", selectedSeatsCount);
                intent.putExtra("TICKET_PRICE", selectedSeatsCount * SEAT_PRICE);
                intent.putExtra("SNACKS_PRICE", 0.0);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please select at least one seat", Toast.LENGTH_SHORT).show();
            }
        });

        updateButtons();
    }

    private void updateButtons() {
        boolean hasSelection = selectedSeatsCount > 0;
        btnToSnacks.setEnabled(hasSelection);
        btnBookDirect.setEnabled(hasSelection);

        // Adjust alpha to visually show if buttons are disabled
        btnToSnacks.setAlpha(hasSelection ? 1.0f : 0.5f);
        btnBookDirect.setAlpha(hasSelection ? 1.0f : 0.5f);
    }
}
