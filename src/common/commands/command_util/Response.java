package common.commands.command_util;

import common.util.LoggingIn;

import java.io.Serializable;

public class Response implements Serializable {
    private String text;
    private LoggingIn loginStatus = LoggingIn.NOT_USED;

    public Response(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LoggingIn getLoginVerificationFlag() {
        return loginStatus;
    }

    public void setLoginStatus(LoggingIn loginStatus) {
        this.loginStatus = loginStatus;
    }
}
