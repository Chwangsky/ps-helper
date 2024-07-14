package org.example.handler;

import java.lang.reflect.Method;
import java.util.logging.Logger;

public class ExecutionTimeHandler implements Invocation {

    private final Logger logger = Logger.getLogger(ExecutionTimeHandler.class.getName());

    @Override
    public Object invoke(Object callee, Method method, Object[] args, InvocationChain chain) {

        long startTime = System.nanoTime();

        chain.invoke(callee, method, args);

        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;

        logger.info(String.format("Execution time: %d ms", executionTime / 1_000_000));
        return null;
    }
}