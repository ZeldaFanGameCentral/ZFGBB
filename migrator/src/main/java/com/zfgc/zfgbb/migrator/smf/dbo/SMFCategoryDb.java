package com.zfgc.zfgbb.migrator.smf.dbo;

import jakarta.annotation.Generated;

public class SMFCategoryDb {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687243228-04:00", comments="Source field: smf_1categories.id_cat")
    private Integer idCat;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687268927-04:00", comments="Source field: smf_1categories.cat_order")
    private Byte catOrder;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687299396-04:00", comments="Source field: smf_1categories.name")
    private String name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687320866-04:00", comments="Source field: smf_1categories.can_collapse")
    private Boolean canCollapse;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687254288-04:00", comments="Source field: smf_1categories.id_cat")
    public Integer getIdCat() {
        return idCat;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687262457-04:00", comments="Source field: smf_1categories.id_cat")
    public void setIdCat(Integer idCat) {
        this.idCat = idCat;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687285597-04:00", comments="Source field: smf_1categories.cat_order")
    public Byte getCatOrder() {
        return catOrder;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687293286-04:00", comments="Source field: smf_1categories.cat_order")
    public void setCatOrder(Byte catOrder) {
        this.catOrder = catOrder;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687306346-04:00", comments="Source field: smf_1categories.name")
    public String getName() {
        return name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687314646-04:00", comments="Source field: smf_1categories.name")
    public void setName(String name) {
        this.name = name;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687327845-04:00", comments="Source field: smf_1categories.can_collapse")
    public Boolean getCanCollapse() {
        return canCollapse;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-05-02T06:12:59.687334645-04:00", comments="Source field: smf_1categories.can_collapse")
    public void setCanCollapse(Boolean canCollapse) {
        this.canCollapse = canCollapse;
    }
}