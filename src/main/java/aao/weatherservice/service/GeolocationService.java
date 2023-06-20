package aao.weatherservice.service;

import aao.weatherservice.dto.PlaceDto;

public interface GeolocationService {
    PlaceDto getPlaceByCityName(String city);
}
