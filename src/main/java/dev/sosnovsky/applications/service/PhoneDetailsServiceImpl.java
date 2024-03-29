package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.dadata.DaDataClient;
import dev.sosnovsky.applications.model.PhoneDetails;
import dev.sosnovsky.applications.repository.PhoneDetailsRepository;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneDetailsServiceImpl implements PhoneDetailsService {
    private final PhoneDetailsRepository phoneDetailsRepository;
    private final DaDataClient daDataClient;

    @Autowired
    public PhoneDetailsServiceImpl(PhoneDetailsRepository phoneDetailsRepository, DaDataClient daDataClient) {
        this.phoneDetailsRepository = phoneDetailsRepository;
        this.daDataClient = daDataClient;
    }

    @Value("${dadata.api-key}")
    private String apiKey;
    @Value("${dadata.secret-key}")
    private String secretKey;

    @Override
    @PermitAll
    public PhoneDetails getPhoneDatailsFromDaData(String phone) {
        PhoneDetails phoneDetails = daDataClient
                .getPhoneDatailsFromDaData(List.of(phone), apiKey, secretKey)
                .getBody().get(0);
        return phoneDetailsRepository.save(phoneDetails);
    }
}
