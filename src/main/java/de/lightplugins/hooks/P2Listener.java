package de.lightplugins.hooks;

import com.plotsquared.core.PlotAPI;

public class P2Listener {

    // if you like the dependency-injection-like approach:
    public P2Listener(PlotAPI api) {
        api.registerListener(this);
    }
}
