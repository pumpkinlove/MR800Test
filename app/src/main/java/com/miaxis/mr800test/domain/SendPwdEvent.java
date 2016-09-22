package com.miaxis.mr800test.domain;

/**
 * Created by xu.nan on 2016/9/20.
 */
public class SendPwdEvent {
    private int index;

    public SendPwdEvent() {
    }

    public SendPwdEvent(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
