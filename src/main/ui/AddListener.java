package ui;

import model.Item;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;

// This represents the ActionListener for the add button
//
// this code is modeled from the following sources:
// https://docs.oracle.com/javase/tutorial/uiswing/components/list.html
// https://alvinalexander.com/java/java-audio-example-java-au-play-sound/
//
class AddListener implements ActionListener, DocumentListener {
    private final GraphicUserInterface graphicUserInterface;
    private boolean alreadyEnabled = false;
    private JButton button;

    public AddListener(GraphicUserInterface graphicUserInterface, JButton button) {
        this.graphicUserInterface = graphicUserInterface;
        this.button = button;
    }

    @Override
    // MODIFIES: this
    // EFFECTS: add the inputted item into the list and play add button audio sound when successfully added in the item.
    public void actionPerformed(ActionEvent e) {
        String name = graphicUserInterface.productName.getText().toUpperCase();
        String group = graphicUserInterface.category.getText().toUpperCase();
        String amount = graphicUserInterface.price.getText();

        if (invalidEntry(name, group, amount)) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }

        int index = graphicUserInterface.list.getSelectedIndex();
        if (index == -1) {
            index = 0;
        } else {
            index++;
        }

        addItemAndSave(name, group, amount, index);

        try {
            addAnItemSound();
        } catch (Exception ex) {
            Toolkit.getDefaultToolkit().beep();
        }

        resetTextField();

        graphicUserInterface.list.setSelectedIndex(index);
        graphicUserInterface.list.ensureIndexIsVisible(index);

        GraphicUserInterface.confirmSavedMessage();
    }

    // MODIFIES: this
    // EFFECTS: insert the item at the selected index and save immediately.
    private void addItemAndSave(String name, String group, String amount, int index) {
        Item newItem = new Item(name, group, Integer.parseInt(amount));
        graphicUserInterface.shoppingList.insertElementAt(newItem, index);
        graphicUserInterface.shoppingListToSave.insertItemAt(newItem, index);
        graphicUserInterface.saveShoppingList();
    }

    // EFFECTS: checks if an input is invalid
    private boolean invalidEntry(String name, String group, String amount) {
        if (name.equals("")) {
            graphicUserInterface.productName.requestFocusInWindow();
            graphicUserInterface.productName.selectAll();
            return true;
        } else if (group.equals("")) {
            graphicUserInterface.category.requestFocusInWindow();
            graphicUserInterface.category.selectAll();
            return true;
        } else if (amount.equals("") || !isInteger(amount)) {
            graphicUserInterface.price.requestFocusInWindow();
            graphicUserInterface.price.selectAll();
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: reset the text field by setting the text to empty strings
    private void resetTextField() {
        graphicUserInterface.productName.requestFocusInWindow();
        graphicUserInterface.category.requestFocusInWindow();
        graphicUserInterface.price.requestFocusInWindow();
        graphicUserInterface.productName.setText("");
        graphicUserInterface.category.setText("");
        graphicUserInterface.price.setText("");
    }

    // MODIFIES: this
    // EFFECTS: added in the audio for add button
    public void addAnItemSound() throws Exception {
        String audioFile = "data/addItemSound.wav";
        InputStream in = new FileInputStream(audioFile);

        AudioStream audioStream = new AudioStream(in);
        AudioPlayer.player.start(audioStream);
    }

    @Override
    // MODIFIES:
    // EFFECTS: enable the button
    public void insertUpdate(DocumentEvent e) {
        enableButton();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: disable the add button if the text field are empty
    public void removeUpdate(DocumentEvent e) {
        handleEmptyTextField(e);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: enable the add button if the text fields are not empty
    public void changedUpdate(DocumentEvent e) {
        if (!handleEmptyTextField(e)) {
            enableButton();
        }
    }

    // MODIFIES: this
    // EFFECTS: enable the add button if it is not already enabled
    private void enableButton() {
        if (!alreadyEnabled) {
            button.setEnabled(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: disable the add button if the text field is empty otherwise return false
    private boolean handleEmptyTextField(DocumentEvent e) {
        if (e.getDocument().getLength() <= 0) {
            button.setEnabled(false);
            alreadyEnabled = false;
            return true;
        }
        return false;
    }

    // EFFECTS: return true if the string is made of integers, false otherwise
    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
