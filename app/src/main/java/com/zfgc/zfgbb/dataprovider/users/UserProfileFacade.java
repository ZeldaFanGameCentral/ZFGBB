package com.zfgc.zfgbb.dataprovider.users;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.config.loadoption.user.BasicUserLoadOptions;
import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.renderer.ContentRenderer;
import com.zfgc.zfgbb.dao.UserPermissionViewDao;
import com.zfgc.zfgbb.dbo.AwardDbo;
import com.zfgc.zfgbb.dbo.AwardDboExample;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDbo;
import com.zfgc.zfgbb.dbo.BoardPermissionViewDboExample;
import com.zfgc.zfgbb.dbo.UserAggregateDbo;
import com.zfgc.zfgbb.dbo.UserAwardDbo;
import com.zfgc.zfgbb.dbo.UserAwardDboExample;
import com.zfgc.zfgbb.dbo.UserPermissionViewDbo;
import com.zfgc.zfgbb.dbo.UserPermissionViewDboExample;
import com.zfgc.zfgbb.dbo.UserReactionSummaryViewDbo;
import com.zfgc.zfgbb.dbo.UserReactionSummaryViewDboExample;
import com.zfgc.zfgbb.mappers.AwardDboMapper;
import com.zfgc.zfgbb.mappers.BoardPermissionViewDboMapper;
import com.zfgc.zfgbb.mappers.UserAwardDboMapper;
import com.zfgc.zfgbb.mappers.UserReactionSummaryViewDboMapper;
import com.zfgc.zfgbb.mappers.custom.MessagePostCountMapper;
import com.zfgc.zfgbb.mappers.custom.UserProfileHydrationMapper;
import com.zfgc.zfgbb.mapstruct.users.AvatarMap;
import com.zfgc.zfgbb.mapstruct.users.PermissionMap;
import com.zfgc.zfgbb.mapstruct.users.UserBioInfoMap;
import com.zfgc.zfgbb.mapstruct.users.UserContactInfoMap;
import com.zfgc.zfgbb.mapstruct.users.UserMap;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.users.Award;
import com.zfgc.zfgbb.model.users.Permission;
import com.zfgc.zfgbb.model.users.ReactionSummary;
import com.zfgc.zfgbb.model.users.UserBioInfo;
import com.zfgc.zfgbb.model.users.UserSettings;
import com.zfgc.zfgbb.model.users.UserContactInfo;

@Component
public class UserProfileFacade {

    @Autowired
    private UserProfileHydrationMapper userProfileHydrationMapper;

    @Autowired
    private UserPermissionViewDao userPermissionDao;

    @Autowired
    private ContentRenderer contentRenderer;

    @Autowired
    private UserMap userMap;

    @Autowired
    private UserBioInfoMap userBioInfoMap;

    @Autowired
    private UserContactInfoMap userContactInfoMap;

    @Autowired
    private AvatarMap avatarMap;

    @Autowired
    private PermissionMap permissionMap;

    @Autowired
    private UserReactionSummaryViewDboMapper reactionSummaryMapper;

    @Autowired
    private UserAwardDboMapper userAwardMapper;

    @Autowired
    private AwardDboMapper awardMapper;

    @Autowired
    private BoardPermissionViewDboMapper boardPermissionViewDboMapper;

    @Autowired
    private MessagePostCountMapper messagePostCountMapper;

    private List<Integer> guestVisibleBoardIds() {
        List<Integer> guestPerms = User.guest().getPermissions().stream()
                .map(Permission::getPermissionId).toList();
        BoardPermissionViewDboExample ex = new BoardPermissionViewDboExample();
        ex.createCriteria().andPermissionIdIn(guestPerms);
        return boardPermissionViewDboMapper.selectByExample(ex).stream()
                .map(BoardPermissionViewDbo::getBoardId).distinct().collect(Collectors.toList());
    }

