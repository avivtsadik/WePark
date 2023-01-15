package models;

import java.util.Vector;

public class FavoriteMock {
    public static final FavoriteMock _instance = new FavoriteMock();

    public static FavoriteMock instance() {
        return _instance;
    }

    public final String[] cities = new String[]{
            "BEER SHEBA",
            "TEL AVIV",
            "JERUSALEM",
            "HOLON",
            "SHOHAM",
            "NATNIYA",
            "PETAH TIKVA",
            "BAT YAM",
            "RISHON LETZION"
    };

    private final Vector<Favorite> favorites = new Vector<>();

    public FavoriteMock() {
        for (int i = 0; i < 20; i++) {
            this.addFavorite(new Favorite(cities[i % (cities.length)]));
        }
    }

    private void addFavorite(Favorite favorite) {
        this.favorites.add(favorite);
    }

    public Vector<Favorite> getFavorites() {
        return this.favorites;
    }
}
