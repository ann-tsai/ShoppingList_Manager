package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

// this represents a class for creating input hint
//
// this code is modeled from this code:
// https://stackoverflow.com/questions/1738966/java-jtextfield-with-input-hint
public class HintTextField extends JTextField implements FocusListener {

    private final String hint;
    private boolean showingHint;


    public HintTextField(final String hint) {
        super(hint);
        this.hint = hint;
        this.showingHint = true;
        super.addFocusListener(this);

        this.setFont(new Font("SansSerif", Font.ITALIC, 30));
        this.setForeground(Color.GRAY);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: make input hints disappear when the user is focusing on the text field
    public void focusGained(FocusEvent e) {
        if (this.getText().isEmpty()) {
            super.setText("");
            showingHint = false;
        }
    }

    @Override
    // MODIFIES: this
    // EFFECTS: make input hints appear when the user is not focusing on the text field
    public void focusLost(FocusEvent e) {
        if (this.getText().isEmpty()) {
            super.setText(hint);
            showingHint = true;
        }
    }

    @Override
    // MODIFIES: this
    // EFFECTS: if showingHint is true, return the text in the text field as string
    public String getText() {
        return showingHint ? "" : super.getText();
    }
}
