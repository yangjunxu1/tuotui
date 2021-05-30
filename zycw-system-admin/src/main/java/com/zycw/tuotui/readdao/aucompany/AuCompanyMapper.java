package com.zycw.tuotui.readdao.aucompany;

import com.zycw.tuotui.entity.aucompany.AuCompany;
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
 * 企业信息 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-30
 */
public interface AuCompanyMapper extends Mapper<AuCompany> {
	
	public void deleteById(@Param("auCompanyId") String auCompanyId);
	
	public void deleteByIdLogic(@Param("auCompanyId") String auCompanyId);
	
	public void byCompanyName(@Param("auCompany") AuCompany auCompany);
	
	
	
	public void updateObjById(AuCompany auCompany);
	
	
	public void testUpdateUpdate(@Param("param") HashMap<String,Object> param);
	
	public void testUpdateInsert(@Param("param") HashMap<String,Object> param);
	
	public void insertObj(AuCompany auCompany);
	
	public void batchInsertObj(List<AuCompany> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("auCompanyId") String auCompanyId);
	
	public List<AuCompany> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<AuCompany> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	public Integer getCompanyByProjectCountNum(@Param("param") HashMap<String,Object> param);
	public List<HashMap> getCompanyByProject(@Param("param") HashMap<String,Object> param);
	
	
	
}
