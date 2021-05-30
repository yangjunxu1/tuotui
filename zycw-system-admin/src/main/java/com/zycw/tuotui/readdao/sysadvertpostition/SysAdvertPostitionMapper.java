package com.zycw.tuotui.readdao.sysadvertpostition;

import com.zycw.tuotui.entity.sysadvertpostition.SysAdvertPostition;
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
 * b广告协议位置 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-30
 */
public interface SysAdvertPostitionMapper extends Mapper<SysAdvertPostition> {
	
	public void deleteById(@Param("sysAdvertPostitionId") String sysAdvertPostitionId);
	
	public void deleteByIdLogic(@Param("sysAdvertPostitionId") String sysAdvertPostitionId);
	
	
	public void updateObjById(SysAdvertPostition sysAdvertPostition);
	
	
	
	public void insertObj(SysAdvertPostition sysAdvertPostition);
	
	public void batchInsertObj(List<SysAdvertPostition> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("sysAdvertPostitionId") String sysAdvertPostitionId);
	
	public List<SysAdvertPostition> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<SysAdvertPostition> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
