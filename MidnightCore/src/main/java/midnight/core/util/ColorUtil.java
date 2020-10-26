/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.util;

// SHADEW: Gonna include this in my PTG library when I know it's stable. I've been fixing countless bugs in this
//         already...
// JONATHING: Whenever you do, give me the signal and I'll update the build.gradle and remove this.
public final class ColorUtil {
    private ColorUtil() {
    }

    public static int redi(int rgb) {
        return rgb >>> 16 & 0xFF;
    }

    public static int greeni(int rgb) {
        return rgb >>> 8 & 0xFF;
    }

    public static int bluei(int rgb) {
        return rgb & 0xFF;
    }

    public static int alphai(int rgba) {
        return rgba >>> 24 & 0xFF;
    }

    public static double redd(int rgb) {
        return redi(rgb) / 255D;
    }

    public static double greend(int rgb) {
        return greeni(rgb) / 255D;
    }

    public static double blued(int rgb) {
        return bluei(rgb) / 255D;
    }

    public static double alphad(int rgba) {
        return alphai(rgba) / 255D;
    }

    public static float redf(int rgb) {
        return redi(rgb) / 255F;
    }

    public static float greenf(int rgb) {
        return greeni(rgb) / 255F;
    }

    public static float bluef(int rgb) {
        return bluei(rgb) / 255F;
    }

    public static float alphaf(int rgba) {
        return alphai(rgba) / 255F;
    }

