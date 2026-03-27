package com.example.cinefast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SeatSelectionFragment extends Fragment {

    private Movie selectedMovie;
    private int selectedSeatsCount = 2; // Hardcoded for simplicity as in Assignment 1

    public static SeatSelectionFragment newInstance(Movie movie) {
        SeatSelectionFragment fragment = new SeatSelectionFragment();
        Bundle args = new Bundle();
        args.putSerializable("movie", movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_seat_selection, container, false);

        if (getArguments() != null) {
            selectedMovie = (Movie) getArguments().getSerializable("movie");
        }

        TextView tvMovieName = view.findViewById(R.id.tvMovieName);
        if (selectedMovie != null) {
            tvMovieName.setText(selectedMovie.getName());
        }

        Button btnBookDirect = view.findViewById(R.id.btnBookDirect);
        Button btnToSnacks = view.findViewById(R.id.btnToSnacks);
        View seatGrid = view.findViewById(R.id.seatGrid);

        if (selectedMovie != null && !selectedMovie.isNowShowing()) {
            // Coming Soon Case
            btnBookDirect.setText("Coming Soon");
            btnBookDirect.setEnabled(false);
            
            btnToSnacks.setText("Watch Trailer");
            btnToSnacks.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(selectedMovie.getTrailerUrl()));
                startActivity(intent);
            });

            // Disable seat selection (simplified: making grid not clickable)
            seatGrid.setClickable(false);
            seatGrid.setAlpha(0.5f);
        } else {
            // Now Showing Case
            btnBookDirect.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Booking Confirmed!", Toast.LENGTH_SHORT).show();
                TicketSummaryFragment fragment = TicketSummaryFragment.newInstance(selectedMovie, selectedSeatsCount, 0);
                ((MainActivity) requireActivity()).navigateToFragment(fragment, true);
            });

            btnToSnacks.setOnClickListener(v -> {
                SnacksFragment fragment = SnacksFragment.newInstance(selectedMovie, selectedSeatsCount);
                ((MainActivity) requireActivity()).navigateToFragment(fragment, true);
            });
        }

        view.findViewById(R.id.btnBack).setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }
}
