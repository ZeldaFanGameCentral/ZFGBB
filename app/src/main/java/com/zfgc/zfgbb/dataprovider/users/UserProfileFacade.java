package com.zfgc.zfgbb.dataprovider.users;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.authorization.UnfilteredBoardRead;
import com.zfgc.zfgbb.dataprovider.loadoption.UserLoadOptions;
import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.ContentRenderingService;
import com.zfgc.zfgbb.dbo.AwardDbo;
import com.zfgc.zfgbb.dbo.AwardDboExample;
import com.zfgc.zfgbb.dbo.UserAggregateDbo;
import com.zfgc.zfgbb.dbo.UserAwardDbo;
import com.zfgc.zfgbb.dbo.UserAwardDboExample;
import com.zfgc.zfgbb.dbo.UserPermissionViewDbo;
import com.zfgc.zfgbb.dbo.UserPermissionViewDboExample;
import com.zfgc.zfgbb.dbo.UserReactionSummaryViewDbo;
import com.zfgc.zfgbb.dbo.UserReactionSummaryViewDboExample;
import com.zfgc.zfgbb.dao.users.AwardDao;
import com.zfgc.zfgbb.dao.users.UserAwardDao;
import com.zfgc.zfgbb.dao.users.UserPermissionViewDao;
import com.zfgc.zfgbb.dao.users.UserReactionSummaryViewDao;
import com.zfgc.zfgbb.dao.forum.MessageDao;
import com.zfgc.zfgbb.mappers.custom.MessagePostCountMapper;
import com.zfgc.zfgbb.dao.users.UserDao;
import com.zfgc.zfgbb.mapstruct.users.UserSettingsMap;
import com.zfgc.zfgbb.mapstruct.users.ReactionSummaryMap;
import com.zfgc.zfgbb.mapstruct.users.AwardMap;
import com.zfgc.zfgbb.mapstruct.users.AvatarMap;
import com.zfgc.zfgbb.mapstruct.users.PermissionMap;
import com.zfgc.zfgbb.mapstruct.users.UserBioInfoMap;
import com.zfgc.zfgbb.mapstruct.users.UserContactInfoMap;
import com.zfgc.zfgbb.mapstruct.users.UserMap;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.users.Award;
import com.zfgc.zfgbb.model.users.Permission;
import com.zfgc.zfgbb.model.users.ReactionSummary;
import com.zfgc.zfgbb.model.users.UserBioInfo;
import com.zfgc.zfgbb.model.users.UserSettings;
import com.zfgc.zfgbb.model.users.UserContactInfo;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@UnfilteredBoardRead("counts only guest-visible boards")
public class UserProfileFacade {

	private final UserDao userDao;

	private final UserPermissionViewDao userPermissionViewDao;

	private final ContentRenderingService contentRenderingService;

	private final UserMap userMap;

	private final UserBioInfoMap userBioInfoMap;

	private final UserContactInfoMap userContactInfoMap;

	private final AvatarMap avatarMap;

	private final PermissionMap permissionMap;

	private final AwardMap awardMap;

	private final UserSettingsMap userSettingsMap;

	private final ReactionSummaryMap reactionSummaryMap;

	private final UserReactionSummaryViewDao reactionSummaryDao;

	private final UserAwardDao userAwardDao;

	private final AwardDao awardDao;

	private final MessageDao messageDao;

	private final GuestPermissionDataProvider guestPermissionDataProvider;

	public List<Integer> guestVisibleBoardIds() {
		return guestPermissionDataProvider.guestVisibleBoardIds();
	}

