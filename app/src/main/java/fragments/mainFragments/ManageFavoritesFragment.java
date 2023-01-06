package fragments.mainFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wepark.R;

import adapters.FavoriteListAdapter;
import models.FavoriteMock;

public class ManageFavoritesFragment extends Fragment {
    public ManageFavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_favorites, container, false);

        RecyclerView list = view.findViewById(R.id.favoritesList);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        FavoriteListAdapter adapter = new FavoriteListAdapter(getLayoutInflater(), FavoriteMock.instance().getFavorites());
        list.setAdapter(adapter);

        return view;
    }
}