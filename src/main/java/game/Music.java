package game;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Hintergrundmusik des Spiels
 * @author T. Heiss, P. Knoll, M. Schlager, N. Schulz
 *
 */
public class Music {
    /**
     * Importiert das erste Hintergrundlied
     */
    private static Media sound = new Media(new File("src/media/Luigi Circuit - Mario Kart Wii [OST].wav").toURI().toString());

    /**
     * Importiert das zweite Hintergrundlied
     */
    private static Media sound2 = new Media(new File("src/media/Mario Kart 8 [Wii] Kuhmuh-Weide _ Moo Moo Meadows.wav").toURI().toString());
    /**
     * Importiert das dritte Hintergrundlied
     */
    private static Media sound3 = new Media(new File("src/media/He's a Pirate.wav").toURI().toString());
    /**
     * Definition des ersten Mediaplayers, der das erste Lied enth�lt
     */
    private static MediaPlayer mediaPlayer = new MediaPlayer(sound);
    /**
     * Definition des zweiten Mediaplayers, der das zweite Lied enth�lt
     */
    private static MediaPlayer mediaPlayer2 = new MediaPlayer(sound2);
    /**
     * Definition des dritten Mediaplayers, der das dritte Lied enth�lt
     */
    private static MediaPlayer mediaPlayer3 = new MediaPlayer(sound3);

    /**
     * Festlegung der Auswahl der Lieder auf der MenuBar unter dem Menupunkt "Music"
     * @return gibt den unten definitierten MenuPunkt "musicMenu" als eigenen Unterpunkt in der MenuBar zur�ck
     */
    @SuppressWarnings("exports")
    public static Menu createMusicMenu() {
        // Music RadioMenuItems

        Menu musicMenu = new Menu("Music");
        ToggleGroup musicToggle = new ToggleGroup();

        RadioMenuItem song1 = new RadioMenuItem("Song 1");
        song1.setSelected(true);

        song1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                playSong1();
            }
        });

        RadioMenuItem song2 = new RadioMenuItem("Song 2");

        song2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                playSong2();
            }
        });

        RadioMenuItem song3 = new RadioMenuItem("Song 3");

        song3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                playSong3();
            }
        });
        RadioMenuItem mute = new RadioMenuItem("Mute");

        mute.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                muteAll();
            }
        });

        song1.setToggleGroup(musicToggle);
        song2.setToggleGroup(musicToggle);
        song3.setToggleGroup(musicToggle);

        mute.setToggleGroup(musicToggle);
        musicMenu.getItems().addAll(song1, song2, song3, new SeparatorMenuItem(), mute);
        return musicMenu;
    }

    /**
     * Methode, die das Abspielen des ersten Songs regelt.
     * Wenn der erste Song spielt, so kann weder der zweite noch der dritte Song abgespielt werden,
     * Au�erdem ist hier, sowohl der Timer, die L�nge  des Songs, als auch dessen Lautst�rke festgelegt
     *
     */
    public static void playSong1() {
        mediaPlayer2.stop();
        mediaPlayer3.stop();
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setStartTime(Duration.seconds(0));
        mediaPlayer.setStopTime(Duration.seconds(128));
        mediaPlayer.setVolume(0.05);
        mediaPlayer.play();
    }

    /**
     * Methode, die das Abspielen des zweiten Songs regelt.
     * Wenn der zweite Song spielt, so kann weder der erste noch der dritte Song abgespielt werden,
     * Au�erdem ist hier, sowohl der Timer, die L�nge  des Songs, als auch dessen Lautst�rke festgelegt
     *
     */
    public static void playSong2() {
        mediaPlayer.stop();
        mediaPlayer3.stop();
        mediaPlayer2.setAutoPlay(true);
        mediaPlayer2.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer2.setStartTime(Duration.seconds(0));
        mediaPlayer2.setStopTime(Duration.seconds(129));
        mediaPlayer2.setVolume(0.08);
        mediaPlayer2.play();
    }

    /**
     * Methode, die das Abspielen des dritten Songs regelt.
     * Wenn der dritte Song spielt, so kann weder der erste noch der zweite Song abgespielt werden,
     * Au�erdem ist hier, sowohl der Timer, die L�nge  des Songs, als auch dessen Lautst�rke festgelegt
     *
     */
    public static void playSong3() {
        mediaPlayer.stop();
        mediaPlayer2.stop();
        mediaPlayer3.setAutoPlay(true);
        mediaPlayer3.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer3.setStartTime(Duration.seconds(0));
        mediaPlayer3.setStopTime(Duration.seconds(85));
        mediaPlayer3.setVolume(0.08);
        mediaPlayer3.play();
    }

    /**
     * Methode um die Musik stummzuschalten.
     */
    public static void muteAll() {
        mediaPlayer.setVolume(0);
        mediaPlayer2.setVolume(0);
        mediaPlayer3.setVolume(0);
    }


}
