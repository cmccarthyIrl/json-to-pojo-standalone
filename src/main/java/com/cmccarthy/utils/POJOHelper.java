package com.cmccarthy.utils;

import com.sun.codemodel.JCodeModel;
import org.apache.commons.io.FileUtils;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class POJOHelper {

    private SourceType sourceType = SourceType.JSON;
    private AnnotationStyle annotationStyle = AnnotationStyle.JACKSON2;

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public void setAnnotationStyle(AnnotationStyle annotationStyle) {
        this.annotationStyle = annotationStyle;
    }

    private static String readLineByLine() throws IOException {
        File directory = new File(Constants.generateSourcesDirectory + "/");
        String[] pathnameList = directory.list();
        
        if (pathnameList == null || pathnameList.length == 0) {
            throw new IOException("No files generated. Please check your input JSON.");
        }
        
        StringBuilder contentBuilder = new StringBuilder();
        for (String pathname : pathnameList) {
            if (pathname.endsWith(".java") || pathname.endsWith(".scala")) {
                try (Stream<String> stream = Files.lines(
                        Paths.get(Constants.generateSourcesDirectory + "/" + pathname), 
                        StandardCharsets.UTF_8)) {
                    stream.forEach(s -> contentBuilder.append(s).append("\n"));
                } catch (IOException e) {
                    throw new IOException("Error reading generated file: " + pathname, e);
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
                return true;
            }

            @Override
            public boolean isIncludeToString() {
                return true;
            }

            @Override
            public boolean isIncludeAdditionalProperties() {
                return false;
            }

            @Override
            public boolean isIncludeHashcodeAndEquals() {
                return true;
            }

            @Override
            public AnnotationStyle getAnnotationStyle() {
                return annotationStyle;
            }

            @Override
            public SourceType getSourceType() {
                return sourceType;
            }
        };
        URL source = new URL(new URL("file:"), Constants.outputDirectory + "/required.json");

        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
        mapper.generate(codeModel, "RootClass", "generated-sources", source);
        codeModel.build(new File(Constants.outputDirectory + "/"), new PrintStream(new ByteArrayOutputStream()));

        return readLineByLine();
    }

    public void removeFiles() throws IOException {
        FileUtils.cleanDirectory(new File(Constants.outputDirectory + "/"));
    }
}
