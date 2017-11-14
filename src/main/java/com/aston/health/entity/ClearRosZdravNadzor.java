package com.aston.health.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clear_ros_zdrav_nadzor")
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@NoArgsConstructor
public class ClearRosZdravNadzor implements Comparable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guid;

    @Column(name = "name", length = 4096)
    private String name;

    @Column(name = "activity_type", length = 4096)
    private String activityType;

    @Column(name = "full_name_licensee", length = 4096)
    private String fullNameLicensee;

    @Column(name = "abbreviated_name_licensee", length = 4096)
    private String abbreviatedNameLicensee;

    @Column(name = "brand_name_licensee", length = 4096)
    private String brandNameLicensee;

    @Column(name = "form", length = 4096)
    private String form;

    @Column(name = "address", length = 4096)
    private String address;

    @Column(name = "ogrn", length = 4096)
    private String ogrn;

    @Column(name = "inn", length = 4096)
    private String inn;

    @Column(name = "number", length = 4096)
    private String number;

    @Column(name = "date", length = 4096)
    private String date;

    @Column(name = "number_orders", length = 4096)
    private String numberOrders;

    @Column(name = "date_order", length = 4096)
    private String dateOrder;

    @Column(name = "date_register", length = 4096)
    private String dateRegister;

    @Column(name = "number_duplicate", length = 4096)
    private String numberDuplicate;

    @Column(name = "date_duplicate", length = 4096)
    private String dateDuplicate;

    @Column(name = "termination", length = 4096)
    private String termination;

    @Column(name = "date_termination", length = 4096)
    private String dateTermination;

    @Column(name = "information", length = 4096)
    private String information;

    @Column(name = "information_regulations", length = 4096)
    private String informationRegulations;

    @Column(name = "information_suspension_resumption", length = 4096)
    private String informationSuspensionResumption;

    @Column(name = "information_cancellation", length = 4096)
    private String informationCancellation;

    @Column(name = "information_reissuing", length = 4096)
    private String informationReissuing;

    @OneToMany(mappedBy = "clearRosZdravNadzor", cascade = {CascadeType.ALL})
    private List<ClearAddress> clearAddresses = new ArrayList<>();
    @Column(name = "ros_zdrav_nadzor_guid", length = 4096)
    private Long parentGuid;

    @Column(name = "region_name", length = 4096)
    private String regionName;

    public ClearRosZdravNadzor(EntityRosMinZdravNadzor entityRosMinZdravNadzor){
        this.name = entityRosMinZdravNadzor.getName();
        this.activityType = entityRosMinZdravNadzor.getActivityType();
        this.fullNameLicensee = entityRosMinZdravNadzor.getFullNameLicensee();
        this.abbreviatedNameLicensee = entityRosMinZdravNadzor.getAbbreviatedNameLicensee();
        this.brandNameLicensee = entityRosMinZdravNadzor.getBrandNameLicensee();
        this.form = entityRosMinZdravNadzor.getForm();
        this.address = entityRosMinZdravNadzor.getAddress();
        this.ogrn = entityRosMinZdravNadzor.getOgrn();
        this.inn = entityRosMinZdravNadzor.getInn();
        this.number = entityRosMinZdravNadzor.getNumber();
        this.date = entityRosMinZdravNadzor.getDate();
        this.numberOrders = entityRosMinZdravNadzor.getNumberOrders();
        this.dateOrder = entityRosMinZdravNadzor.getDateOrder();
        this.dateRegister = entityRosMinZdravNadzor.getDateRegister();
        this.numberDuplicate = entityRosMinZdravNadzor.getNumberDuplicate();
        this.dateDuplicate =entityRosMinZdravNadzor.getDateDuplicate();
        this.termination = entityRosMinZdravNadzor.getTermination();
        this.dateTermination = entityRosMinZdravNadzor.getDateTermination();
        this.information = entityRosMinZdravNadzor.getInformation();
        this.informationRegulations = entityRosMinZdravNadzor.getInformationRegulations();
        this.informationSuspensionResumption = entityRosMinZdravNadzor.getInformationSuspensionResumption();
        this.informationCancellation = entityRosMinZdravNadzor.getInformationCancellation();
        this.informationReissuing = entityRosMinZdravNadzor.getInformationReissuing();
        this.parentGuid=entityRosMinZdravNadzor.getGuid();
//        List<ClearAddress> clearAddresses = new ArrayList<>();
//                entityRosMinZdravNadzor.getAddressPlace().forEach(address1 -> clearAddresses.add(new ClearAddress(address1,this)));
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}