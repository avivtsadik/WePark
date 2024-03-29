package fragments.mainFragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.wepark.R;

import activities.OnFragmentInteractionListener;
import adapters.ParkingListAdapter;
import models.LoadingState;
import models.ParkingMock;
import models.ParkingListFragmentViewModel;

public class HomeFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private ParkingListFragmentViewModel viewModel;
    private ParkingListAdapter adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView list = view.findViewById(R.id.parkingList);
        progressBar = view.findViewById(R.id.progressBar);
        Button addParkingBtn = view.findViewById(R.id.addParkingButton);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);

        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ParkingListAdapter(getLayoutInflater(), viewModel.getParkingList().getValue(), R.layout.parking_card);
        list.setAdapter(adapter);

        addParkingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                Navigation.findNavController(view2).navigate(R.id.action_homeFragmentNav_to_addParkingFragmentNav);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            ParkingMock.instance().refreshAllParkingLots();
        });

        ParkingMock.instance().EventStudentsListLoadingState.observe(getViewLifecycleOwner(),status->{
            swipeRefreshLayout.setRefreshing(status == LoadingState.LOADING_PARKING);
        });

        viewModel.getParkingList().observe(getViewLifecycleOwner(), updatedList -> {
            adapter.setData(updatedList);
            progressBar.setVisibility(View.GONE);

        });

        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(ParkingListFragmentViewModel.class);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}