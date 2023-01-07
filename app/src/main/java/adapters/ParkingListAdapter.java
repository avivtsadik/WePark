package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wepark.R;

import java.util.List;

import models.Parking;
import models.ParkingSize;

class ParkingListHolder extends RecyclerView.ViewHolder {
    TextView cityTv;
    TextView streetTv;
    TextView sizeTv;
    List<Parking> data;

    public ParkingListHolder(@NonNull View itemView, ParkingListAdapter.OnItemClickListener listener, List<Parking> data) {
        super(itemView);
        this.data = data;
        cityTv = itemView.findViewById(R.id.cityTextView);
        streetTv = itemView.findViewById(R.id.streetTextView);
        sizeTv = itemView.findViewById(R.id.sizeTextView);
    }

    public void bind(Parking parking, int pos) {
        cityTv.setText(parking.getCity());
        streetTv.setText(parking.getStreet());
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