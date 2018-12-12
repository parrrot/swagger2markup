/*
 * Copyright 2017 Robert Winkler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.swagger2markup;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.fail;

public class ProfilingTest {
    private static final String[] EXPECTED_FILES = new String[]{"definitions.md", "overview.md", "paths.md", "security.md"};
    private List<String> expectedFiles;

    @Before
    public void setUp() {
        expectedFiles = new ArrayList<>(asList(EXPECTED_FILES));
    }

    @Test
    public void testToFolder() throws IOException, URISyntaxException {
        //Given
        Path file = Paths.get(MarkdownConverterTest.class.getResource("/yaml/swagger_petstore.yaml").toURI());
        Path outputDirectory = Paths.get("build/test/markdown/to_folder");
        FileUtils.deleteQuietly(outputDirectory.toFile());

        //When
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .build();
        Swagger2MarkupConverter.from(file)
                .withConfig(config)
                .build()
                .toFolder(outputDirectory);

        assert true;
    }

    @Test
    public void testWithMinMaxItems() throws IOException, URISyntaxException {
        //Given
        Path file = Paths.get(MarkdownConverterTest.class.getResource("/yaml/swagger_petstore_min_max_items.yaml").toURI());
        Path outputDirectory = Paths.get("build/test/markdown/to_folder");
        FileUtils.deleteQuietly(outputDirectory.toFile());

        //When
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .build();
        Swagger2MarkupConverter.from(file)
                .withConfig(config)
                .build()
                .toFolder(outputDirectory);
        assert true;

    }

    @Test
    public void testWithInterDocumentCrossReferences() throws IOException, URISyntaxException {
        //Given
        Path file = Paths.get(AsciidocConverterTest.class.getResource("/yaml/swagger_petstore.yaml").toURI());
        Path outputDirectory = Paths.get("build/test/markdown/idxref");
        FileUtils.deleteQuietly(outputDirectory.toFile());

        //When
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withInterDocumentCrossReferences()
                .build();

        Swagger2MarkupConverter.from(file).withConfig(config).build()
                .toFolder(outputDirectory);

    }

    @Test
    public void testWithSeparatedDefinitions() throws IOException, URISyntaxException {
        //Given
        Path file = Paths.get(MarkdownConverterTest.class.getResource("/yaml/swagger_petstore.yaml").toURI());
        Path outputDirectory = Paths.get("build/test/markdown/generated");
        FileUtils.deleteQuietly(outputDirectory.toFile());

        //When
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withSeparatedDefinitions()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .build();
        Swagger2MarkupConverter.from(file)
                .withConfig(config)
                .build()
                .toFolder(outputDirectory);

    }

    @Test
    public void testWithResponseHeaders() throws IOException, URISyntaxException {
        //Given
        Path file = Paths.get(AsciidocConverterTest.class.getResource("/yaml/swagger_response_headers.yaml").toURI());
        Path outputDirectory = Paths.get("build/test/markdown/response_headers");
        FileUtils.deleteQuietly(outputDirectory.toFile());

        //When
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .build();
        Swagger2MarkupConverter.from(file)
                .withConfig(config)
                .build()
                .toFolder(outputDirectory);
    }

    @Test
    public void testHandlesComposition() throws IOException, URISyntaxException {
        //Given
        Path file = Paths.get(MarkdownConverterTest.class.getResource("/yaml/swagger_petstore.yaml").toURI());
        Path outputDirectory = Paths.get("build/test/markdown/generated");
        FileUtils.deleteQuietly(outputDirectory.toFile());

        //When
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withSeparatedDefinitions()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .build();
        Swagger2MarkupConverter.from(file)
                .withConfig(config)
                .build()
                .toFolder(outputDirectory);
    }
}
