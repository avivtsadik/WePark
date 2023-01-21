package fragments.mainFragments;

import android.app.Activity;
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
import android.widget.Button;

import com.example.wepark.R;

import java.util.LinkedList;
import java.util.List;

import activities.OnFragmentInteractionListener;
import adapters.ParkingListAdapter;
import models.Parking;
import models.ParkingMock;
import models.ParkingsListFragmentViewModel;

public class HomeFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    ParkingsListFragmentViewModel viewModel;
    ParkingListAdapter adapter;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ParkingMock.instance().getAllParkingLots((prkList) -> {
            viewModel.setParkingList(prkList);
            adapter.setData(viewModel.getParkingList());
        });

        RecyclerView list = view.findViewById(R.id.parkingList);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ParkingListAdapter(getLayoutInflater(), viewModel.getParkingList(), R.layout.parking_card);
        list.setAdapter(adapter);

        Button addParkingBtn = view.findViewById(R.id.addParkingButton);

        addParkingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.loadFragment(new AddParkingFragment());
            }
        });

        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(ParkingsListFragmentViewModel.class);
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
    public void onResume() {
        super.onResume();
        ParkingMock.instance().getAllParkingLots((prkList) -> {
            viewModel.setParkingList(prkList);
            adapter.setData(viewModel.getParkingList());
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}