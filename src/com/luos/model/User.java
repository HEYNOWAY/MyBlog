package com.luos.model;

/**
 * 用户类
 *
 * Created by luos on 2016/10/29.
 */
public class User {

    /**
     * 用户Id
     */
    private int userID;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String passWord;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 头像照片路径
     */
    private String imageName;

    /**
     * 心情描述
     */
    private String mood;

    public User(){

    }

    public User(String userName, String passWord){
        this.userName = userName;
        this.passWord = passWord;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    @Override
    public String toString() {
        return "useId : "+userID
                +"\npassWord : "+passWord
                +"\nnickName :　"+nickName
                +"\nimageName : "+imageName
                +"\nmodd : "+mood;
    }
}
