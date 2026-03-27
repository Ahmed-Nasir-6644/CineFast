package com.example.cinefast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MovieListFragment extends Fragment {

    private static final String ARG_NOW_SHOWING = "now_showing";

    public static MovieListFragment newInstance(boolean isNowShowing) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_NOW_SHOWING, isNowShowing);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        boolean isNowShowing = getArguments() != null && getArguments().getBoolean(ARG_NOW_SHOWING);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewMovies);

        List<Movie> movies = getMovies(isNowShowing);
        MovieAdapter adapter = new MovieAdapter(movies, new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onBookClick(Movie movie) {
                SeatSelectionFragment fragment = SeatSelectionFragment.newInstance(movie);
                ((MainActivity) requireActivity()).navigateToFragment(fragment, true);
            }

            @Override
            public void onTrailerClick(Movie movie) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getTrailerUrl()));
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<Movie> getMovies(boolean isNowShowing) {
        List<Movie> list = new ArrayList<>();
        if (isNowShowing) {
            list.add(new Movie("The Dark Knight", R.drawable.dnk, "Action", "152 min", "https://www.youtube.com/watch?v=EXeTwQWaywY", true));
            list.add(new Movie("Inception", R.drawable.ic, "Sci-Fi", "148 min", "https://www.youtube.com/watch?v=YoHD9XEInc0", true));
            list.add(new Movie("Interstellar", R.drawable.is, "Sci-Fi", "169 min", "https://www.youtube.com/watch?v=zSWdZVtXT7E", true));
        } else {
            list.add(new Movie("Dune: Part Two", R.drawable.dnk, "Sci-Fi", "166 min", "https://www.youtube.com/watch?v=Way9Dexny3w", false));
            list.add(new Movie("Deadpool & Wolverine", R.drawable.ic, "Action", "127 min", "https://www.youtube.com/watch?v=73_1biulkYk", false));
            list.add(new Movie("Joker: Folie à Deux", R.drawable.is, "Drama", "138 min", "https://www.youtube.com/watch?v=_OKAwz2MsJs", false));
        }
        return list;
    }
}
