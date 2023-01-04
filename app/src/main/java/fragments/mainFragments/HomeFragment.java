package fragments.mainFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wepark.R;

import adapters.ParkingListAdapter;
import models.ParkingMock;

public class HomeFragment extends Fragment {
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView list = view.findViewById(R.id.parkingList);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        ParkingListAdapter adapter = new ParkingListAdapter(getLayoutInflater(), ParkingMock.instance().getParkingLots());
        list.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }
}