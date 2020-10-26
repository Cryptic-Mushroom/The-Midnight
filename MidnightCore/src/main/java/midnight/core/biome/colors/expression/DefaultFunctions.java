/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors.expression;

import midnight.core.util.MnMath;

public final class DefaultFunctions {
    public static final ExpressionParser.Func ABS = pars -> Math.abs(pars[0].evaluate());
    public static final ExpressionParser.Func MIN = pars -> Math.min(pars[0].evaluate(), pars[1].evaluate());
    public static final ExpressionParser.Func MAX = pars -> Math.max(pars[0].evaluate(), pars[1].evaluate());
    public static final ExpressionParser.Func SQRT = pars -> Math.sqrt(pars[0].evaluate());
    public static final ExpressionParser.Func CBRT = pars -> Math.cbrt(pars[0].evaluate());
    public static final ExpressionParser.Func SIN = pars -> Math.sin(pars[0].evaluate());
    public static final ExpressionParser.Func COS = pars -> Math.cos(pars[0].evaluate());
    public static final ExpressionParser.Func TAN = pars -> Math.tan(pars[0].evaluate());
    public static final ExpressionParser.Func SINH = pars -> Math.sinh(pars[0].evaluate());
    public static final ExpressionParser.Func COSH = pars -> Math.cosh(pars[0].evaluate());
    public static final ExpressionParser.Func TANH = pars -> Math.tanh(pars[0].evaluate());
    public static final ExpressionParser.Func ASIN = pars -> Math.asin(pars[0].evaluate());
    public static final ExpressionParser.Func ACOS = pars -> Math.acos(pars[0].evaluate());
    public static final ExpressionParser.Func ATAN = pars -> Math.atan(pars[0].evaluate());
    public static final ExpressionParser.Func ATAN2 = pars -> Math.atan2(pars[0].evaluate(), pars[1].evaluate());
    public static final ExpressionParser.Func SIGN = pars -> Math.signum(pars[0].evaluate());
    public static final ExpressionParser.Func FLOOR = pars -> Math.floor(pars[0].evaluate());
    public static final ExpressionParser.Func CEIL = pars -> Math.ceil(pars[0].evaluate());
    public static final ExpressionParser.Func ROUND = pars -> Math.round(pars[0].evaluate());
    public static final ExpressionParser.Func EXP = pars -> Math.exp(pars[0].evaluate());
    public static final ExpressionParser.Func LN = pars -> Math.log(pars[0].evaluate());
    public static final ExpressionParser.Func LOG = pars -> Math.log(pars[0].evaluate()) / Math.log(pars[1].evaluate());
    public static final ExpressionParser.Func LOG10 = pars -> Math.log10(pars[0].evaluate());
    public static final ExpressionParser.Func LOG2 = pars -> Math.log(pars[0].evaluate()) / Math.log(2);
    public static final ExpressionParser.Func E = pars -> Math.E;
    public static final ExpressionParser.Func PI = pars -> Math.PI;
    public static final ExpressionParser.Func RAD = pars -> Math.toRadians(pars[0].evaluate());
    public static final ExpressionParser.Func DEG = pars -> Math.toDegrees(pars[0].evaluate());
    public static final ExpressionParser.Func LERP = pars -> MnMath.lerp(pars[0].evaluate(), pars[1].evaluate(), pars[2].evaluate());
    public static final ExpressionParser.Func UNLERP = pars -> MnMath.unlerp(pars[0].evaluate(), pars[1].evaluate(), pars[2].evaluate());
    public static final ExpressionParser.Func CLAMP = pars -> MnMath.clamp(pars[0].evaluate(), pars[1].evaluate(), pars[2].evaluate());
    public static final ExpressionParser.Func RELERP = pars -> MnMath.relerp(pars[0].evaluate(), pars[1].evaluate(), pars[2].evaluate(), pars[3].evaluate(), pars[4].evaluate());

    private DefaultFunctions() {
    }

    public static void addDefaults(ExpressionParser parser) {
        parser.addFunction("abs", ABS, 1);
        parser.addFunction("min", MIN, 2);
        parser.addFunction("max", MAX, 2);
        parser.addFunction("sqrt", SQRT, 1);
        parser.addFunction("cbrt", CBRT, 1);
        parser.addFunction("sin", SIN, 1);
        parser.addFunction("cos", COS, 1);
        parser.addFunction("tan", TAN, 1);
        parser.addFunction("sinh", SINH, 1);
        parser.addFunction("cosh", COSH, 1);
        parser.addFunction("tanh", TANH, 1);
        parser.addFunction("asin", ASIN, 1);
        parser.addFunction("acos", ACOS, 1);
        parser.addFunction("atan", ATAN, 1);
        parser.addFunction("atan2", ATAN2, 2);
        parser.addFunction("sign", SIGN, 1);
        parser.addFunction("floor", FLOOR, 1);
        parser.addFunction("ceil", CEIL, 1);
        parser.addFunction("round", ROUND, 1);
        parser.addFunction("exp", EXP, 1);
        parser.addFunction("ln", LN, 1);
        parser.addFunction("log", LOG, 2);
        parser.addFunction("log10", LOG10, 1);
        parser.addFunction("log2", LOG2, 1);
        parser.addFunction("e", E, 0);
        parser.addFunction("pi", PI, 0);
        parser.addFunction("rad", RAD, 1);
        parser.addFunction("deg", DEG, 1);
        parser.addFunction("lerp", LERP, 3);
        parser.addFunction("unlerp", UNLERP, 3);
        parser.addFunction("clamp", CLAMP, 3);
        parser.addFunction("relerp", RELERP, 5);
    }
}
