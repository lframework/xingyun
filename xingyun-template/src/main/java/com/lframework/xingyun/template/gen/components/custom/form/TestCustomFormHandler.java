package com.lframework.xingyun.template.gen.components.custom.form;

import com.lframework.xingyun.template.gen.components.custom.form.TestCustomFormHandler.OClass;
import com.lframework.xingyun.template.gen.components.custom.form.TestCustomFormHandler.PClass;
import com.lframework.xingyun.template.gen.components.custom.form.TestCustomFormHandler.RClass;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class TestCustomFormHandler extends AbstractCustomFormHandler<RClass, OClass, PClass> {

  @Override
  public RClass getOne(OClass o) {
    System.out.println("getOne");
    return null;
  }

  @Override
  public void handle(PClass pClass) {

    System.out.println("handle");
  }

  @Data
  public static class RClass {

    private String id;
  }

  @Data
  public static class OClass {

    private String id;
  }

  @Data
  public static class PClass {

    private String id;
  }
}
