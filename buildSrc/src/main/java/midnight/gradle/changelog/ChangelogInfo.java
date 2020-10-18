/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.gradle.changelog;

import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangelogInfo {
    private static final Pattern VERSION_PATTERN = Pattern.compile("([0-9]+)\\.([0-9]+)(?:\\.([0-9]+)(?:-([0-9]+))?)?");

    private int versionMajor;
    private int versionMinor;
    private int versionPatch;
    private int versionHotfix;
    private String versionName;
    private String description;
    private String mcversion;
    private boolean stable;
    private final List<String> changelog = new ArrayList<>();

    public void parseVersionNumber(String version) {
        Matcher versionMatcher = VERSION_PATTERN.matcher(version);
        if (!versionMatcher.matches()) {
            throw new NumberFormatException("Not a valid version: " + version);
        }

        String majorStr = versionMatcher.group(1);
        String minorStr = versionMatcher.group(2);
        String patchStr = orElse(versionMatcher.group(3), "0");
        String hotfixStr = orElse(versionMatcher.group(4), "0");

        versionMajor = Integer.parseUnsignedInt(majorStr);
        versionMinor = Integer.parseUnsignedInt(minorStr);
        versionPatch = Integer.parseUnsignedInt(patchStr);
        versionHotfix = Integer.parseUnsignedInt(hotfixStr);
    }

    public String getVersionNumber() {
        StringBuilder num = new StringBuilder();
        num.append(versionMajor);
        num.append(".");
        num.append(versionMinor);
        num.append(".");
        num.append(versionPatch);
        if (versionHotfix != 0) {
            num.append("-");
            num.append(versionHotfix);
        }
        return num.toString();
    }

    private static String orElse(String nullable, String other) {
        return nullable == null ? other : nullable;
    }

    public void setVersionMajor(int versionMajor) {
        this.versionMajor = versionMajor;
    }

    public int getVersionMajor() {
        return versionMajor;
    }

    public void setVersionMinor(int versionMinor) {
        this.versionMinor = versionMinor;
    }

    public int getVersionMinor() {
        return versionMinor;
    }

    public void setVersionPatch(int versionPatch) {
        this.versionPatch = versionPatch;
    }

    public int getVersionPatch() {
        return versionPatch;
    }

    public void setVersionHotfix(int versionHotfix) {
        this.versionHotfix = versionHotfix;
    }

    public int getVersionHotfix() {
        return versionHotfix;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public void addChangelog(String item) {
        changelog.add(item);
    }

    public List<String> getChangelog() {
        return changelog;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setMcversion(String mcversion) {
        this.mcversion = mcversion;
    }

    public String getMcversion() {
        return mcversion;
    }

    public boolean isStable() {
        return stable;
    }

    public void setStable(boolean stable) {
        this.stable = stable;
    }

    public static ChangelogInfo parseChangelog(JsonElement element) {
        if (!element.isJsonObject()) throw new JsonSyntaxException("changelog.json: Expected an object");
        JsonObject object = element.getAsJsonObject();
        JsonElement version = object.get("version");
        JsonElement changelog = object.get("changelog");
        JsonElement description = object.get("description");

        if (version == null) throw new JsonSyntaxException("changelog.json: Missing 'version'");
        if (!version.isJsonObject()) throw new JsonSyntaxException("changelog.json: 'version' is not an object");
        JsonObject versionObject = version.getAsJsonObject();

        ChangelogInfo info = new ChangelogInfo();
        JsonElement number = versionObject.get("number");
        if (number.isJsonObject()) {
            JsonObject numberObject = number.getAsJsonObject();
            if (!numberObject.has("major"))
                throw new JsonSyntaxException("changelog.json: Missing major version number");
            if (!numberObject.has("minor"))
                throw new JsonSyntaxException("changelog.json: Missing minor version number");
            JsonElement major = numberObject.get("major");
            JsonElement minor = numberObject.get("minor");
            JsonElement patch = numberObject.get("patch");
            JsonElement hotfix = numberObject.get("hotfix");

            if (!major.isJsonPrimitive())
                throw new JsonSyntaxException("changelog.json: Major version number is not a number");
            info.setVersionMajor(major.getAsInt());

            if (!minor.isJsonPrimitive())
                throw new JsonSyntaxException("changelog.json: Minor version number is not a number");
            info.setVersionMinor(minor.getAsInt());

            if (patch != null) {
                if (!patch.isJsonPrimitive())
                    throw new JsonSyntaxException("changelog.json: Patch version number is not a number");
                info.setVersionPatch(patch.getAsInt());
            }

            if (hotfix != null) {
                if (!hotfix.isJsonPrimitive())
                    throw new JsonSyntaxException("changelog.json: Hotfix version number is not a number");
                info.setVersionHotfix(hotfix.getAsInt());
            }
        } else if (number.isJsonArray()) {
            JsonArray numberArray = number.getAsJsonArray();
            if (numberArray.size() == 0)
                throw new JsonSyntaxException("changelog.json: Missing major version number");
            if (numberArray.size() == 1)
                throw new JsonSyntaxException("changelog.json: Missing minor version number");
            JsonElement major = numberArray.get(0);
            JsonElement minor = numberArray.get(1);
            JsonElement patch = numberArray.size() >= 2 ? numberArray.get(2) : null;
            JsonElement hotfix = numberArray.size() >= 3 ? numberArray.get(3) : null;

            if (!major.isJsonPrimitive())
                throw new JsonSyntaxException("changelog.json: Major version number is not a number");
            info.setVersionMajor(major.getAsInt());

            if (!minor.isJsonPrimitive())
                throw new JsonSyntaxException("changelog.json: Minor version number is not a number");
            info.setVersionMinor(minor.getAsInt());

            if (patch != null) {
                if (!patch.isJsonPrimitive())
                    throw new JsonSyntaxException("changelog.json: Patch version number is not a number");
                info.setVersionPatch(patch.getAsInt());
            }

            if (hotfix != null) {
                if (!hotfix.isJsonPrimitive())
                    throw new JsonSyntaxException("changelog.json: Hotfix version number is not a number");
                info.setVersionHotfix(hotfix.getAsInt());
            }
        } else if (number.isJsonPrimitive()) {
            try {
                info.parseVersionNumber(number.getAsString());
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                throw new JsonSyntaxException("changelog.json: Invalid version number format");
            }
        } else {
            throw new JsonSyntaxException("changelog.json: Version number not an object, array or primitive");
        }

        JsonElement name = versionObject.get("name");
        if (name == null)
            throw new JsonSyntaxException("changelog.json: Missing version name");
        if (!name.isJsonPrimitive())
            throw new JsonSyntaxException("changelog.json: Version name is not a string");
        info.setVersionName(name.getAsString());

        JsonElement minecraft = versionObject.get("minecraft");
        if (minecraft == null)
            throw new JsonSyntaxException("changelog.json: Missing minecraft version");
        if (!minecraft.isJsonPrimitive())
            throw new JsonSyntaxException("changelog.json: Minecraft version is not a string");
        info.setMcversion(minecraft.getAsString());

        JsonElement stable = versionObject.get("stable");
        if (stable == null)
            throw new JsonSyntaxException("changelog.json: Missing version stability");
        if (!stable.isJsonPrimitive())
            throw new JsonSyntaxException("changelog.json: Version stability is not a boolean");
        info.setStable(stable.getAsBoolean());

        if (changelog != null) {
            if (!changelog.isJsonArray()) throw new JsonSyntaxException("changelog.json: Changelog is not an array");
            JsonArray changelogArray = changelog.getAsJsonArray();

            for (JsonElement entry : changelogArray) {
                if (!entry.isJsonPrimitive())
                    throw new JsonSyntaxException("changelog.json: Changelog entry is not a string");
                info.addChangelog(entry.getAsString());
            }
        }

        if (description != null) {
            if (description.isJsonPrimitive()) {
                info.setDescription(description.getAsString());
            } else if (description.isJsonArray()) {
                JsonArray descriptionArray = description.getAsJsonArray();
                List<String> lines = new ArrayList<>();
                for (JsonElement entry : descriptionArray) {
                    if (!entry.isJsonPrimitive())
                        throw new JsonSyntaxException("changelog.json: Description entry is not a string");
                    lines.add(entry.getAsString());
                }
                info.setDescription(String.join(" ", lines));
            } else {
                throw new JsonSyntaxException("changelog.json: Description is not a string or array");
            }
        }

        return info;
    }

    public static ChangelogInfo load(File file) throws FileNotFoundException {
        JsonElement el = new JsonParser().parse(new FileReader(file));
        if (!el.isJsonObject()) throw new JsonParseException("changelog.json: Root element not an object");
        try {
            return parseChangelog(el.getAsJsonObject());
        } catch (Throwable thr) {
            thr.printStackTrace();
            throw new RuntimeException(thr);
        }
    }
}
