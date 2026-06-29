package com.zfgc.zfgbb.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zfgc.zfgbb.model.forum.Message;
import com.zfgc.zfgbb.model.forum.MessageHistory;
import com.zfgc.zfgbb.model.meta.IpAddress;
import com.zfgc.zfgbb.model.users.Avatar;
import com.zfgc.zfgbb.model.users.Award;
import com.zfgc.zfgbb.model.users.EmailAddress;
import com.zfgc.zfgbb.model.users.EncodedPassword;
import com.zfgc.zfgbb.model.users.PasswordAlgo;
import com.zfgc.zfgbb.model.users.Permission;
import com.zfgc.zfgbb.model.users.ReactionSummary;
import com.zfgc.zfgbb.model.users.UserBioInfo;
import com.zfgc.zfgbb.model.users.UserContactInfo;
import com.zfgc.zfgbb.model.users.UserSettings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@SuperBuilder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel implements UserDetails {
	@JsonIgnore
	private Integer userId;
	private String displayName;
	@JsonIgnore
	private String userName;
	private Boolean activeFlag;
	@JsonIgnore
	private EmailAddress email;
	@JsonIgnore
	private String ssoKey;
	@JsonIgnore
	private String passwordHash;
	@JsonIgnore
	private PasswordAlgo passwordAlgo;
	@JsonIgnore
	private String passwordSalt;
	@JsonIgnore
	private OffsetDateTime lockedUntilTs;
	@JsonIgnore
	private Integer failedLoginCount;
	@JsonIgnore
	private OffsetDateTime passwordChangedTs;
	@JsonIgnore
	private OffsetDateTime tokensValidAfterTs;
	@JsonIgnore
	private Boolean credentialsNonExpired;
	private List<Permission> permissions = new ArrayList<>();
	
	private IpAddress currentIpAddress;
	private List<IpAddress> allKnownIpAddresses = new ArrayList<>();
	private UserBioInfo bioInfo;
	private UserContactInfo contactInfo;
	private ReactionSummary reactionSummary;
	private List<Award> awards;
	private UserSettings settings;
	
	public static User orphaned() {
		return User.builder()
				.displayName("ORPHANED")
				.permissions(new ArrayList<>())
				.build();
	}

	public static User guest() {
		User guest = new User();
		guest.setDisplayName("Friend");
		guest.setUserId(-1);

		Permission guestPerm = new Permission();
		guestPerm.setId(2);
		guestPerm.setPermissionCode("ZFGC_GUEST");

		Permission readPerm = new Permission();
		readPerm.setId(9);
		readPerm.setPermissionCode("ZFGC_READ_ONLY");

		guest.getPermissions().add(guestPerm);
		guest.getPermissions().add(readPerm);

		return guest;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public static final Set<String> PUBLIC_RANK_PERMISSIONS = Set.of(
			"ZFGC_SITE_ADMIN", "ZFGC_SITE_MODERATOR", "ZFGC_WIKI_MODERATOR");

	public void retainPublicRankPermissions() {
		this.permissions = permissions == null ? new ArrayList<>() : permissions.stream()
				.filter(permission -> PUBLIC_RANK_PERMISSIONS.contains(permission.getPermissionCode()))
				.collect(Collectors.toList());
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (permissions == null)
			return List.of();
		return permissions.stream()
					      .map(perm -> {
					    	  return new SimpleGrantedAuthority("ROLE_" + perm.getPermissionCode());
					      })
					      .collect(Collectors.toList());
	}


	@JsonIgnore
	@Override
	public String getUsername() {
		return userName;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return lockedUntilTs == null || !lockedUntilTs.isAfter(OffsetDateTime.now(java.time.ZoneOffset.UTC));
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired == null || credentialsNonExpired;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return Boolean.TRUE.equals(activeFlag);
	}

	@Override
	public Integer getId() {
		return userId;
	}

	@Override
	public void setId(Integer id) {
		userId = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public IpAddress getCurrentIpAddress() {
		return currentIpAddress;
	}

	public void setCurrentIpAddress(IpAddress currentIpAddress) {
		this.currentIpAddress = currentIpAddress;
	}

	public List<IpAddress> getAllKnownIpAddresses() {
		return allKnownIpAddresses;
	}

	public void setAllKnownIpAddresses(List<IpAddress> allKnownIpAddresses) {
		this.allKnownIpAddresses = allKnownIpAddresses;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public EmailAddress getEmail() {
		return email;
	}

	public void setEmail(EmailAddress email) {
		this.email = email;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getSsoKey() {
		return ssoKey;
	}

	public void setSsoKey(String ssoKey) {
		this.ssoKey = ssoKey;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		if (passwordHash == null)
			return null;
		return new EncodedPassword(passwordAlgo == null ? PasswordAlgo.BCRYPT : passwordAlgo, passwordSalt, passwordHash)
				.toEncoded();
	}

	public UserBioInfo getBioInfo() {
		return bioInfo;
	}

	public void setBioInfo(UserBioInfo bioInfo) {
		this.bioInfo = bioInfo;
	}
	
	@JsonIgnore
	public boolean hasPermission(String permission) {
		if(permissions != null) {
			return permissions.stream().anyMatch(pr -> permission.equals(pr.getPermissionCode()));
		}
		
		return false;
	}
	
}