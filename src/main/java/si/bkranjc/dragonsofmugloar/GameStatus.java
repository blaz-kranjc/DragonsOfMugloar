package si.bkranjc.dragonsofmugloar;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GameStatus {
    VICTORY("Victory"),
    DEFEAT("Defeat");

    private final String statusStr;

    GameStatus(final String status) {
        this.statusStr = status;
    }

    @JsonCreator
    public static GameStatus of(final String str) {
        for (GameStatus status : values()) {
            if (status.statusStr.equals(str)) {
                return status;
            }
        }
        return null;
    }
}
