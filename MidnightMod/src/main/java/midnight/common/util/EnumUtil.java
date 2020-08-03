/*
 * Copyright (c) 2020 RedGalaxy
 * All rights reserved. Do not distribute.
 *
 * Date:   02 - 13 - 2020
 * Author: rgsw
 */

package midnight.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public final class EnumUtil {
    private static Field enumConstantsField;
    private static Field enumConstantDirectoryField;
    private static Field constructorAccessorField;
    private static Field modifiersField;
    private static String valuesFieldName;

    private EnumUtil() {
    }

    // Here are some very unsafe hacks to add enums... Only use if you have a very good reason!!!
    // RGSW: I will check if we can replace this with ASM, but for now this should do the work

    @SuppressWarnings("unchecked")
    private static <T extends Enum<T>> T buildEnum(Class<T> cls, String name, Class<?>[] argumentTypes, Object... arguments) throws Exception {
        if (enumConstantsField == null) {
            enumConstantsField = Class.class.getDeclaredField("enumConstants");
            enumConstantsField.setAccessible(true);
        }

        if (enumConstantDirectoryField == null) {
            enumConstantDirectoryField = Class.class.getDeclaredField("enumConstantDirectory");
            enumConstantDirectoryField.setAccessible(true);
        }

        if (modifiersField == null) {
            modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
        }

        if (constructorAccessorField == null) {
            constructorAccessorField = Constructor.class.getDeclaredField("constructorAccessor");
            constructorAccessorField.setAccessible(true);
        }

        enumConstantsField.set(cls, null);
        enumConstantDirectoryField.set(cls, null);

        Field valuesField;
        if (valuesFieldName == null) {
            valuesField = findValuesField(cls, true);
        } else {
            try {
                valuesField = cls.getField(valuesFieldName);
            } catch (NoSuchFieldException exc) {
                valuesField = findValuesField(cls, false);
            }
        }

        valuesField.setAccessible(true);

        int modifiers = valuesField.getModifiers();
        modifiers &= ~Modifier.FINAL;
        modifiersField.set(valuesField, modifiers);

        T[] values = (T[]) valuesField.get(null);

        Class<?>[] params = new Class<?>[argumentTypes.length + 2];
        Object[] args = new Object[arguments.length + 2];

        System.arraycopy(argumentTypes, 0, params, 2, argumentTypes.length);
        System.arraycopy(arguments, 0, args, 2, arguments.length);

        params[0] = String.class;
        params[1] = int.class;
        args[0] = name;
        args[1] = values.length;

        Constructor<T> constr = cls.getDeclaredConstructor(params);
        constr.setAccessible(true);

        Object ca = constructorAccessorField.get(constr);
        if (ca == null) {
            Method acquireConstructorAccessorMethod = Constructor.class.getDeclaredMethod("acquireConstructorAccessor");
            acquireConstructorAccessorMethod.setAccessible(true);
            ca = acquireConstructorAccessorMethod.invoke(constr);
        }

        Class<?> caClass = ca.getClass();

        Method newInstanceMethod = caClass.getDeclaredMethod("newInstance", Object[].class);
        newInstanceMethod.setAccessible(true);

        T instance = (T) newInstanceMethod.invoke(ca, (Object) args);

        T[] newValues = Arrays.copyOf(values, values.length + 1);
        newValues[values.length] = instance;

        valuesField.set(null, newValues);

        return instance;
    }

    private static <T> Field findValuesField(Class<T> cls, boolean cache) throws ReflectiveOperationException {
        Field[] fields = cls.getDeclaredFields();

        Field valuesField = null;

        for (Field field : fields) {
            if (field.isSynthetic()) {
                Class<?> type = field.getType();
                if (type.isArray()) {
                    if (type.getComponentType() == cls) {
                        if (cache) valuesFieldName = field.getName();
                        valuesField = field;
                        break;
                    }
                }
            }
        }

        if (valuesField == null) {
            throw new ReflectiveOperationException("Enum class does not have a values field");
        }

        return valuesField;
    }

    public static <T extends Enum<T>> T addEnum(Class<T> cls, String name, Class<?>[] params, Object... args) {
        try {
            return buildEnum(cls, name, params, args);
        } catch (Exception e) {
            throw new RuntimeException("Can't add enum", e);
        }
    }

    public static <T extends Enum<T>> T addEnum(Class<T> cls, String name) {
        try {
            return buildEnum(cls, name, new Class<?>[0]);
        } catch (Exception e) {
            throw new RuntimeException("Can't add enum", e);
        }
    }


    @SafeVarargs
    public static <D, T extends Enum<T>> D enumSelect(T value, D... items) {
        if (value == null) throw new NullPointerException();
        return items[value.ordinal()];
    }
}
