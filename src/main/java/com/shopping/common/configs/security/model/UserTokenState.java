package com.shopping.common.configs.security.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserTokenState {
    private String access_token;
    private int expires_in;
    private long user_id;

    public UserTokenState() {
        this.access_token = null;
        this.expires_in = 0;
        this.user_id=0;
    }

    public UserTokenState(String access_token, int expires_in) {
        this.access_token = access_token;
        this.expires_in = expires_in;
    }

    public UserTokenState(String access_token, int expires_in, long user_id) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.user_id = user_id;
    }
}
