package com.zycw.tuotui.readdao.auscheme;

import com.zycw.tuotui.entity.auscheme.AuScheme;
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
 * 套餐表 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-30
 */
public interface AuSchemeMapper extends Mapper<AuScheme> {
	
	public void deleteById(@Param("auSchemeId") String auSchemeId);
	
	public void deleteByIdLogic(@Param("auSchemeId") String auSchemeId);
	
	
	public void updateObjById(AuScheme auScheme);
	
	
	
	public void insertObj(AuScheme auScheme);
	
	public void batchInsertObj(List<AuScheme> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("auSchemeId") String auSchemeId);
	
	public List<AuScheme> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<AuScheme> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
