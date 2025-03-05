// SID: 2408078
package com.trackgenesis.menuActions.loggedIn;

import com.trackgenesis.menuActions.Interface.UserAction;
import com.trackgenesis.UI.ViewRankedCVs;

import java.io.IOException;

public class ViewRankedCVsAction implements UserAction<Void> {

    private final ViewRankedCVs viewRankedCVs;

    public ViewRankedCVsAction(ViewRankedCVs viewRankedCVs) {
        this.viewRankedCVs = viewRankedCVs;
    }



    @Override
    public Void execute() throws IOException {
        this.viewRankedCVs.view();
        return null;
    }
}
