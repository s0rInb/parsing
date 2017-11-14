package com.aston.health.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rosminzdrav", schema = "public", catalog = "cross")
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
public class EntityRosminzdrav {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guid;


    @Column(name = "sys_recordid")
    private Long sys_recordid;

    @Column(name = "create_date", length = 1024)
    private String createDate;

    @Column(name = "delete_date", length = 1024)
    private String deleteDate;

    @Column(name = "delete_reason", length = 1024)
    private String deleteReason;

    @Column(name = "dictionary_id", length = 1024)
    private String dictionaryId;

    @Column(name = "founder", length = 1024)
    private String founder;

    @Column(name = "id", length = 1024)
    private String id;

    @Column(name = "inn", length = 1024)
    private String inn;

    @Column(name = "kpp", length = 1024)
    private String kpp;

    @Column(name = "medical_subject_id", length = 1024)
    private String medicalSubjectId;

    @Column(name = "medical_subject_name", length = 1024)
    private String medicalSubjectName;

    @Column(name = "mo_agency_kind", length = 1024)
    private String moAgencyKind;

    @Column(name = "mo_dept_name", length = 1024)
    private String moDeptName;

    @Column(name = "mo_level", length = 1024)
    private String moLevel;

    @Column(name = "modify_date", length = 1024)
    private String modifyDate;

    @Column(name = "name_full", length = 1024)
    private String nameFull;

    @Column(name = "name_short", length = 1024)
    private String nameShort;

    @Column(name = "ogrn", length = 1024)
    private String ogrn;

    @Column(name = "oid", length = 1024)
    private String oid;

    @Column(name = "old_oid", length = 1024)
    private String oldOid;

    @Column(name = "organization_type", length = 1024)
    private String organizationType;

    @Column(name = "parent_id", length = 1024)
    private String parentId;

    @Column(name = "profile_agency_kind", length = 1024)
    private String profileAgencyKind;

    @Column(name = "region_id", length = 1024)
    private String regionId;

    @Column(name = "region_name", length = 1024)
    private String regionName;

    @Column(name = "search_input", length = 1024)
    private String searchInput;

    @Column(name = "subscribed", length = 1024)
    private String subscribed;
}