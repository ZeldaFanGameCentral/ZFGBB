package com.zfgc.zfgbb.services.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.zfgc.zfgbb.dataprovider.users.UserDataProvider;
import com.zfgc.zfgbb.exception.ZfgcInvalidRequestException;
import com.zfgc.zfgbb.exception.ZfgcNotFoundException;
import com.zfgc.zfgbb.dao.users.UserErasureDao;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.users.DeletionMode;
import com.zfgc.zfgbb.services.users.deletion.CoreUserDataHandler;
import com.zfgc.zfgbb.services.users.deletion.UserDataHandler;
import com.zfgc.zfgbb.services.forum.ForumService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDeletionService {

    private final List<UserDataHandler> dataHandlers;
    private final CoreUserDataHandler coreUserDataHandler;
    private final UserErasureDao userErasureDao;
    private final UserDataProvider userDataProvider;
    private final ForumService forumService;
    private final PlatformTransactionManager transactionManager;

    public void deleteUser(Integer userId, DeletionMode mode, User requester) {
        guard(userId, requester);
        DeletionMode effectiveMode = mode == null ? DeletionMode.ANONYMIZE : mode;
        List<String> releasedBlobPaths = new TransactionTemplate(transactionManager)
                .execute(transaction -> eraseUserData(userId, effectiveMode));
        coreUserDataHandler.deleteBlobFiles(releasedBlobPaths);
        forumService.evictUnfilteredForumCache();
    }

    private List<String> eraseUserData(Integer userId, DeletionMode mode) {
        List<Integer> emailAddressIds = userErasureDao.findEmailAddressIds(userId);
        List<String> releasedBlobPaths = new ArrayList<>();
        for (UserDataHandler handler : dataHandlers)
            releasedBlobPaths.addAll(switch (mode) {
                case ANONYMIZE -> handler.anonymizeData(userId, Optional.empty());
                case PURGE -> handler.purgeData(userId, Optional.empty());
            });
        coreUserDataHandler.neutralizeUserIdentity(userId);
        coreUserDataHandler.releaseEmailAddresses(emailAddressIds);
        return releasedBlobPaths;
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
        if (userErasureDao.isSiteAdmin(userId)) {
            throw new ZfgcInvalidRequestException("Site administrators cannot be deleted.");
        }
        if (userErasureDao.findUserIdBySsoKey("__deleted__").filter(userId::equals).isPresent()) {
            throw new ZfgcInvalidRequestException("The deleted-user account cannot be deleted.");
        }
    }

    public record UserDeletionRequest(Integer userId, DeletionMode mode) {}
}
