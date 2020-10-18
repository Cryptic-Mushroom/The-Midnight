/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 18
 */

package midnight.core.util;

// TODO There's a lot of generic stuff in here. Do you want to add this to the PTG library as well?
public final class MnMath {
    public static final float PI = (float) Math.PI;
    public static final float E = (float) Math.E;
    public static final float SQRT2 = 1.41421356237F;
    public static final float EPS = 0.0001F;

    private MnMath() {
    }

    public static float toRadians(float deg) {
        return deg / 180 * PI;
    }

    public static float toDegrees(float rad) {
        return rad / PI * 180;
    }

    public static float sin(float x) {
        return (float) Math.sin(x);
    }

    public static float cos(float x) {
        return (float) Math.cos(x);
    }

    public static float tan(float x) {
        return (float) Math.tan(x);
    }

    public static float asin(float x) {
        return (float) Math.asin(x);
    }

    public static float acos(float x) {
        return (float) Math.acos(x);
    }

    public static float atan(float x) {
        return (float) Math.atan(x);
    }

    public static float atan2(float y, float x) {
        return (float) Math.atan2(y, x);
    }

    public static float sqrt(float x) {
        return (float) Math.sqrt(x);
    }

    public static float cbrt(float x) {
        return (float) Math.cbrt(x);
    }

    public static int floor(float x) {
        return (int) x - (x < 0 ? 1 : 0);
    }

    public static int ceil(float x) {
        return (int) x + (x > 0 ? 1 : 0);
    }

    public static float random() {
        return (float) Math.random();
    }

    public static float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }

    public static double lerp(double a, double b, double t) {
        return a + t * (b - a);
    }

    public static float unlerp(float a, float b, float t) {
        return (t - a) / (b - a);
    }

    public static double unlerp(double a, double b, double t) {
        return (t - a) / (b - a);
    }

    public static float relerp(float a, float b, float p, float q, float t) {
        return lerp(p, q, unlerp(a, b, t));
    }

    public static double relerp(double a, double b, double p, double q, double t) {
        return lerp(p, q, unlerp(a, b, t));
    }

    public static float clamp(float a, float b, float t) {
        return Math.min(b, Math.max(a, t));
    }

    public static double clamp(double a, double b, double t) {
        return Math.min(b, Math.max(a, t));
    }

    public static float avg(float a, float b) {
        return (a + b) / 2;
    }

    public static double avg(double a, double b) {
        return (a + b) / 2;
    }

    public static float distSq(float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        return dx * dx + dy * dy;
    }

    public static double distSq(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return dx * dx + dy * dy;
    }

    public static float dist(float x1, float y1, float x2, float y2) {
        return sqrt(distSq(x1, y1, x2, y2));
    }

    public static double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(distSq(x1, y1, x2, y2));
    }
}
