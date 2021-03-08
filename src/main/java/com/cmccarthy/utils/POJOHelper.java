package com.cmccarthy.utils;

import com.sun.codemodel.JCodeModel;
import org.apache.commons.io.FileUtils;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class POJOHelper {

    private static String readLineByLine() {
        String[] pathnameList = new File("target/generated-sources/").list();
        StringBuilder contentBuilder = new StringBuilder();

        assert pathnameList != null;
        for (String pathname : pathnameList) {
            if (pathname.endsWith(".java")) {
                try (Stream<String> stream = Files.lines(Paths.get("target/generated-sources/" + pathname), StandardCharsets.UTF_8)) {
                    stream.forEach(s -> contentBuilder.append(s).append("\n"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                contentBuilder.append("\n")
                        .append("==============================================================").append("\n")
                        .append("==============================================================").append("\n");
            }
        }

        return contentBuilder.toString();
    }

    public String buildJson() throws IOException {
        JCodeModel codeModel = new JCodeModel();

        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() {
                return false;
            }

            @Override
            public boolean isIncludeToString() {
                return false;
            }

            @Override
            public boolean isIncludeAdditionalProperties() {
                return false;
            }

            @Override
            public boolean isIncludeHashcodeAndEquals() {
                return false;
            }

            @Override
            public AnnotationStyle getAnnotationStyle() {
                return AnnotationStyle.JACKSON2;
            }

            @Override
            public Language getTargetLanguage() {
                return Language.JAVA;
            }

            @Override
            public SourceType getSourceType() {
                return SourceType.JSON;
            }
        };
        URL source = new URL(new URL("file:"), "target/required.json");

//        File appDir = new File(System.getProperty("user.dir"));
//        URI uri = new URI(appDir.toURI()+"/target/required.json");
//        // just to check if the file exists
//        File file = new File(uri);
//        System.out.println(file.exists());
//        URL source = uri.toURL();

        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
        mapper.generate(codeModel, "RootClass", "generated-sources", source);
        codeModel.build(new File("target/"), new PrintStream(new ByteArrayOutputStream()));

        return readLineByLine();
    }

    public void removeFiles() throws IOException {
        FileUtils.cleanDirectory(new File("target/generated-sources/"));
    }
}
