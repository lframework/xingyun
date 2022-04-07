package com.dstz.adaptor.org.xingyun.controller;


import cn.hutool.core.collection.CollectionUtil;
import com.dstz.adaptor.org.xingyun.entity.Subsystem;
import com.dstz.adaptor.org.xingyun.entity.SysResource;
import com.dstz.adaptor.org.xingyun.manager.SubsystemManager;
import com.dstz.adaptor.org.xingyun.manager.SysResourceManager;
import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.model.page.PageResult;
import com.dstz.base.rest.ControllerTools;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.sys.api.constant.ResouceTypeConstant;
import com.github.pagehelper.Page;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 系统资源 控制器类
 */
@RestController
@RequestMapping("/org/sysResource")
public class SysResourceController extends ControllerTools {

  @Resource
  SysResourceManager sysResourceManager;
  @Resource
  SubsystemManager subsystemManager;

  /**
   * 子系统资源列表(分页条件查询)数据
   *
   * @param request
   * @param response
   * @return
   * @throws Exception PageJson
   * @throws
   */
  @RequestMapping("listJson")
  public PageResult listJson(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    QueryFilter queryFilter = getQueryFilter(request);
    Page<SysResource> sysResourceList = (Page<SysResource>) sysResourceManager.query(queryFilter);
    return new PageResult(sysResourceList);
  }


  /**
   * 子系统资源明细页面
   *
   * @param request
   * @param response
   * @return
   * @throws Exception ModelAndView
   */
  @RequestMapping("getJson")
  public ResultMsg<SysResource> getJson(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    String id = RequestUtil.getString(request, "id");
    if (StringUtil.isEmpty(id)) {
      String parentId = RequestUtil.getString(request, "parentId");
      String sysytemId = RequestUtil.getString(request, "systemId");
      SysResource sysResource = new SysResource();
      sysResource.setSystemId(sysytemId);
      sysResource.setParentId(parentId);
      sysResource.setOpened(1);
      return getSuccessResult(sysResource);
    } else {
      return getSuccessResult(sysResourceManager.get(id));
    }
  }

  /**
   * 保存子系统资源信息
   *
   * @param sysResource
   * @throws Exception void
   * @throws
   */
  @RequestMapping("save")
  @CatchErr
  public ResultMsg<String> save(@RequestBody SysResource sysResource) throws Exception {
    String resultMsg = null;
    String id = sysResource.getId();
    checkResouce(sysResource);

    if (StringUtil.isEmpty(id)) {
      sysResourceManager.create(sysResource);
      resultMsg = "添加子系统资源成功";
    } else {
      sysResourceManager.update(sysResource);
      resultMsg = "更新子系统资源成功";
    }

    return getSuccessResult(sysResource.getId(), resultMsg);

  }

  private void checkResouce(SysResource sysResource) {
    boolean isExist = sysResourceManager.isExist(sysResource);
    if (isExist) {
      throw new BusinessMessage("资源已经存在,请修改重新添加!");
    }
    // 如果是菜单、那上级也必须是菜单、防止按钮下面配置菜单
    if (ResouceTypeConstant.MENU.getKey().equals(sysResource.getType())) {
      SysResource parent = sysResourceManager.get(sysResource.getParentId());
      if (parent == null) {
        return;
      }

      if (!ResouceTypeConstant.MENU.getKey().equals(parent.getType())) {
        throw new BusinessMessage("菜单类型的资源，上级资源[" + parent.getName() + "]也必须是菜单！");
      }
    }
    if (StringUtil.isNotEmpty(sysResource.getUrl())) {
      sysResource.setUrl(sysResource.getUrl().trim());
    }

  }


  /**
   * 批量删除子系统资源记录
   *
   * @param request
   * @param response
   * @throws Exception void
   * @throws
   */
  @RequestMapping("remove")
  public ResultMsg remove(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    ResultMsg message = null;
    try {
      String id = RequestUtil.getString(request, "id");
      sysResourceManager.removeByResId(id);
      message = new ResultMsg(ResultMsg.SUCCESS, "删除子系统资源成功");
    } catch (Exception e) {
      message = new ResultMsg(ResultMsg.FAIL, "删除子系统资源失败");
    }
    return message;
  }


  @RequestMapping("sysResourceGet")
  @CatchErr(value = "获取资源失败", write2response = true)
  public ResultMsg<SysResource> sysResourceGet(HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    SysResource sysResource = sysResourceManager.get(id);
    return getSuccessResult(sysResource);
  }

  @RequestMapping("getTreeData")
  @CatchErr
  public List<SysResource> getTreeData(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    String systemId = RequestUtil.getString(request, "systemId");
    Subsystem subsystem = subsystemManager.get(systemId);
    List<SysResource> groupList = getGroupTree(systemId);
    if (CollectionUtil.isEmpty(groupList)) {
      groupList = new ArrayList<SysResource>();
    }
    SysResource rootResource = new SysResource();
    rootResource.setName(subsystem.getName());
    rootResource.setId("0");
    rootResource.setSystemId(systemId); // 根节点
    groupList.add(rootResource);
    return groupList;
  }

  private List<SysResource> getGroupTree(String systemId) {
    List<SysResource> groupList = sysResourceManager.getBySystemId(systemId);
    return groupList;
  }
}
