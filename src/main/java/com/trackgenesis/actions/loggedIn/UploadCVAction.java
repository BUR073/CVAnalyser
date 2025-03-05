package com.trackgenesis.actions.loggedIn;

import com.trackgenesis.Interface.UserAction;
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
