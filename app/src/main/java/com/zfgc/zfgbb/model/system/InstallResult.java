package com.zfgc.zfgbb.model.system;

import com.zfgc.zfgbb.model.User;

public record InstallResult(InstallResponse response, User admin) {
}
