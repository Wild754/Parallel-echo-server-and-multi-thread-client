package ua.edu.chmnu.net_dev.c4.tcp.core;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public class CmdLineOptions {

    private final String[] args;

    public CmdLineOptions(String[] args) {
        this.args = args;
    }

    public <T> Optional<T> getShortOption(char option, Function<String, T> converter) {
        return getOption("-" + option + ":", converter);
    }

    public <T> Optional<T> getLongOption(String option, Function<String, T> converter) {
        return getOption("--" + option + ":", converter);
    }

    private <T> Optional<T> getOption(String prefix, Function<String, T> converter) {
        return Arrays.stream(args)
                .filter(a -> a.startsWith(prefix))
                .findFirst()
                .map(o -> o.split(":")[1])
                .map(converter);
    }
}
