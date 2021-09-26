package com.example.weather;

import java.util.concurrent.ThreadLocalRandom;

class RandomLocationGenerator implements LocationProvider {

    private static final String[] LOCATIONS = new String[]{"Cracow", "Warsaw", "London", "Lodz", "Kielce", "Tokyo", "NewYork", "Buenos Aires", "Rzeszow"};

    @Override
    public String location() {
        return LOCATIONS[ThreadLocalRandom.current().nextInt(0, LOCATIONS.length - 1)];
    }

    @Override
    public int getSize() {
        return LOCATIONS.length;
    }
}
