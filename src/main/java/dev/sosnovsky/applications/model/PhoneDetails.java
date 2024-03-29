package dev.sosnovsky.applications.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "phone_details")
public class PhoneDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String phone;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "city_code")
    private String cityCode;

    private String number;

    private String provider;

    private String country;

    private String region;

    public PhoneDetails(String phone) {
        this.phone = phone;
    }
}
