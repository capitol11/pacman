package tud.ai1.pacman.model.level;

/**
 * Interface, welches die {@link ICoordinate} eines {@link AbstractField} darstellt.
 * 
 * @author Artur Seitz
 * @author Dennis Schirmer
 * @author Mahmoud El-Hindi
 * @author Darjush Siahdohoni
 * @author Igor Cherepanov
 * @author Hermann Berket
 * @author Maximilian Kratz
 * 
 * @version 2020-05-13
 */
public interface ICoordinate {

  /**
   * Gibt den x Wert zurueck.
   *
   * @return X Wert.
   */
  public int getX();

  /**
   * Setzt eine neue x Position.
   *
   * @param x Neuer x Wert.
   */
  public void setX(final int x);

  /**
   * Gibt den y Wert zurueck.
   *
   * @return Y Wert.
   */
  public int getY();

  /**
   * Setzt eine neue y Position.
   *
   * @param y Neuer y Wert.
   */
  public void setY(final int y);
}

