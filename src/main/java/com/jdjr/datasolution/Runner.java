package com.jdjr.datasolution; /**
 * Created by xiaodl on 15/12/27.
 */

import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.concurrent.ArrayBlockingQueue;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
public class Runner {

    public static void main(String[] args) throws Exception {
        Server server = new Server();

        // 设置线程池
        ArrayBlockingQueue queue = new ArrayBlockingQueue(4000);
        QueuedThreadPool pool = new QueuedThreadPool(queue);
        pool.setMinThreads(20);
        pool.setMinThreads(200);
        server.setThreadPool(pool);

        // 设置connector
        SelectChannelConnector connector0 = new SelectChannelConnector();
        connector0.setPort(8088);
        connector0.setMaxIdleTime(30000);
        connector0.setAcceptors(4);
        connector0.setStatsOn(false);
        connector0.setLowResourcesConnections(5000);
        connector0.setLowResourcesMaxIdleTime(2000);
        server.setConnectors(new Connector[]{ connector0 });


        // 设置WebAppContext的handler
        final String home = System.getProperty("home", "");
        ProtectionDomain domain = Runner.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        if (home.length() != 0) {
            webapp.setTempDirectory(new File(home));
        }
        webapp.setWar(location.toExternalForm());
        server.setHandler(webapp);

        // 启动jetty
        server.start();
        server.join();
    }
}
