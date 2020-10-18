/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.gradle.changelog;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

public class MarkdownGenTask extends DefaultTask {
    private ChangelogInfo info;
    private final List<File> markdownOut = new ArrayList<>();

    public void setInfo(ChangelogInfo info) {
        this.info = info;
    }

    public void setMarkdownOut(File markdownOut) {
        this.markdownOut.clear();
        this.markdownOut.add(markdownOut);
    }

    public void setMarkdownOut(List<File> markdownOut) {
        this.markdownOut.clear();
        this.markdownOut.addAll(markdownOut);
    }

    public ChangelogInfo getInfo() {
        return info;
    }

    public List<File> getMarkdownOut() {
        return markdownOut;
    }

    public void markdown(File out) {
        this.markdownOut.add(out);
    }

    public void changelog(ChangelogInfo info) {
        setInfo(info);
    }

    @TaskAction
    private void invoke() {
        for (File out : markdownOut) {
            MarkdownChangelogGenerator gen = new MarkdownChangelogGenerator(info, out);
            try {
                gen.generate();
            } catch (IOException exc) {
                exc.printStackTrace();
                throw new UncheckedIOException(exc);
            }
        }
    }
}
