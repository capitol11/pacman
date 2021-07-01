package tud.ai1.pacman.view.actions;

import eea.engine.action.Action;
import eea.engine.component.Component;

import java.time.LocalDateTime;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import tud.ai1.pacman.model.Highscore;
import tud.ai1.pacman.model.HighscoreEntry;
import tud.ai1.pacman.util.Consts;

/**
 * Speichert einen neuen Highscore-Eintrag.
 *
 * @author Simon Breitfelder
 * @author Dominik Puellen
 * @author Robert Cieslinski
 */
public class SaveHighscoreAction implements Action {
    /** Punktzahl des Eintrags */
    private int newPoints;
    /** Nickname des Eintrags */
    private String newName;

    /**
     * @param points aktualisierte Punktzahl
     * @param name aktualisierter Nickname
     */
    public SaveHighscoreAction(int points, String name) {
        newPoints = points;
        newName = name;
    }

    public void update(GameContainer gc, StateBasedGame sb, int delta, Component event) {
        Highscore.getInstance().addHighscore(new HighscoreEntry(LocalDateTime.now(), newName, newPoints));
        Highscore.getInstance().saveToFile(Consts.HIGHSCORE_FILE);
        sb.enterState(Consts.HIGHSCORE_STATE);
    }
}
