// SID: 2408078
package com.trackgenesis.menuActions.loggedIn;

import com.trackgenesis.menuActions.UserAction;
import com.trackgenesis.UI.ViewRankedCVs;



public class ViewRankedCVsAction implements UserAction<Void> {

    private final ViewRankedCVs viewRankedCVs;

    public ViewRankedCVsAction(ViewRankedCVs viewRankedCVs) {
        this.viewRankedCVs = viewRankedCVs;
    }



    @Override
    public Void execute() {
        this.viewRankedCVs.view();
        return null;
    }
}
