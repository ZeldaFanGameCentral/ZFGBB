package com.zfgc.zfgbb.mappers.custom;

import java.time.LocalDate;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserProfileMapper {

	@Select("""
			select user_id from zfgbb."user"
			where user_id = #{userId} and active_flag = true
			for update
			""")
	Integer lockActiveUserId(@Param("userId") Integer userId);

	@Select("""
			select exists(
			  select 1 from zfgbb.avatar a
			  where a.avatar_id = #{avatarId} and a.active_flag = true
			  and not exists(
			    select 1 from zfgbb.user_bio_info b
			    where b.avatar_id = a.avatar_id and b.user_id <> #{userId}))
			""")
	boolean isAvatarAvailable(@Param("avatarId") Integer avatarId, @Param("userId") Integer userId);

	@Update("""
			update zfgbb."user"
			set display_name = #{displayName}, updated_ts = current_timestamp
			where user_id = #{userId}
			""")
	int updateDisplayName(@Param("displayName") String displayName, @Param("userId") Integer userId);

	@Insert("""
			insert into zfgbb.user_bio_info(user_id, created_ts, updated_ts)
			values (#{userId}, current_timestamp, current_timestamp)
			on conflict (user_id) do nothing
			""")
	int ensureUserBioInfoRow(@Param("userId") Integer userId);

	@Update("""
			<script>
			update zfgbb.user_bio_info
			<set>
			  updated_ts = current_timestamp,
			  <if test='personalTextPresent'>personal_text = #{personalText,jdbcType=VARCHAR},</if>
			  <if test='signaturePresent'>signature = #{signature,jdbcType=VARCHAR},</if>
			  <if test='locationPresent'>location = #{location,jdbcType=VARCHAR},</if>
			  <if test='birthDatePresent'>birth_date = #{birthDate,jdbcType=DATE},</if>
			  <if test='genderIdPresent'>gender_id = #{genderId,jdbcType=INTEGER},</if>
			  <if test='websiteTitlePresent'>website_title = #{websiteTitle,jdbcType=VARCHAR},</if>
			  <if test='websiteUrlPresent'>website_url = #{websiteUrl,jdbcType=VARCHAR},</if>
			  <if test='hideEmailFlagPresent'>hide_email_flag = #{hideEmailFlag,jdbcType=BIT},</if>
			  <if test='hideOnlineStatusPresent'>hide_online_status = #{hideOnlineStatus,jdbcType=BIT},</if>
			  <if test='avatarIdPresent'>avatar_id = #{avatarId,jdbcType=INTEGER},</if>
			</set>
			where user_id = #{userId}
			</script>
			""")
	int updateUserBioInfoSelective(
			@Param("userId") Integer userId,
			@Param("personalTextPresent") boolean personalTextPresent, @Param("personalText") String personalText,
			@Param("signaturePresent") boolean signaturePresent, @Param("signature") String signature,
			@Param("locationPresent") boolean locationPresent, @Param("location") String location,
			@Param("birthDatePresent") boolean birthDatePresent, @Param("birthDate") LocalDate birthDate,
			@Param("genderIdPresent") boolean genderIdPresent, @Param("genderId") Integer genderId,
			@Param("websiteTitlePresent") boolean websiteTitlePresent, @Param("websiteTitle") String websiteTitle,
			@Param("websiteUrlPresent") boolean websiteUrlPresent, @Param("websiteUrl") String websiteUrl,
			@Param("hideEmailFlagPresent") boolean hideEmailFlagPresent, @Param("hideEmailFlag") Boolean hideEmailFlag,
			@Param("hideOnlineStatusPresent") boolean hideOnlineStatusPresent,
			@Param("hideOnlineStatus") Boolean hideOnlineStatus,
			@Param("avatarIdPresent") boolean avatarIdPresent, @Param("avatarId") Integer avatarId);
}
