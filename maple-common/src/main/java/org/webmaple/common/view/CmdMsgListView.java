package org.webmaple.common.view;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lyifee
 * on 2021/5/4
 */
public class CmdMsgListView {
    private List<CommanderMsgView> noticeView = new ArrayList<>();

    private List<CommanderMsgView> accuseView = new ArrayList<>();

    private List<CommanderMsgView> otherView = new ArrayList<>();

    public List<CommanderMsgView> getNoticeView() {
        return noticeView;
    }

    public void setNoticeView(List<CommanderMsgView> noticeView) {
        this.noticeView = noticeView;
    }

    public List<CommanderMsgView> getAccuseView() {
        return accuseView;
    }

    public void setAccuseView(List<CommanderMsgView> accuseView) {
        this.accuseView = accuseView;
    }

    public List<CommanderMsgView> getOtherView() {
        return otherView;
    }

    public void setOtherView(List<CommanderMsgView> otherView) {
        this.otherView = otherView;
    }
}
