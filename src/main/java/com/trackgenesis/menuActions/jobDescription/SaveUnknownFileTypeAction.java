// SID: 2408078
package com.trackgenesis.menuActions.jobDescription;

import com.trackgenesis.menuActions.Interface.UserAction;
import com.trackgenesis.util.FileSaver;

import java.io.IOException;

public class SaveUnknownFileTypeAction implements UserAction<Void> {

    private final FileSaver save;
    private final String fileName;
    private final String saveLocation;

    public SaveUnknownFileTypeAction(FileSaver fileSaver, String fileName, String saveLocation) {
        this.save = fileSaver;
        this.fileName = fileName;
        this.saveLocation = saveLocation;
    }

    @Override
    public Void execute() throws IOException {
        save.saveUnknownFileType(save.chooseFile(), this.saveLocation, this.fileName);
        return null;
    }
}
