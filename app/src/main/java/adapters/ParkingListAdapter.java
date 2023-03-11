package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wepark.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import fragments.mainFragments.MyPostsFragmentDirections;
import models.Parking;
import models.ParkingMock;

class ParkingListHolder extends RecyclerView.ViewHolder {
    TextView cityTv;
    TextView sizeTv;
    TextView userIdTv;
    AppCompatImageView editPostButton;
    AppCompatImageView deletePostButton;
    List<Parking> data;
    ImageView parkingImage;

    public ParkingListHolder(@NonNull View itemView, ParkingListAdapter.OnItemEditListener listener, List<Parking> data) {
        super(itemView);
        this.data = data;
        cityTv = itemView.findViewById(R.id.cityTextView);
        sizeTv = itemView.findViewById(R.id.sizeTextView);
        editPostButton = itemView.findViewById(R.id.editPostButton);
        deletePostButton = itemView.findViewById(R.id.deletePostButton);
        userIdTv = itemView.findViewById(R.id.userIdTextView);
        parkingImage = itemView.findViewById(R.id.parkingImage);
    }

    public void bind(Parking parking, ParkingListAdapter.OnItemEditListener editListener, ParkingListAdapter.OnItemDeleteListener deleteListener) {
        cityTv.setText(parking.getCity());
        sizeTv.setText(parking.getSize());
        userIdTv.setText(parking.getUserId());
        if (parking.getAvatarUrl() != "") {
            Picasso.get().load(parking.getAvatarUrl()).placeholder(R.drawable.avatar).into(parkingImage);
        } else {
            parkingImage.setImageResource(R.drawable.avatar);
        }
        if (editPostButton != null) {
            editPostButton.setOnClickListener(view -> {
                MyPostsFragmentDirections.ActionFragmentMyPostsNavGraphToEditParkingFragment action = MyPostsFragmentDirections.actionFragmentMyPostsNavGraphToEditParkingFragment(parking.getId());

                Navigation.findNavController(view).navigate(action);
            });
        }

        if (deletePostButton != null) {
            deletePostButton.setOnClickListener(view -> {
                ParkingMock.instance().deleteParkingLot(parking, (data) -> deleteListener.onItemDeleted());
            });
        }
    }
}

public class ParkingListAdapter extends RecyclerView.Adapter<ParkingListHolder> {
    OnItemEditListener editListener;
    OnItemDeleteListener deleteListener;

    public static interface OnItemEditListener {
        void onItemEdited();
    }

    public static interface OnItemDeleteListener {
        void onItemDeleted();
    }

    LayoutInflater inflater;
    List<Parking> data;
    int layout;

    public ParkingListAdapter(LayoutInflater inflater, List<Parking> data, int layout) {
        this.layout = layout;
        this.inflater = inflater;
        this.data = data;
    }

    public void setData(List<Parking> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnItemEditListener(OnItemEditListener listener) {
        this.editListener = listener;
    }

    public void setOnItemDeleteListener(OnItemDeleteListener listener) {
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public ParkingListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(this.layout, parent, false);
        return new ParkingListHolder(view, editListener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkingListHolder holder, int position) {
        Parking parking = data.get(position);
        holder.bind(parking, editListener, deleteListener);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }

}