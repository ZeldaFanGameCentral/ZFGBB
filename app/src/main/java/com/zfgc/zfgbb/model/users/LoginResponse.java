package com.zfgc.zfgbb.model.users;

import com.zfgc.zfgbb.model.User;

public record LoginResponse(String accessToken, String refreshToken, boolean stayLoggedIn, User user) {
}
