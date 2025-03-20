// SID: 2408078
package com.trackgenesis.menuActions.loggedIn;

import com.trackgenesis.menuActions.UserAction;
import com.trackgenesis.UI.ViewRankedCVs;


/**
 * Class that implements UserAction interface
 * @author henryburbridge
 */
public class ViewRankedCVsAction implements UserAction<Void> {

    private final ViewRankedCVs viewRankedCVs;

    /**
     * Constructor
     * @param viewRankedCVs ViewRankedCVs object
     */
    public ViewRankedCVsAction(ViewRankedCVs viewRankedCVs) {
        this.viewRankedCVs = viewRankedCVs;
    }


    /**
     * Calls view() method in ViewRankedCVs class
     * @return null
     */
    @Override
    public Void execute() {
        this.viewRankedCVs.view();
        return null;
    }
}
