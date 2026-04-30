package com.zfgc.zfgbb.migrator.converters;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dbo.AvatarDbo;
import com.zfgc.zfgbb.dbo.AvatarDboExample;
import com.zfgc.zfgbb.dbo.ContentResourceDbo;
import com.zfgc.zfgbb.dbo.ContentResourceDboExample;
import com.zfgc.zfgbb.dbo.GenderLkupDbo;
import com.zfgc.zfgbb.dbo.GenderLkupDboExample;
import com.zfgc.zfgbb.dbo.UserBioInfoDbo;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.mappers.AvatarDboMapper;
import com.zfgc.zfgbb.mappers.ContentResourceDboMapper;
import com.zfgc.zfgbb.mappers.GenderLkupDboMapper;
import com.zfgc.zfgbb.mappers.UserBioInfoDboMapper;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFAttachmentsDb;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFAttachmentsDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMembersDbExample;
import com.zfgc.zfgbb.migrator.smf.dbo.SMFMembersDbWithBLOBs;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFAttachmentsDbMapper;
import com.zfgc.zfgbb.migrator.smf.mappers.SMFMembersDbMapper;

@Component
public class UserBioInfoConverter extends AbstractConverter<Map<Integer, UserBioInfoDbo>> {

	@Override
	public JobType getType() {
		return JobType.USER_BIO_INFO;
	}

	
	@Autowired
	public SMFMembersDbMapper smfMembersMapper;
	
	@Autowired
	public UserBioInfoDboMapper bioInfoMapper;
	
	@Autowired
	private SMFAttachmentsDbMapper smfAttachmentsDbMapper;
	
	@Autowired
	private AvatarDboMapper avatarMapper;
	
	@Autowired
	private ContentResourceDboMapper contentMapper;
	
	@Autowired
	private GenderLkupDboMapper genderLkupMapper;
	
