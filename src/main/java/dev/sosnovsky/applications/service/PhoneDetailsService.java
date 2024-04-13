package dev.sosnovsky.applications.service;

import dev.sosnovsky.applications.model.PhoneDetails;

public interface PhoneDetailsService {
    PhoneDetails getPhoneDetailsFromDaData(String phone);

    Boolean isCorrectPhone(String phone);
}
