package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.dadata.DaDataClient;
import dev.sosnovsky.applications.exception.IncorrectPhoneException;
import dev.sosnovsky.applications.model.PhoneDetails;
import dev.sosnovsky.applications.repository.PhoneDetailsRepository;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneDetailsServiceImpl implements PhoneDetailsService {
    private final PhoneDetailsRepository phoneDetailsRepository;
    private final DaDataClient daDataClient;
    private final String apiKey;
    private final String secretKey;

    @Autowired
    public PhoneDetailsServiceImpl(PhoneDetailsRepository phoneDetailsRepository, DaDataClient daDataClient,
                                   @Value("${dadata.api-key}") String apiKey,
                                   @Value("${dadata.secret-key}") String secretKey) {
        this.phoneDetailsRepository = phoneDetailsRepository;
        this.daDataClient = daDataClient;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }

    @Override
    @PermitAll
    public PhoneDetails getPhoneDetailsFromDaData(String phone) {
        PhoneDetails phoneDetails = daDataClient
                .getPhoneDatailsFromDaData(List.of(phone), apiKey, secretKey)
                .getBody().get(0);
        if (phoneDetails.getPhone() == null) {
            throw new IncorrectPhoneException("Указанный телефон отсутствует в базе сервиса DaData");
        }
        return phoneDetailsRepository.save(phoneDetails);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Boolean isCorrectPhone(String phone) {
        return daDataClient.getPhoneDatailsFromDaData(List.of(phone), apiKey, secretKey).getBody().get(0)
                .getPhone() != null;
    }
}
