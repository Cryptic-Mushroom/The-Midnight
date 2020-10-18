/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - $today.date
 */

package midnight;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Replaces a certain constant value with a value generated during Gradle build. Usage:<br><br>
 * <code>
 * {@literal @}DynamicConstant("version")<br> public static String VERSION = "-uninjected-";
 * </code>
 *
 * @author Shadew
 * @version 0.6.0
 * @since 0.6.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface DynamicConstant {
    /**
     * The constant name to inject, set in the Gradle buildscript.
     */
    String value();
}
