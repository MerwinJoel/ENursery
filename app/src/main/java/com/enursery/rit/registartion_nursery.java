package com.enursery.rit;

public class registartion_nursery {
    String uname;
    String email;
    String password;
    String cpassword;
public registartion_nursery(){
    }

   public registartion_nursery(String uname, String email, String password, String cpassword){
    uname=uname;
    email=email;
    password=password;
    cpassword=cpassword;
   }

    public String getUname() {
        return uname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCpassword() {
        return cpassword;
    }
}
