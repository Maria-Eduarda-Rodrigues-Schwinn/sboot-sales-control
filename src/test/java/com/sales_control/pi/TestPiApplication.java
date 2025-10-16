package com.sales_control.pi;

import org.springframework.boot.SpringApplication;

public class TestPiApplication {

  public static void main(String[] args) {
    SpringApplication.from(PiApplication::main).with(TestcontainersConfiguration.class).run(args);
  }
}
