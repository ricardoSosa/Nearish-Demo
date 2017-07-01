package com.enterprise.ij.nearish.Models;

/**
 * Created by juan on 29/06/2017.
 */

public class Usuario {

    private static Usuario user;
    private String email=null;
    private String password=null;
    private String name=null;
    private String token=null;
    private boolean firstTime=true;

    private Usuario(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    private Usuario(String email, String password) {
        this.password = password;
        this.email = email;
    }

    public Usuario() {
    }

    public static Usuario getUserInstance(String email, String password, String name){
        if(user == null){
            user = new Usuario(email,password,name);
        }
        return user;
    }

    public static Usuario getUserInstance(String name, String password){
        if(user == null){
            user = new Usuario(name,password);
        }
        return user;
    }

    public static Usuario getUserInstance(){


        if(user == null){
            user = new Usuario();
        }
        return user;

    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public void clearData(){
        setToken("");
        setemail("");
        setpassword("");
        setname("");
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
