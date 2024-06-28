package org.example.util;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class SimpleFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        return record.getLevel() + ": " + record.getMessage() + "\n";
    }

    public SimpleFormatter() {
        super();
    }
}