package com.zfgc.zfgbb.migrator.converters;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.zfgc.zfgbb.dbo.PermissionGroupDbo;
import com.zfgc.zfgbb.dbo.PermissionGroupAssocDbo;
import com.zfgc.zfgbb.dbo.PermissionGroupAssocDboExample;
import com.zfgc.zfgbb.dbo.UserPermissionGroupAssocDbo;
import com.zfgc.zfgbb.dbo.UserPermissionGroupAssocDboExample;
import com.zfgc.zfgbb.mappers.PermissionGroupDboMapper;
import com.zfgc.zfgbb.mappers.PermissionGroupAssocDboMapper;
import com.zfgc.zfgbb.mappers.UserPermissionGroupAssocDboMapper;
import com.zfgc.zfgbb.migrator.jobs.JobContextHolder;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.jobs.LegacyEntityType;
import com.zfgc.zfgbb.migrator.jobs.MigratorIdMapService;
import com.zfgc.zfgbb.migrator.jobs.MigratorPermissionService;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMembergroupDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMembersDb;
import com.zfgc.zfgbb.migrator.smf.queries.SmfResilientReadMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberGroupsConverter extends AbstractConverter<Void> {

	private final SmfResilientReadMapper resilientReads;
	private final PermissionGroupDboMapper groupMapper;
	private final UserPermissionGroupAssocDboMapper assocMapper;
	private final PermissionGroupAssocDboMapper groupPermissionMapper;
	private final MigratorPermissionService permissions;
	private final MigratorIdMapService idMap;
	private final Map<Integer, Set<String>> groupCodes = new HashMap<>();

	@Override
	public JobType getType() {
		return JobType.MEMBER_GROUPS;
	}

	@Override
	@Transactional
	public Void convertToZfgbb() {
		List<SMFMembergroupDb> smfGroups = resilientReads.selectAllMembergroups();
		smfGroups.sort(Comparator.comparing(SMFMembergroupDb::getIdGroup));

		groupCodes.clear();
		for (SMFMembergroupDb smfGroup : smfGroups) {
			Cancellable.check();
			convertGroup(smfGroup);
		}
		for (SMFMembergroupDb smfGroup : smfGroups) {
			linkParent(smfGroup);
		}
		convertMemberships();
		return null;
	}

	private void convertGroup(SMFMembergroupDb smfGroup) {
		PermissionGroupDbo group = new PermissionGroupDbo();
		group.setGroupName(HtmlUtils.htmlUnescape(smfGroup.getGroupName()));
		group.setDescription(blankToNull(HtmlUtils.htmlUnescape(smfGroup.getDescription())));
		group.setColor(blankToNull(smfGroup.getOnlineColor()));
		group.setMinPosts(smfGroup.getMinPosts());
		group.setMigrationHash(MigrationHasher.hash("group" + smfGroup.getIdGroup()
				+ group.getGroupName()
				+ group.getDescription()
				+ group.getColor()
				+ group.getMinPosts()
				+ smfGroup.getIdParent()));

		Integer existingId = idMap.lookupOrNull(LegacyEntityType.PERMISSION_GROUP, smfGroup.getIdGroup());
		Integer permissionGroupId;
		if (existingId == null) {
			groupMapper.insert(group);
			permissionGroupId = group.getPermissionGroupId();
			idMap.record(LegacyEntityType.PERMISSION_GROUP, smfGroup.getIdGroup(), permissionGroupId);
		} else {
			permissionGroupId = existingId;
			PermissionGroupDbo existing = groupMapper.selectByPrimaryKey(existingId);
			if (existing == null) {
				groupMapper.insert(group);
				permissionGroupId = group.getPermissionGroupId();
				idMap.record(LegacyEntityType.PERMISSION_GROUP, smfGroup.getIdGroup(), permissionGroupId);
			} else if (JobContextHolder.isForce()
					|| !Objects.equals(existing.getMigrationHash(), group.getMigrationHash())) {
				group.setPermissionGroupId(existingId);
				group.setStarImage(existing.getStarImage());
				group.setParentGroup(existing.getParentGroup());
				groupMapper.updateByPrimaryKey(group);
			}
		}

		Set<String> codes = resolveGroupCodes(smfGroup.getIdGroup());
		groupCodes.put(permissionGroupId, codes);
		writeGroupPermissions(permissionGroupId, codes);
	}

	private void linkParent(SMFMembergroupDb smfGroup) {
		if (smfGroup.getIdParent() == null || smfGroup.getIdParent() < 0) {
			return;
		}
		Integer groupId = idMap.lookupOrNull(LegacyEntityType.PERMISSION_GROUP, smfGroup.getIdGroup());
		Integer parentId = idMap.lookupOrNull(LegacyEntityType.PERMISSION_GROUP, smfGroup.getIdParent());
		if (groupId == null || parentId == null) {
			return;
		}
		PermissionGroupDbo group = groupMapper.selectByPrimaryKey(groupId);
		if (group != null && !Objects.equals(group.getParentGroup(), parentId)) {
			group.setParentGroup(parentId);
			groupMapper.updateByPrimaryKey(group);
		}
	}

	private Set<String> resolveGroupCodes(Integer legacyGroupId) {
		List<String> override = JobContextHolder.getGroupPermissionCodes(legacyGroupId);
		if (override != null) {
			return new LinkedHashSet<>(override);
		}
		return new LinkedHashSet<>(permissions.mapSmfGroupToCodes(legacyGroupId));
	}

	private void writeGroupPermissions(Integer permissionGroupId, Set<String> codes) {
		for (String code : codes) {
			Integer permissionId = permissions.permissionIdByCode(code);
			if (permissionId == null) {
				continue;
			}
			PermissionGroupAssocDboExample ex = new PermissionGroupAssocDboExample();
			ex.createCriteria().andPermissionGroupIdEqualTo(permissionGroupId).andPermissionIdEqualTo(permissionId);
			if (!groupPermissionMapper.selectByExample(ex).isEmpty()) {
				continue;
			}
			PermissionGroupAssocDbo assoc = new PermissionGroupAssocDbo();
			assoc.setPermissionGroupId(permissionGroupId);
			assoc.setPermissionId(permissionId);
			assoc.setMigrationHash(MigrationHasher.hash("groupperm" + permissionGroupId + "p" + permissionId));
			groupPermissionMapper.insert(assoc);
		}
	}

	private void convertMemberships() {
		var members = resilientReads.selectAllMembers();
		for (SMFMembersDb member : members) {
			Cancellable.check();
			Integer userId = idMap.lookupOrNull(LegacyEntityType.USER, member.getIdMember());
			if (userId == null) {
				continue;
			}
			Set<String> userCodes = new LinkedHashSet<>();
			userCodes.add(MigratorPermissionService.CODE_USER);
			for (Integer legacyGroupId : memberGroupIds(member)) {
				Integer groupId = idMap.lookupOrNull(LegacyEntityType.PERMISSION_GROUP, legacyGroupId);
				if (groupId == null) {
					continue;
				}
				ensureMembership(userId, groupId);
				Set<String> codes = groupCodes.get(groupId);
				if (codes != null) {
					userCodes.addAll(codes);
				}
			}
			permissions.replaceUserPermissions(userId, userCodes);
		}
	}

	private Set<Integer> memberGroupIds(SMFMembersDb member) {
		Set<Integer> groupIds = new LinkedHashSet<>();
		if (member.getIdGroup() != null && member.getIdGroup() > 0) {
			groupIds.add(member.getIdGroup());
		}
		if (member.getIdPostGroup() != null && member.getIdPostGroup() > 0) {
			groupIds.add(member.getIdPostGroup());
		}
		if (member.getAdditionalGroups() != null && !member.getAdditionalGroups().isBlank()) {
			for (String part : member.getAdditionalGroups().split(",")) {
				try {
					int groupId = Integer.parseInt(part.trim());
					if (groupId > 0) {
						groupIds.add(groupId);
					}
				} catch (NumberFormatException ignored) {
				}
			}
		}
		return groupIds;
	}

	private void ensureMembership(Integer userId, Integer groupId) {
		UserPermissionGroupAssocDboExample ex = new UserPermissionGroupAssocDboExample();
		ex.createCriteria().andUserIdEqualTo(userId).andPermissionGroupIdEqualTo(groupId);
		if (!assocMapper.selectByExample(ex).isEmpty()) {
			return;
		}
		UserPermissionGroupAssocDbo assoc = new UserPermissionGroupAssocDbo();
		assoc.setUserId(userId);
		assoc.setPermissionGroupId(groupId);
		assoc.setMigrationHash(MigrationHasher.hash("groupassoc" + groupId + "u" + userId));
		assocMapper.insert(assoc);
	}

	private String blankToNull(String value) {
		return value == null || value.isBlank() ? null : value;
	}
}
