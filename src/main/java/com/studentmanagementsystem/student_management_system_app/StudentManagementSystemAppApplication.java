package com.studentmanagementsystem.student_management_system_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(
        //        exclude = {DataSourceAutoConfiguration.class}
)
public class StudentManagementSystemAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementSystemAppApplication.class, args);
  }
}
