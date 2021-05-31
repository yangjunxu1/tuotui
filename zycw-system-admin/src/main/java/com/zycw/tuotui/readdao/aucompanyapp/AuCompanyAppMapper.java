package com.zycw.tuotui.readdao.aucompanyapp;

import com.zycw.tuotui.entity.aucompanyapp.AuCompanyApp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zycw.distributed.database.util.PageUtil;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import java.lang.String;
import java.lang.Integer;
import java.util.List;
import java.util.HashMap;

    		
/**
 * <p>
 * 企业app Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
public interface AuCompanyAppMapper extends Mapper<AuCompanyApp> {
	
	public void deleteById(@Param("auCompanyAppId") String auCompanyAppId);
	
	public void deleteByIdLogic(@Param("auCompanyAppId") String auCompanyAppId);
	
	public void deleteByauCompanyAppCompanyId(@Param("id") String id);
    
	
	
	public void updateObjById(AuCompanyApp auCompanyApp);
	
	
	
	public void insertObj(AuCompanyApp auCompanyApp);
	
	public void batchInsertObj(List<AuCompanyApp> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("auCompanyAppId") String auCompanyAppId);
	
	public List<AuCompanyApp> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<AuCompanyApp> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
