package com.example.cinefast;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.Locale;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        viewPager.setAdapter(new HomePagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) tab.setText("Now Showing");
            else tab.setText("Coming Soon");
        }).attach();

        view.findViewById(R.id.btnMenu).setOnClickListener(this::showPopupMenu);

        return view;
    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(requireContext(), view);
        popup.getMenu().add("View Last Booking");
        popup.setOnMenuItemClickListener(item -> {
            if (item.getTitle().equals("View Last Booking")) {
                showLastBooking();
                return true;
            }
            return false;
        });
        popup.show();
    }

    private void showLastBooking() {
        SharedPreferences prefs = requireContext().getSharedPreferences("CineFastPrefs", Context.MODE_PRIVATE);
        String movie = prefs.getString("last_movie", null);
        
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Last Booking");

        if (movie == null) {
            builder.setMessage("No previous booking found.");
        } else {
            int seats = prefs.getInt("last_seats", 0);
            float price = prefs.getFloat("last_price", 0);
            String message = String.format(Locale.US, "Movie: %s\nSeats: %d\nTotal Price: $%.2f", movie, seats, price);
            builder.setMessage(message);
        }
        
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    private static class HomePagerAdapter extends FragmentStateAdapter {
        public HomePagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) return new NowShowingFragment();
            else return new ComingSoonFragment();
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
