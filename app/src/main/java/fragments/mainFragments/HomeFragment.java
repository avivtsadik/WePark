package fragments.mainFragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wepark.R;

import activities.MainActivity;
import activities.OnFragmentInteractionListener;
import adapters.ParkingListAdapter;
import fragments.loginFragments.LoginFragment;
import models.ParkingMock;

public class HomeFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

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
        ParkingListAdapter adapter = new ParkingListAdapter(
                getLayoutInflater(),
                ParkingMock.instance().getParkingLots(),
                R.layout.parking_card);
        list.setAdapter(adapter);

        View addParkingBtn = view.findViewById(R.id.addParkingButton);
        addParkingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.loadFragment(new AddParkingFragment());
            }
        });
        // Inflate the layout for this fragment
        return view;
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