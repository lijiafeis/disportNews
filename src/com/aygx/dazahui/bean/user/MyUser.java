package com.aygx.dazahui.bean.user;

import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser {
	private String nickName;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
