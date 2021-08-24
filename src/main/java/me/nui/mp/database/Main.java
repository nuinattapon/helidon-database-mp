package me.nui.mp.database;


import io.helidon.microprofile.server.Server;
import org.eclipse.microprofile.auth.LoginConfig;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;

@LoginConfig(authMethod = "MP-JWT", realmName = "TCK-MP-JWT")
@ApplicationPath("/")
public final class Main extends Application {

    private Main() {
    }


    public static void main(final String[] args) {
        Server server = startServer();

        // Show all configs
//        Iterable<String> iterable = ConfigProvider.getConfig().getPropertyNames();
//        for (String s : iterable) {
//            String[] propertyString = ConfigProvider.getConfig().getValue(s , String[].class);
//                System.out.printf("'%s' - %s\n", s, Arrays.toString(propertyString));
//        }


    }

    static Server startServer() {
        return Server.create().start();
    }

}