package fragments.mainFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.wepark.R;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import adapters.FavoriteListAdapter;
import models.City;
import models.CityMock;
import models.User;
import models.UserFragmentViewModel;
import models.UserMock;

public class ManageFavoritesFragment extends Fragment {
    private AutoCompleteTextView cityAutoComplete;
    private UserFragmentViewModel userViewModel;

    public ManageFavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_favorites, container, false);

        RecyclerView list = view.findViewById(R.id.favoritesList);
        cityAutoComplete = view.findViewById(R.id.autoCompleteCity);

        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        FavoriteListAdapter adapter = new FavoriteListAdapter(getLayoutInflater(), new LinkedList());
        list.setAdapter(adapter);

        ArrayAdapter<String> cityAutoCompleteAdapter = new ArrayAdapter(getContext(), R.layout.list_item, new LinkedList());
        cityAutoComplete.setDropDownHeight(600);
        cityAutoComplete.setAdapter(cityAutoCompleteAdapter);

        LiveData<List<City>> citiesLive = CityMock.instance.getCities();
        citiesLive.observe(getViewLifecycleOwner(), citiesList -> {
            List<String> cityNames = citiesList.stream().map(City::getName).collect(Collectors.toList());
            ArrayAdapter<String> cityAutoCompleteAdapter2 = new ArrayAdapter(getContext(), R.layout.list_item, cityNames);
            cityAutoComplete.setAdapter(cityAutoCompleteAdapter2);
        });

        Button addFavoriteButton = view.findViewById(R.id.add_favorite_btn);
        addFavoriteButton.setOnClickListener(view1 -> {
            String newFavorite = cityAutoComplete.getText().toString();
            User user = UserMock.instance().getUser().getValue();
            if (newFavorite.isEmpty()) {
                Toast.makeText(getContext(), "Please select favorite city", Toast.LENGTH_SHORT).show();
            } else if (!user.getFavorites().stream().anyMatch(favorite -> favorite.equals(newFavorite))) {
                user.getFavorites().add(newFavorite);
                UserMock.instance().updateUserData(user, (unused) -> {
                    Toast.makeText(getContext(), "Favorite Added Successfully", Toast.LENGTH_SHORT).show();
                });
            } else {
                Toast.makeText(getContext(), "Favorite already exists", Toast.LENGTH_SHORT).show();
            }
        });

        userViewModel.getUser().observe(getViewLifecycleOwner(), updatedUser -> {
            if (updatedUser != null) {
                adapter.setData(updatedUser.getFavorites());
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        userViewModel = new ViewModelProvider(this).get(UserFragmentViewModel.class);
    }
}