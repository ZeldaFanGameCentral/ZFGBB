package com.zfgc.zfgbb.dbo;

public class UserAggregateDbo {
    private UserDbo user;
    private UserBioInfoDbo bio;
    private AvatarDbo avatar;
    private UserContactInfoDbo contact;
    private EmailAddressDbo email;
    private UserSettingsDbo settings;

    public UserDbo getUser() {
        return user;
    }

    public void setUser(UserDbo user) {
        this.user = user;
    }

    public UserBioInfoDbo getBio() {
        return bio;
    }

    public void setBio(UserBioInfoDbo bio) {
        this.bio = bio;
    }

    public AvatarDbo getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarDbo avatar) {
        this.avatar = avatar;
    }

    public UserContactInfoDbo getContact() {
        return contact;
    }

    public void setContact(UserContactInfoDbo contact) {
        this.contact = contact;
    }

    public EmailAddressDbo getEmail() {
        return email;
    }

    public void setEmail(EmailAddressDbo email) {
        this.email = email;
    }

    public UserSettingsDbo getSettings() {
        return settings;
    }

    public void setSettings(UserSettingsDbo settings) {
        this.settings = settings;
    }
}
