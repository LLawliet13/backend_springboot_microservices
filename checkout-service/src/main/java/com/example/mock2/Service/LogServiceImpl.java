package com.example.mock2.Service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@NoArgsConstructor
public class LogServiceImpl implements LogService{
    private String pathName ="./logs/spring-boot-app.log";
    private String previousContent;
    @Override
    public void info(String username,String action){
//        try {
//            read();
//            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//            LocalDateTime now = LocalDateTime.now();
//            System.out.println();
//            String currentDate = dtf.format(now);
//
//            String newData = "LoggerTYPE: INFO: "+currentDate+": "+username +" do "+action;
//            File file = new File(pathName);
//            OutputStream out = new FileOutputStream(pathName);
//            // Converts the string into bytes
//            byte[] dataBytes = (previousContent+"\n"+newData).getBytes();
//            // Writes data to the output stream
//            out.write(dataBytes);
//        } catch (IOException e) {
//            throw new RuntimeException(e.getMessage(),e.getCause());
//        }
    }

    @Override
    public void warn(String username,String action) {
//        try {
//            read();
//            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//            LocalDateTime now = LocalDateTime.now();
//            System.out.println();
//            String currentDate = dtf.format(now);
//
//            String newData = "LoggerTYPE: WARN: "+currentDate+": "+username +" do "+action;
//            File file = new File(pathName);
//            OutputStream out = new FileOutputStream(pathName);
//            // Converts the string into bytes
//            byte[] dataBytes = (previousContent+"\n"+newData).getBytes();
//            // Writes data to the output stream
//            out.write(dataBytes);
//        } catch (IOException e) {
//            throw new RuntimeException(e.getMessage(),e.getCause());
//        }
    }

    @Override
    public void trace(String username,String action) {
//        try {
//            read();
//            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//            LocalDateTime now = LocalDateTime.now();
//            System.out.println();
//            String currentDate = dtf.format(now);
//
//            String newData = "LoggerTYPE: TRACE: "+currentDate+": "+username +" do "+action;
//            File file = new File(pathName);
//            OutputStream out = new FileOutputStream(pathName);
//            // Converts the string into bytes
//            byte[] dataBytes = (previousContent+"\n"+newData).getBytes();
//            // Writes data to the output stream
//            out.write(dataBytes);
//        } catch (IOException e) {
//            throw new RuntimeException(e.getMessage(),e.getCause());
//        }
    }

    @Override
    public void error(String username,String action) {
//        try {
//            read();
//            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//            LocalDateTime now = LocalDateTime.now();
//            System.out.println();
//            String currentDate = dtf.format(now);
//
//            String newData = "LoggerTYPE: ERROR: "+currentDate+": "+username +" do "+action;
//            File file = new File(pathName);
//            OutputStream out = new FileOutputStream(pathName);
//            // Converts the string into bytes
//            byte[] dataBytes = (previousContent+"\n"+newData).getBytes();
//            // Writes data to the output stream
//            out.write(dataBytes);
//        } catch (IOException e) {
//            throw new RuntimeException(e.getMessage(),e.getCause());
//        }
    }
    private void read() throws IOException {
//        InputStream is = new FileInputStream(pathName);
//        byte[] array = new byte[is.available()];
//        is.read(array);
//        String content = new String(array);
//        if(content==null) previousContent = "";
//        previousContent = content;
    }

}