	@Override
	@Transactional
	public Map<Integer, UserBioInfoDbo> convertToZfgbb() {
		List<SMFMembersDbWithBLOBs> SMFMembers = smfMembersMapper.selectByExampleWithBLOBs(new SMFMembersDbExample());
		Map<Integer,UserBioInfoDbo> result = new HashMap<>();
		
		SMFAttachmentsDbExample avatarEx = new SMFAttachmentsDbExample();
		avatarEx.createCriteria().andIdMemberIsNotNull()
								 .andIdMemberNotEqualTo(0);
		Map<Integer,SMFAttachmentsDb> SMFAvatarAttachments = 
				smfAttachmentsDbMapper.selectByExample(avatarEx).stream()
									  .collect(Collectors.toMap(SMFAttachmentsDb::getIdMember, Function.identity()));
		
		List<GenderLkupDbo> genderLk = genderLkupMapper.selectByExample(new GenderLkupDboExample());
		
		AtomicInteger contentId = new AtomicInteger(1);
		SMFMembers.forEach((smfMember) -> {
			Cancellable.check();
			//create the avatar first if one exists for the user
			AvatarDbo avatar = new AvatarDbo();
			SMFAttachmentsDb smfAvatar = SMFAvatarAttachments.get(smfMember.getIdMember());
			if(smfAvatar != null || !smfMember.getAvatar().equals("")) {
				avatar.setUrl(smfMember.getAvatar());
				avatar.setActiveFlag(true);
				
				if(smfAvatar != null) {
					//if the avatar is a user uploaded avatar, create/update content resource record
					ContentResourceDbo contentResource = new ContentResourceDbo();
					contentResource.setContentResourceId(contentId.getAndIncrement());
					contentResource.setContentTypeId(1);
					contentResource.setFilename(smfAvatar.getFilename());
					contentResource.setFileExt(smfAvatar.getFileext());
					contentResource.setMimeType(smfAvatar.getMimeType());
					contentResource.setChecksum(smfAvatar.getFileHash());
					contentResource.setUploadedUserId(smfMember.getIdMember());
					contentResource.setFileSize(smfAvatar.getSize().longValue());
					contentResource.setMigrationHash(MigrationHasher.hash(contentResource.getContentTypeId().toString()
							+ contentResource.getUploadedUserId().toString()
							+ contentResource.getFilename()
							+ contentResource.getChecksum()
							+ contentResource.getFileExt()
							+ contentResource.getMimeType()
							+ contentResource.getFileSize().toString()));
					
					ContentResourceDboExample resourceEx = new ContentResourceDboExample();
					resourceEx.createCriteria().andContentResourceIdEqualTo(contentResource.getContentResourceId());
					Optional<ContentResourceDbo> existingResource = contentMapper.selectByExample(resourceEx).stream().findFirst();
					
					existingResource.ifPresentOrElse(existing -> {
						if(!existing.getMigrationHash().equals(contentResource.getMigrationHash())) {
							contentMapper.updateByPrimaryKey(contentResource);
						}
					}, 
					() -> {
						contentMapper.insert(contentResource);
					});
					
					avatar.setContentResourceId(contentResource.getContentResourceId());
					avatar.setActiveFlag(smfAvatar.getApproved() == 1);
				}
				
				avatar.setMigrationHash(MigrationHasher.hash((avatar.getUrl() != null ? avatar.getUrl() : "")
						+ avatar.getActiveFlag().toString()
						+ (avatar.getContentResourceId() != null ? avatar.getContentResourceId().toString() : "-1")));
				
				AvatarDboExample avEx = new AvatarDboExample();
				avEx.createCriteria().andMigrationHashEqualTo(avatar.getMigrationHash());
				Optional<AvatarDbo> existingAvatar = avatarMapper.selectByExample(avEx).stream().findFirst();
				
				existingAvatar.ifPresentOrElse((existing) -> {
					avatar.setAvatarId(existing.getAvatarId());
					avatarMapper.updateByPrimaryKey(avatar);
				},
				() -> {
					avatarMapper.insert(avatar);
				});
			}
			
			UserBioInfoDbo user = new UserBioInfoDbo();
			
			String genderCode = smfMember.getGender().intValue() == 1 ? "M" : (smfMember.getGender().intValue() == 2 ? "F" : null);
			Integer genderId = genderLk.stream().filter(lk -> lk.getCode().equals(genderCode)).findFirst().map(GenderLkupDbo::getGenderId).orElse(null);
			
			user.setUserId(smfMember.getIdMember());
			user.setCustomTitle(smfMember.getUsertitle());
			user.setPersonalText(smfMember.getPersonalText());
			user.setSignature(smfMember.getSignature());
			user.setAvatarId(avatar.getAvatarId());
			user.setBirthDate(smfMember.getBirthdate());
			user.setKarmaBad(smfMember.getKarmaBad());
			user.setKarmaGood(smfMember.getKarmaGood());
			user.setHideEmailFlag(Boolean.TRUE.equals(smfMember.getHideEmail()));
			user.setHideOnlineStatus(!Boolean.TRUE.equals(smfMember.getShowOnline()));
			user.setGenderId(genderId);
			
			Instant instant = Instant.ofEpochMilli(TimeUnit.SECONDS.toMillis(smfMember.getDateRegistered()));
		    LocalDate dateRegistered =
		      LocalDate.ofInstant(instant, ZoneId.of("UTC"));
			
			user.setDateRegistered(dateRegistered);
			
			
			user.setMigrationHash(MigrationHasher.hash(user.getUserId().toString()
					+ (user.getSignature() != null ? user.getSignature() : "")
					+ (user.getCustomTitle() != null ? user.getCustomTitle() : "")
					+ (user.getAvatarId() != null ? user.getAvatarId() : "")
					+ (user.getPersonalText() != null ? user.getPersonalText() : "")
					+ (user.getKarmaGood() != null ? user.getKarmaGood() : "")
					+ (user.getKarmaBad() != null ? user.getKarmaBad() : "")
					+ (user.getHideOnlineStatus() != null ? user.getHideOnlineStatus().toString() : "")
					+ (user.getHideEmailFlag() != null ? user.getHideEmailFlag().toString() : "")
					+ (user.getBirthDate() != null ? user.getBirthDate().toString() : "")
					+ (user.getGenderId() != null ? user.getGenderId().toString() : "")
					+ (user.getDateRegistered() != null ? user.getDateRegistered().toString() : "")));
			
			result.put(user.getUserId(), user);
			
			
			UserBioInfoDbo existingUser = bioInfoMapper.selectByPrimaryKey(user.getUserId());
			if(existingUser == null) {
				bioInfoMapper.insert(user);
			}
			else if(!existingUser.getMigrationHash().equals(user.getMigrationHash())) {
				bioInfoMapper.updateByPrimaryKey(user);
			}
		});
		
		return result;
	}
}
