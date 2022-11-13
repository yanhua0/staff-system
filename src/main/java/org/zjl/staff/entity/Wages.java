package org.zjl.staff.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * (Wages)实体类
 * @since 2022-11-12 16:38:24
 */
@Table(name = "wages")
@Getter
@Setter
public class Wages{
        
    @Column(name = "id")
    @Id
    private Integer id;
    @Column(name = "dep_name")
    private String depName;
    @Column(name = "staff_name")
    private String staffName;
        
    @Column(name = "id_number")
    private String idNumber;
        
    @Column(name = "bank_desc")
    private String bankDesc;
        
    @Column(name = "bank_number")
    private String bankNumber;
        
    @Column(name = "cal_time")
    private String calTime;
        
    @Column(name = "money")
    private String money;
        
    @Column(name = "phone_number")
    private String phoneNumber;
        
    @Column(name = "settlement_time")
    private String settlementTime;
        
    @Column(name = "days")
    private Integer days;

    @Column(name = "enabled")
    private Integer enabled;
    @Column(name = "description")
    private String description;
}

