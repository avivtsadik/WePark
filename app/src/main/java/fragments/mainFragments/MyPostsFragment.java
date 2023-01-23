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
import java.util.stream.Collectors;

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

        RecyclerView list = view.findViewById(R.id.myPostsList);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Parking> data = viewModel.getParkingList().getValue().stream().filter(parking -> parking.getUserId().equals(userId)).collect(Collectors.toList());
        adapter = new ParkingListAdapter(getLayoutInflater(), data, R.layout.parking_card_editable);

        adapter.setOnItemEditListener(() -> {
            ParkingMock.instance().refreshAllParkingLots();
        });

        adapter.setOnItemDeleteListener(() -> {
            ParkingMock.instance().refreshAllParkingLots();
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