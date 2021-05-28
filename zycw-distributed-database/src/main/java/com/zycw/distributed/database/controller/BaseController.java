package com.zycw.distributed.database.controller;

import com.zycw.distributed.database.service.BaseService;
import com.zycw.common.context.BaseContextHandler;
import com.zycw.common.msg.ObjectRestResponse;
import com.zycw.common.msg.TableResultResponse;
import com.zycw.distributed.database.common.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author wanghaobin
 * @create 2017-06-15 8:48
 */
public class BaseController<Biz extends BaseService,Entity> {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected Biz baseService;

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Entity> add(@RequestBody Entity entity){
    	baseService.insertSelective(entity);
        return new ObjectRestResponse<Entity>();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Entity> get(@PathVariable int id){
        ObjectRestResponse<Entity> entityObjectRestResponse = new ObjectRestResponse<Entity>();
        Object o = baseService.selectById(id);
        entityObjectRestResponse.data((Entity)o);
        return entityObjectRestResponse;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse<Entity> update(@RequestBody Entity entity){
    	baseService.updateSelectiveById(entity);
        return new ObjectRestResponse<Entity>();
    }
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public ObjectRestResponse<Entity> remove(@PathVariable int id){
    	baseService.deleteById(id);
        return new ObjectRestResponse<Entity>();
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    public List<Entity> all(){
        return baseService.selectListAll();
    }
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public TableResultResponse<Entity> list(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        return baseService.selectByQuery(query);
    }
    public String getCurrentUserName(){
        return BaseContextHandler.getUsername();
    }
}
