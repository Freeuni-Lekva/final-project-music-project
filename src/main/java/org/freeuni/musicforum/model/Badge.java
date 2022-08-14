package org.freeuni.musicforum.model;

public class Badge {

    private final static int CONTRIBUTOR_THRESHOLD = 1000;
    private final static int ENTHUSIAST_THRESHOLD = 100;

    public enum BadgeEnum {
        NEWCOMER,
        ENTHUSIAST,
        CONTRIBUTOR,
        ADMINISTRATOR;
    }

    private BadgeEnum badge;
    public Badge(BadgeEnum badge) {
        this.badge = badge;
    }

    public boolean isAdministrator() {
        return this.badge == BadgeEnum.ADMINISTRATOR;
    }

    public void modifyAccordingTo(int prestige) {
        if (isAdministrator()) return;
        if (prestige >= CONTRIBUTOR_THRESHOLD) {
            makeContributor();
        } else if (prestige >= ENTHUSIAST_THRESHOLD) {
            makeEnthusiast();
        }
    }

    private void makeContributor() {
        if (this.badge.compareTo(BadgeEnum.CONTRIBUTOR) < 0)
        this.badge = BadgeEnum.CONTRIBUTOR;
    }

    private void makeEnthusiast() {
        if (this.badge.compareTo(BadgeEnum.ENTHUSIAST) < 0)
        this.badge = BadgeEnum.ENTHUSIAST;
    }

    @Override
    public String toString() {
        return this.badge.name();
    }

}
