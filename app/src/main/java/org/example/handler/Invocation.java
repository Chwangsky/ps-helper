package org.example.handler;

import java.lang.reflect.Method;

public interface Invocation {
    Object invoke(Object callee, Method method, Object[] args, InvocationChain chain);
}