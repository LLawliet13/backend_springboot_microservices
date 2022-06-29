package com.example.mock2.Service;


import com.example.mock2.Entity.Log;
import com.example.mock2.Repository.LogRepository;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.example.mock2.filter.CustomAuthorizationFilter.USERNAME;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class LogServiceImpl implements LogService {
    @Autowired
    private LogRepository logRepository;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override

    public void info(String username, String action) {


        LocalDateTime now = LocalDateTime.now();
        System.out.println();
        String currentDate = dtf.format(now);
        Log log = new Log("INFO", currentDate, USERNAME, action);
        save(log);


    }

    @Override
    public void warn(String username, String action) {

        LocalDateTime now = LocalDateTime.now();
        System.out.println();
        String currentDate = dtf.format(now);
        Log log = new Log("WARN", currentDate, USERNAME, action);
        save(log);


    }

    @Override
    public void trace(String username, String action) {

        LocalDateTime now = LocalDateTime.now();
        System.out.println();
        String currentDate = dtf.format(now);
        Log log = new Log("TRACE", currentDate, USERNAME, action);
        save(log);


    }

    @Override
    public void error(String username, String action) {


        LocalDateTime now = LocalDateTime.now();
        System.out.println();
        String currentDate = dtf.format(now);
        Log log = new Log("ERROR", currentDate, USERNAME, action);
        save(log);


    }

    @Override
    public Log save(Log log) {
        for (int i = 0; i < 10; i++)
            System.out.println("logRepository" + logRepository);
        return logRepository.save(log);
    }

}
