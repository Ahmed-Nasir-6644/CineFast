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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ComingSoonFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Dune: Part Two", R.drawable.dune, "Sci-Fi", "166 min", "https://www.youtube.com/watch?v=Way9Dexny3w", false));
        movies.add(new Movie("Deadpool & Wolverine", R.drawable.dnw, "Action", "127 min", "https://www.youtube.com/watch?v=73_1biulkYk", false));
        movies.add(new Movie("Joker: Folie à Deux", R.drawable.j, "Drama", "138 min", "https://www.youtube.com/watch?v=_OKAwz2MsJs", false));

        MovieAdapter adapter = new MovieAdapter(movies, new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onBookClick(Movie movie) {
                // For Coming Soon, we still navigate to SeatSelectionFragment but it will show restricted UI
                ((MainActivity) requireActivity()).navigateToFragment(SeatSelectionFragment.newInstance(movie), true);
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
}
