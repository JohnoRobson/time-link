package com.timelink;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


@Named("etc")
@RequestScoped
public class Basicbean {
  public String getMessage() {
    return "bean gud";
  }
}
