package com.example.cinefast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SeatSelectionFragment extends Fragment {

    private Movie selectedMovie;
    private int selectedSeatsCount = 0;
    private GridLayout seatGrid;

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
        seatGrid = view.findViewById(R.id.seatGrid);

        setupSeats();

        if (selectedMovie != null && !selectedMovie.isNowShowing()) {
            // Coming Soon Case
            btnBookDirect.setText("Coming Soon");
            btnBookDirect.setEnabled(false);
            
            btnToSnacks.setText("Watch Trailer");
            btnToSnacks.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(selectedMovie.getTrailerUrl()));
                startActivity(intent);
            });

            // Disable seat selection
            for (int i = 0; i < seatGrid.getChildCount(); i++) {
                seatGrid.getChildAt(i).setEnabled(false);
            }
            seatGrid.setAlpha(0.5f);
        } else {
            // Now Showing Case
            btnBookDirect.setOnClickListener(v -> {
                if (selectedSeatsCount > 0) {
                    Toast.makeText(getContext(), "Booking Confirmed!", Toast.LENGTH_SHORT).show();
                    TicketSummaryFragment fragment = TicketSummaryFragment.newInstance(selectedMovie, selectedSeatsCount, 0);
                    ((MainActivity) requireActivity()).navigateToFragment(fragment, true);
                } else {
                    Toast.makeText(getContext(), "Please select at least one seat", Toast.LENGTH_SHORT).show();
                }
            });

            btnToSnacks.setOnClickListener(v -> {
                if (selectedSeatsCount > 0) {
                    SnacksFragment fragment = SnacksFragment.newInstance(selectedMovie, selectedSeatsCount);
                    ((MainActivity) requireActivity()).navigateToFragment(fragment, true);
                } else {
                    Toast.makeText(getContext(), "Please select at least one seat", Toast.LENGTH_SHORT).show();
                }
            });
        }

        view.findViewById(R.id.btnBack).setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }

    private void setupSeats() {
        int rows = 8;
        int cols = 7;
        
        // Define seat dimensions in pixels (using a smaller value)
        int seatSize = (int) (40 * getResources().getDisplayMetrics().density); // 40dp
        int seatMargin = (int) (4 * getResources().getDisplayMetrics().density); // 4dp

        boolean canHaveBooked = (selectedMovie != null && selectedMovie.isNowShowing());

        for (int i = 0; i < rows * cols; i++) {
            ImageView seat = new ImageView(getContext());
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = seatSize;
            params.height = seatSize;
            params.setMargins(seatMargin, seatMargin, seatMargin, seatMargin);
            seat.setLayoutParams(params);
            seat.setPadding(4, 4, 4, 4); // Internal padding for icons
            
            // For Coming Soon movies, all seats should be available (but disabled in onCreateView)
            if (canHaveBooked && Math.random() > 0.8) {
                seat.setImageResource(R.drawable.seat_booked_new);
                seat.setTag("booked");
            } else {
                seat.setImageResource(R.drawable.seat_available_new);
                seat.setTag("available");
            }
            
            seat.setOnClickListener(v -> {
                String tag = (String) v.getTag();
                if ("available".equals(tag)) {
                    ((ImageView)v).setImageResource(R.drawable.seat_selected_new);
                    v.setTag("selected");
                    selectedSeatsCount++;
                } else if ("selected".equals(tag)) {
                    ((ImageView)v).setImageResource(R.drawable.seat_available_new);
                    v.setTag("available");
                    selectedSeatsCount--;
                }
            });
            
            seatGrid.addView(seat);
        }
    }
}
