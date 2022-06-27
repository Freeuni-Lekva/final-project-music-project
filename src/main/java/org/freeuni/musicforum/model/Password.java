package org.freeuni.musicforum.model;

import org.freeuni.musicforum.util.UserUtils;

public class Password {
    private final int clearTextLen;
    private final String hashed;

    public Password(String clearText) {
        this.clearTextLen = clearText.length();
        this.hashed = UserUtils.hashPassword(clearText);
    }

    public String hashed() {
        return hashed;
    }

    public int getPasswordSize() {
        return clearTextLen;
    }
}
