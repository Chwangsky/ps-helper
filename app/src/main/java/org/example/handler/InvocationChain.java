package org.example.handler;

import java.lang.reflect.Method;

public interface InvocationChain {
    public Object invoke(Object callee, Method method, Object[] args);
}