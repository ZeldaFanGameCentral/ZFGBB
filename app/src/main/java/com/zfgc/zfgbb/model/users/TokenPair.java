package com.zfgc.zfgbb.model.users;

public record TokenPair(String accessToken, String refreshToken, boolean stayLoggedIn) {
}
