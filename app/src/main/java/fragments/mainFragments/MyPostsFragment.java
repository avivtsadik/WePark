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
import com.google.firebase.auth.FirebaseAuth;

import java.util.LinkedList;
import java.util.List;

import adapters.ParkingListAdapter;
import models.Parking;
import models.ParkingMock;
import models.ParkingsListFragmentViewModel;
import services.LoginService;

public class MyPostsFragment extends Fragment {
    private ParkingListAdapter adapter;
    ParkingsListFragmentViewModel viewModel;

    public MyPostsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_posts, container, false);
        String userId = LoginService.instance().getLoginService().getUserId();

        ParkingMock.instance().getParkingLotsByUserId(userId, (prkList) -> {
            viewModel.setParkingList(prkList);
            adapter.setData(viewModel.getParkingList());
        });

        RecyclerView list = view.findViewById(R.id.myPostsList);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new ParkingListAdapter(getLayoutInflater(), viewModel.getParkingList(), R.layout.parking_card_editable);

        adapter.setOnItemEditListener(() -> {
            ParkingMock.instance().getParkingLotsByUserId(userId, (prkList) -> {
                viewModel.setParkingList(prkList);
                adapter.setData(viewModel.getParkingList());
            });
        });

        adapter.setOnItemDeleteListener(() -> {
            ParkingMock.instance().getParkingLotsByUserId(userId, (prkList) -> {
                viewModel.setParkingList(prkList);
                adapter.setData(viewModel.getParkingList());
            });
        });

        list.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(ParkingsListFragmentViewModel.class);
    }
}