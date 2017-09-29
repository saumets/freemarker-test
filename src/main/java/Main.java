import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {
    final static Logger logger = LoggerFactory.getLogger(Main.class);

    private static FilenameFilter filterByFileType(String extension) {
        return new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(extension);
            }
        };
    }

    public static void main(String[] args) throws IOException {
        port(8000);

        if (System.getenv("TEST_RESOURCE_PATH") == null) {
            logger.error("TEST_RESOURCE_PATH environment variable is not configured. Bailing out!");
            System.exit(0);
        }

        File testingFolder = new File(System.getenv("TEST_RESOURCE_PATH"));
        staticFiles.externalLocation(testingFolder.getName());

        final Configuration configuration = new Configuration(new Version(2, 3, 26));
        configuration.setDirectoryForTemplateLoading(testingFolder);

        get("/", (req, res) -> {
            // File folder = new File(externalTestingPath);
            File[] files = testingFolder.listFiles(Main.filterByFileType("ftl"));

            Map<String, Object> model = new HashMap<>();
            model.put("template_files", files);

            return new FreeMarkerEngine().render(
                new ModelAndView(model, "index.ftl")
            );
        });

        get("/template", (req, res) -> {
            StringWriter writer = new StringWriter();
            try {
                Template formTemplate = configuration.getTemplate(req.queryParams("id"));
                formTemplate.process(null, writer);
            } catch (Exception e) {
                logger.error(String.format("Error processing template %s. Does it exist? " +
                        "Check your template syntax?", req.queryParams("id")));
                Spark.halt(500);
            }

            return writer;
        });
    }
}
