package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wepark.R;

import java.util.List;

import models.User;
import models.UserMock;


class FavoriteListHolder extends RecyclerView.ViewHolder {
    TextView textView;
    ImageView removeBtn;
    List<String> data;

    public FavoriteListHolder(@NonNull View itemView, FavoriteListAdapter.OnItemClickListener listener, List<String> data) {
        super(itemView);
        this.data = data;
        textView = itemView.findViewById(R.id.favoriteItemTitle);
        removeBtn = itemView.findViewById(R.id.removeFavoriteButton);

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = UserMock.instance().getUser().getValue();
                user.getFavorites().removeIf(favorite -> favorite.equals(textView.getText().toString()));
                UserMock.instance().updateUserData(user, (unused) -> {
                    Toast.makeText(view.getContext(), "Favorite removed successfully", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    public void bind(String favorite) {
        textView.setText(favorite);
    }
}

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListHolder> {
    OnItemClickListener listener;

    public static interface OnItemClickListener {
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<String> data;

    public FavoriteListAdapter(LayoutInflater inflater, List<String> data) {
        this.inflater = inflater;
        this.data = data;
    }

    public void setData(List<String> favorites) {
        this.data = favorites;
        notifyDataSetChanged();
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
        String favorite = data.get(position);
        holder.bind(favorite);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}