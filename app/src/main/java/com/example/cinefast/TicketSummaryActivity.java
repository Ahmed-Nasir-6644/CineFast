package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class TicketSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_summary);

        String movieName = getIntent().getStringExtra("MOVIE_NAME");
        int moviePosterResId = getIntent().getIntExtra("MOVIE_POSTER_RES_ID", 0);
        int seatsCount = getIntent().getIntExtra("SEATS_COUNT", 0);
        int ticketPrice = getIntent().getIntExtra("TICKET_PRICE", 0);
        double snacksPrice = getIntent().getDoubleExtra("SNACKS_PRICE", 0.0);
        String snacksDetails = getIntent().getStringExtra("SNACKS_DETAILS");
        double totalPrice = ticketPrice + snacksPrice;

        TextView tvMovieName = findViewById(R.id.tvMovieName);
        ImageView ivMoviePoster = findViewById(R.id.ivMoviePoster);
        TextView tvSeatsDetail = findViewById(R.id.tvSeatsDetail);
        TextView tvTicketPriceValue = findViewById(R.id.tvTicketPriceValue);
        TextView tvSnacksDetail = findViewById(R.id.tvSnacksDetail);
        TextView tvSnacksPriceValue = findViewById(R.id.tvSnacksPriceValue);
        TextView tvTotalValue = findViewById(R.id.tvTotalValue);

        if (movieName != null) {
            tvMovieName.setText(movieName);
        }

        if (moviePosterResId != 0) {
            ivMoviePoster.setImageResource(moviePosterResId);
        }

        tvSeatsDetail.setText("Selected Seats: " + seatsCount);
        tvTicketPriceValue.setText(String.format(Locale.getDefault(), "%d USD", ticketPrice));
        
        if (snacksDetails != null) {
            tvSnacksDetail.setText(snacksDetails);
        }
        tvSnacksPriceValue.setText(String.format(Locale.getDefault(), "%.2f USD", snacksPrice));
        
        tvTotalValue.setText(String.format(Locale.getDefault(), "%.2f USD", totalPrice));

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        Button btnSendTicket = findViewById(R.id.btnSendTicket);
        btnSendTicket.setOnClickListener(v -> {
            String ticketDetails = "🎬 *CineFAST Movie Ticket* 🎬\n\n"
                    + "🍿 *Movie:* " + movieName + "\n"
                    + "🎟️ *Seats:* " + seatsCount + "\n"
                    + "🥤 *Snacks:* " + (snacksDetails != null ? snacksDetails : "None") + "\n"
                    + "💵 *Total Price:* " + String.format(Locale.getDefault(), "%.2f USD", totalPrice) + "\n\n"
                    + "Enjoy your movie! 🎥";

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Your CineFAST Movie Ticket");
            sendIntent.putExtra(Intent.EXTRA_TEXT, ticketDetails);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, "Share Ticket via:");
            startActivity(shareIntent);
        });
    }
}
