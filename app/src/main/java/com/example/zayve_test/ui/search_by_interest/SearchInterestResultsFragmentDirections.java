package com.example.zayve_test.ui.search_by_interest;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import com.example.zayve_test.R;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class SearchInterestResultsFragmentDirections {
  private SearchInterestResultsFragmentDirections() {
  }

  @NonNull
  public static ActionNavSearchToNavUser actionNavSearchToNavUser(@NonNull String userID) {
    return new ActionNavSearchToNavUser(userID);
  }

  public static class ActionNavSearchToNavUser implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionNavSearchToNavUser(@NonNull String userID) {
      if (userID == null) {
        throw new IllegalArgumentException("Argument \"userID\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("userID", userID);
    }

    @NonNull
    public ActionNavSearchToNavUser setUserID(@NonNull String userID) {
      if (userID == null) {
        throw new IllegalArgumentException("Argument \"userID\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("userID", userID);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("userID")) {
        String userID = (String) arguments.get("userID");
        __result.putString("userID", userID);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_nav_search_to_nav_user;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getUserID() {
      return (String) arguments.get("userID");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionNavSearchToNavUser that = (ActionNavSearchToNavUser) object;
      if (arguments.containsKey("userID") != that.arguments.containsKey("userID")) {
        return false;
      }
      if (getUserID() != null ? !getUserID().equals(that.getUserID()) : that.getUserID() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getUserID() != null ? getUserID().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionNavSearchToNavUser(actionId=" + getActionId() + "){"
          + "userID=" + getUserID()
          + "}";
    }
  }
}
