package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SnacksActivity extends AppCompatActivity {

    private String movieName;
    private int moviePosterResId;
    private int seatsCount;
    private int ticketPrice;

    private TextView tvQty1, tvQty2, tvQty3, tvQty4;
    private int qty1 = 0, qty2 = 0, qty3 = 0, qty4 = 0;
    private final double PRICE1 = 8.99, PRICE2 = 7.99, PRICE3 = 5.99, PRICE4 = 6.99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snacks);

        movieName = getIntent().getStringExtra("MOVIE_NAME");
        moviePosterResId = getIntent().getIntExtra("MOVIE_POSTER_RES_ID", 0);
        seatsCount = getIntent().getIntExtra("SEATS_COUNT", 0);
        ticketPrice = getIntent().getIntExtra("TICKET_PRICE", 0);

        tvQty1 = findViewById(R.id.tvQty1);
        tvQty2 = findViewById(R.id.tvQty2);
        tvQty3 = findViewById(R.id.tvQty3);
        tvQty4 = findViewById(R.id.tvQty4);

        Button btnMinus1 = findViewById(R.id.btnMinus1);
        Button btnPlus1 = findViewById(R.id.btnPlus1);
        Button btnMinus2 = findViewById(R.id.btnMinus2);
        Button btnPlus2 = findViewById(R.id.btnPlus2);
        Button btnMinus3 = findViewById(R.id.btnMinus3);
        Button btnPlus3 = findViewById(R.id.btnPlus3);
        Button btnMinus4 = findViewById(R.id.btnMinus4);
        Button btnPlus4 = findViewById(R.id.btnPlus4);
        Button btnConfirmSnacks = findViewById(R.id.btnConfirmSnacks);

        btnMinus1.setOnClickListener(v -> updateQuantity(tvQty1, --qty1, 1));
        btnPlus1.setOnClickListener(v -> updateQuantity(tvQty1, ++qty1, 1));
        btnMinus2.setOnClickListener(v -> updateQuantity(tvQty2, --qty2, 2));
        btnPlus2.setOnClickListener(v -> updateQuantity(tvQty2, ++qty2, 2));
        btnMinus3.setOnClickListener(v -> updateQuantity(tvQty3, --qty3, 3));
        btnPlus3.setOnClickListener(v -> updateQuantity(tvQty3, ++qty3, 3));
        btnMinus4.setOnClickListener(v -> updateQuantity(tvQty4, --qty4, 4));
        btnPlus4.setOnClickListener(v -> updateQuantity(tvQty4, ++qty4, 4));

        btnConfirmSnacks.setOnClickListener(v -> {
            double snacksPrice = (qty1 * PRICE1) + (qty2 * PRICE2) + (qty3 * PRICE3) + (qty4 * PRICE4);
            
            StringBuilder snacksDetails = new StringBuilder();
            if (qty1 > 0) snacksDetails.append("Popcorn x").append(qty1).append(", ");
            if (qty2 > 0) snacksDetails.append("Nachos x").append(qty2).append(", ");
            if (qty3 > 0) snacksDetails.append("Soft Drink x").append(qty3).append(", ");
            if (qty4 > 0) snacksDetails.append("Candy Mix x").append(qty4).append(", ");
            
            String snacksString = snacksDetails.toString();
            if (snacksString.endsWith(", ")) {
                snacksString = snacksString.substring(0, snacksString.length() - 2);
            }
            if (snacksString.isEmpty()) snacksString = "None";

            Intent intent = new Intent(SnacksActivity.this, TicketSummaryActivity.class);
            intent.putExtra("MOVIE_NAME", movieName);
            intent.putExtra("MOVIE_POSTER_RES_ID", moviePosterResId);
            intent.putExtra("SEATS_COUNT", seatsCount);
            intent.putExtra("TICKET_PRICE", ticketPrice);
            intent.putExtra("SNACKS_PRICE", snacksPrice);
            intent.putExtra("SNACKS_DETAILS", snacksString);
            startActivity(intent);
        });
    }

    private void updateQuantity(TextView tv, int quantity, int item) {
        if (quantity < 0) {
            switch (item) {
                case 1: qty1 = 0; break;
                case 2: qty2 = 0; break;
                case 3: qty3 = 0; break;
                case 4: qty4 = 0; break;
            }
            return;
        }
        tv.setText(String.valueOf(quantity));
    }
}
