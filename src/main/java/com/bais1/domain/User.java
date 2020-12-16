package com.bais1.domain;

import java.io.Serializable;

public class User implements Serializable {
    private String userAccount;
    private String passwd;
    private String userName;
    private int age;
    private Gender gender;//GB2261-80国标代码 0、未知，1、男，2、女，3、女改男，4、男改女，5、其他
    private String email;
    private UserRole userRole;
    private boolean isEnable;
    private String activeCode;

    public User(String userAccount, String userName) {
        this.userAccount = userAccount;
        this.userName = userName;
    }

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "userAccount='" + userAccount + '\'' +
                ", passwd='" + passwd + '\'' +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", userRole=" + userRole +
                ", isEnable=" + isEnable +
                ", activeCode='" + activeCode + '\'' +
                '}';
    }

    public User(String userAccount, String passwd, String userName, int age, Gender gender, String email, UserRole userRole, boolean isEnable, String activeCode) {
        this.userAccount = userAccount;
        this.passwd = passwd;
        this.userName = userName;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.userRole = userRole;
        this.isEnable = isEnable;
        this.activeCode = activeCode;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }
}


