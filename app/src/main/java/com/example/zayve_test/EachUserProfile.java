package com.example.zayve_test;


public class EachUserProfile
{
    private String userName,profilePicture,firstInterest,secondInterest,thirdInterest,fourthInterest,fifthInterest;

    public EachUserProfile(String userName, String profilePicture, String firstInterest, String secondInterest,
                           String thirdInterest, String fourthInterest, String fifthInterest) {
        this.userName = userName;
        this.profilePicture = profilePicture;
        this.firstInterest = firstInterest;
        this.secondInterest = secondInterest;
        this.thirdInterest = thirdInterest;
        this.fourthInterest = fourthInterest;
        this.fifthInterest = fifthInterest;
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
