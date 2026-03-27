package com.example.cinefast;

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

public class NowShowingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("The Dark Knight", R.drawable.dnk, "Action", "152 min", "https://www.youtube.com/watch?v=EXeTwQWaywY", true));
        movies.add(new Movie("Inception", R.drawable.ic, "Sci-Fi", "148 min", "https://www.youtube.com/watch?v=YoHD9XEInc0", true));
        movies.add(new Movie("Interstellar", R.drawable.is, "Sci-Fi", "169 min", "https://www.youtube.com/watch?v=zSWdZVtXT7E", true));

        MovieAdapter adapter = new MovieAdapter(movies, new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onBookClick(Movie movie) {
                ((MainActivity) requireActivity()).navigateToFragment(SeatSelectionFragment.newInstance(movie), true);
            }

            @Override
            public void onTrailerClick(Movie movie) {
                // Trailer logic
            }
        });

        recyclerView.setAdapter(adapter);
        return view;
    }
}
