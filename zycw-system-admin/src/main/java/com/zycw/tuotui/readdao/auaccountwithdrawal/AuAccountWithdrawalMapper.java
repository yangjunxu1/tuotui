package com.zycw.tuotui.readdao.auaccountwithdrawal;

import com.zycw.tuotui.entity.auaccountwithdrawal.AuAccountWithdrawal;
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
 * 提现明细表 Mapper 接口
 * </p>
 *
 * @author junxu.yang
 * @since 2021-05-30
 */
public interface AuAccountWithdrawalMapper extends Mapper<AuAccountWithdrawal> {
	
	public void deleteById(@Param("auAccountWithdrawalId") String auAccountWithdrawalId);
	
	public void deleteByIdLogic(@Param("auAccountWithdrawalId") String auAccountWithdrawalId);
	
	
	public void updateObjById(AuAccountWithdrawal auAccountWithdrawal);
	
	
	
	public void insertObj(AuAccountWithdrawal auAccountWithdrawal);
	
	public void batchInsertObj(List<AuAccountWithdrawal> list);
	
	
	public Integer countNum(@Param("param") HashMap<String,Object> params);
	
	public HashMap<String,Object> selectObjById(@Param("auAccountWithdrawalId") String auAccountWithdrawalId);
	
	public List<AuAccountWithdrawal> pageList(@Param("param") HashMap<String,Object> param);
	
	public List<AuAccountWithdrawal> allList(@Param("param") HashMap<String,Object> param);
	
	
	
	
	
}
