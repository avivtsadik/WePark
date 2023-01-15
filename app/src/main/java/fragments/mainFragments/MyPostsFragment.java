package fragments.mainFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wepark.R;

import java.util.List;
import java.util.Vector;

import adapters.ParkingListAdapter;
import models.Parking;
import models.ParkingMock;

public class MyPostsFragment extends Fragment {
    public MyPostsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_posts, container, false);

        RecyclerView list = view.findViewById(R.id.myPostsList);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        //TODO: change the data to the posts of the user
        List<Parking> data = ParkingMock.instance().getParkingLots();
        ParkingListAdapter adapter = new ParkingListAdapter(getLayoutInflater(), data, R.layout.parking_card_editable);
        list.setAdapter(adapter);

        return view;
    }
}