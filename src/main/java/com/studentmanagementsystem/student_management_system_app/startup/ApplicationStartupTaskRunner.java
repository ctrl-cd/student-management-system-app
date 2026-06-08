package com.studentmanagementsystem.student_management_system_app.startup;

import com.studentmanagementsystem.student_management_system_app.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ApplicationStartupTaskRunner implements ApplicationRunner {

  @Override
  public void run(ApplicationArguments args) throws Exception {
    applicationReadyLog();
    //    TODO - Uncomment below line if required
    //    systemOutSystemErrDisableAll();
  }

  private void applicationReadyLog() {
    System.out.println("""
                               
                               
                               ===================================================================================
                               🚀 \u001B[30;43m Application has successfully started and is ready! \u001B[0m
                               ===================================================================================
                               
                               """);
  }

  private void systemOutSystemErrDisableAll() {
    if (Utils.isDev()) {
      Utils.disableAllSystemOut(false);
    }
    else if (Utils.isProd()) {
      Utils.disableAllSystemOut(true);
    }
  }
}
