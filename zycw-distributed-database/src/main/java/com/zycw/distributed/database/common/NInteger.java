package com.zycw.distributed.database.common;

public class NInteger {
	
	public static int parserInt(String num){
		if(num!=null&&!"".equals(num)){
			return Integer.parseInt(num);
		}
		return 0;
	}

}
