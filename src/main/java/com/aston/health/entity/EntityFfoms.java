package com.aston.health.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.apache.poi.ss.usermodel.Row;

import javax.persistence.*;

@Entity
@Table(name = "ffoms", schema = "public", catalog = "cross")
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@NoArgsConstructor
public class EntityFfoms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guid;

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

    @Column(name = "add_date", length = 4096)
    private String addDate;

    public EntityFfoms(Row row) {
        this.subject = row.getCell(0).toString().trim();
        this.years = row.getCell(1).toString().trim();
        this.medNumber = row.getCell(2).toString().trim();
        this.fullName = row.getCell(3).toString().trim();
        this.shortName = row.getCell(4).toString().trim();
        this.okopf = row.getCell(5).toString().trim();
        this.mailIndex = row.getCell(6).toString().trim();
        this.address = row.getCell(7).toString().trim();
        this.directorSurname = row.getCell(8).toString().trim();
        this.directorName = row.getCell(9).toString().trim();
        this.directorPatronymic = row.getCell(10).toString().trim();
        this.phone = row.getCell(11).toString().trim();
        this.fax = row.getCell(12).toString().trim();
        this.email = row.getCell(13).toString().trim();
        this.licenseNumber = row.getCell(14).toString().trim();
        this.licenseStartDate = row.getCell(15).toString().trim();
        this.licenseEndDate = row.getCell(16).toString().trim();
        this.typeMedicalCare = row.getCell(17).toString().trim();
        this.addDate = row.getCell(18).toString().trim();
    }
}
