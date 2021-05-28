package com.zycw.common.jwt;




/**
 * <p>
 * Title: 客户端基础类
 * </p>
 * <p>
 * Description: 
 * </p>
 * @author   yangjunxu
 * @version  1.0
 * @since    
 * @Date	 2018年4月6日  上午10:29:56	 
 *
 */
public class ClientInfo implements IJWTInfo {
    String clientId;
    String name;

    public ClientInfo(String clientId, String name, String id) {
        this.clientId = clientId;
        this.name = name;
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getUniqueName() {
        return clientId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
