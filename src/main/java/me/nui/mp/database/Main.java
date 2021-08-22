package me.nui.mp.database;


import io.helidon.microprofile.server.Server;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Arrays;
import java.util.Optional;

public final class Main {

    private Main() {
    }


    public static void main(final String[] args) {
        Server server = startServer();
        System.out.println("http://localhost:" + server.port());

        Iterable<String> iterable = ConfigProvider.getConfig().getPropertyNames();

//        for (String s : iterable) {
//            Optional<String> value = ConfigProvider.getConfig().getOptionalValue(s, String.class);
//            if (value.isPresent()) {
//                System.out.printf("'%s' - '%s'\n", s, value);
//            }
//        }

        for (String s : iterable) {
            String[] propertyString = ConfigProvider.getConfig().getValue(s , String[].class);
                System.out.printf("'%s' - %s\n", s, Arrays.toString(propertyString));
        }


    }

    static Server startServer() {
        return Server.create().start();
    }

}