/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - $today.date
 */

package midnight.gradle;

import org.gradle.api.DefaultTask;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.ClassRemapper;

import java.io.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class ShadeRenameTask extends DefaultTask {
    private File input;
    private File output;
    private ShadeRemapper remapper;

    public ShadeRenameTask() {

        doLast(task -> {
            try {
                JarOutputStream out = new JarOutputStream(new FileOutputStream(output));

                JarFile jar = new JarFile(input);
                jar.stream().forEach(entry -> {
                    try {
                        out.putNextEntry(new JarEntry(this.remapper.map(entry.getName())));
                        if (entry.getName().endsWith(".class")) {
                            ClassReader reader = new ClassReader(jar.getInputStream(entry));
                            ClassWriter writer = new ClassWriter(0);
                            ClassRemapper remapper = new ClassRemapper(writer, this.remapper);
                            reader.accept(remapper, ClassReader.EXPAND_FRAMES);
                            out.write(writer.toByteArray());
                        } else {
                            InputStream is = jar.getInputStream(entry);
                            byte[] buf = new byte[1024];
                            int r;
                            while ((r = is.read(buf)) != -1) {
                                out.write(buf, 0, r);
                            }
                        }
                        out.closeEntry();
                    } catch (Throwable thr) {
                        thr.printStackTrace();
                        throw new RuntimeException(thr);
                    }
                });
                out.close();
            } catch (IOException exc) {
                exc.printStackTrace();
                throw new UncheckedIOException(exc);
            }
        });
    }

    public void setRemapper(ShadeRemapper remapper) {
        this.remapper = remapper;
    }

    public ShadeRemapper getRemapper() {
        return remapper;
    }

    public void setInput(File input) {
        this.input = input;
    }

    public void setOutput(File output) {
        output.getParentFile().mkdirs();
        this.output = output;
    }

    public File getInput() {
        return input;
    }

    public File getOutput() {
        return output;
    }
}
