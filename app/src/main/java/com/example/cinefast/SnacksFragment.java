package com.example.cinefast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class SnacksFragment extends Fragment {

    private Movie selectedMovie;
    private int selectedSeatsCount;
    private List<Snack> snackList;

    public static SnacksFragment newInstance(Movie movie, int seatsCount) {
        SnacksFragment fragment = new SnacksFragment();
        Bundle args = new Bundle();
        args.putSerializable("movie", movie);
        args.putInt("seats", seatsCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_snacks, container, false);

        if (getArguments() != null) {
            selectedMovie = (Movie) getArguments().getSerializable("movie");
            selectedSeatsCount = getArguments().getInt("seats");
        }

        ListView listView = view.findViewById(R.id.listViewSnacks);
        snackList = getSnacks();
        SnackAdapter adapter = new SnackAdapter(requireContext(), snackList);
        listView.setAdapter(adapter);

        Button btnConfirm = view.findViewById(R.id.btnConfirmSnacks);
        btnConfirm.setOnClickListener(v -> {
            double snackTotal = 0;
            for (Snack s : snackList) {
                snackTotal += s.getPrice() * s.getQuantity();
            }
            TicketSummaryFragment fragment = TicketSummaryFragment.newInstance(selectedMovie, selectedSeatsCount, snackTotal);
            ((MainActivity) requireActivity()).navigateToFragment(fragment, true);
        });

        return view;
    }

    private List<Snack> getSnacks() {
        List<Snack> list = new ArrayList<>();
        list.add(new Snack("Popcorn", R.drawable.popcorn, 8.99));
        list.add(new Snack("Nachos", R.drawable.nacho, 7.99));
        list.add(new Snack("Soft Drink", R.drawable.pepsi, 5.99));
        list.add(new Snack("Candy Mix", R.drawable.candy, 6.99));
        return list;
    }
}
