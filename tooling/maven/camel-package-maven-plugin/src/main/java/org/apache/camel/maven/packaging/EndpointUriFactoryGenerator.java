/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.maven.packaging;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;

import org.apache.camel.tooling.model.BaseOptionModel;
import org.apache.camel.tooling.model.ComponentModel;

public final class EndpointUriFactoryGenerator {

    private EndpointUriFactoryGenerator() {
    }

    public static void generateEndpointUriFactory(
            String pn, String cn, String en,
            String pfqn, String psn, ComponentModel model, Writer w)
            throws IOException {

        w.write("/* " + AbstractGeneratorMojo.GENERATED_MSG + " */\n");
        w.write("package " + pn + ";\n");
        w.write("\n");
        w.write("import java.net.URISyntaxException;\n");
        w.write("import java.util.HashMap;\n");
        w.write("import java.util.HashSet;\n");
        w.write("import java.util.Map;\n");
        w.write("import java.util.Set;\n");
        w.write("\n");
        w.write("import org.apache.camel.spi.EndpointUriFactory;\n");
        w.write("\n");
        w.write("/**\n");
        w.write(" * " + AbstractGeneratorMojo.GENERATED_MSG + "\n");
        w.write(" */\n");
        w.write("public class " + cn + " extends " + psn + " implements EndpointUriFactory {\n");
        w.write("\n");
        w.write("    private static final String BASE = \"" + baseSyntax(model) + "\";\n");

        String alternative = alternativeSchemes(model);
        if (alternative != null) {
            w.write("    private static final String[] SCHEMES = " + alternative + ";\n");
        }
        w.write("\n");
        w.write("    private static final Set<String> PROPERTY_NAMES;\n");
        w.write(generatePropertyNames(model));
        w.write("\n");
        w.write("    @Override\n");
        w.write("    public boolean isEnabled(String scheme) {\n");
        if (alternative == null) {
            w.write("        return \"" + model.getScheme() + "\".equals(scheme);\n");
        } else {
            w.write("        for (String s : SCHEMES) {\n");
            w.write("            if (s.equals(scheme)) {\n");
            w.write("                return true;\n");
            w.write("            }\n");
            w.write("        }\n");
            w.write("        return false;\n");
        }
        w.write("    }\n");
        w.write("\n");
        w.write("    @Override\n");
        w.write("    public String buildUri(String scheme, Map<String, Object> properties) throws URISyntaxException {\n");
        w.write("        String syntax = scheme + BASE;\n");
        w.write("        String uri = syntax;\n");
        w.write("\n");
        w.write("        Map<String, Object> copy = new HashMap<>(properties);\n");
        w.write("\n");
        for (BaseOptionModel option : model.getEndpointPathOptions()) {
            w.write("        uri = buildPathParameter(syntax, uri, \"" + option.getName() + "\", "
                    + defaultValue(option) + ", " + option.isRequired() + ", copy);\n");
        }
        w.write("        uri = buildQueryParameters(uri, copy);\n");
        w.write("        return uri;\n");
        w.write("    }\n");
        w.write("\n");
        w.write("    @Override\n");
        w.write("    public Set<String> propertyNames() {\n");
        w.write("        return PROPERTY_NAMES;\n");
        w.write("    }\n");
        w.write("\n");
        w.write("    @Override\n");
        w.write("    public boolean isLenientProperties() {\n");
        w.write("        return " + model.isLenientProperties() + ";\n");
        w.write("    }\n");
        w.write("}\n");
        w.write("\n");
    }

    private static String generatePropertyNames(ComponentModel model) {
        int size = model.getEndpointOptions().size();
        // use sorted set so the code is always generated the same way
        Set<String> apis = new TreeSet<>();
        if (model.isApi()) {
            // gather all the option names from the api (they can be duplicated as the same name can be used by multiple methods)
            model.getApiOptions().forEach(a -> a.getMethods().forEach(m -> m.getOptions().forEach(o -> apis.add(o.getName()))));
            size += apis.size();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("    static {\n");
        sb.append("        Set<String> set = new HashSet<>(").append(size).append(");\n");
        for (ComponentModel.EndpointOptionModel option : model.getEndpointOptions()) {
            sb.append("        set.add(\"").append(option.getName()).append("\");\n");
        }
        for (String name : apis) {
            sb.append("        set.add(\"").append(name).append("\");\n");
        }
        sb.append("        PROPERTY_NAMES = set;\n");
        sb.append("    }\n");
        return sb.toString();
    }

    private static String alternativeSchemes(ComponentModel model) {
        StringBuilder sb = new StringBuilder();
        if (model.getAlternativeSchemes() != null) {
            sb.append("new String[]{");
            String[] alts = model.getAlternativeSchemes().split(",");
            StringJoiner sj = new StringJoiner(", ");
            for (String alt : alts) {
                sj.add("\"" + alt + "\"");
            }
            sb.append(sj.toString());
            sb.append("}");
        }
        if (sb.length() == 0) {
            return null;
        }
        return sb.toString();
    }

    private static String baseSyntax(ComponentModel model) {
        String base = model.getSyntax();
        base = base.replaceFirst(model.getScheme(), "");
        return base;
    }

    private static Object defaultValue(BaseOptionModel option) {
        Object obj = option.getDefaultValue();
        if (obj instanceof String) {
            return "\"" + obj + "\"";
        } else {
            return obj;
        }
    }

}
