package com.example.zayve_test.ui.search_by_interest;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class EachUserInFragmentSearchResultsArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private EachUserInFragmentSearchResultsArgs() {
  }

  private EachUserInFragmentSearchResultsArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static EachUserInFragmentSearchResultsArgs fromBundle(@NonNull Bundle bundle) {
    EachUserInFragmentSearchResultsArgs __result = new EachUserInFragmentSearchResultsArgs();
    bundle.setClassLoader(EachUserInFragmentSearchResultsArgs.class.getClassLoader());
    if (bundle.containsKey("userID")) {
      String userID;
      userID = bundle.getString("userID");
      if (userID == null) {
        throw new IllegalArgumentException("Argument \"userID\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("userID", userID);
    } else {
      throw new IllegalArgumentException("Required argument \"userID\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getUserID() {
    return (String) arguments.get("userID");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("userID")) {
      String userID = (String) arguments.get("userID");
      __result.putString("userID", userID);
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    EachUserInFragmentSearchResultsArgs that = (EachUserInFragmentSearchResultsArgs) object;
    if (arguments.containsKey("userID") != that.arguments.containsKey("userID")) {
      return false;
    }
    if (getUserID() != null ? !getUserID().equals(that.getUserID()) : that.getUserID() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getUserID() != null ? getUserID().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "EachUserInFragmentSearchResultsArgs{"
        + "userID=" + getUserID()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(EachUserInFragmentSearchResultsArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder(@NonNull String userID) {
      if (userID == null) {
        throw new IllegalArgumentException("Argument \"userID\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("userID", userID);
    }

    @NonNull
    public EachUserInFragmentSearchResultsArgs build() {
      EachUserInFragmentSearchResultsArgs result = new EachUserInFragmentSearchResultsArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setUserID(@NonNull String userID) {
      if (userID == null) {
        throw new IllegalArgumentException("Argument \"userID\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("userID", userID);
      return this;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getUserID() {
      return (String) arguments.get("userID");
    }
  }
}
