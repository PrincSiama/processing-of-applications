package dev.sosnovsky.applications.dadata;

import dev.sosnovsky.applications.model.PhoneDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "dadata", url = "https://cleaner.dadata.ru/api/v1/clean/phone")
public interface DaDataClient {
    @PostMapping
    ResponseEntity<List<PhoneDetails>> getPhoneDatailsFromDaData(@RequestBody List<String> query,
                                                                 @RequestHeader("Authorization") String apiKey,
                                                                 @RequestHeader("X-Secret") String secretKey);
}
