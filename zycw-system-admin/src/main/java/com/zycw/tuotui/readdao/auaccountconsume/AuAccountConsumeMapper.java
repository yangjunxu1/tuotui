package com.zycw.tuotui.readdao.auaccountconsume;

import com.zycw.tuotui.entity.auaccountconsume.AuAccountConsume;
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
 * 公司账户消费明细 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-30
 */
public interface AuAccountConsumeMapper extends Mapper<AuAccountConsume> {
	
	public void deleteById(@Param("auAccountConsumeId") String auAccountConsumeId);
	
	public void deleteByIdLogic(@Param("auAccountConsumeId") String auAccountConsumeId);
	
	
	public void updateObjById(AuAccountConsume auAccountConsume);
	
	
	
	public void insertObj(AuAccountConsume auAccountConsume);
	
	public void batchInsertObj(List<AuAccountConsume> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("auAccountConsumeId") String auAccountConsumeId);
	
	public List<AuAccountConsume> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<AuAccountConsume> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
