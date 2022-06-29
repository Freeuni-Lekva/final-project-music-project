package org.freeuni.musicforum.model;

import org.freeuni.musicforum.util.Utils;

public class Password {
    private final int clearTextLen;
    private final String hashed;

    public Password(String clearText) {
        this.clearTextLen = clearText.length();
        this.hashed = Utils.hashText(clearText);
    }

    public String hashed() {
        return hashed;
    }

    public int getPasswordSize() {
        return clearTextLen;
    }
}
