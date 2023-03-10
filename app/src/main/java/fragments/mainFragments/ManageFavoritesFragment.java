package fragments.mainFragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import activities.OnFragmentInteractionListener;
import adapters.FavoriteListAdapter;
import models.ParkingsListFragmentViewModel;
import models.User;
import models.UserFragmentViewModel;
import models.UserMock;
import services.LoginService;

public class ManageFavoritesFragment extends Fragment {
    private AutoCompleteTextView cityAutoComplete;
    private String[] citiesArray;
    private UserFragmentViewModel viewModel;

    public ManageFavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        citiesArray = getCities();
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

        ArrayAdapter<String> cityAutoCompleteAdapter = new ArrayAdapter(getContext(), R.layout.list_item, citiesArray);
        cityAutoComplete.setDropDownHeight(600);
        cityAutoComplete.setAdapter(cityAutoCompleteAdapter);

        Button addFavoriteButton = view.findViewById(R.id.add_favorite_btn);
        addFavoriteButton.setOnClickListener(view1 -> {
            String newFavorite = cityAutoComplete.getText().toString();
            User user = UserMock.instance().getUser(LoginService.instance().getLoginService().getUserId()).getValue();
            if(newFavorite.isEmpty()) {
                Toast.makeText(getContext(), "Please select favorite city", Toast.LENGTH_SHORT).show();
            } else if (!user.getFavorites().stream().anyMatch(favorite -> favorite.equals(newFavorite))) {
                user.getFavorites().add(newFavorite);
                UserMock.instance().updateFavorites(user, (unused) -> {
                    Toast.makeText(getContext(), "Favorite Added Successfully", Toast.LENGTH_SHORT).show();
                });
            } else {
                Toast.makeText(getContext(), "Favorite already exists", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getUser().observe(getViewLifecycleOwner(), updatedUser -> {
            adapter.setData(updatedUser.getFavorites());
        });
        return view;
    }

    public String[] getCities() {
        List<String> items = new ArrayList();
        AssetManager am = getContext().getAssets();
        try {
            InputStream inputStream = am.open("cities.txt");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String str_line;

            while ((str_line = buffer.readLine()) != null) {
                str_line = str_line.trim();
                if ((str_line.length() != 0)) {
                    items.add(str_line);
                }
            }

            return items.toArray(new String[items.size()]);

        } catch (IOException e) {
            return new String[0];
        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(UserFragmentViewModel.class);
    }
}