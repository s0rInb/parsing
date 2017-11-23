package com.aston.health.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "clear_ffoms", schema = "public", catalog = "cross")
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@NoArgsConstructor
public class ClearFfoms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guid;
    @Column(name = "serial_code")
    private Integer serialCode;
    @Column(name = "indexOfDuplicate")
    private Integer indexOfDuplicate;

    @Column(name = "clearRosZdravNadzor_serial_code")
    private Integer clearRosZdravNadzorSerialNumber;
    @Column(name = "clearRosminzdrav_serial_code")
    private Integer clearRosminzdravSerialNumber;

    @Column(name = "subject", length = 4096)
    private String subject;

    @Column(name = "years", length = 4096)
    private String years;

    @Column(name = "med_number", length = 4096)
    private String medNumber;

    @Column(name = "full_name", length = 4096)
    private String fullName;

    @Column(name = "short_name", length = 4096)
    private String shortName;

    @Column(name = "okopf", length = 4096)
    private String okopf;

    @Column(name = "mail_index", length = 4096)
    private String mailIndex;

    @Column(name = "address", length = 4096)
    private String address;

    @Column(name = "director_surname", length = 4096)
    private String directorSurname;

    @Column(name = "director_name", length = 4096)
    private String directorName;

    @Column(name = "director_patronymic", length = 4096)
    private String directorPatronymic;

    @Column(name = "phone", length = 4096)
    private String phone;

    @Column(name = "fax", length = 4096)
    private String fax;

    @Column(name = "email", length = 4096)
    private String email;

    @Column(name = "license_number", length = 4096)
    private String licenseNumber;

    @Column(name = "license_start_date", length = 4096)
    private String licenseStartDate;

    @Column(name = "license_end_date", length = 4096)
    private String licenseEndDate;

    @Column(name = "type_medical_care", length = 4096)
    private String typeMedicalCare;

    @Column(name = "adddate", length = 4096)
    private String addDate;

    @Column(name = "ffoms_guid")
    private Long parentGuid;

    public ClearFfoms(EntityFfoms ffoms) {
        this.subject = ffoms.getSubject();
        this.years = ffoms.getYears();
        this.medNumber = ffoms.getMedNumber();
        this.fullName = ffoms.getFullName();
        this.shortName = ffoms.getShortName();
        this.okopf = ffoms.getOkopf();
        this.mailIndex = ffoms.getMailIndex();
        this.address = ffoms.getAddress();
        this.directorSurname = ffoms.getDirectorSurname();
        this.directorName = ffoms.getDirectorName();
        this.directorPatronymic = ffoms.getDirectorPatronymic();
        this.phone = ffoms.getPhone();
        this.fax = ffoms.getFax();
        this.email = ffoms.getEmail();
        this.licenseNumber = ffoms.getLicenseNumber();
        this.licenseStartDate = ffoms.getLicenseStartDate();
        this.licenseEndDate = ffoms.getLicenseEndDate();
        this.typeMedicalCare = ffoms.getTypeMedicalCare();
        this.addDate = ffoms.getAddDate();
        this.parentGuid = ffoms.getGuid();
    }

}
