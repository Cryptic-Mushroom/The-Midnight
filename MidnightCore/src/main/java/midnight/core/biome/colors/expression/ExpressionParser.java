/*
 * Copyright (c) 2020 Cryptic Mushroom and contributors
 * This file belongs to the Midnight mod and is licensed under the terms and conditions of Cryptic Mushroom. See
 * https://github.com/Cryptic-Mushroom/The-Midnight/blob/rewrite/LICENSE.md for the full license.
 *
 * Last updated: 2020 - 10 - 26
 */

package midnight.core.biome.colors.expression;

import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionParser {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]+(?:\\.[0-9]+)?(?:[eE][+\\-]?[0-9]+)?");
    private static final String[] OPERATORS = {
        "+", "-", "*", "/", "%", "^", "(", ")", ","
    };

    private final String input;
    private final Token[] tokens;
    private int cursor;

    private final HashMap<String, HashMap<Integer, Func>> funcs = new HashMap<>();

    public ExpressionParser(String input) {
        this.input = input;

        Matcher numMatcher = NUMBER_PATTERN.matcher(input);

        ArrayList<Token> tokenList = new ArrayList<>();
        int[] cursor = {0};
        while (cursor[0] < input.length()) {
            skipWhitespace(cursor);

            if (cursor[0] >= input.length()) break;

            Token operator = findOperator(cursor);
            if (operator != null) {
                tokenList.add(operator);
                continue;
            }
            Token identifier = findIdentifier(cursor);
            if (identifier != null) {
                tokenList.add(identifier);
                continue;
            }
            Token number = findNumber(cursor, numMatcher);
            if (number != null) {
                tokenList.add(number);
                continue;
            }

            Token illegal = new Token(input.substring(cursor[0], cursor[0] + 1), cursor[0], TokenType.ILLEGAL);
            tokenList.add(illegal);
            cursor[0]++;
        }

        tokens = tokenList.toArray(new Token[0]);
    }

    public void addFunction(String name, Func func, int argCount) {
        funcs.computeIfAbsent(name, key -> new HashMap<>()).put(argCount, func);
    }

    private void skipWhitespace(int[] index) {
        int nextIndex = index[0];
        while (nextIndex < input.length() && Character.isWhitespace(input.codePointAt(nextIndex))) {
            nextIndex++;
        }
        index[0] = nextIndex;
    }

    private Token findOperator(int[] index) {
        int i = index[0];
        for (String operator : OPERATORS) {
            int len = operator.length();
            if (available(i, len)) {
                if (input.substring(i, i + len).equals(operator)) {
                    index[0] = i + len;
                    return new Token(operator, i, TokenType.OPERATOR);
                }
            }
        }
        return null;
    }

    private Token findIdentifier(int[] index) {
        int i = index[0];
        int nextIndex = i;
        boolean num = false;
        while (nextIndex < input.length() && isIDChar(input.charAt(nextIndex), num)) {
            nextIndex++;
            num = true;
        }
        if (nextIndex > i) {
            index[0] = nextIndex;
            return new Token(input.substring(i, nextIndex), i, TokenType.IDENTIFIER);
        }
        return null;
    }

    private Token findNumber(int[] index, Matcher matcher) {
        int i = index[0];
        if (matcher.find(i)) {
            if (matcher.start() == i) {
                int end = matcher.end();
                index[0] = end;
                return new Token(input.substring(i, end), i, TokenType.NUMBER);
            }
        }
        return null;
    }

    private boolean isIDChar(char c, boolean num) {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_' || c == '$' || num && c >= '0' && c <= '9';
    }

    private boolean available(int index, int amount) {
        return input.length() - index >= amount;
    }


    private Token nextToken() {
        if (cursor >= tokens.length) {
            return null;
        }

        return tokens[cursor++];
    }

    private Token requireNext(TokenType type) throws SyntaxError {
        Token t = nextToken();
        if (t != null)
            if (t.type == type) return t;
            else cursor--;
        throw new SyntaxError(t, input, "Expected " + type + ", found " + string(t));
    }

    private Token requireNext(TokenType type, String... expected) throws SyntaxError {
        Token t = nextToken();
        if (t != null)
            if (t.type == type) {
                for (String ex : expected) if (t.input.equals(ex)) return t;
                cursor--;
                throw new SyntaxError(t, input, "Expected " + exString(expected) + ", found '" + t.input + "'");
            } else cursor--;
        throw new SyntaxError(t, input, "Expected " + type + ", found EOF");
    }

    private Token requireNextNoIgnore(TokenType type, String... expected) throws SyntaxError {
        Token t = nextToken();
        if (t != null)
            if (t.type == type) {
                for (String ex : expected) if (t.input.equals(ex)) return t;
                cursor--;
                throw new NoIgnoreSyntaxError(t, input, "Expected " + exString(expected) + ", found '" + t.input + "'");
            } else cursor--;
        throw new NoIgnoreSyntaxError(t, input, "Expected " + type + ", found EOF");
    }

    private String exString(String[] expectations) {
        StringBuilder builder = new StringBuilder();
        boolean comma = false;
        for (String ex : expectations) {
            if (!comma) {
                comma = true;
            } else {
                builder.append("; ");
            }
            builder.append("'").append(ex).append("'");
        }
        return builder.toString();
    }

    private Token peekToken() {
        if (cursor >= tokens.length) {
            return null;
        }

        return tokens[cursor];
    }

    private Node parse0() throws ExpressionSyntaxException {
        cursor = 0;
        for (Token t : tokens) {
            if (t.type == TokenType.ILLEGAL) {
                throw new ExpressionSyntaxException(input, t.index, t.getEnd(), "Illegal token");
            }
        }
        try {
            if (tokens.length == 0) {
                throw new ExpressionSyntaxException(input, 0, 0, "Expected expression, found EOF");
            }
            Node expr = parseExpression();
            if (cursor != tokens.length) {
                Token cur = tokens[cursor];
                throw new ExpressionSyntaxException(input, cur.index, cur.getEnd(), "Expected EOF, found " + string(cur));
            }
            return expr;
        } catch (SyntaxError err) {
            throw new ExpressionSyntaxException(err.input, err.start, err.end, err.getMessage());
        }
    }

    private Node parseExpression() throws SyntaxError {
        int cur = cursor;
        Token next = peekToken();

        Node out;
        try {
            out = parseMulExpr();
        } catch (SyntaxError error) {
            error.rethrow();
            cursor = cur;
            throw new SyntaxError(next, input, "Expected multiplyExpression, found " + string(next));
        }

        while (true) {
            Token peek = peekToken();
            if (peek != null && peek.is(TokenType.OPERATOR, "+", "-")) {
                cursor++;
                try {
                    Node pe = parseMulExpr();
                    out = new BinaryNode(input, out.start, pe.end, peek.input, out, pe);
                } catch (SyntaxError error) {
                    error.rethrow();
                    Token n = peekToken();
                    throw new NoIgnoreSyntaxError(n, input, "Expected multiplyExpression, found " + string(n));
                }
            } else {
                break;
            }
        }

        return out;
    }

    private Node parseMulExpr() throws SyntaxError {
        int cur = cursor;
        Token next = peekToken();

        Node out;
        try {
            out = parsePowExpr();
        } catch (SyntaxError error) {
            error.rethrow();
            cursor = cur;
            throw new SyntaxError(next, input, "Expected powerExpression, found " + string(next));
        }

        while (true) {
            Token peek = peekToken();
            if (peek != null && peek.is(TokenType.OPERATOR, "*", "/", "%")) {
                cursor++;
                try {
                    Node pe = parsePowExpr();
                    out = new BinaryNode(input, out.start, pe.end, peek.input, out, pe);
                } catch (SyntaxError error) {
                    error.rethrow();
                    Token n = peekToken();
                    throw new NoIgnoreSyntaxError(n, input, "Expected powerExpression, found " + string(n));
                }
            } else {
                break;
            }
        }

        return out;
    }

    private Node parsePowExpr() throws SyntaxError {
        int cur = cursor;
        Token next = peekToken();
        try {
            Token t = requireNext(TokenType.OPERATOR, "+", "-");
            Node powExpr = parsePowExpr();
            return new UnaryNode(input, t.index, powExpr.end, t.input, powExpr);
        } catch (SyntaxError err) { err.rethrow(); }
        try {
            Node atom = parseAtom();
            Token peek = peekToken();
            if (peek != null && peek.is(TokenType.OPERATOR, "^")) {
                cursor++;
                Node powExpr = parsePowExpr();
                return new BinaryNode(input, atom.start, powExpr.end, "^", atom, powExpr);
            }
            return atom;
        } catch (SyntaxError err) { err.rethrow(); }
        cursor = cur;
        throw new SyntaxError(next, input, "Expected '+'; '-'; atom, found " + string(next));
    }

    private Node parseAtom() throws SyntaxError {
        int cur = cursor;
        Token next = peekToken();
        try {
            return parseParens();
        } catch (SyntaxError err) { err.rethrow(); }
        try {
            return parseNumeric();
        } catch (SyntaxError err) { err.rethrow(); }
        try {
            return parseFunction();
        } catch (SyntaxError err) { err.rethrow(); }
        try {
            return parseVariable();
        } catch (SyntaxError err) { err.rethrow(); }
        cursor = cur;
        throw new SyntaxError(next, input, "Expected '('; NUMBER; IDENTIFIER, found " + string(next));
    }

    private Node parseParens() throws SyntaxError {
        requireNext(TokenType.OPERATOR, "(");
        Token next = peekToken();
        if (next == null) throw new SyntaxError(null, input, "Expected expression, found EOF");
        if (next.is(TokenType.OPERATOR, ")"))
            throw new NoIgnoreSyntaxError(null, input, "Expected expression, found ')'");
        Node expr;
        try {
            expr = parseExpression();
        } catch (SyntaxError err) {
            err.rethrow();
            next = peekToken();
            throw new NoIgnoreSyntaxError(next, input, "Expected expression, found " + string(next));
        }
        requireNextNoIgnore(TokenType.OPERATOR, ")");
        return expr;
    }

    private Node parseFunction() throws SyntaxError {
        int cur = cursor;
        Token id = requireNext(TokenType.IDENTIFIER);
        String name = id.input;
        try {
            requireNext(TokenType.OPERATOR, "(");
        } catch (SyntaxError error) {
            cursor = cur;
            throw error;
        }
        Token next = peekToken();
        if (next != null && next.input.equals(")")) {
            return new FunctionNode(input, id.index, next.getEnd(), name, new Node[0]);
        }

        ArrayList<Node> subNodes = new ArrayList<>();

        int end;
        while (true) {
            next = peekToken();
            if (next == null)
                throw new NoIgnoreSyntaxError(null, input, "Expected expression; ')', found EOF");
            if (next.is(TokenType.OPERATOR, ",")) {
                throw new NoIgnoreSyntaxError(next, input, "Expected expression; ')', found ','");
            }
            try {
                subNodes.add(parseExpression());
            } catch (SyntaxError err) {
                err.rethrow();
                next = peekToken();
                throw new NoIgnoreSyntaxError(next, input, "Expected expression, found " + string(next));
            }
            next = requireNextNoIgnore(TokenType.OPERATOR, ")", ",");
            if (next.input.equals(")")) {
                end = next.getEnd();
                break;
            }
        }

        return new FunctionNode(input, id.index, end, name, subNodes.toArray(new Node[0]));
    }

    private Node parseVariable() throws SyntaxError {
        Token next = requireNext(TokenType.IDENTIFIER);
        return new VariableNode(input, next.index, next.getEnd(), next.input);
    }

    private Node parseNumeric() throws SyntaxError {
        Token next = requireNext(TokenType.NUMBER);
        return new NumericNode(input, next.index, next.getEnd(), next.input);
    }

    private static String string(Token token) {
        return token == null ? "EOF" : token.string();
    }

    public Factory parse() throws ExpressionSyntaxException {
        Node parsed = parse0();
        VarCollector collector = new VarCollector(funcs);
        parsed.collect(collector);
        return () -> {
            VarManager vars = collector.toManager();
            Expr expr = parsed.makeExpr(vars);
            return new ExprImpl(expr, vars);
        };
    }


    private static abstract class Node {
        final int start;
        final int end;
        final String input;

        Node(String input, int start, int end) {
            this.start = start;
            this.end = end;
            this.input = input;
        }

        abstract void collect(VarCollector collector) throws ExpressionSyntaxException;

        abstract Expr makeExpr(VarManager vars);
    }

    private static class NumericNode extends Node {
        private final String number;
        private double value;

        NumericNode(String input, int start, int end, String number) {
            super(input, start, end);
            this.number = number;
        }

        @Override
        public String toString() {
            return number;
        }

        @Override
        void collect(VarCollector collector) throws ExpressionSyntaxException {
            try {
                value = Double.parseDouble(number);
            } catch (NumberFormatException exc) {
                throw new ExpressionSyntaxException(input, start, end, "Malformed number");
            }
        }

        @Override
        Expr makeExpr(VarManager vs) {
            return vars -> value;
        }
    }

    private static class VariableNode extends Node {
        private final String variable;
        private int index;

        VariableNode(String input, int start, int end, String variable) {
            super(input, start, end);
            this.variable = variable;
        }

        @Override
        public String toString() {
            return variable;
        }

        @Override
        void collect(VarCollector collector) {
            index = collector.collect(variable);
        }

        @Override
        Expr makeExpr(VarManager vs) {
            int i = index;
            return vars -> vars.get(i);
        }
    }

    private static class FunctionNode extends Node {
        private final String name;
        private final Node[] nodes;

        private Func func;

        FunctionNode(String input, int start, int end, String name, Node[] nodes) {
            super(input, start, end);
            this.name = name;
            this.nodes = nodes;
        }

        @Override
        public String toString() {
            return name + Arrays.toString(nodes);
        }

        @Override
        void collect(VarCollector collector) throws ExpressionSyntaxException {
            func = collector.findFunc(name, nodes.length);
            if (func == null) {
                throw new ExpressionSyntaxException(input, start, end, "No such function '" + name + "' with " + nodes.length + " arguments");
            }
            for (Node n : nodes) n.collect(collector);
        }

        @Override
        Expr makeExpr(VarManager vs) {
            ExprImpl[] exprs = new ExprImpl[nodes.length];
            for (int i = 0; i < nodes.length; i++) {
                Node n = nodes[i];
                exprs[i] = new ExprImpl(n.makeExpr(vs), vs);
            }
            return vars -> func.call(exprs);
        }
    }

    private static class UnaryNode extends Node {
        private final String operand;
        private final Node node;

        private UnaryNode(String input, int start, int end, String operand, Node node) {
            super(input, start, end);
            this.operand = operand;
            this.node = node;
        }

        @Override
        public String toString() {
            return operand + "[" + node + "]";
        }

        @Override
        void collect(VarCollector collector) throws ExpressionSyntaxException {
            node.collect(collector);
        }

        @Override
        Expr makeExpr(VarManager vs) {
            if (operand.equals("+")) return node.makeExpr(vs);
            else {
                Expr a = node.makeExpr(vs);
                return vars -> -a.eval(vars);
            }
        }
    }

    private static class BinaryNode extends Node {
        private final String operand;
        private final Node nodeA;
        private final Node nodeB;

        private BinaryNode(String input, int start, int end, String operand, Node nodeA, Node nodeB) {
            super(input, start, end);
            this.operand = operand;
            this.nodeA = nodeA;
            this.nodeB = nodeB;
        }

        @Override
        public String toString() {
            return operand + "[" + nodeA + ", " + nodeB + "]";
        }

        @Override
        void collect(VarCollector collector) throws ExpressionSyntaxException {
            nodeA.collect(collector);
            nodeB.collect(collector);
        }

        @Override
        Expr makeExpr(VarManager vs) {
            Expr a = nodeA.makeExpr(vs);
            Expr b = nodeB.makeExpr(vs);
            switch (operand) {
                default:
                case "+": return vars -> a.eval(vars) + b.eval(vars);
                case "-": return vars -> a.eval(vars) - b.eval(vars);
                case "*": return vars -> a.eval(vars) * b.eval(vars);
                case "/": return vars -> a.eval(vars) / b.eval(vars);
                case "%": return vars -> a.eval(vars) % b.eval(vars);
                case "^": return vars -> Math.pow(a.eval(vars), b.eval(vars));
            }
        }
    }


    private static class SyntaxError extends Exception {
        private final int start;
        private final int end;
        private final String input;

        private SyntaxError(Token token, String input, String reason) {
            super(reason);
            this.start = token == null ? input.length() : token.index;
            this.end = token == null ? input.length() : token.getEnd();
            this.input = input;
        }

        void rethrow() throws SyntaxError {
        }
    }

    private static class NoIgnoreSyntaxError extends SyntaxError {

        private NoIgnoreSyntaxError(Token token, String input, String reason) {
            super(token, input, reason);
        }

        @Override
        void rethrow() throws SyntaxError {
            throw this;
        }
    }

    private static class Token {
        private final String input;
        private final int index;
        private final TokenType type;

        private Token(String input, int index, TokenType type) {
            this.input = input;
            this.index = index;
            this.type = type;
        }

        public int getEnd() {
            return index + input.length();
        }

        @Override
        public String toString() {
            return type.toString() + "[" + input + "]";
        }

        public boolean is(TokenType type, String... in) {
            if (type == this.type)
                for (String i : in)
                    if (i.equals(input))
                        return true;
            return false;
        }

        private String string() {
            if (type != TokenType.OPERATOR) return type.toString();
            return "'" + input + "'";
        }
    }

    private enum TokenType {
        IDENTIFIER,
        NUMBER,
        OPERATOR,
        ILLEGAL
    }

    private static class VarCollector {
        private final HashMap<String, Integer> vars = new HashMap<>();
        private final HashMap<String, HashMap<Integer, Func>> funcs;
        private int size;

        private VarCollector(HashMap<String, HashMap<Integer, Func>> funcs) {
            this.funcs = funcs;
        }

        int collect(String name) {
            if (!vars.containsKey(name)) {
                vars.put(name, size);
                size++;
                return size - 1;
            }
            return vars.get(name);
        }

        VarManager toManager() {
            return new VarManager(Collections.unmodifiableMap(vars), new double[size]);
        }

        Func findFunc(String name, int argCount) {
            if (funcs.containsKey(name)) return funcs.get(name).get(argCount);
            return null;
        }
    }

    private static class VarManager {
        private final Map<String, Integer> mappings;
        private final double[] values;

        VarManager(Map<String, Integer> mappings, double[] values) {
            this.mappings = mappings;
            this.values = values;
        }

        int index(String name) {
            return mappings.getOrDefault(name, -1);
        }

        void set(int index, double value) {
            if (index >= 0) {
                values[index] = value;
            }
        }

        double get(int index) {
            if (index >= 0) {
                return values[index];
            }
            return 0;
        }
    }

    @FunctionalInterface
    private interface Expr {
        double eval(VarManager vars);
    }

    private static class ExprImpl implements Expression {
        private final Expr expr;
        private final VarManager vars;

        private ExprImpl(Expr expr, VarManager vars) {
            this.expr = expr;
            this.vars = vars;
        }

        @Override
        public double evaluate() {
            return expr.eval(vars);
        }

        @Override
        public double getVariable(int index) {
            return vars.get(index);
        }

        @Override
        public double getVariable(String name) {
            return vars.get(vars.index(name));
        }

        @Override
        public void setVariable(int index, double value) {
            vars.set(index, value);
        }

        @Override
        public void setVariable(String name, double value) {
            vars.set(vars.index(name), value);
        }

        @Override
        public int indexOfVariable(String name) {
            return vars.index(name);
        }
    }

    private static class ThreadLocalExpr implements Expression {
        private final ThreadLocal<Expression> expr;

        private ThreadLocalExpr(Supplier<Expression> expr) {
            this.expr = ThreadLocal.withInitial(expr);
        }

        @Override
        public double evaluate() {
            return expr.get().evaluate();
        }

        @Override
        public double getVariable(int index) {
            return expr.get().getVariable(index);
        }

        @Override
        public double getVariable(String name) {
            return expr.get().getVariable(name);
        }

        @Override
        public void setVariable(int index, double value) {
            expr.get().setVariable(index, value);
        }

        @Override
        public void setVariable(String name, double value) {
            expr.get().setVariable(name, value);
        }

        @Override
        public int indexOfVariable(String name) {
            return expr.get().indexOfVariable(name);
        }
    }

    @FunctionalInterface
    public interface Func {
        double call(Expression... args);
    }

    @FunctionalInterface
    public interface Factory {
        Expression build();

        default Expression threadLocal() {
            return new ThreadLocalExpr(this::build);
        }
    }
}
