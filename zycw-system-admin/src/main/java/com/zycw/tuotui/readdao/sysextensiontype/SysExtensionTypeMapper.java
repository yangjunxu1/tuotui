package com.zycw.tuotui.readdao.sysextensiontype;

import com.zycw.tuotui.entity.sysextensiontype.SysExtensionType;
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
 * b推广类型· Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-30
 */
public interface SysExtensionTypeMapper extends Mapper<SysExtensionType> {
	
	public void deleteById(@Param("sysExtensionTypeId") String sysExtensionTypeId);
	
	public void deleteByIdLogic(@Param("sysExtensionTypeId") String sysExtensionTypeId);
	
	
	public void updateObjById(SysExtensionType sysExtensionType);
	
	
	
	public void insertObj(SysExtensionType sysExtensionType);
	
	public void batchInsertObj(List<SysExtensionType> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("sysExtensionTypeId") String sysExtensionTypeId);
	
	public List<SysExtensionType> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<SysExtensionType> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
