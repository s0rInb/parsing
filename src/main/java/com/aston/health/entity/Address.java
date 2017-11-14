package com.aston.health.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Table(name = "address")
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long guid;

    @Column(name = "address",length = 4096)
    String address  =  "";
    @Column(name = "index",length = 4096)
    String index =  "";
    @Column(name = "region",length = 4096)
    String region =  "";
    @Column(name = "city",length = 4096)
    String city =  "";
    @Column(name = "street",length = 4096)
    String street =  "";

//    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//            @JoinTable(name = "address_work",
//            joinColumns = @JoinColumn(name = "address_guid"),
//            inverseJoinColumns = @JoinColumn(name = "work_guid"))
//    List<Works> works = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "ros_min_zdrav_nadzor_guid")
    EntityRosMinZdravNadzor entityRosMinZdravNadzor;
}
