// SID: 2408078
package com.trackgenesis.menuActions.loggedIn;

import com.trackgenesis.menuActions.Interface.UserAction;
import com.trackgenesis.UI.UploadCV;

import java.io.IOException;

public class UploadCVAction implements UserAction<Void> {
    private final UploadCV uploadCV;

    public UploadCVAction(UploadCV uploadCV) {
        this.uploadCV = uploadCV;
    }
    @Override
    public Void execute() throws IOException {
        uploadCV.upload();

        return null;
    }
}
