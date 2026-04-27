package com.zfgc.zfgbb.model.users;

import com.zfgc.zfgbb.model.User;

public record LoginResponse(String accessToken, String refreshToken, User user) {
}
