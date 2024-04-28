package com.store.footballstore.entity;

import io.jsonwebtoken.Claims;

/**
 * jwt验证消息
 * @author yuanlin
 * @create 2023/8/26 0026 15:59
 */
public class CheckResult {
    private int errCode;

    private boolean success;

    private Claims claims;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Claims getClaims() {
        return claims;
    }

    public void setClaims(Claims claims) {
        this.claims = claims;
    }
}
