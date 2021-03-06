package com.aston.health.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "result", schema = "public", catalog = "cross")
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@NoArgsConstructor
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guid;

    @OneToOne
    private ClearFfoms clearFfoms;
    @Column(name = "ffoms_serial_code")
    private Integer ffomsSerialNumber;
    @OneToOne
    private ClearRosminzdrav clearRosminzdrav;
    @Column(name = "clearRosminzdrav_serial_code")
    private Integer clearRosminzdravSerialNumber;
    @OneToOne
    private ClearRosZdravNadzor clearRosZdravNadzor;
    @Column(name = "clearRosZdravNadzor_serial_code")
    private Integer clearRosZdravNadzorSerialNumber;

    private Integer countTe = 0;
}
