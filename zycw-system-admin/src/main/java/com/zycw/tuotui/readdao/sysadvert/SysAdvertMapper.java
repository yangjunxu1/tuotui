package com.zycw.tuotui.readdao.sysadvert;

import com.zycw.tuotui.entity.sysadvert.SysAdvert;
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
 * 广告协议信息 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-28
 */
public interface SysAdvertMapper extends Mapper<SysAdvert> {
	
	public void deleteById(@Param("sysAdvertId") String sysAdvertId);
	
	public void deleteByIdLogic(@Param("sysAdvertId") String sysAdvertId);
	
	
	public void updateObjById(SysAdvert sysAdvert);
	
	
	
	public void insertObj(SysAdvert sysAdvert);
	
	public void batchInsertObj(List<SysAdvert> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("sysAdvertId") String sysAdvertId);
	
	public List<SysAdvert> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<SysAdvert> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
