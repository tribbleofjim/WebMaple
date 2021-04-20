package org.webmaple.common.view;

import java.util.List;

/**
 * @author lyifee
 * on 2021/2/26
 */
public class UserSourceView {
    private boolean isCommander = false;

    private List<SourceAuthView> sourceAuthViews;

    private List<String> authValues;

    public boolean isCommander() {
        return isCommander;
    }

    public void setCommander(boolean commander) {
        isCommander = commander;
    }

    public List<SourceAuthView> getSourceAuthViews() {
        return sourceAuthViews;
    }

    public void setSourceAuthViews(List<SourceAuthView> sourceAuthViews) {
        this.sourceAuthViews = sourceAuthViews;
    }

    public List<String> getAuthValues() {
        return authValues;
    }

    public void setAuthValues(List<String> authValues) {
        this.authValues = authValues;
    }
}
