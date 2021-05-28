package com.zycw.tuotui.readdao.auoccupation;

import com.zycw.tuotui.entity.auoccupation.AuOccupation;
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
 * b职业 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-28
 */
public interface AuOccupationMapper extends Mapper<AuOccupation> {
	
	public void deleteById(@Param("auOccupationId") String auOccupationId);
	
	public void deleteByIdLogic(@Param("auOccupationId") String auOccupationId);
	
	
	public void updateObjById(AuOccupation auOccupation);
	
	
	
	public void insertObj(AuOccupation auOccupation);
	
	public void batchInsertObj(List<AuOccupation> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("auOccupationId") String auOccupationId);
	
	public List<AuOccupation> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<AuOccupation> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
