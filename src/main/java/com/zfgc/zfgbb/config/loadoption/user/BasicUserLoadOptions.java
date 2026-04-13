package com.zfgc.zfgbb.config.loadoption.user;

import com.zfgc.zfgbb.config.loadoption.BaseLoadOption;

public class BasicUserLoadOptions extends BaseLoadOption {

	public boolean loadAvatar() {
		return true;
	}
	
	public boolean loadBio() {
		return true;
	}
	
	public boolean loadKarma() {
		return false;
	}
	
	public boolean loadPermissions() {
		return false;
	}
	
	public boolean loadContactInfo() {
		return true;
	}
}
