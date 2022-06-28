package com.example.mock2.Service;

import java.io.IOException;

public interface LogService {
    void info(String username,String action) ;
    void warn(String username,String action);
    void trace(String username,String action);
    void error(String username,String action);
}
