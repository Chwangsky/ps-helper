package org.example.handler;

import java.lang.reflect.Method;
import java.util.logging.Logger;

public class ExecutionMemoryHandler implements Invocation {

    private final Logger logger = Logger.getLogger(ExecutionTimeHandler.class.getName());

    @Override
    public Object invoke(Object callee, Method method, Object[] args, InvocationChain chain) {

        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); // 가비지 컬렉션 수행

        chain.invoke(callee, method, args);

        long memoryAfter = runtime.totalMemory();

        logger.info(String.format("Memory used: %d MB", memoryAfter / (1024 * 1024)));

        return null;
    }
}
