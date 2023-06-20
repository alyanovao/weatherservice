package aao.weatherservice.service;

import aao.weatherservice.dto.WindDto;

public interface ConvertorService {
    WindDto windSpeedConvert(WindDto wind);
}
