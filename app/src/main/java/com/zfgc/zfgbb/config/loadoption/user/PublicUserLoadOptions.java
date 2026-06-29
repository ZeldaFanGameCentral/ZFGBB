package com.zfgc.zfgbb.config.loadoption.user;

public class PublicUserLoadOptions extends BasicUserLoadOptions {
	@Override
	public boolean loadPermissions() {
		return true;
	}
}
