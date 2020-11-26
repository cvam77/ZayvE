package com.example.zayve_test.search_by_interest;

import java.util.ArrayList;

public class UserInterests {
    ArrayList<String> interests;

    public UserInterests(){}

    public UserInterests(ArrayList<String> interests)
    {
        this.interests = interests;
    }

    public ArrayList<String> getInterests()
    {
        return interests;
    }
}
