// SID: 2408078
package com.trackgenesis.menuActions.loggedIn;

import com.trackgenesis.menuActions.UserAction;
import com.trackgenesis.UI.UploadCV;


/**
 * Action class that implements UserAction
 * @author henryburbridge
 */
public class UploadCVAction implements UserAction<Void> {
    private final UploadCV uploadCV;

    /**
     * Constructor
     * @param uploadCV UploadCV object
     */
    public UploadCVAction(UploadCV uploadCV) {
        this.uploadCV = uploadCV;
    }

    /**
     * Calls upload() in the UploadCV class
     * @return null
     */
    @Override
    public Void execute() {
        uploadCV.upload();

        return null;
    }
}
