package com.gaoda.bean;

import java.io.Serializable;

public class UserBean implements Serializable {


    /**
     * phone_number : 17802127048
     * enduser_id : 985a2b5e33f211e889210090a2d108f7
     * is_active : true
     * app_id : 31925e61330111e886e2f8cab81d2c1a
     * binding_role : 2
     * email : null
     */

    private String phone_number;
    private String enduser_id;
    private boolean is_active;
    private String app_id;
    private int binding_role;
    private Object email;

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEnduser_id() {
        return enduser_id;
    }

    public void setEnduser_id(String enduser_id) {
        this.enduser_id = enduser_id;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public int getBinding_role() {
        return binding_role;
    }

    public void setBinding_role(int binding_role) {
        this.binding_role = binding_role;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }
}
