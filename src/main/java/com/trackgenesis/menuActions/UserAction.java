// SID: 2408078
package com.trackgenesis.menuActions;

import java.io.IOException;

public interface UserAction<T> {
    Void execute() throws IOException;
}
