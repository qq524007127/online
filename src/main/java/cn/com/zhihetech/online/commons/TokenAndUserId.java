package cn.com.zhihetech.online.commons;

/**
 * Created by YangDaiChun on 2015/12/22.
 */
public class TokenAndUserId {

    private String userID;
    private String Token;

    public TokenAndUserId(String userID, String token) {
        this.userID = userID;
        Token = token;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
