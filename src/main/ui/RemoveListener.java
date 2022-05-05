package ui;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;

// This represents the ActionListener for the remove button
//
// this code is modeled from the following sources:
// https://docs.oracle.com/javase/tutorial/uiswing/components/list.html
// https://alvinalexander.com/java/java-audio-example-java-au-play-sound/
//
class RemoveListener implements ActionListener {
    private final GraphicUserInterface graphicUserInterface;

    public RemoveListener(GraphicUserInterface graphicUserInterface) {
        this.graphicUserInterface = graphicUserInterface;
    }

    // REQUIRES: valid selection
    // MODIFIES: this
    // EFFECTS: remove the selected item from the shopping list and save immediately. Play the remove button audio when
    //          successfully remove the selected item.
    public void actionPerformed(ActionEvent e) {
        int index = graphicUserInterface.list.getSelectedIndex();
        graphicUserInterface.shoppingList.remove(index);
        graphicUserInterface.shoppingListToSave.removeItem(
                graphicUserInterface.shoppingListToSave.findItemByIndex(index));
        graphicUserInterface.saveShoppingList();

        int size = graphicUserInterface.shoppingList.getSize();

        if (size == 0) {
            graphicUserInterface.removeButton.setEnabled(false);
        } else {
            if (index == graphicUserInterface.shoppingList.getSize()) {
                //removed item in last position
                index--;
            }

            graphicUserInterface.list.setSelectedIndex(index);
            graphicUserInterface.list.ensureIndexIsVisible(index);

            try {
                deleteAnItemSound();
            } catch (Exception ex) {
                Toolkit.getDefaultToolkit().beep();
            }

            GraphicUserInterface.confirmSavedMessage();
        }
    }

    // MODIFIES: this
    // EFFECTS: added in the audio for remove button
    public void deleteAnItemSound() throws Exception {
        String audioFile = "data/deleteItemSound.wav";
        InputStream in = new FileInputStream(audioFile);

        AudioStream audioStream = new AudioStream(in);
        AudioPlayer.player.start(audioStream);
    }
}
