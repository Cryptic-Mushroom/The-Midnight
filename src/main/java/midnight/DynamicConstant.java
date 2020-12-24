/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 12 - 24
 */

package midnight;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO THIS DOESN'T WORK
//  Until we have ModUtil we'll have to inject our constants manually

/**
 * Replaces a certain constant value with a value generated during Gradle build. Usage:<br><br>
 * <code>
 * {@literal @}DynamicConstant("version")<br> public static String VERSION = "-uninjected-";
 * </code>
 *
 * @author Shadew
 * @since 0.6.0
 * @deprecated We still have to implement dynamic constant injection. Keep the usages for now but please note that this
 *     doesn't work yet.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
@Deprecated
public @interface DynamicConstant {
    /**
     * The constant name to inject, set in the Gradle buildscript.
     */
    String value();
}
