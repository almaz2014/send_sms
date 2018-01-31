package com.example.alm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GreetingController {

    private static final String template = "SEND, %s!";
    private final AtomicLong counter = new AtomicLong();


    @RequestMapping("/send")
    public Greeting greeting(@RequestParam(value = "name", required = true) String name) {


       // Process process;

        try {
            String[] cmd = { "bash", "-c", "/home/pi/send_sms.sh "+name };
            Process p = Runtime.getRuntime().exec(cmd);
        }

        catch(Exception ex) {


            System.err.println("An InterruptedException was caught: " + ex.getMessage());
            // assert exitCode == 0;
        }











        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }


    private static class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines()
                    .forEach(consumer);
        }
    }

}