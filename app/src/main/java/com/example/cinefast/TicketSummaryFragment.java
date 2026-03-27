package com.example.cinefast;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TicketSummaryFragment extends Fragment {

    private Movie selectedMovie;
    private int seatsCount;
    private double snacksPrice;

    public static TicketSummaryFragment newInstance(Movie movie, int seats, double snacks) {
        TicketSummaryFragment fragment = new TicketSummaryFragment();
        Bundle args = new Bundle();
        args.putSerializable("movie", movie);
        args.putInt("seats", seats);
        args.putDouble("snacks", snacks);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ticket_summary, container, false);

        if (getArguments() != null) {
            selectedMovie = (Movie) getArguments().getSerializable("movie");
            seatsCount = getArguments().getInt("seats");
            snacksPrice = getArguments().getDouble("snacks");
        }

        TextView tvMovieName = view.findViewById(R.id.tvMovieName);
        ImageView ivPoster = view.findViewById(R.id.ivMoviePoster);
        TextView tvSeatsDetail = view.findViewById(R.id.tvSeatsDetail);
        TextView tvTicketPrice = view.findViewById(R.id.tvTicketPriceValue);
        TextView tvSnacksPrice = view.findViewById(R.id.tvSnacksPriceValue);
        TextView tvTotal = view.findViewById(R.id.tvTotalValue);

        if (selectedMovie != null) {
            tvMovieName.setText(selectedMovie.getName());
            ivPoster.setImageResource(selectedMovie.getPosterResId());
        }

        tvSeatsDetail.setText(seatsCount + " Seats Selected");
        double ticketTotal = seatsCount * 12.0; // Assume $12 per seat
        tvTicketPrice.setText(ticketTotal + " USD");
        tvSnacksPrice.setText(snacksPrice + " USD");
        
        double grandTotal = ticketTotal + snacksPrice;
        tvTotal.setText(grandTotal + " USD");

        // Save to SharedPreferences
        saveBooking(selectedMovie.getName(), seatsCount, (float) grandTotal);

        view.findViewById(R.id.btnBack).setOnClickListener(v -> requireActivity().onBackPressed());
        view.findViewById(R.id.btnSendTicket).setOnClickListener(v -> {
            // Logic for sending ticket (Assignment 1 logic)
            requireActivity().getSupportFragmentManager().popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
        });

        return view;
    }

    private void saveBooking(String movieName, int seats, float totalPrice) {
        SharedPreferences prefs = requireContext().getSharedPreferences("CineFastPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("last_movie", movieName);
        editor.putInt("last_seats", seats);
        editor.putFloat("last_price", totalPrice);
        editor.apply();
    }
}
