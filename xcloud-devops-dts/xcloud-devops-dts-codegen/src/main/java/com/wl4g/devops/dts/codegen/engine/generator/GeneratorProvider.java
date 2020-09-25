/*
 * Copyright 2017 ~ 2025 the original author or authors. <wanglsir@gmail.com, 983708408@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wl4g.devops.dts.codegen.engine.generator;

import com.wl4g.components.common.annotation.Nullable;
import com.wl4g.devops.dts.codegen.engine.converter.DbTypeConverter.CodeLanguage;

import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static com.wl4g.components.common.collection.Collections2.isEmptyArray;
import static com.wl4g.components.common.collection.Collections2.safeList;
import static com.wl4g.components.common.lang.Assert2.*;
import static com.wl4g.components.common.reflect.ReflectionUtils2.getFieldValues;
import static com.wl4g.devops.dts.codegen.engine.generator.GeneratorProvider.GenProviderAlias.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * {@link GeneratorProvider}
 *
 * @author Wangl.sir <wanglsir@gmail.com, 983708408@qq.com>
 * @version v1.0 2020-09-07
 * @since
 */
public interface GeneratorProvider extends Runnable {

    @Override
    default public void run() {
        try {
            doGenerate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Do execution generate.
     *
     * @throws Exception
     */
    void doGenerate() throws Exception;

    /**
     * {@link GenProviderAlias}
     */
    public static interface GenProviderAlias {

        /**
         * Standard spring cloud + maven projecs gen provider.
         */
        public static final String SPINGCLOUD_MVN = "springCloudMvnProvider";

        /**
         * Standard golang(mod) projecs gen provider.
         */
        public static final String GO_STANDARD = "standardGoProvider";
        /**
         * Standard csharp projecs gen provider.
         */
        public static final String CSHARP_STANDARD = "standardCsharpProvider";
        /**
         * Standard python projecs gen provider.
         */
        public static final String PYTHON_STANDARD = "standardPythonProvider";

        /**
         * VueJS projecs gen provider.
         */
        public static final String VUEJS = "vuejsProvider";

        /**
         * AngularJS projecs gen provider.
         */
        public static final String NGJS = "ngjsProvider";

        /**
         * List of field values of class {@link GenProviderAlias}.
         */
        public static final String[] VALUES = getFieldValues(GenProviderAlias.class, null, "VALUES").toArray(new String[]{});

    }

    /**
     * An extensible configuration options {@link GenExtraOptionSupport} which
     * is supported by itself, If NULL is returned, there is no extensible
     * configuration item.
     *
     * @author Wangl.sir <wanglsir@gmail.com, 983708408@qq.com>
     * @version v1.0 2020-09-16
     * @since
     */
    public static enum GenExtraOptionSupport {

        ExtSpringCloudMvnBuildAssetsType(new ConfigOption(SPINGCLOUD_MVN, "gen.build.assets-type", "MvnAssTar", "SpringExecJar")),

        ExtVueJSCompression(new ConfigOption(VUEJS, "gen.compression", "true", "false")),

        ExtVueJSBasedOnAdminUi(new ConfigOption(VUEJS, "gen.basedon.adminui", "true", "false")),

        ExtNgJSCompression(new ConfigOption(NGJS, "gen.compression", "true", "false"));

        /**
         * Gen provider extra option of {@link ConfigOption} .
         */
        @NotNull
        private final ConfigOption option;

        private GenExtraOptionSupport(@NotNull ConfigOption option) {
            notNullOf(option, "option");
            this.option = option.validate();
        }

        public final ConfigOption getOption() {
            return option;
        }

        /**
         * Gets {@link ConfigOption} by providers.
         *
         * @param provider
         * @return
         */
        public static List<ConfigOption> getOptions(@Nullable String... providers) {
            final List<String> conditions = new ArrayList<>();
            if (!isEmptyArray(providers)) {
                conditions.addAll(asList(providers));
            }
            return asList(values()).stream()
                    .filter(o -> (isEmpty(conditions) || conditions.contains(o.getOption().getProvider())))
                    .map(o -> o.getOption()).collect(toList());
        }

        /**
         * Validation {@link ConfigOption} name and values invalid?
         *
         * @param option
         */
        public static void validateOption(@NotBlank String provider, @NotBlank String name, @NotBlank String value) {
            hasTextOf(provider, "provider");
            hasTextOf(name, "name");
            hasTextOf(value, "value");

            // Validate option name & values.
            List<ConfigOption> options = safeList(getOptions(provider));
            isTrue(options.stream().filter(o -> o.getName().equals(name)).count() > 0,
                    "Invalid option name: '%s' of provider: '%s'", name, provider);
            isTrue(options.stream().filter(o -> o.getValues().contains(value)).count() > 0,
                    "Invalid option name: '%s', value: '%s' of provider: '%s'", name, value, provider);
        }

        /**
         * Check whether the current configured items match the additional
         * configuration items of the specified service provider.
         *
         * @param provider
         * @param configuredOptions
         * @return
         */
//		public static boolean checkConfigured(@NotBlank String provider, @NotEmpty List<ConfigOption> configuredOptions) {
//			hasTextOf(provider, "provider");
//			notEmptyOf(configuredOptions, "configuredOptions");
//			for (ConfigOption defineOption : safeList(getOptions(provider))) {
//				for (ConfigOption configuredOption : configuredOptions) {
//					if (defineOption.getName().equals(configuredOption.getName())
//							&& defineOption.getValues().contains(configuredOption.getSelectedValue())) {
//						return true;
//					}
//				}
//			}
//			return false;
//		}

        /**
         * {@link ConfigOption}
         *
         * @author Wangl.sir <wanglsir@gmail.com, 983708408@qq.com>
         * @version v1.0 2020-09-16
         * @since
         */
        public static class ConfigOption {

            /**
             * {@link GeneratorProvider} alias.
             */
            @NotBlank
            private String provider;

            /**
             * Gen provider extra configuration option name.
             */
            @NotBlank
            private String name;

            /**
             * Gen provider extra configuration option values.
             */
            @NotEmpty
            private List<String> values;

            /**
             * Gen provider used configured value.
             */
            @Nullable
            private String selectedValue;

            public ConfigOption() {
                super();
            }

            public ConfigOption(@NotBlank String provider, @NotBlank String name, @NotEmpty String... values) {
                this(provider, name, asList(notEmptyOf(values, "values")));
            }

            public ConfigOption(@NotBlank String provider, @NotBlank String name, @NotEmpty List<String> values) {
                setProvider(provider);
                setName(name);
                setValues(values);
            }

            /**
             * Gets extra option of gen provider.
             *
             * @return
             */
            @NotBlank
            public String getProvider() {
                return provider;
            }

            /**
             * Sets extra option of gen provider.
             *
             * @param provider
             */
            public void setProvider(@NotBlank String provider) {
                this.provider = hasTextOf(provider, "provider");
            }

            /**
             * Sets extra option of gen provider.
             *
             * @param provider
             */
            public ConfigOption withProvider(@NotBlank String provider) {
                setProvider(provider);
                return this;
            }

            /**
             * Gets extra option name.
             *
             * @return
             */
            @NotBlank
            public final String getName() {
                return name;
            }

            /**
             * Sets extra option name.
             *
             * @param name
             */
            public void setName(@NotBlank String name) {
                this.name = hasTextOf(name, "name");
            }

            /**
             * Sets extra option name.
             *
             * @param name
             */
            public ConfigOption withName(@NotBlank String name) {
                setName(name);
                return this;
            }

            /**
             * Gets extra option values.
             *
             * @return
             */
            @NotEmpty
            public final List<String> getValues() {
                return values;
            }

            /**
             * Sets extra option values.
             *
             * @param values
             */
            public void setValues(@NotEmpty List<String> values) {
                this.values = notEmptyOf(values, "values");
            }

            /**
             * Sets extra option values.
             *
             * @param values
             */
            public ConfigOption withValues(@NotEmpty List<String> values) {
                setValues(values);
                return this;
            }

            /**
             * Gets selected extra option value.
             *
             * @return
             */
            @Nullable
            public final String getSelectedValue() {
                return selectedValue;
            }

            /**
             * Sets selected extra option value.
             *
             * @param values
             */
            public void setSelectedValue(@Nullable String selectedValue) {
                // this.selectedValue = hasTextOf(selectedValue,
                // "selectedValue");
                this.selectedValue = selectedValue;
            }

            /**
             * Sets selected extra option value.
             *
             * @param values
             */
            public ConfigOption withSelectedValue(@Nullable String selectedValue) {
                setSelectedValue(selectedValue);
                return this;
            }

            /**
             * Validation for itself attributes.
             *
             * @return
             */
            public final ConfigOption validate() {
                hasTextOf(provider, "provider");
                hasTextOf(name, "name");
                notEmptyOf(values, "values");
                return this;
            }

            /**
             * Validation for attributes.
             *
             * @param option
             */
            public static void validate(@NotNull ConfigOption option) {
                notNullOf(option, "option");
                hasTextOf(option.provider, "provider");
                hasTextOf(option.name, "name");
                notEmptyOf(option.values, "values");
            }

        }

    }

    /**
     * {@link GeneratorProvider} group set.
     *
     * @author Wangl.sir <wanglsir@gmail.com, 983708408@qq.com>
     * @version v1.0 2020-09-16
     * @since
     */
    public static enum GenProviderSet {

        DaoServiceController(asList(SPINGCLOUD_MVN), CodeLanguage.JAVA, "Mybatis/Spring Cloud/XCloud Iam"),

        DaoServiceControllerVueJS(asList(SPINGCLOUD_MVN, VUEJS), CodeLanguage.JAVA,
                "Mybatis/Spring Cloud/XCloud Iam/ElementUI Vue"),

        // Nothing to do with DAO layer
        JustVueJS(asList(VUEJS), null, "ElementUI Vue"),

        // Nothing to do with DAO layer
        JustNgJS(asList(NGJS), null, "AngularJS");

        /**
         * {@link GenProviderAlias}
         */
        @NotEmpty
        private final List<String> providers;

        /**
         * When the generator provider group contains the DAO layer of the
         * generated database, it is necessary to set the source
         * {@link CodeLanguage} type to map the relationship between
         * DbColumnType and attrType.
         */
        @Nullable
        private final CodeLanguage language;

        @Nullable
        private final String description;

        private GenProviderSet(@NotEmpty List<String> providers, @Nullable CodeLanguage language, @Nullable String description) {
            this.providers = notEmptyOf(providers, "providers");
            this.language = language;
            this.description = description;
        }

        /**
         * Gets that providers.
         *
         * @return
         */
        public final List<String> getProviders() {
            return providers;
        }

        /**
         * Gets that {@link CodeLanguage}.
         *
         * @return
         */
        public final CodeLanguage getLanguage() {
            return language;
        }

        public String getDescription() {
            return description;
        }

        /**
         * Gets providers by group name.
         *
         * @param group
         * @return
         */
        public static List<String> getProviders(@Nullable String group) {
            for (GenProviderSet en : values()) {
                if (StringUtils.equalsIgnoreCase(en.name(), group)) {
                    return en.getProviders();
                }
            }
            return emptyList();
        }

        /**
         * Parse {@link GenProviderSet} name.
         *
         * @param group
         * @return
         */
        public static GenProviderSet safeOf(@NotBlank String group) {
            for (GenProviderSet gpg : values()) {
                if (equalsIgnoreCase(gpg.name(), group)) {
                    return gpg;
                }
            }
            return null;
        }

        /**
         * Parse {@link GenProviderSet} name.
         *
         * @param group
         * @return
         */
        public static GenProviderSet of(@NotBlank String group) {
            return notNull(safeOf(group), "Cannot parse gen provider group of '%s'", group);
        }

    }

}