package tud.ai1.pacman.model.entity;

import org.newdawn.slick.geom.Vector2f;

import tud.ai1.pacman.model.PacmanGame;
import tud.ai1.pacman.model.level.Level;
import tud.ai1.pacman.util.Consts;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Modelliert eine gegnerische Geister-Entitaet.
 *
 * @author Simon Breitfelder
 * @author Dominik Puellen
 * @author Kurt Cieslinski
 */
public class Ghost extends MovingEntity {
    public static final String[] saveStateOrder = {"number", "respawnTime", "idle", "oldPos"};

    /** Ein Random-Generator. NICHT ABSPEICHERN/LADEN! */
    protected final Random rnd;

    /** Geister-Nummer, bestimmt den Skin */
    private final int number;
    /** Zeitpunkt des letzten Respawns */
    private long respawnTime;
    /** Ob der Geist gerade seine Respawn-Bewegung ausfuehrt */
    private boolean idle;

    /** Position vor der letzten Bewegung */
    private Point oldPos = null;

    /**
     * Konstruktor.
     *
     * @param num Geister-ID
     */
    public Ghost(int num) {
        super(Consts.G_IDLE_MOVE_SPEED);
        number = num;
        rnd = new Random();
        idle = true;
    }

    /**
     * Konstruktor.
     * Alle Daten werden aus dem uebergebenen Stream gelesen.
     *
     * @param s der Stream, aus dem die Daten gelesen werden
     * @throws IOException bei Lesefehlern
     */
    public Ghost(DataInputStream s) throws IOException {
        super(s);
        rnd = new Random();
        number = s.readInt();
        respawnTime = PacmanGame.getTime() + s.readLong();
        idle = s.readBoolean();
        oldPos = (s.readBoolean() ? new Point(s.readInt(), s.readInt()) : null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeEntity(DataOutputStream s) throws IOException {
        super.writeEntity(s);
        s.writeInt(number);
        s.writeLong(respawnTime - PacmanGame.getTime());
        s.writeBoolean(idle);
        s.writeBoolean(oldPos != null);
        if (oldPos != null) {
            s.writeInt(oldPos.x);
            s.writeInt(oldPos.y);
        }
    }

    /**
     * Setzt den Geist an eine bestimmte Position zurueck.
     * In der Regel wurde der Geist vorher von Pacman gefressen.
     *
     * @param pos Respawn-Position
     */
    public void respawn(Point pos) {
        respawnTime = System.nanoTime();
        setSpeed(Consts.G_IDLE_MOVE_SPEED);
        idle = true;
        super.abortMove();
        // zufaellige startposition der wartebewegung (sieht beim start etwas schoener aus)
        super.setPos(new Vector2f(pos.x, pos.y + (rnd.nextFloat() - 0.5f) * (Consts.G_IDLE_MOVE_SIZE * 2)));
    }

    /**
     * @return die Geister-Nummer
     */
    public int getNumber() {
        return number;
    }

    /**
     * @return ob der Geist noch in der Respawn-Animation ist
     */
    public boolean isIdle() {
        return idle;
    }

    /**
     * {@inheritDoc}
     * Ist der Geist in der Respawnanimation, kann er nicht mit Pacman kollidieren.
     * Ansonsten koennen Geister nur entfernt werden, wenn Pacman im Powerup-Zustand ist.
     */
    public boolean collide(PacmanGame game, Pacman pacman) {
        // geister duerfen nicht idle sein um den spieler zu toeten / punkte zu bringen
        if (!idle) {
            if (pacman.isPoweredUp()) {
                // im powerup-zustand den geist toeten
                game.updatePoints(Consts.GHOST_POINTS);
                respawn(game.getLevel().getNextGhostSpawn());
            } else game.kill();
        }
        return false;
    }

    /**
     * {@inheritDoc}
     * Plane eine neue Bewegung, falls der Geist gerade stillsteht.
     * Teleportiere den Geist am Rand.
     */
    @Override
    public void update(PacmanGame game) {
        if (idle && Consts.TEST) {
            super.abortMove();
            idle = false;
        }

        super.update(game);

        if (super.notMoving()) {
            // naechste bewegung starten
            if (idle && (System.nanoTime() - respawnTime) < Consts.G_RESPAWN_IDLE_TIME) {
                // idle-bewegung
                if (super.getPos().y > Math.round(super.getPos().y))
                    super.move(new Vector2f(super.getPos().x, Math.round(super.getPos().y) - Consts.G_IDLE_MOVE_SIZE));
                else
                    super.move(new Vector2f(super.getPos().x, Math.round(super.getPos().y) + Consts.G_IDLE_MOVE_SIZE));
            } else {
                if (idle) {
                    // idle-state verlassen
                    super.setPos(new Point((int) super.getPos().x, Math.round(super.getPos().y)));
                    setSpeed(Consts.G_MOVE_SPEED);
                    idle = false;
                }

                // neue bewegung starten und alte position speichern
                Point p = super.getGridPos();
                super.move(getTargetPoint(game));
                oldPos = p;
            }
        }
        borderTeleport(game.getLevel());
    }

    /**
     * @param game das Pacman-Spiel
     * @return der aktuell passende Zielpunkt
     */
    private Point getTargetPoint(PacmanGame game) {
        // erst auf Sichtkontakt pruefen
        Point p = getSightPoint(game);
        if (p != null) return p;

        // alle Abzweigungen ermitteln
        Point[] branches = game.getLevel().getBranches(super.getGridPos());
        // zufaellig eine Abzweigung waehlen, die moeglichst nicht der vorherigen Position entspricht
        return getRandomBranch(branches, oldPos);
    }

    
    private Point getSightPoint(PacmanGame game) {
        if (game == null)
            throw new IllegalArgumentException();

        Pacman pacman = game.getPacman();
        Level level = game.getLevel();
        // Sichtkontakt zur Spielfigur?
        if (level.existsStraightPath(super.getGridPos(), pacman.getGridPos())) {
            // Spielfeld in diese Richtung ermitteln
            int dx = (int) Math.signum(pacman.getGridPos().x - super.getGridPos().x);
            int dy = (int) Math.signum(pacman.getGridPos().y - super.getGridPos().y);
            Point p = new Point(super.getGridPos().x + dx, super.getGridPos().y + dy);

            if (pacman.isPoweredUp()) {
                // alle moeglichen Abzweigungen ermitteln
                Point[] branches = level.getBranches(super.getGridPos());
                // zufaellig eine waehlen, die nicht in Richtung der Spielfigur steuert
                return getRandomBranch(branches, p);
            }
            // direkt zur Spielfigur steuern
            return p;
        }
        return null;
    }

    
    private Point getRandomBranch(Point[] branches, Point avoid) {
        if(branches == null)
            throw new IllegalArgumentException();
        if (branches.length == 0)
            return null;
        // keine alternative wahl moeglich:
        if (branches.length == 1)
            return branches[0];
        // zufaellig einen punkt waehlen, der nicht avoid entspricht
        if (avoid == null)
            return branches[rnd.nextInt(branches.length)];
        while (true) {
            int i = rnd.nextInt(branches.length);
            if (!branches[i].equals(avoid))
                return branches[i];
        }
    }


}
