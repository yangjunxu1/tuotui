package com.zycw.common.jwt;

/**
 * Title: JWT
 *
 * @author   yangjunxu
 * @version  1.0
 * @since    
 * @Date	 2018年4月4日  上午12:19:56	
 */
public interface IJWTInfo {
    /**
     * 获取用户名
     * @return
     */
    String getUniqueName();

    /**
     * 获取用户ID
     * @return
     */
    String getId();

    /**
     * 获取名称
     * @return
     */
    String getName();
}
