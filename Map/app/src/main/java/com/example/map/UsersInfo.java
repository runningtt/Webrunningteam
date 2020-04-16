package com.example.map;

public class UsersInfo {
    public String name;
    public String password;

    public UsersInfo(String name, String password){
        this.name = name;
        this.password = password;
    }

    public UsersInfo() {

    }

    public String getName(){
        return name;
    }

    public void setName(){
        this.name = name;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(){
        this.password = password;
    }

    @Override
    public String toString(){
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
