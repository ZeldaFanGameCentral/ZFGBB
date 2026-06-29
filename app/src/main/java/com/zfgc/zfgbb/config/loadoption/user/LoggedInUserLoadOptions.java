package com.zfgc.zfgbb.config.loadoption.user;

public class LoggedInUserLoadOptions extends BasicUserLoadOptions {

	public boolean loadAvatar() {
		return false;
	}
	
	public boolean loadBio() {
		return false;
	}
	
	public boolean loadPermissions() {
		return true;
	}

	public boolean loadReactions() {
		return false;
	}

	@Override
	public boolean loadSettings() {
		return true;
	}
}
