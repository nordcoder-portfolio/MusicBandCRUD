package common.util;

import java.io.Serializable;

import static common.util.AccountCard.Authorization.*;

public class AccountCard implements Serializable {
    public enum Authorization {
        AUTHORIZED,
        UNAUTHORIZED
    }
    private Authorization status = UNAUTHORIZED;
    private int userId = -1;
    private String username = "";

    public void setStatus(Authorization s) {
        status = s;
    }

    public void setUserId(int uId) {
        userId = uId;
    }

    public void setUsername(String un) {
        username = un;
    }

    public Authorization getStatus() {
        return status;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
