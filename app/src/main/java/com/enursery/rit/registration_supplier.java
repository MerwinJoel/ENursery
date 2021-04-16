package com.enursery.rit;

public class registration_supplier {
    String userid;
    String uname;
    String email;
    String password;
    String usertype;

    public registration_supplier() {
    }

    public registration_supplier(String userid, String uname, String email, String password, String usertype) {
        this.uname = uname;
        this.email = email;
        this.password = password;
        this.userid = userid;
        this.usertype = usertype;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


