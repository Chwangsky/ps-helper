package org.example.handler;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

import java.lang.reflect.Method;

public class InvocationChainImpl implements InvocationChain {

    List<Invocation> list = new ArrayList<>();
    Object result;
    Iterator<Invocation> tasks;

    InvocationChainImpl(Invocation... handlers) {
        list.add(new ExecutionMemoryHandler());
        list.add(new ExecutionTimeHandler());
        tasks = list.iterator();
    }

    @Override
    public Object invoke(Object callee, Method method, Object[] args) {
        if (tasks.hasNext()) {
            Object result = tasks.next().invoke(callee, method, args, this);
            this.result = (this.result == null ? result : this.result);
        }
        return this.result;
    }
}