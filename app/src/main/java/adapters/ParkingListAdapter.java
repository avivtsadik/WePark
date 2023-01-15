package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wepark.R;

import java.util.List;

import models.Parking;
import models.ParkingMock;
import models.ParkingSize;

class ParkingListHolder extends RecyclerView.ViewHolder {
    TextView cityTv;
    TextView sizeTv;
    AppCompatImageView editPostButton;
    AppCompatImageView deletePostButton;
    List<Parking> data;

    public ParkingListHolder(@NonNull View itemView, ParkingListAdapter.OnItemClickListener listener, List<Parking> data) {
        super(itemView);
        this.data = data;
        cityTv = itemView.findViewById(R.id.cityTextView);
        sizeTv = itemView.findViewById(R.id.sizeTextView);
        editPostButton = itemView.findViewById(R.id.editPostButton);
        deletePostButton = itemView.findViewById(R.id.deletePostButton);

        if (editPostButton != null) {
            editPostButton.setOnClickListener(view -> {
                itemView.getContext();
            });
        }

        if (deletePostButton != null) {
            deletePostButton.setOnClickListener(view -> {

            });
        }

    }

    public void bind(Parking parking, int pos) {
        cityTv.setText(parking.getCity());
        sizeTv.setText(this.getParkingSizeText(parking.getSize()));
    }

    private String getParkingSizeText(ParkingSize parkingSize) {
        switch (parkingSize) {
            case BIG:
                return "Big";
            case MEDIUM:
                return "Medium";
            case SMALL:
                return "Small";
            default:
                return "";
        }
    }
}

public class ParkingListAdapter extends RecyclerView.Adapter<ParkingListHolder> {
    OnItemClickListener listener;

    public static interface OnItemClickListener {
        void onItemClick(int pos);
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ParkingListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(this.layout, parent, false);
        return new ParkingListHolder(view, listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkingListHolder holder, int position) {
        Parking parking = data.get(position);
        holder.bind(parking, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}