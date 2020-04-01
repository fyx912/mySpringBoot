package com.ding.domain;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author tintin
 * @version V1.0
 * @Description
 * @@copyright
 * @ClassName SysRole
 * @date 2020-03-09 11:11
 */
public class SysRole {
    private int rid;    //角色ID
    private int parent_rid;
    private String role_name;   //角色名称
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date create_date;   //创建时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date update_date ;  //更新时间
    private String description; //描述
    private String create_by;   //创建人
    private String update_by;   //更新人

    public SysRole() {
    }

    public SysRole(int rid, int parent_rid, String role_name, Date create_date, Date update_date, String description, String create_by, String update_by) {
        this.rid = rid;
        this.parent_rid = parent_rid;
        this.role_name = role_name;
        this.create_date = create_date;
        this.update_date = update_date;
        this.description = description;
        this.create_by = create_by;
        this.update_by = update_by;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getParent_rid() {
        return parent_rid;
    }

    public void setParent_rid(int parent_rid) {
        this.parent_rid = parent_rid;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    @Override
    public String toString() {
        return "SysRole{" +
                "rid=" + rid +
                ", parent_rid=" + parent_rid +
                ", role_name='" + role_name + '\'' +
                ", create_date=" + create_date +
                ", update_date=" + update_date +
                ", description='" + description + '\'' +
                ", create_by='" + create_by + '\'' +
                ", update_by='" + update_by + '\'' +
                '}';
    }
}
