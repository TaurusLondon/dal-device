package com.vf.uk.dal.device.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.mockito.Mockito;

public class BeanTest {

  public static <T> void test(final Class<T> clazz)
      throws IntrospectionException, NoSuchMethodException {
    test(clazz, "class");
  }

  public static <T> void test(final Class<T> clazz, final String... skips)
      throws IntrospectionException {
    final PropertyDescriptor[] props = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
    nextProp: for (PropertyDescriptor property : props) {
      String propertyName = property.getName();
      for (String skip : skips) {
        if (skip.equals(propertyName)) {
          continue nextProp;
        }
      }
      findBooleanIsMethods(clazz, property);
      final Method getter = property.getReadMethod();
      final Method setter = property.getWriteMethod();
      if (getter != null && setter != null) {
        final Class<?> returnClass = getter.getReturnType();
        final Class<?>[] paramsClassArray = setter.getParameterTypes();
        if (paramsClassArray.length == 1 && paramsClassArray[0] == returnClass) {
          try {
            Object value = buildValue(returnClass);
            T bean = clazz.newInstance();
            setter.invoke(bean, value);
            final Object expectedValue = value;
            final Object actualValue = getter.invoke(bean);
            assertEquals(String.format("Failed while testing property %s", propertyName),
                expectedValue, actualValue);
            if (!propertyName.equals("hashcode")) {
              try {
                value = buildValue(returnClass);
                final Method nameMethod = clazz.getMethod(propertyName, returnClass);
                nameMethod.invoke(bean, value);

                for (Method method : clazz.getMethods()) {

                  String addpropertyName = "add" + propertyName.substring(0, 1).toUpperCase()
                      + propertyName.substring(1) + "Item";
                  if (method.getName().equals(addpropertyName)) {
                    Class<?> x = method.getParameterTypes()[0];
                    Object newInstance = x.newInstance();
                    method.invoke(bean, newInstance);
                  }
                }
              } catch (Exception e) {
              }
            }
          } catch (Exception ex) {
            fail(String.format("An exception was thrown while testing the property %s: %s",
                propertyName, ex.toString()));
          }
        }
      }
    }
    try {
      T bean = clazz.newInstance();
      final Method equals = clazz.getMethod("equals", Object.class);
      equals.invoke(bean, bean);
      equals.invoke(bean, "");
      equals.invoke(bean, clazz.newInstance());

      final Method toString = clazz.getMethod("toString");
      toString.invoke(bean);

      final Method hashCode = clazz.getMethod("hashCode");
      hashCode.invoke(bean);

    } catch (Exception e) {
      fail(
          String.format("An exception was thrown while testing the property %s: %s", e.toString()));
      e.printStackTrace();
    }
  }

  private static <T> Object buildValue(Class<T> clazz) throws InstantiationException,
      IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    if (!Modifier.isFinal(clazz.getModifiers())) {
      return Mockito.mock(clazz);
    } else {
      if (clazz == String.class) {
        return "testvalue";
      } else if (clazz.isArray()) {
        return Array.newInstance(clazz.getComponentType(), 1);
      } else if (clazz == boolean.class || clazz == Boolean.class) {
        return true;
      } else if (clazz == int.class || clazz == Integer.class) {
        return 1;
      } else if (clazz == long.class || clazz == Long.class) {
        return 1L;
      } else if (clazz == double.class || clazz == Double.class) {
        return 1.0D;
      } else if (clazz == float.class || clazz == Float.class) {
        return 1.0F;
      } else if (clazz == char.class || clazz == Character.class) {
        return 'Y';
      } else if (clazz.isEnum()) {
        return clazz.getEnumConstants()[0];
      } else {
        fail("Unable to build an instance of class " + clazz.getName()
            + ", please add some code to the " + BeanTest.class.getName() + " class to do this.");
        return null;
      }
    }
  }

  private static <T> void findBooleanIsMethods(Class<T> clazz, PropertyDescriptor descriptor)
      throws IntrospectionException {
    if (needToFindReadMethod(descriptor)) {
      findTheReadMethod(descriptor, clazz);
    }
  }

  private static boolean needToFindReadMethod(PropertyDescriptor property) {
    return property.getReadMethod() == null && property.getPropertyType() == Boolean.class;
  }

  private static <T> void findTheReadMethod(PropertyDescriptor descriptor, Class<T> clazz) {
    try {
      PropertyDescriptor pd = new PropertyDescriptor(descriptor.getName(), clazz);
      descriptor.setReadMethod(pd.getReadMethod());
    } catch (IntrospectionException e) {
    }
  }
}
