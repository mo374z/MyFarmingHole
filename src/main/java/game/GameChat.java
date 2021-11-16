package game;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.scene.control.Label;

/**
 * Erstellung eines Game-Chats, der w�hrend dem Spiel Nachrichten und Hinweise ausgibt.
 * Im Spiel wird ein GameChat unten rechts auf der {@link SideBar} angezeigt.
 * @author T. Heiss, P. Knoll, M. Schlager, N. Schulz
 *
 */
public class GameChat {

    /**
     * Label, das den Game-Chat repr�sentiert.
     */
    private static Label gameChat = new Label("<> Messages will appear here");

    /**
     * Aktuelles Datum mit Uhrzeit.
     */
    static Date date;

    /**
     * Formatiert ein Datum in die Form HH:mm:ss, wie es schlie�lich auch im Game-Chat angezeigt wird.
     */
    static SimpleDateFormat formatter = new SimpleDateFormat("<HH:mm:ss>");

    /**
     * Methode, mit der eine neue Nachricht zum Game-Chat hinzugef�gt wird.
     * @param message Nachricht, die dem Game-Chat hinzuf�gt werden soll.
     */
    public static void setNewMessage(String message) {
        date = new Date(System.currentTimeMillis());
        gameChat.setText(gameChat.getText() + "\n" + formatter.format(date) + message);
        trimChat(5);
    }

    /**
     * Getter f�r den Game-Chat.
     * @return gameChat Label, das die Chat-Nachrichten beinhaltet
     */
    @SuppressWarnings("exports")
    public static Label getGameChat() {
        return gameChat;
    }

    /**
     * Methode, mit der die L�nge des Game-Chats gek�rzt wird.
     * @param maxlength L�nge, auf die der Game-Chat gek�rzt werden soll.
     */
    public static void trimChat(int maxlength) {
        char [] chat = gameChat.getText().toCharArray();
        int count = 0;
        for (int i = 0; i<chat.length; i++) { //Durchsucht den Text im GameChat nach Nachrichtenanf�ngen ("<"), um sicherzustellen, dass der Chat maximal die in maxlength festgelegte Nachrichtenanzahl hat.
            if(chat[i] == '<') {
                count++;
            }
        }

        String[] lines;
        if (count>maxlength) {
            lines = gameChat.getText().split("\\n");
            StringBuilder trimmedChat = new StringBuilder();
            for(int i = 1; i<maxlength; i++) {
                trimmedChat.append(lines[i] + "\n");
            }
            trimmedChat.append(lines[maxlength]);
            gameChat.setText(trimmedChat.toString());
        }

    }

    /**
     * Getter f�r die Variable gameChat.
     * @return gameChat Label, das den Game-Chat darstellt.
     */
    @SuppressWarnings("exports")
    public static Label getChat() {
        return gameChat;
    }



}
