package tud.ai1.pacman.model.level;

/**
 * Abstrakte Klasse, die ein {@link AbstractField} des Spielfeldes darstellt. Bei dieser Darstellung
 * sind nur die {@link ICoordinate} relevant. Dies bedeutet, dass alle anderen Eigenschaften eines
 * Feldes in einer separaten Klasse implementiert werden muessen, die von dieser Klasse erbt.
 * 
 * @author Maximilian Kratz
 * @author Devin Balian
 * @version 2021-05-25
 */
public abstract class AbstractField {
  /**
   * {@link ICoordinate} des {@link AbstractField}.
   */
  protected ICoordinate coordinates;

  /**
   * Initialisiert ein neues {@link AbstractField} mit uebergebenen {@link ICoordinate}.
   * 
   * @param coordinate {@link ICoordinate} zum Initialisieren des Feldes.
   */
  public AbstractField(final ICoordinate coordinate) {
    this.validateCoordinate(coordinate);
    this.coordinates = coordinate;
  }

  /**
   * Prueft, ob eine uebergebene {@link ICoordinate} valide ist. Wirft eine
   * {@link IllegalArgumentException}, wenn dies nicht der Fall ist.
   * 
   * @param coordinate {@link ICoordinate}, welche ueberprueft werden soll.
   */
  protected void validateCoordinate(final ICoordinate coordinate) {
    if (coordinate == null) {
        throw new IllegalArgumentException("Uebergebene Koordinaten sind null!");
    }
  }

  /**
   * Gibt einen String zurueck, welcher das {@link AbstractField} repraesentiert.
   * 
   * @return Zum {@link AbstractField} passenden String. Dieser String repraesentiert den Zustand
   *         des {@link AbstractField}.
   */
  @Override
  public abstract String toString();

  /**
   * Setzt die {@link ICoordinate} des {@link AbstractField} auf den neuen, uebergebenen Wert.
   * 
   * @param coordinate Zu setzende {@link ICoordinate}.
   */
  public abstract void setCoordinates(final ICoordinate coordinate);

  /**
   * Gibt die {@link ICoordinate} des {@link AbstractField} zurueck.
   * 
   * @return Gibt die {@link ICoordinate} des {@link AbstractField} zurueck.
   */
  public abstract ICoordinate getCoordinates();

}

