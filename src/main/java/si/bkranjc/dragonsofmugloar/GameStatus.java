package si.bkranjc.dragonsofmugloar;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum GameStatus {
    VICTORY("Victory"),
    DEFEAT("Defeat");

    private final String statusStr;

    GameStatus(final @Nonnull String status) {
        this.statusStr = status;
    }

    @JsonCreator
    @Nullable
    public static GameStatus of(final String str) {
        for (GameStatus status : values()) {
            if (status.statusStr.equals(str)) {
                return status;
            }
        }
        return null;
    }
}
