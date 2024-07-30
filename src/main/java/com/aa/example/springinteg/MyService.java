package com.aa.example.springinteg;

import org.springframework.stereotype.Service;

@Service
public class MyService {

    public String process(DataClass payload) {
        System.out.println("Processing: " + payload.getId() + " and " + payload.getName() + " - " + Thread.currentThread().getName());
        //implement the processing here.
        return "Processed";
    }
}