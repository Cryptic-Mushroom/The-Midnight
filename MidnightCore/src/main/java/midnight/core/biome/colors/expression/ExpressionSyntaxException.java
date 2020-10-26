/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors.expression;

public class ExpressionSyntaxException extends Exception {
    private final String input;
    private final int start;
    private final int end;
    private final String problem;

    private final String message;

    public ExpressionSyntaxException(String input, int start, int end, String problem) {
        this.input = input;
        this.start = start;
        this.end = end;
        this.problem = problem;

        StringBuilder builder = new StringBuilder("\n").append(input).append('\n');
        for (int i = 0; i < start; i++) builder.append(' ');
        builder.append('^');
        for (int i = start + 1; i < end; i++) {
            builder.append('~');
        }
        builder.append('\n');
        builder.append(problem);
        message = builder.toString();
    }

    public ExpressionSyntaxException(String input, int start, int end, String problem, Throwable cause) {
        super(cause);
        this.input = input;
        this.start = start;
        this.end = end;
        this.problem = problem;

        StringBuilder builder = new StringBuilder("\n").append(input).append('\n');
        for (int i = 0; i < start; i++) builder.append(' ');
        builder.append('^');
        for (int i = start + 1; i < end; i++) {
            builder.append('~');
        }
        builder.append('\n');
        builder.append(problem);
        message = builder.toString();
    }

    public String getInput() {
        return input;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
