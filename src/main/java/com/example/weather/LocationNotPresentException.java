package com.example.weather;

class LocationNotPresentException extends RuntimeException {

    public LocationNotPresentException(String location) {
        super("Can't find given location = " + location);
    }

}
