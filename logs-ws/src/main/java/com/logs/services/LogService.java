package com.logs.services;

import com.logs.dto.LogAuthenticationDto;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class LogService {

    private final String DIR_PATH = "/home/haxul/Documents/development/music-player-service/logs-ws/logs";

    private boolean isPathCreated() {
        Path path = Paths.get(DIR_PATH);
        return Files.exists(path);
    }

    private void createPath() throws FileAlreadyExistsException {
        if (isPathCreated()) return;
        boolean mkdirs = new File(DIR_PATH).mkdirs();
        if (!mkdirs) throw new FileAlreadyExistsException(DIR_PATH);
    }

    private String createEmptyLogFile() throws IOException {
        String fileName = getCurrentLogFileName();
        String absoluteFileName = DIR_PATH + "/" + fileName;
        Files.write(Paths.get(absoluteFileName), "".getBytes());
        return absoluteFileName;
    }

    private boolean doesLogFileExist() {
        return Files.exists(Paths.get(DIR_PATH + "/" + getCurrentLogFileName()));
    }

    private String getCurrentLogFileName() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd");
        LocalDate localDate = LocalDate.now();
        return dtf.format(localDate) + ".txt";
    }

    private String formatMessage(LogAuthenticationDto log) {
        String header = log.getIsSuccessfulAuthentication() ? "SUCCESS" : "FAILURE";
        String date = String.format("[ %s ]", new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(log.getDate()));
        String username = log.getUsername();
        return String.format("Attempt to login: %s %s, username: %s", header, date, username);
    }

    public void addLine(LogAuthenticationDto log) throws IOException {
        if (!isPathCreated()) createPath();
        if (!doesLogFileExist()) createEmptyLogFile();
        String filename = DIR_PATH + "/" + getCurrentLogFileName();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename, true));
        String message = formatMessage(log);
        bufferedWriter.append(message + "\n");
        bufferedWriter.close();
    }
}
