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

    public int getBadgeIntValue(){
        if(badge==BadgeEnum.ADMINISTRATOR){
            return 0;
        } else if(badge==BadgeEnum.NEWCOMER){
            return 1;
        } else if(badge==BadgeEnum.CONTRIBUTOR){
            return 2;
        } else if(badge==Badge.BadgeEnum.ENTHUSIAST){
            return 3;
        }
        return -1;
    }

    @Override
    public String toString() {
        return this.badge.name();
    }


}