	public Map<Integer, User> loadUsersByIds(Collection<Integer> userIds, UserLoadOptions loadOptions) {
		if (userIds == null || userIds.isEmpty()) return Collections.emptyMap();
		List<Integer> distinctIds = userIds.stream().filter(id -> id != null).distinct().toList();
		if (distinctIds.isEmpty()) return Collections.emptyMap();

		List<UserAggregateDbo> aggregates = userDao.hydrate(distinctIds);
		if (aggregates.isEmpty()) return Collections.emptyMap();

		Map<Integer, List<Permission>> permissionsByUserId = Collections.emptyMap();
		if (loadOptions.loadPermissions()) {
			UserPermissionViewDboExample permissionEx = new UserPermissionViewDboExample();
			permissionEx.createCriteria().andUserIdIn(distinctIds);
			permissionsByUserId = userPermissionViewDao.get(permissionEx).stream()
					.collect(Collectors.groupingBy(UserPermissionViewDbo::getUserId,
							Collectors.mapping(permissionMap::toModel, Collectors.toList())));
		}

		Map<Integer, ReactionSummary> reactionByUserId = Collections.emptyMap();
		if (loadOptions.loadReactions()) {
			UserReactionSummaryViewDboExample reactionEx = new UserReactionSummaryViewDboExample();
			reactionEx.createCriteria().andUserIdIn(distinctIds);
			reactionByUserId = reactionSummaryDao.get(reactionEx).stream()
					.collect(Collectors.toMap(UserReactionSummaryViewDbo::getUserId, reactionSummaryMap::toModel));
		}

		Map<Integer, Integer> postCountByOwnerId = Collections.emptyMap();
		List<Integer> guestVisibleBoardIds = guestVisibleBoardIds();
		if (!guestVisibleBoardIds.isEmpty() && loadOptions.loadBio()) {
			postCountByOwnerId = messageDao
					.postCountsByOwnerWithinBoards(distinctIds, guestVisibleBoardIds).stream()
					.collect(Collectors.toMap(MessagePostCountMapper.OwnerPostCount::getOwnerId,
							ownerPostCount -> (int) ownerPostCount.getPostCount()));
		}

		Map<Integer, List<Award>> awardsByUserId = Collections.emptyMap();
		if (loadOptions.loadAwards()) {
			UserAwardDboExample grantEx = new UserAwardDboExample();
			grantEx.createCriteria().andUserIdIn(distinctIds);
			grantEx.setOrderByClause("granted_ts desc");
			List<UserAwardDbo> grants = userAwardDao.get(grantEx);
			if (!grants.isEmpty()) {
				List<Integer> awardIds = grants.stream().map(UserAwardDbo::getAwardId).distinct().toList();
				AwardDboExample awardEx = new AwardDboExample();
				awardEx.createCriteria().andAwardIdIn(awardIds);
				Map<Integer, AwardDbo> awardsById = awardDao.get(awardEx).stream()
						.collect(Collectors.toMap(AwardDbo::getAwardId, awardDbo -> awardDbo));
				awardsByUserId = grants.stream()
						.filter(grant -> awardsById.containsKey(grant.getAwardId()))
						.collect(Collectors.groupingBy(UserAwardDbo::getUserId,
								Collectors.mapping(grant -> awardMap.toGrantedAward(awardsById.get(grant.getAwardId()), grant), Collectors.toList())));
			}
		}

		Map<Integer, User> users = new HashMap<>();
		for (UserAggregateDbo agg : aggregates) {
			Integer userId = agg.getUser().getUserId();
			UserBioInfo bioInfo = null;
			if (agg.getBio() != null && loadOptions.loadBio()) {
				bioInfo = userBioInfoMap.toModel(agg.getBio())
						.toBuilder()
						.postCount(postCountByOwnerId.getOrDefault(userId, 0))
						.signatureParsed(contentRenderingService.render(agg.getBio().getSignature(),
										ContentFormat.BBCODE, ContentScope.SIGNATURE))
						.avatar(agg.getAvatar() != null && loadOptions.loadAvatar() ? avatarMap.toModel(agg.getAvatar()) : null)
						.build();
			}

			UserContactInfo contactInfo = null;
			if (agg.getContact() != null && agg.getEmail() != null && loadOptions.loadContactInfo()) {
				contactInfo = userContactInfoMap.toModel(agg.getContact(), agg.getEmail());
			}

			UserSettings settings = null;
			if (loadOptions.loadSettings()) {
				settings = agg.getSettings() != null
						? userSettingsMap.toModel(agg.getSettings())
						: new UserSettings();
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

}
