package io.spatializer.core.controllers;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    String getIndex() {
        var exe = "/usr/bin/mapserv";
        var commandLine = CommandLine.parse(exe);
        var executor = DefaultExecutor.builder().get();

        var baos = new ByteArrayOutputStream();
        var streamHandler = new PumpStreamHandler(baos);
        executor.setStreamHandler(streamHandler);

        try {
            executor.execute(commandLine);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var stdout = baos.toString();
        return stdout;
    }

}
