package com.zfgc.zfgbb.model.users;

public record HashedPassword(String hash, PasswordAlgo algo, String salt) {
}
