package com.dstz.adaptor.org.xingyun.controller;


import com.dstz.adaptor.org.xingyun.entity.Subsystem;
import com.dstz.adaptor.org.xingyun.manager.SubsystemManager;
import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.id.IdUtil;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.rest.BaseController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.org.api.model.IUser;
import com.dstz.sys.util.ContextUtil;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 描述：子系统定义 控制器类
 */
@RestController
@RequestMapping("/org/subsystem")
public class SubsystemController extends BaseController<Subsystem> {

  @Resource
  SubsystemManager subsystemManager;

  @RequestMapping("getUserSystem")
  @CatchErr(write2response = true)
  public @ResponseBody
  ResultMsg<List> getUserSystem(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    List Subsystem = subsystemManager.getAll();
    return getSuccessResult(Subsystem);
  }

  /**
   * 子系统定义明细页面
   *
   * @param request
   * @param response
   * @return
   * @throws Exception ModelAndView
   */
  @RequestMapping("getJson")
  public @ResponseBody
  Subsystem getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = RequestUtil.getString(request, "id");
    if (StringUtil.isEmpty(id)) {
      return null;
    }
    Subsystem subsystem = subsystemManager.get(id);
    return subsystem;
  }

  /**
   * @param subsystem
   * @return 保存子系统定义信息
   * @throws Exception void
   * @throws
   */
  @RequestMapping("save")
  @CatchErr
  @Override
  public ResultMsg<String> save(@RequestBody Subsystem subsystem) throws Exception {
    String resultMsg = null;

    boolean isExist = subsystemManager.isExist(subsystem);
    if (isExist) {
      throw new BusinessMessage("别名子系统中已存在!");
    }

    String id = subsystem.getId();
    if (StringUtil.isEmpty(id)) {
      subsystem.setId(IdUtil.getSuid());
      IUser user = ContextUtil.getCurrentUser();
      subsystemManager.create(subsystem);
      resultMsg = "添加子系统定义成功";
    } else {
      subsystemManager.update(subsystem);
      resultMsg = "更新子系统定义成功";
    }
    return getSuccessResult(resultMsg);
  }

  @Override
  protected String getModelDesc() {
    return "子系统定义";
  }
}
