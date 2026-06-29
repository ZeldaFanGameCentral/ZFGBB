package com.zfgc.zfgbb.config.loadoption.user;

public class FullUserLoadOptions extends BasicUserLoadOptions {
	@Override
	public boolean loadPermissions() {
		return true;
	}

	@Override
	public boolean loadAwards() {
		return true;
	}

	@Override
	public boolean loadContactInfo() {
		return true;
	}

	@Override
	public boolean loadSettings() {
		return true;
	}
}
