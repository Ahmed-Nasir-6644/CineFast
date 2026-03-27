package com.example.cinefast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private OnMovieClickListener listener;

    public interface OnMovieClickListener {
        void onBookClick(Movie movie);
        void onTrailerClick(Movie movie);
    }

    public MovieAdapter(List<Movie> movieList, OnMovieClickListener listener) {
        this.movieList = movieList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.tvMovieName.setText(movie.getName());
        holder.tvMovieInfo.setText(movie.getGenre() + " / " + movie.getDuration());
        holder.ivMoviePoster.setImageResource(movie.getPosterResId());

        if (!movie.isNowShowing()) {
            holder.btnBook.setText("Coming Soon");
            holder.btnBook.setEnabled(false);
            holder.btnBook.setAlpha(0.5f);
        } else {
            holder.btnBook.setText("Book Seats");
            holder.btnBook.setEnabled(true);
            holder.btnBook.setAlpha(1.0f);
        }

        holder.btnBook.setOnClickListener(v -> listener.onBookClick(movie));
        holder.btnTrailer.setOnClickListener(v -> listener.onTrailerClick(movie));
        
        // As per requirement, for Coming Soon movies, clicking the item should also open SeatSelection but with restricted UI.
        // Actually, the requirement says "When a movie is selected: The selected movie information must be passed to SeatSelectionFragment".
        // And "If the selected movie belongs to the Coming Soon tab, the Seat Selection screen must behave differently."
        // So clicking the item or a button should navigate. Let's make the whole item clickable or just the Book/Coming Soon button.
        // I'll make the Book button (even if disabled text-wise for UI) clickable for navigation if we want to show the restricted SeatSelection.
        // Wait, "Coming Soon (disabled button)" is on the SEAT SELECTION screen.
        // On Home Screen, it says "Book Seats Button".
        
        holder.itemView.setOnClickListener(v -> listener.onBookClick(movie));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMoviePoster, btnTrailer;
        TextView tvMovieName, tvMovieInfo;
        Button btnBook;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMoviePoster = itemView.findViewById(R.id.ivMoviePoster);
            btnTrailer = itemView.findViewById(R.id.btnTrailer);
            tvMovieName = itemView.findViewById(R.id.tvMovieName);
            tvMovieInfo = itemView.findViewById(R.id.tvMovieInfo);
            btnBook = itemView.findViewById(R.id.btnBook);
        }
    }
}