    public static int rgb(int r, int g, int b) {
        return (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
    }

    public static int rgba(int r, int g, int b, int a) {
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
    }

    public static int rgba(int r, int g, int b) {
        return 0xFF000000 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
    }

    public static int rgb(float r, float g, float b) {
        return rgb((int) (r * 255), (int) (g * 255), (int) (b * 255));
    }

    public static int rgba(float r, float g, float b, float a) {
        return rgba((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255));
    }

    public static int rgba(float r, float g, float b) {
        return rgba((int) (r * 255), (int) (g * 255), (int) (b * 255));
    }

    public static int rgb(double r, double g, double b) {
        return rgb((int) (r * 255), (int) (g * 255), (int) (b * 255));
    }

    public static int rgba(double r, double g, double b, double a) {
        return rgba((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255));
    }

    public static int rgba(double r, double g, double b) {
        return rgba((int) (r * 255), (int) (g * 255), (int) (b * 255));
    }

    public static int opaque(int rgb) {
        return 0xFF000000 | rgb & 0xFFFFFF;
    }

    public static int withAlpha(int rgb, int a) {
        return (a & 0xFF) << 24 | rgb & 0xFFFFFF;
    }

    public static int withAlpha(int rgb, float a) {
        return withAlpha(rgb, (int) (a * 255));
    }

    public static int withAlpha(int rgb, double a) {
        return withAlpha(rgb, (int) (a * 255));
    }

    public static int rgb(int rgba) {
        return rgba & 0x00FFFFFF;
    }

    public static int hsv(float h, float s, float v) {
        h %= 360;

        float c = v * s;
        float x = c * (1 - Math.abs(h / 60 % 2 - 1));
        int hi = (int) (h / 60);

        float ra, ga, ba;
        switch(hi) {
            default:
            case 0:
                ra = c;
                ga = x;
                ba = 0;
                break;
            case 1:
                ra = x;
                ga = c;
                ba = 0;
                break;
            case 2:
                ra = 0;
                ga = c;
                ba = x;
                break;
            case 3:
                ra = 0;
                ga = x;
                ba = c;
                break;
            case 4:
                ra = x;
                ga = 0;
                ba = c;
                break;
            case 5:
                ra = c;
                ga = 0;
                ba = x;
                break;
        }

        float m = v - c;

        float r = ra + m;
        float g = ga + m;
        float b = ba + m;

        return rgb(r, g, b);
    }

    public static int hsva(float h, float s, float v, float a) {
        return withAlpha(hsv(h, s, v), a);
    }

    public static int hsva(float h, float s, float v) {
        return opaque(hsv(h, s, v));
    }

    public static int hsv(double h, double s, double v) {
        return hsv((float) h, (float) s, (float) v);
    }

    public static int hsva(double h, double s, double v, double a) {
        return hsva((float) h, (float) s, (float) v, (float) a);
    }

    public static int hsva(double h, double s, double v) {
        return hsva((float) h, (float) s, (float) v);
    }

    public static float greyf(int rgb) {
        return (redf(rgb) + greenf(rgb) + bluef(rgb)) / 3;
    }

    public static double greyd(int rgb) {
        return (redd(rgb) + greend(rgb) + blued(rgb)) / 3;
    }

    public static int greyi(int rgb) {
        return (redi(rgb) + greeni(rgb) + bluei(rgb)) / 3;
    }

    public static float cie1931yf(int rgb) {
        return (2126 * redf(rgb) + 7152 * greenf(rgb) + 722 * bluef(rgb)) / 10000;
    }

    public static double cie1931yd(int rgb) {
        return (2126 * redd(rgb) + 7152 * greend(rgb) + 722 * blued(rgb)) / 10000;
    }

    public static int cie1931yi(int rgb) {
        return (2126 * redi(rgb) + 7152 * greeni(rgb) + 722 * bluei(rgb)) / 10000;
    }

    public static int grey(int tint) {
        return rgb(tint, tint, tint);
    }

    public static int greya(int tint, int a) {
        return rgba(tint, tint, tint, a);
    }

    public static int greya(int tint) {
        return rgba(tint, tint, tint);
    }

    public static int grey(float tint) {
        return rgb(tint, tint, tint);
    }

    public static int greya(float tint, float a) {
        return rgba(tint, tint, tint, a);
    }

    public static int greya(float tint) {
        return rgba(tint, tint, tint);
    }

    public static int grey(double tint) {
        return rgb(tint, tint, tint);
    }

    public static int greya(double tint, double a) {
        return rgba(tint, tint, tint, a);
    }

    public static int greya(double tint) {
        return rgba(tint, tint, tint);
    }

    public static int inverse(int rgb) {
        int a = rgb >>> 24 & 0xFF;
        int r = rgb >>> 16 & 0xFF;
        int g = rgb >>> 8 & 0xFF;
        int b = rgb & 0xFF;
        r = 255 - r;
        g = 255 - g;
        b = 255 - b;
        return rgba(r, g, b, a);
    }

    public static int darker(int rgb, float amount) {
        if(amount < 0) lighter(rgb, -amount);
        float a = alphaf(rgb);
        float r = redf(rgb);
        float g = greenf(rgb);
        float b = bluef(rgb);
        float mul = 1 - amount;
        r *= mul;
        g *= mul;
        b *= mul;
        return rgba(r, g, b, a);
    }

    public static int lighter(int rgb, float amount) {
        if(amount < 0) darker(rgb, -amount);
        float a = alphaf(rgb);
        float r = 1 - redf(rgb);
        float g = 1 - greenf(rgb);
        float b = 1 - bluef(rgb);
        float mul = 1 - amount;
        r *= mul;
        g *= mul;
        b *= mul;
        return rgba(1 - r, 1 - g, 1 - b, a);
    }

    public static int saturate(int rgb, float amount) {
        amount += 1;
        amount /= 2;

        float hue = huef(rgb);
        float sat = saturationf(rgb);
        float val = valuef(rgb);
        float a = alphaf(rgb);

        return hsva(hue, sat * amount, val, a);
    }

    public static int saturate(int rgb, double amount) {
        return saturate(rgb, (float) amount);
    }

    public static int darker(int rgb, double amount) {
        return darker(rgb, (float) amount);
    }

    public static int lighter(int rgb, double amount) {
        return lighter(rgb, (float) amount);
    }

    public static int darker(int rgb, int amount) {
        return darker(rgb, amount / 255F);
    }

    public static int lighter(int rgb, int amount) {
        return lighter(rgb, amount / 255F);
    }

    public static int add(int a, int b) {
        return rgba(
            MnMath.clamp(0, 1, redf(a) + redf(b)),
            MnMath.clamp(0, 1, greenf(a) + greenf(b)),
            MnMath.clamp(0, 1, bluef(a) + bluef(b)),
            MnMath.clamp(0, 1, alphaf(a) + alphaf(b))
        );
    }

    public static int sub(int a, int b) {
        return rgba(
            MnMath.clamp(0, 1, redf(a) - redf(b)),
            MnMath.clamp(0, 1, greenf(a) - greenf(b)),
            MnMath.clamp(0, 1, bluef(a) - bluef(b)),
            Math.max(alphaf(a), alphaf(b))
        );
    }

    public static int subAlpha(int a, int b) {
        return rgba(
            MnMath.clamp(0, 1, redf(a) - redf(b)),
            MnMath.clamp(0, 1, greenf(a) - greenf(b)),
            MnMath.clamp(0, 1, bluef(a) - bluef(b)),
            MnMath.clamp(0, 1, alphaf(a) - alphaf(b))
        );
    }

    public static int mul(int a, int b) {
        return rgba(
            MnMath.clamp(0, 1, redf(a) * redf(b)),
            MnMath.clamp(0, 1, greenf(a) * greenf(b)),
            MnMath.clamp(0, 1, bluef(a) * bluef(b)),
            MnMath.clamp(0, 1, alphaf(a) * alphaf(b))
        );
    }

    public static int mul(int a, float val) {
        return rgba(
            MnMath.clamp(0, 1, redf(a) * val),
            MnMath.clamp(0, 1, greenf(a) * val),
            MnMath.clamp(0, 1, bluef(a) * val),
            MnMath.clamp(0, 1, alphaf(a) * val)
        );
    }

    public static int div(int a, int b) {
        return rgba(
            MnMath.clamp(0, 1, redf(a) / redf(b)),
            MnMath.clamp(0, 1, greenf(a) / greenf(b)),
            MnMath.clamp(0, 1, bluef(a) / bluef(b)),
            MnMath.clamp(0, 1, alphaf(a) / alphaf(b))
        );
    }

    public static int div(int a, float val) {
        return rgba(
            MnMath.clamp(0, 1, redf(a) / val),
            MnMath.clamp(0, 1, greenf(a) / val),
            MnMath.clamp(0, 1, bluef(a) / val),
            MnMath.clamp(0, 1, alphaf(a) / val)
        );
    }

    public static int darken(int a, int b) {
        return rgba(
            Math.min(redf(a), redf(b)),
            Math.min(greenf(a), greenf(b)),
            Math.min(bluef(a), bluef(b)),
            Math.min(alphaf(a), alphaf(b))
        );
    }

    public static int lighten(int a, int b) {
        return rgba(
            Math.max(redf(a), redf(b)),
            Math.max(greenf(a), greenf(b)),
            Math.max(bluef(a), bluef(b)),
            Math.max(alphaf(a), alphaf(b))
        );
    }

    public static int darkest(int a, int b) {
        return greyf(a) < greyf(b) ? a : b;
    }

    public static int lightest(int a, int b) {
        return greyf(a) > greyf(b) ? a : b;
    }

    public static int darkestCIE1931(int a, int b) {
        return cie1931yf(a) < cie1931yf(b) ? a : b;
    }

    public static int lightestCIE1931(int a, int b) {
        return cie1931yf(a) > cie1931yf(b) ? a : b;
    }

    public static int darkestRed(int a, int b) {
        return redf(a) < redf(b) ? a : b;
    }

    public static int lightestRed(int a, int b) {
        return redf(a) > redf(b) ? a : b;
    }

    public static int darkestGreen(int a, int b) {
        return greenf(a) < greenf(b) ? a : b;
    }

    public static int lightestGreen(int a, int b) {
        return greenf(a) > greenf(b) ? a : b;
    }

    public static int darkestBlue(int a, int b) {
        return bluef(a) < bluef(b) ? a : b;
    }

    public static int lightestBlue(int a, int b) {
        return bluef(a) > bluef(b) ? a : b;
    }

    public static int overlay(int a, int b) {
        return interpolate(a, b, alphaf(b));
    }

    public static int interpolate(int a, int b, float t) {
        return rgba(
            MnMath.lerp(redf(a), redf(b), t),
            MnMath.lerp(greenf(a), greenf(b), t),
            MnMath.lerp(bluef(a), bluef(b), t),
            MnMath.lerp(alphaf(a), alphaf(b), t)
        );
    }

    public static int interpolate(int a, int b, double t) {
        return interpolate(a, b, (float) t);
    }

    public static int interpolate(int a, int b, int t) {
        return interpolate(a, b, t / 255F);
    }

    public static int noTranslucency(int color, float threshold) {
        return withAlpha(color, alphaf(color) < threshold ? 0F : 1F);
    }

    public static int noTranslucency(int color, int threshold) {
        return withAlpha(color, alphai(color) < threshold ? 0 : 255);
    }

    public static int noTranslucency(int color, double threshold) {
        return withAlpha(color, alphad(color) < threshold ? 0D : 1D);
    }

    public static float valuef(int color) {
        float r = redf(color);
        float g = greenf(color);
        float b = bluef(color);
        return Math.max(r, Math.max(g, b));
    }

    public static double valued(int color) {
        double r = redd(color);
        double g = greend(color);
        double b = blued(color);
        return Math.max(r, Math.max(g, b));
    }

    public static float lightnessf(int color) {
        float r = redf(color);
        float g = greenf(color);
        float b = bluef(color);
        float cmax = Math.max(r, Math.max(g, b));
        float cmin = Math.min(r, Math.min(g, b));
        return (cmax + cmin) / 2;
    }

    public static double lightnessd(int color) {
        double r = redd(color);
        double g = greend(color);
        double b = blued(color);
        double cmax = Math.max(r, Math.max(g, b));
        double cmin = Math.min(r, Math.min(g, b));
        return (cmax + cmin) / 2;
    }

    public static float saturationf(int color) {
        float r = redf(color);
        float g = greenf(color);
        float b = bluef(color);
        float cmax = Math.max(r, Math.max(g, b));
        float cmin = Math.min(r, Math.min(g, b));
        float delta = cmax - cmin;
        return cmax == 0 ? 0 : delta / cmax;
    }

    public static double saturationd(int color) {
        double r = redd(color);
        double g = greend(color);
        double b = blued(color);
        double cmax = Math.max(r, Math.max(g, b));
        double cmin = Math.min(r, Math.min(g, b));
        double delta = cmax - cmin;
        return cmax == 0 ? 0 : delta / cmax;
    }

    public static float huef(int color) {
        float r = redf(color);
        float g = greenf(color);
        float b = bluef(color);

        float cmax = Math.max(r, Math.max(g, b));
        float cmin = Math.min(r, Math.min(g, b));

        float delta = cmax - cmin;
        if(delta == 0) return 0;

        if(cmax == r) {
            return (g - b) / delta % 6 * 60;
        }

        if(cmax == g) {
            return ((b - r) / delta + 2) * 60;
        }

        return ((r - g) / delta + 4) * 60;
    }

    public static double hued(int color) {
        double r = redd(color);
        double g = greend(color);
        double b = blued(color);

        double cmax = Math.max(r, Math.max(g, b));
        double cmin = Math.min(r, Math.min(g, b));

        double delta = cmax - cmin;
        if(delta == 0) return 0;

        if(cmax == r) {
            return (g - b) / delta % 6 * 60;
        }

        if(cmax == g) {
            return ((b - r) / delta + 2) * 60;
        }

        return ((r - g) / delta + 4) * 60;
    }

    public static int hsl(float h, float s, float l) {
        h %= 360;

        float c = (1 - Math.abs(2 * l - 1)) * s;
        float x = c * (1 - Math.abs(h / 60 % 2 - 1));
        int hi = (int) (h / 60);

        float ra, ga, ba;
        switch(hi) {
            default:
            case 0:
                ra = c;
                ga = x;
                ba = 0;
                break;
            case 1:
                ra = x;
                ga = c;
                ba = 0;
                break;
            case 2:
                ra = 0;
                ga = c;
                ba = x;
                break;
            case 3:
                ra = 0;
                ga = x;
                ba = c;
                break;
            case 4:
                ra = x;
                ga = 0;
                ba = c;
                break;
            case 5:
                ra = c;
                ga = 0;
                ba = x;
                break;
        }

        float m = l - c / 2;

        float r = ra + m;
        float g = ga + m;
        float b = ba + m;

        return rgb(r, g, b);
    }

    public static int hsla(float h, float s, float l, float a) {
        return withAlpha(hsl(h, s, l), a);
    }

    public static int hsla(float h, float s, float l) {
        return opaque(hsl(h, s, l));
    }

    public static int hsl(double h, double s, double l) {
        return hsl((float) h, (float) s, (float) l);
    }

    public static int hsla(double h, double s, double l, double a) {
        return hsla((float) h, (float) s, (float) l, (float) a);
    }

    public static int hsla(double h, double s, double l) {
        return hsla((float) h, (float) s, (float) l);
    }

    public static int cmyk(float c, float m, float y, float k) {
        return rgb(
            (1 - c) * (1 - k),
            (1 - m) * (1 - k),
            (1 - y) * (1 - k)
        );
    }

    public static int cmyka(float c, float m, float y, float k, float a) {
        return withAlpha(cmyk(c, m, y, k), a);
    }

    public static int cmyka(float c, float m, float y, float k) {
        return opaque(cmyk(c, m, y, k));
    }

    public static int cmyk(double c, double m, double y, double k) {
        return cmyk((float) c, (float) m, (float) y, (float) k);
    }

    public static int cmyka(double c, double m, double y, double k, double a) {
        return cmyka((float) c, (float) m, (float) y, (float) k, (float) a);
    }

    public static int cmyka(double c, double m, double y, double k) {
        return cmyka((float) c, (float) m, (float) y, (float) k);
    }

    public static float keyf(int rgb) {
        float r = redf(rgb);
        float g = greenf(rgb);
        float b = bluef(rgb);
        float max = Math.max(r, Math.max(g, b));
        return 1 - max;
    }

    public static double keyd(int rgb) {
        double r = redd(rgb);
        double g = greend(rgb);
        double b = blued(rgb);
        double max = Math.max(r, Math.max(g, b));
        return 1 - max;
    }

    public static float cyanf(int rgb) {
        float r = redf(rgb);
        float k = keyf(rgb);
        return (1 - r - k) / (1 - k);
    }

    public static double cyand(int rgb) {
        double r = redd(rgb);
        double k = keyd(rgb);
        return (1 - r - k) / (1 - k);
    }

    public static float magentaf(int rgb) {
        float g = greenf(rgb);
        float k = keyf(rgb);
        return (1 - g - k) / (1 - k);
    }

    public static double magentad(int rgb) {
        double g = greend(rgb);
        double k = keyd(rgb);
        return (1 - g - k) / (1 - k);
    }

    public static float yellowf(int rgb) {
        float b = bluef(rgb);
        float k = keyf(rgb);
        return (1 - b - k) / (1 - k);
    }

    public static double yellowd(int rgb) {
        double b = blued(rgb);
        double k = keyd(rgb);
        return (1 - b - k) / (1 - k);
    }

    private static int hexChar(char c) {
        if(c >= '0' && c <= '9') {
            return c - '0';
        } else if(c >= 'a' && c <= 'f') {
            return c - 'a' + 10;
        } else if(c >= 'A' && c <= 'F') {
            return c - 'A' + 10;
        } else {
            throw new NumberFormatException("Not a hex char: '" + c + "'");
        }
    }

    private static int d(int s) {
        return s << 4 | s;
    }

    private static int d(int a, int b) {
        return a << 4 | b;
    }

    public static int hex(String hex) {
        if(hex.length() == 3) {
            int r = d(hexChar(hex.charAt(0)));
            int g = d(hexChar(hex.charAt(1)));
            int b = d(hexChar(hex.charAt(2)));
            return rgb(r, g, b);
        }
        if(hex.length() == 6) {
            int r = d(hexChar(hex.charAt(0)), hexChar(hex.charAt(1)));
            int g = d(hexChar(hex.charAt(2)), hexChar(hex.charAt(3)));
            int b = d(hexChar(hex.charAt(4)), hexChar(hex.charAt(5)));
            return rgb(r, g, b);
        }
        throw new NumberFormatException("Color input '" + hex + "' does not have 3 or 6 hex chars");
    }

    public static int hexa(String hex) {
        if(hex.length() == 3) {
            int r = d(hexChar(hex.charAt(0)));
            int g = d(hexChar(hex.charAt(1)));
            int b = d(hexChar(hex.charAt(2)));
            return rgba(r, g, b);
        }
        if(hex.length() == 4) {
            int r = d(hexChar(hex.charAt(1)));
            int g = d(hexChar(hex.charAt(2)));
            int b = d(hexChar(hex.charAt(3)));
            int a = d(hexChar(hex.charAt(0)));
            return rgba(r, g, b, a);
        }
        if(hex.length() == 6) {
            int r = d(hexChar(hex.charAt(0)), hexChar(hex.charAt(1)));
            int g = d(hexChar(hex.charAt(2)), hexChar(hex.charAt(3)));
            int b = d(hexChar(hex.charAt(4)), hexChar(hex.charAt(5)));
            return rgba(r, g, b);
        }
        if(hex.length() == 8) {
            int r = d(hexChar(hex.charAt(2)), hexChar(hex.charAt(3)));
            int g = d(hexChar(hex.charAt(4)), hexChar(hex.charAt(5)));
            int b = d(hexChar(hex.charAt(6)), hexChar(hex.charAt(7)));
            int a = d(hexChar(hex.charAt(0)), hexChar(hex.charAt(1)));
            return rgba(r, g, b, a);
        }
        throw new NumberFormatException("Color input '" + hex + "' does not have 3, 4, 6 or 8 hex chars");
    }

    private static float dist(float x, float y, float z) {
        return MnMath.sqrt(x * x + y * y + z * z);
    }

    public static float distRGB(int a, int b) {
        return dist(redf(a) - redf(b), greenf(a) - greenf(b), bluef(a) - bluef(b));
    }

    public static float distHSV(int a, int b) {
        return dist(huef(a) - huef(b), saturationf(a) - saturationf(b), valuef(a) - valuef(b));
    }

    public static float distHSL(int a, int b) {
        return dist(huef(a) - huef(b), saturationf(a) - saturationf(b), lightnessf(a) - lightnessf(b));
    }

    public static float distHSCie1931y(int a, int b) {
        return dist(huef(a) - huef(b), saturationf(a) - saturationf(b), cie1931yf(a) - cie1931yf(b));
    }

    public static Integer parseHexString(String string) {
        if (string.charAt(0) != '#') {
            return null;
        }
        if (string.length() != 7 && string.length() != 4) {
            return null;
        }
        String hex = string.substring(1);

        if (hex.length() == 6) {
            try {
                return Integer.parseInt(hex, 16);
            } catch (NumberFormatException exc) {
                return null;
            }
        } else {
            try {
                int r = Integer.parseInt(hex.charAt(0) + "", 16);
                int g = Integer.parseInt(hex.charAt(1) + "", 16);
                int b = Integer.parseInt(hex.charAt(2) + "", 16);

                return r << 20 | r << 16 | g << 12 | g << 8 | b << 4 | b;
            } catch (NumberFormatException exc) {
                return null;
            }
        }
    }
}