    public Map<Integer, User> loadUsersByIds(Collection<Integer> userIds, BasicUserLoadOptions loadOptions) {
        if (userIds == null || userIds.isEmpty()) return Collections.emptyMap();
        List<Integer> distinctIds = userIds.stream().filter(id -> id != null).distinct().toList();
        if (distinctIds.isEmpty()) return Collections.emptyMap();

        List<UserAggregateDbo> aggregates = userProfileHydrationMapper.hydrateUsers(distinctIds);
        if (aggregates.isEmpty()) return Collections.emptyMap();

        Map<Integer, List<Permission>> permissionsByUserId = Collections.emptyMap();
        if (Boolean.TRUE.equals(loadOptions.loadPermissions())) {
            UserPermissionViewDboExample permissionEx = new UserPermissionViewDboExample();
            permissionEx.createCriteria().andUserIdIn(distinctIds);
            permissionsByUserId = userPermissionDao.get(permissionEx).stream()
                    .collect(Collectors.groupingBy(UserPermissionViewDbo::getUserId,
                            Collectors.mapping(permissionMap::toModel, Collectors.toList())));
        }

        Map<Integer, ReactionSummary> reactionByUserId = Collections.emptyMap();
        if (Boolean.TRUE.equals(loadOptions.loadReactions())) {
            UserReactionSummaryViewDboExample reactionEx = new UserReactionSummaryViewDboExample();
            reactionEx.createCriteria().andUserIdIn(distinctIds);
            reactionByUserId = reactionSummaryMapper.selectByExample(reactionEx).stream()
                    .collect(Collectors.toMap(UserReactionSummaryViewDbo::getUserId, this::toReactionSummary));
        }

        Map<Integer, Integer> postCountByOwnerId = Collections.emptyMap();
        List<Integer> guestVisibleBoardIds = guestVisibleBoardIds();
        if (!guestVisibleBoardIds.isEmpty() && Boolean.TRUE.equals(loadOptions.loadBio())) {
            postCountByOwnerId = messagePostCountMapper
                    .postCountsByOwnerWithinBoards(distinctIds, guestVisibleBoardIds).stream()
                    .collect(Collectors.toMap(MessagePostCountMapper.OwnerPostCount::getOwnerId,
                            ownerPostCount -> (int) ownerPostCount.getPostCount()));
        }

        Map<Integer, List<Award>> awardsByUserId = Collections.emptyMap();
        if (Boolean.TRUE.equals(loadOptions.loadAwards())) {
            UserAwardDboExample grantEx = new UserAwardDboExample();
            grantEx.createCriteria().andUserIdIn(distinctIds);
            grantEx.setOrderByClause("granted_ts desc");
            List<UserAwardDbo> grants = userAwardMapper.selectByExample(grantEx);
            if (!grants.isEmpty()) {
                List<Integer> awardIds = grants.stream().map(UserAwardDbo::getAwardId).distinct().toList();
                AwardDboExample awardEx = new AwardDboExample();
                awardEx.createCriteria().andAwardIdIn(awardIds);
                Map<Integer, AwardDbo> awardsById = awardMapper.selectByExample(awardEx).stream()
                        .collect(Collectors.toMap(AwardDbo::getAwardId, awardDbo -> awardDbo));
                awardsByUserId = grants.stream()
                        .filter(grant -> awardsById.containsKey(grant.getAwardId()))
                        .collect(Collectors.groupingBy(UserAwardDbo::getUserId,
                                Collectors.mapping(grant -> toAward(grant, awardsById.get(grant.getAwardId())), Collectors.toList())));
            }
        }

        Map<Integer, User> users = new HashMap<>();
        for (UserAggregateDbo agg : aggregates) {
            Integer userId = agg.getUser().getUserId();
            UserBioInfo bioInfo = null;
            if (agg.getBio() != null && Boolean.TRUE.equals(loadOptions.loadBio())) {
                bioInfo = userBioInfoMap.toModel(agg.getBio())
                        .toBuilder()
                        .postCount(postCountByOwnerId.getOrDefault(userId, 0))
                        .signatureParsed(contentRenderer.render(agg.getBio().getSignature(), ContentFormat.BBCODE))
                        .avatar(agg.getAvatar() != null && Boolean.TRUE.equals(loadOptions.loadAvatar()) ? avatarMap.toModel(agg.getAvatar()) : null)
                        .build();
            }

            UserContactInfo contactInfo = null;
            if (agg.getContact() != null && agg.getEmail() != null && Boolean.TRUE.equals(loadOptions.loadContactInfo())) {
                contactInfo = userContactInfoMap.toModel(agg.getContact(), agg.getEmail());
            }

            UserSettings settings = null;
            if (agg.getSettings() != null && Boolean.TRUE.equals(loadOptions.loadSettings())) {
                settings = UserSettings.builder()
                        .userId(agg.getSettings().getUserId())
                        .theme(agg.getSettings().getTheme())
                        .smileySet(agg.getSettings().getSmileySet())
                        .notifyAnnouncementsFlag(agg.getSettings().getNotifyAnnouncementsFlag())
                        .notifySendBodyFlag(agg.getSettings().getNotifySendBodyFlag())
                        .sendHappyBirthdayFlag(agg.getSettings().getSendHappyBirthdayFlag())
                        .build();
            } else if (Boolean.TRUE.equals(loadOptions.loadSettings())) {
                settings = new UserSettings();
            }

            User user = userMap.toModel(agg.getUser())
                    .toBuilder()
                    .bioInfo(bioInfo)
                    .contactInfo(contactInfo)
                    .reactionSummary(reactionByUserId.get(userId))
                    .awards(awardsByUserId.getOrDefault(userId, Collections.emptyList()))
                    .permissions(permissionsByUserId.getOrDefault(userId, Collections.emptyList()))
                    .settings(settings)
                    .build();

            users.put(userId, user);
        }
        return users;
    }

    private ReactionSummary toReactionSummary(UserReactionSummaryViewDbo dbo) {
        ReactionSummary summary = new ReactionSummary();
        summary.setReputationPoints(dbo.getReputationPoints() == null ? 0 : dbo.getReputationPoints().intValue());
        summary.setPositiveCount(dbo.getPositiveCount() == null ? 0 : dbo.getPositiveCount().intValue());
        summary.setNegativeCount(dbo.getNegativeCount() == null ? 0 : dbo.getNegativeCount().intValue());
        summary.setReactionCount(dbo.getReactionCount() == null ? 0 : dbo.getReactionCount().intValue());
        return summary;
    }

    private Award toAward(UserAwardDbo grant, AwardDbo award) {
        Award model = new Award();
        model.setAwardId(award.getAwardId());
        model.setCode(award.getCode());
        model.setName(award.getName());
        model.setDescription(award.getDescription());
        model.setIcon(award.getIcon());
        model.setReason(grant.getReason());
        model.setContentEntityId(grant.getContentEntityId());
        model.setGrantedTs(grant.getGrantedTs() == null ? null : grant.getGrantedTs().toString());
        return model;
    }
}
