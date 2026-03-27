package com.example.cinefast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class SnackAdapter extends ArrayAdapter<Snack> {

    public SnackAdapter(@NonNull Context context, @NonNull List<Snack> snacks) {
        super(context, 0, snacks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_snack, parent, false);
        }

        Snack snack = getItem(position);

        ImageView ivImage = convertView.findViewById(R.id.ivSnackImage);
        TextView tvName = convertView.findViewById(R.id.tvSnackName);
        TextView tvPrice = convertView.findViewById(R.id.tvSnackPrice);
        TextView tvQty = convertView.findViewById(R.id.tvQty);
        Button btnPlus = convertView.findViewById(R.id.btnPlus);
        Button btnMinus = convertView.findViewById(R.id.btnMinus);

        ivImage.setImageResource(snack.getImageResId());
        tvName.setText(snack.getName());
        tvPrice.setText("$" + snack.getPrice());
        tvQty.setText(String.valueOf(snack.getQuantity()));

        btnPlus.setOnClickListener(v -> {
            snack.setQuantity(snack.getQuantity() + 1);
            tvQty.setText(String.valueOf(snack.getQuantity()));
        });

        btnMinus.setOnClickListener(v -> {
            if (snack.getQuantity() > 0) {
                snack.setQuantity(snack.getQuantity() - 1);
                tvQty.setText(String.valueOf(snack.getQuantity()));
            }
        });

        return convertView;
    }
}
