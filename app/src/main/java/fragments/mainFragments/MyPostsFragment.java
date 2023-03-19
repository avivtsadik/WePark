package fragments.mainFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wepark.R;

import java.util.LinkedList;

import adapters.ParkingListAdapter;
import models.ParkingMock;
import models.ParkingListFragmentViewModel;
import services.LoginService;

public class MyPostsFragment extends Fragment {
    private ParkingListAdapter adapter;
    ParkingListFragmentViewModel viewModel;

    public MyPostsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_posts, container, false);
        String userId = LoginService.instance().getLoginService().getUserId();

        RecyclerView list = view.findViewById(R.id.myPostsList);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ParkingListAdapter(getLayoutInflater(), new LinkedList(), R.layout.parking_card_editable);

        adapter.setOnItemEditListener(() -> {
            ParkingMock.instance().refreshAllParkingLots();
            ParkingMock.instance().getParkingLotsByUserId(userId, data -> {
                adapter.setData(data);
            });
        });

        adapter.setOnItemDeleteListener(() -> {
            ParkingMock.instance().refreshAllParkingLots();
            ParkingMock.instance().getParkingLotsByUserId(userId, data -> {
                adapter.setData(data);
            });
        });

        list.setAdapter(adapter);

        ParkingMock.instance().getParkingLotsByUserId(userId, data -> {
            adapter.setData(data);
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(ParkingListFragmentViewModel.class);
    }
}