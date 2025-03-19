// SID: 2408078
package com.trackgenesis.menuActions.loggedIn;

import com.trackgenesis.menuActions.UserAction;
import com.trackgenesis.UI.UploadCV;



public class UploadCVAction implements UserAction<Void> {
    private final UploadCV uploadCV;

    public UploadCVAction(UploadCV uploadCV) {
        this.uploadCV = uploadCV;
    }
    @Override
    public Void execute() {
        uploadCV.upload();

        return null;
    }
}
