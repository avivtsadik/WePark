package adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wepark.R;

import java.util.List;

import models.Favorite;

class FavoriteListHolder extends RecyclerView.ViewHolder {
    TextView textView;
    ImageView removeBtn;
    List<Favorite> data;

    public FavoriteListHolder(@NonNull View itemView, FavoriteListAdapter.OnItemClickListener listener, List<Favorite> data) {
        super(itemView);
        this.data = data;
        textView = itemView.findViewById(R.id.favoriteItemTitle);
        removeBtn = itemView.findViewById(R.id.removeFavoriteButton);

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                view.refreshDrawableState();
            }
        });
    }

    public void bind(Favorite favorite, int pos) {
        textView.setText(favorite.getCity());
    }
}

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListHolder> {
    OnItemClickListener listener;

    public static interface OnItemClickListener {
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<Favorite> data;

    public FavoriteListAdapter(LayoutInflater inflater, List<Favorite> data) {
        this.inflater = inflater;
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoriteListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.favorite_item, parent, false);
        return new FavoriteListHolder(view, listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteListHolder holder, int position) {
        Favorite favorite = data.get(position);
        holder.bind(favorite, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}