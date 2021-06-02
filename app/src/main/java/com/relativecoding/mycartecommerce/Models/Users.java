package com.relativecoding.mycartecommerce.Models;

public class Users {
    String profilePic, username, mail, password, lastMessage,userId;

    String number, address;

    public Users(String profilePic, String username, String mail, String password, String lastMessage, String userId, String number, String address) {
        this.profilePic = profilePic;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.lastMessage = lastMessage;
        this.userId = userId;
        this.number = number;
        this.address = address;
    }
    //empty constructor for firebase

    public Users() {
    }

    //construtor for sign up


    public Users(String username, String mail, String password) {
        this.username = username;
        this.mail = mail;
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
