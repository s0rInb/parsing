package com.aston.health.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ros_min_zdrav_nadzor")
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@XmlRootElement(name = "licenses")
@XmlAccessorType(XmlAccessType.FIELD)
public class EntityRosMinZdravNadzor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guid;

    @Column(name = "name", length = 4096)
    private String name = "";

    @Column(name = "activity_type", length = 4096)
    private String activityType =  "";

    @Column(name = "full_name_licensee", length = 4096)
    private String fullNameLicensee =  "";

    @Column(name = "abbreviated_name_licensee", length = 4096)
    private String abbreviatedNameLicensee =  "";

    @Column(name = "brand_name_licensee", length = 4096)
    private String brandNameLicensee =  "";

    @Column(name = "form", length = 4096)
    private String form =  "";

    @Column(name = "address", length = 4096)
    private String address =  "";

    @Column(name = "ogrn", length = 4096)
    private String ogrn =  "";

    @Column(name = "inn", length = 4096)
    private String inn =  "";

    @Column(name = "number", length = 4096)
    private String number =  "";

    @Column(name = "date", length = 4096)
    private String date =  "";

    @Column(name = "number_orders", length = 4096)
    private String numberOrders =  "";

    @Column(name = "date_order", length = 4096)
    private String dateOrder =  "";

    @Column(name = "date_register", length = 4096)
    private String dateRegister =  "";

    @Column(name = "number_duplicate", length = 4096)
    private String numberDuplicate =  "";

    @Column(name = "date_duplicate", length = 4096)
    private String dateDuplicate =  "";

    @Column(name = "termination", length = 4096)
    private String termination =  "";

    @Column(name = "date_termination", length = 4096)
    private String dateTermination =  "";

    @Column(name = "information", length = 4096)
    private String information =  "";

    @Column(name = "information_regulations", length = 4096)
    private String informationRegulations =  "";

    @Column(name = "information_suspension_resumption", length = 4096)
    private String informationSuspensionResumption =  "";

    @Column(name = "information_cancellation", length = 4096)
    private String informationCancellation =  "";

    @Column(name = "information_reissuing", length = 4096)
    private String informationReissuing =  "";

    @OneToMany(mappedBy = "entityRosMinZdravNadzor", cascade = {CascadeType.ALL})
    private List<Address> addressPlace = new ArrayList<>();



}