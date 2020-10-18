/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - $today.date
 */

package midnight.gradle.changelog;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class MarkdownChangelogGenerator {
    private final ChangelogInfo info;
    private final File outFile;

    public MarkdownChangelogGenerator(ChangelogInfo info, File out) {
        this.info = info;
        this.outFile = out;
    }

    public void generate() throws IOException {
        outFile.getParentFile().mkdirs();
        PrintStream out = new PrintStream(outFile);
        out.printf("## %s - %s\n", info.getVersionNumber(), info.getVersionName());
        out.println();
        out.printf("**For Minecraft %s**\n", info.getMcversion());
        out.println();
        if (info.getDescription() != null) {
            out.println(info.getDescription());
            out.println();
        }
        out.println("#### Changelog");
        out.println();
        for (String changelog : info.getChangelog()) {
            out.printf("- %s\n", changelog);
        }
        out.close();
    }
}
