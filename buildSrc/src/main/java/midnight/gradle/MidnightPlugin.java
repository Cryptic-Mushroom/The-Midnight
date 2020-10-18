/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.gradle;

import midnight.gradle.changelog.MarkdownGenTask;
import midnight.gradle.changelog.VersionJsonTask;
import net.minecraftforge.gradle.userdev.tasks.RenameJarInPlace;
import org.gradle.api.*;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.compile.JavaCompile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MidnightPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        // Create our midnight extension
        MidnightExtension ext = project.getExtensions().create("midnight", MidnightExtension.class, project);

        if (project.getPluginManager().findPlugin("java") == null) {
            project.getPluginManager().apply("java");
        }

        NamedDomainObjectContainer<ShadeRenameTask> shade = project.container(ShadeRenameTask.class, jarName -> {
            String name = Character.toUpperCase(jarName.charAt(0)) + jarName.substring(1);

            String reobfJarName = "reobf" + name;
            String shadeJarName = "shade" + name;

            ShadeRenameTask task = project.getTasks().maybeCreate(shadeJarName, ShadeRenameTask.class);
            task.setRemapper(ext.getShadeRemapper());
            project.getTasks().getByName("assemble").dependsOn(task);

            project.afterEvaluate(p -> {
                Task jar = project.getTasks().getByName(jarName);
                Task reobfJar = project.getTasks().getByName(reobfJarName);
                if (!(jar instanceof Jar)) {
                    throw new IllegalStateException(jarName + " is not a jar task. Can only shade jars!");
                }
                if (!(reobfJar instanceof RenameJarInPlace)) {
                    // This jar task is not reobfed, so we shade it directly
                    project.getLogger().info(reobfJarName + " is not found. Shading " + jarName + " output directly");

                    // Actually our input is our output now, so we shade into a separate output file since we can't
                    // modify a file while reading it in the meantime
                    File jarOut = ((Jar) jar).getArchivePath();
                    File shadeOut = project.file("build/" + shadeJarName + "/output.jar");

                    task.setInput(jarOut);
                    task.setOutput(shadeOut);

                    // After shading, copy the output file back in place (which
                    task.doLast(tsk -> {
                        try {
                            FileInputStream fis = new FileInputStream(shadeOut);
                            FileOutputStream fos = new FileOutputStream(jarOut);
                            int r;
                            byte[] buf = new byte[1024];
                            while ((r = fis.read(buf)) > -1) {
                                fos.write(buf, 0, r);
                            }
                            fis.close();
                            fos.close();
                        } catch (IOException exc) {
                            exc.printStackTrace();
                            throw new UncheckedIOException(exc);
                        }
                    });
                    task.dependsOn(jar);
                } else {
                    // This jar task is reobfed, so we shade the reobfed jar
                    task.setInput(project.file("build/" + reobfJarName + "/output.jar"));
                    task.setOutput(((Jar) jar).getArchivePath());
                    task.dependsOn(reobfJar);
                }
            });

            return task;
        });
        project.getExtensions().add("shade", shade);

        JavaPluginConvention java = (JavaPluginConvention) project.getConvention().getPlugins().get("java");

        // TODO:
        //  - Inject constants in resources

        ConstantInjectionTask injectConstants = project.getTasks().create("injectConstants", ConstantInjectionTask.class, task -> {
            task.from(java.getSourceSets().getByName("main").getAllSource());
            task.into(project.getBuildDir() + "/sources/java/main");
            task.annotation("midnight.DynamicConstant");
            task.constants(ext::getConstant);
        });

        JavaCompile compileJava = (JavaCompile) project.getTasks().getByName("compileJava");
        compileJava.setSource(project.getBuildDir() + "/sources/java/main/");
        compileJava.dependsOn(injectConstants);

        if (project == project.getRootProject()) {
            project.getTasks().create("updateVersionJson", VersionJsonTask.class, task -> {
                project.afterEvaluate(p -> {
                    task.setJsonFile(ext.getUpdateJson());
                    task.setInfo(ext.getChangelogInfo());
                });
            });
            project.getTasks().create("genChangelogMarkdown", MarkdownGenTask.class, task -> {
                project.afterEvaluate(p -> {
                    task.setMarkdownOut(ext.getMarkdownChangelog());
                    task.setInfo(ext.getChangelogInfo());
                });
            });
        }
    }
}
