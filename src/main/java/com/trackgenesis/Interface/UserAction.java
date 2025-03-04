package com.trackgenesis.Interface;

import java.io.IOException;

public interface UserAction<T> {
    T execute() throws IOException;
}
