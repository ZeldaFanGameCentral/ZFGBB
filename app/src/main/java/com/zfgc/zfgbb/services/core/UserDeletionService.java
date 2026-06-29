package com.zfgc.zfgbb.services.core;

import com.zfgc.zfgbb.services.core.deletion.UserDataHandler;
import com.zfgc.zfgbb.services.core.deletion.CoreUserDataHandler;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zfgc.zfgbb.dao.users.UserDao;
import com.zfgc.zfgbb.dataprovider.users.UserDataProvider;
import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.mappers.custom.UserDeletionMapper;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.users.DeletionMode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDeletionService {

    private final List<UserDataHandler> dataHandlers;
    private final CoreUserDataHandler coreUserDataHandler;
    private final UserDeletionMapper deletionMapper;
    private final UserDataProvider userDataProvider;
    private final UserDao userDao;

    public void deleteUser(Integer userId, DeletionMode mode, User requester) {
        guard(userId, requester);
        Integer sentinelId = coreUserDataHandler.ensureSentinelUser();
        switch (mode == null ? DeletionMode.ANONYMIZE : mode) {
            case ANONYMIZE -> anonymize(userId, sentinelId);
            case PURGE -> purge(userId, sentinelId);
        }
        userDao.getMapper().deleteByPrimaryKey(userId);
    }

    private void guard(Integer userId, User requester) {
        if (userId == null) {
            throw new ZfgcInvalidRequestException("userId is required.");
        }
        if (requester != null && userId.equals(requester.getUserId())) {
            throw new ZfgcInvalidRequestException("You cannot delete your own account.");
        }
        if (userDataProvider.findUser(userId).isEmpty()) {
            throw new ZfgcNotFoundException();
        }
        if (deletionMapper.isSiteAdmin(userId)) {
            throw new ZfgcInvalidRequestException("Site administrators cannot be deleted.");
        }
        if (deletionMapper.findUserIdBySsoKey("__deleted__").filter(userId::equals).isPresent()) {
            throw new ZfgcInvalidRequestException("The deleted-user account cannot be deleted.");
        }
    }

    private void anonymize(Integer userId, Integer sentinelId) {
        for (UserDataHandler handler : dataHandlers) {
            handler.anonymizeData(userId);
        }
    }

    private void purge(Integer userId, Integer sentinelId) {
        for (UserDataHandler handler : dataHandlers) {
            handler.hardDeleteData(userId);
        }
    }

    public record UserDeletionRequest(Integer userId, DeletionMode mode) {}
}
