package com.example.zayve_test.ui.browse_friends;


public class EachUserProfile
{
    private String userId, userName, userAbout, profilePicture,firstInterest,secondInterest,thirdInterest,fourthInterest,fifthInterest;

    public EachUserProfile(String userId, String userName, String userAbout,
                           String firstInterest, String secondInterest, String thirdInterest,
                           String fourthInterest, String fifthInterest) {
        this.userId = userId;
        this.userName = userName;
        this.userAbout = userAbout;
        this.firstInterest = firstInterest;
        this.secondInterest = secondInterest;
        this.thirdInterest = thirdInterest;
        this.fourthInterest = fourthInterest;
        this.fifthInterest = fifthInterest;
    }

    public String getUserAbout() {
        return userAbout;
    }

    public void setUserAbout(String userAbout) {
        this.userAbout = userAbout;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getFirstInterest() {
        return firstInterest;
    }

    public void setFirstInterest(String firstInterest) {
        this.firstInterest = firstInterest;
    }

    public String getSecondInterest() {
        return secondInterest;
    }

    public void setSecondInterest(String secondInterest) {
        this.secondInterest = secondInterest;
    }

    public String getThirdInterest() {
        return thirdInterest;
    }

    public void setThirdInterest(String thirdInterest) {
        this.thirdInterest = thirdInterest;
    }

    public String getFourthInterest() {
        return fourthInterest;
    }

    public void setFourthInterest(String fourthInterest) {
        this.fourthInterest = fourthInterest;
    }

    public String getFifthInterest() {
        return fifthInterest;
    }

    public void setFifthInterest(String fifthInterest) {
        this.fifthInterest = fifthInterest;
    }
}
