/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.gradle;

import groovy.lang.Closure;
import groovy.lang.GroovyObjectSupport;
import midnight.gradle.changelog.ChangelogInfo;
import org.gradle.api.Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class MidnightExtension extends GroovyObjectSupport {
    protected final Project project;
    protected final ShadeRemapper shadeRemapper = new ShadeRemapper();
    protected final List<Function<String, Object>> constants = new ArrayList<>();
    protected ChangelogInfo info;
    protected File updateJson;
    protected final List<File> markdownOutput = new ArrayList<>();

    public MidnightExtension(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public void shade(String fromPackage, String toPackage) {
        shadeRemapper.addPackageRename(fromPackage.replace('.', '/'), toPackage.replace('.', '/'));
    }

    public ShadeRemapper getShadeRemapper() {
        return shadeRemapper;
    }

    public List<Function<String, Object>> getConstants() {
        return Collections.unmodifiableList(constants);
    }

    public Object getConstant(String name) {
        for (Function<String, Object> fn : constants) {
            Object val = fn.apply(name);
            if (val != null) return val;
        }
        return null;
    }

    public void constant(String name, Object value) {
        constants.add(key -> key.equals(name) ? value : null);
    }

    public void constants(Function<String, Object> fn) {
        constants.add(fn);
    }

    public void constants(Map<String, Object> map) {
        constants.add(map::get);
    }

    public void constants(Closure<Object> closure) {
        constants.add(closure::call);
    }

    public void changelogJson(File changelogJson) {
        try {
            info = ChangelogInfo.load(changelogJson);
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
            throw new UncheckedIOException(exc);
        }
    }

    public void changelogInfo(ChangelogInfo info) {
        this.info = info;
    }

    public ChangelogInfo getChangelogInfo() {
        return info;
    }

    public void updateJson(File updateJson) {
        this.updateJson = updateJson;
    }

    public File getUpdateJson() {
        return updateJson;
    }

    public void markdownChangelog(File out) {
        markdownOutput.add(out);
    }

    public List<File> getMarkdownChangelog() {
        return markdownOutput;
    }
}
