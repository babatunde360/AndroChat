package com.example.androchat;

public class Word {
    private String mName;

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getLastText() {
        return mLastText;
    }

    public void setLastText(String mLastText) {
        this.mLastText = mLastText;
    }

    private String mLastText;
    public Word(String name, String lastText) {
        mName = name;
        mLastText = lastText;

    }
}
