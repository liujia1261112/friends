package main;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import main.dao.MyDAO;
import main.health.TemplateHealthCheck;
import main.resource.MyResource;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.jdbi.v3.core.Jdbi;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class MyApplication extends Application<MyConfiguration> {
    public static void main(String[] args) throws Exception {
        new MyApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<MyConfiguration> bootstrap) {
//        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index1.html"));
    }

    @Override
    public void run(MyConfiguration configuration,
                    Environment environment) {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");
//        loadSrt(jdbi);

        final MyResource resource = new MyResource(jdbi);
        environment.jersey().register(resource);

        // Enable CORS
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter("Access-Control-Allow-Credentials", "true");
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

//        environment.jersey().setUrlPattern("/api/*");
    }

    private void loadSrt(Jdbi db) {
        final MyDAO dao = db.onDemand(MyDAO.class);
        File dir = new File("C:\\Users\\Downloads\\friends");
        int s = 0;
        for (File child : dir.listFiles()) {
            s++;
            int e = 0;
            for (File grandchild : child.listFiles()) {
                e++;
                if (s == 1 && e < 5) continue;
                try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(grandchild), "UTF-16"))) {
                    List<String> list = br.lines()
                            .filter(str -> str.contains("Dialogue"))
                            .collect(Collectors.toList());
                    int pos = 0;
                    for (String str : list) {
                        String chn = "";
                        String eng = "";
                        try {
                            chn = str.substring(str.indexOf("0000,,") + 6, str.indexOf("\\N{\\fn")).replaceAll("\\s+", " ").trim();
                            eng = str.substring(str.indexOf("{\\4c&H000000&}") + 14).replaceAll("\\.\\.\\.", "");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            continue;
                        }
                        pos++;
                        dao.insertCaption(eng, chn, "s" + s + "e" + e, pos);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
