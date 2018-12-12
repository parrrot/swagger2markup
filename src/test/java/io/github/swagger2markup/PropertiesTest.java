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

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.BDDAssertions.assertThat;

public class PropertiesTest {


    private static final String PROPERTIES_DEFAULT = "io/github/swagger2markup/config/custom_test.properties";


    private Configuration getDefaultConfiguration() {
        Configurations configs = new Configurations();
        try {
            return configs.properties(PROPERTIES_DEFAULT);
        } catch (ConfigurationException e) {
            throw new RuntimeException(String.format("Can't load default properties '%s'", PROPERTIES_DEFAULT), e);
        }
    }

    @Test
    public void testProperties() throws ConfigurationException {

        Configuration configuration = getDefaultConfiguration();

        Swagger2MarkupProperties swagger2MarkupProperties =
                new Swagger2MarkupProperties(configuration);


        assertThat(swagger2MarkupProperties.getInt("unexistingProperty",666)).isEqualTo(666);
        assertThat(swagger2MarkupProperties.getInteger("unexistingProperty").isPresent()).isEqualTo(false);


        Assertions.assertThatIllegalStateException().isThrownBy(()->{
            swagger2MarkupProperties.getRequiredInt("someNonePresentRequiredField");
        });

        Assertions.assertThatIllegalStateException().isThrownBy(()->{
            swagger2MarkupProperties.getRequiredBoolean("someNonePresentRequiredField");
        });

        assertThat(swagger2MarkupProperties.getURI("someNonPresentUri").isPresent()).isEqualTo(false);

        Assertions.assertThatIllegalStateException().isThrownBy(()->{
            swagger2MarkupProperties.getRequiredURI("someNonPresentUri");
        });

        assertThat(swagger2MarkupProperties.getPath("someNonPresentUri").isPresent()).isEqualTo(false);

        assertThat(swagger2MarkupProperties.getPathList("someNonPresentUri").size()).isEqualTo(0);


        Assertions.assertThatIllegalStateException().isThrownBy(()->{
            swagger2MarkupProperties.getRequiredPath("someNonPresentUri");
        });

        assertThat(swagger2MarkupProperties.getMarkupLanguage("someNonPresentUri").isPresent()).isEqualTo(false);


        Assertions.assertThatIllegalStateException().isThrownBy(()->{
            swagger2MarkupProperties.getRequiredString("someNonPresentUri");
        });


        assertThat(swagger2MarkupProperties.getLanguage("swagger2markup.outputLanguage").name()).isEqualTo("EN");

        assertThat(swagger2MarkupProperties.getGroupBy("swagger2markup.pathsGroupedBy")).isEqualTo(GroupBy.AS_IS);


        assertThat(swagger2MarkupProperties.getOrderBy("swagger2markup.parameterOrderBy")).isEqualTo(OrderBy.NATURAL);

        final int expectedPropSize = 29;
        assertThat(swagger2MarkupProperties.getKeys().size()).isEqualTo(expectedPropSize);
        assertThat(swagger2MarkupProperties.getKeys("swagger2markup").size()).isEqualTo(expectedPropSize);

        assertThat(
                swagger2MarkupProperties.
                        getPageBreakLocations("swagger2markup.somePageBreakLocations").get(0))
                .isEqualTo(PageBreakLocations.BEFORE_OPERATION_DESCRIPTION);


        assertThat(swagger2MarkupProperties.getHeaderPattern("swagger2markup.flatBodyEnabled").get().pattern()).isEqualTo("false");

    }
}
