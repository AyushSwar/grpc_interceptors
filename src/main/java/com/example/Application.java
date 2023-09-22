package com.example;

import com.example.server.ServerStarter;
import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {
        Micronaut.run(Application.class, args);

        ApplicationContext applicationContext = ApplicationContext.run();
        ServerStarter serverStarter = applicationContext.getBean(ServerStarter.class);
        serverStarter.startService();
    }
}