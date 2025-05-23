package com.starter.springboot3.chapter2.runner;

import com.starter.springboot3.chapter2.service.DataService;
import com.starter.springboot3.chapter2.service.MyData;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CmdRunner implements CommandLineRunner {

    private final DataService dataService;

    public CmdRunner(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public void run(String... args) throws Exception {
        MyData data = dataService.loadDataFromJson();
        System.out.println("Loaded data = " + data);
        String jsonData = dataService.convertDataToJson(new MyData("Serialized Data", 200));
        System.out.println("Serialized data = " + jsonData);
    }
}
