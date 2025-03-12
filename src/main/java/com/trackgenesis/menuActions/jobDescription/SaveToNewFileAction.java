// SID: 2408078
package com.trackgenesis.menuActions.jobDescription;

import com.trackgenesis.menuActions.Interface.UserAction;
import com.trackgenesis.util.FileSaver;
import com.trackgenesis.util.KeyboardReader;

import java.io.IOException;


public class SaveToNewFileAction implements UserAction<Void> {

    private final FileSaver save;
    private final KeyboardReader kbr;
    private final String saveLocation;
    private final String fileName;

    public SaveToNewFileAction(FileSaver fileSaver, KeyboardReader kbr, String saveLocation, String fileName ) {
        this.save = fileSaver;
        this.kbr = kbr;
        this.saveLocation = saveLocation;
        this.fileName = fileName;
    }

    @Override
    public Void execute() {
        save.saveToNewFile(kbr.getLongString("Enter the job description: "), this.saveLocation, this.fileName);
        return null;
    }
}
