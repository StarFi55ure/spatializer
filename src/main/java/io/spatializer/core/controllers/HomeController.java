package io.spatializer.core.controllers;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rawhttp.core.*;

import java.io.*;
import java.nio.charset.StandardCharsets;


@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    String getIndex() {
        var exe = "/usr/bin/mapserv";
        var commandLine = CommandLine.parse(exe);
        var executor = DefaultExecutor.builder().get();

        String stdout = "";
        try(var baos = new ByteArrayOutputStream()) {
            baos.writeBytes("HTTP/1.1 200 OK\n".getBytes());
            executor.setStreamHandler(new PumpStreamHandler(baos));
            executor.execute(commandLine);

            var bais = new ByteArrayInputStream(baos.toByteArray());
            var rawResponse = new RawHttp().parseResponse(bais);
            var body = rawResponse.getBody();
            if (body.isPresent()) {
                stdout = body.get().decodeBodyToString(StandardCharsets.UTF_8);
            }
            System.out.println(stdout);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stdout;
    }

}
