package com.zycw.tuotui.readdao.sysadverttype;

import com.zycw.tuotui.entity.sysadverttype.SysAdvertType;
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
 * 广告类型 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-06-01
 */
public interface SysAdvertTypeMapper extends Mapper<SysAdvertType> {
	
	public void deleteById(@Param("sysAdvertTypeId") String sysAdvertTypeId);
	
	public void deleteByIdLogic(@Param("sysAdvertTypeId") String sysAdvertTypeId);
	
	
	
	public void updateObjById(SysAdvertType sysAdvertType);
	
	
	
	public void insertObj(SysAdvertType sysAdvertType);
	
	public void batchInsertObj(List<SysAdvertType> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("sysAdvertTypeId") String sysAdvertTypeId);
	
	public List<SysAdvertType> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<SysAdvertType> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
