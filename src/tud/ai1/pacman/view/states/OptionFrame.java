package tud.ai1.pacman.view.states;

import javax.swing.*;

import tud.ai1.pacman.util.Consts;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Diese Klasse implementiert ein Auswahlmenue fuer die Spieleinstellungen.
 * 
 * @author Phil Reize
 * @author Robert Cieslinski
 * @version 07.09.2020
 */
public class OptionFrame extends JFrame implements ActionListener {

  /**
   * Vorgegebene serial Versionsnummer.
   */
  private static final long serialVersionUID = 1L;

  public static int id = -1;

  private byte start = 0;

  private final ButtonGroup buttonGroup = new ButtonGroup();

  /** Preset bei dem ein Level geladen wird */
  private final JRadioButton prePreset = new JRadioButton("Custom premade (choose file name)");

  /** Spiele alle Level */
  private final JRadioButton allLevels = new JRadioButton("Play all Levels in the level folder");

  /** Random level mit zufaelligen Dimensionen */
  private final JRadioButton ranPreset = new JRadioButton("Random level (with random size)");

  /** Starte das Spiel */
  private final JButton startGame = new JButton("Start game");

  /** Die verfuegbaren Level (Dropdown) */
  private final JComboBox<String> combo = new JComboBox<>();

  /** Die verfuegbaren Level */
  private final String[] levels;

  /**
   * Konstruktor, welcher ein neues Objekt des {@link OptionFrame} erzeugt.
   */
  public OptionFrame(String[] levels) {
    super("Game settings");
    this.levels = levels;
    this.setBounds((Toolkit.getDefaultToolkit().getScreenSize().width - 600) / 2,
        (Toolkit.getDefaultToolkit().getScreenSize().height - 300) / 2, 600, 300);
    this.setResizable(false);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setIconImage(
        Toolkit.getDefaultToolkit().getImage(Consts.THEME_FOLDER + "icon.png"));
    this.setContentPane(new JPanel());
    this.setLayout(null);
    this.setUp();
  }

  /**
   * Methode, die alle Komponenten des {@link OptionFrame} aufsetzt und mit Werten versieht.
   */
  private void setUp() {
    final int yDistanceBetweenButtons = 65;

    // Fenster ist schliessbar.
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    allLevels.setFocusable(false);
    ranPreset.setFocusable(false);
    prePreset.setFocusable(false);
    startGame.setFocusable(false);

    buttonGroup.add(allLevels);
    buttonGroup.add(ranPreset);
    buttonGroup.add(prePreset);

    prePreset.setSelected(true);

    /*
     * Positioniere die Auswahlfenster der Optionen.
     */
    prePreset.setBounds(10, 10, 290, 20);
    allLevels.setBounds(prePreset.getX(), prePreset.getY() + yDistanceBetweenButtons, prePreset.getWidth(), prePreset.getHeight());
    ranPreset.setBounds(prePreset.getX(), allLevels.getY() + yDistanceBetweenButtons, prePreset.getWidth(), prePreset.getHeight());

    startGame.setBounds(0, 211, 584, 50);
    combo.setBounds(300, prePreset.getY() + 2, 150, 20);

    /*
     * Fuege die Levelnamen hinzu.
     */
    for (String s: levels) {
      s = s.replace(".txt", "");
      s = s.replace("_", " ");
      combo.addItem(s);
    }

    /*
     * Klasse ruft actionPerformed Methode auf, sobald sich etwas veraendert.
     */
    startGame.addActionListener(this);

    this.add(allLevels);
    this.add(ranPreset);
    this.add(prePreset);
    this.add(startGame);
    this.add(combo);
  }

  /**
   * Wartet auf sinnvolle Eingabe und deren Bestatigung.
   */
  public void waitForStart() {
    this.setVisible(true);

    while (start == 0) {
      try {
        Thread.sleep(100);
      } catch (final InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Ueberprueft die Eingabe und loest gegebenenfalls den Wechsel in den {@link GamePlayState} aus,
   * indem waitForStart() beendet wird.
   */
  @Override
  public void actionPerformed(final ActionEvent e) {
    if (allLevels.isSelected()) {
      id = -2;
    } else if (ranPreset.isSelected()) {
      id = -3;
    } else if (prePreset.isSelected()) {
      id = combo.getSelectedIndex();
    }

    // Gibt die grafischen Resourcen wieder frei.
    this.dispose();
  }

  @Override
  public void dispose() {
    start = 1;
    super.dispose();
  }

}
