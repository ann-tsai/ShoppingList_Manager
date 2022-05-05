package ui;

import model.Item;
import model.ShoppingList;
import persistence.ShoppingListJsonReader;
import persistence.ShoppingListJsonWriter;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Shopping List Graphic User Interface
//
// this code is modeled from the following sources:
// https://docs.oracle.com/javase/tutorial/uiswing/components/list.html
// https://stackoverflow.com/questions/21330682/confirmation-before-press-yes-to-exit-program-in-java
// http://www.java2s.com/Tutorial/Java/0240__Swing/MessagePopUps.htm
public class GraphicUserInterface extends JPanel implements ListSelectionListener {

    private static final String JSON_STORE_SHOPPING_LIST = "./data/shoppingList.json";
    private ShoppingListJsonWriter shoppingListJsonWriter;
    private ShoppingListJsonReader shoppingListJsonReader;

    protected JList<Item> list;
    protected DefaultListModel<Item> shoppingList;
    protected ShoppingList shoppingListToSave;

    protected static final String addString = "Add";
    protected static final String removeString = "Remove";
    protected JButton removeButton;
    protected JTextField productName;
    protected JTextField category;
    protected JTextField price;


    public GraphicUserInterface() {
        super(new BorderLayout());

        shoppingList = new DefaultListModel<>();
        shoppingListToSave = new ShoppingList();

        shoppingListJsonWriter = new ShoppingListJsonWriter(JSON_STORE_SHOPPING_LIST);
        shoppingListJsonReader = new ShoppingListJsonReader(JSON_STORE_SHOPPING_LIST);

        loadShoppingList();

        runGraphicShoppingList();
    }

    // MODIFIES: this
    // EFFECTS: creates the graphic list, put it in the scroll pane, and add in buttons
    private void runGraphicShoppingList() {
        list = new JList<>(shoppingList);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        list.setFont(new Font("SansSerif", Font.PLAIN, 40));
        list.setFixedCellHeight(100);

        JButton addButton = new JButton(addString);
        AddListener addListener = new AddListener(this, addButton);
        initializedAddButton(addButton, addListener);
        initializeRemoveButton();

        initializeItemFields(addListener);

        JPanel buttonPane = createButtonPanel(addButton);

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: create the button panel for GUI using BoxLayout
    private JPanel createButtonPanel(JButton addButton) {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));

        buttonPane.add(removeButton);
        buttonPane.add(Box.createHorizontalStrut(30));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(30));
        buttonPane.add(productName);
        buttonPane.add(Box.createHorizontalStrut(10));
        buttonPane.add(category);
        buttonPane.add(Box.createHorizontalStrut(10));
        buttonPane.add(price);
        buttonPane.add(Box.createHorizontalStrut(30));
        buttonPane.add(addButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        return buttonPane;
    }

    // MODIFIES: this
    // EFFECTS: initializes the add button by adding action listener, setting enable to false when inputs are empty,
    //          and setting dimensions and fonts
    private void initializedAddButton(JButton addButton, AddListener addListener) {
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);
        addButton.setPreferredSize(new Dimension(250, 60));
        addButton.setFont(new Font("SansSerif", Font.PLAIN, 30));
    }

    // MODIFIES: this
    // EFFECTS: initializes input fields by adding input hints and initializes item fields
    private void initializeItemFields(AddListener addListener) {
        productName = new HintTextField("Enter product name...                         ");
        productName.addActionListener(addListener);
        productName.getDocument().addDocumentListener(addListener);

        category = new HintTextField("Enter category...                                ");
        category.addActionListener(addListener);
        category.getDocument().addDocumentListener(addListener);

        price = new HintTextField("Enter price...(enter 0 if not applicable)      ");
        price.addActionListener(addListener);
        price.getDocument().addDocumentListener(addListener);
    }

    // MODIFIES: this
    // EFFECTS: initializes the remove button by adding action listener and setting dimensions and fonts.
    private void initializeRemoveButton() {
        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener(this));
        removeButton.setPreferredSize(new Dimension(250, 60));
        removeButton.setFont(new Font("SansSerif", Font.PLAIN, 30));
    }


    @Override
    // MODIFIES: this
    // EFFECTS: handles the enabling of remove button with user selections
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {

            removeButton.setEnabled(list.getSelectedIndex() != -1);
        }
    }

    // EFFECTS: create the GUI and show it
    static void createAndShowGUI() {
        JFrame frame = new JFrame("Shopping List");
        saveFileOptionGUI(frame);

        JComponent newContentPane = new GraphicUserInterface();
        newContentPane.setOpaque(true); // content panes must be opaque
        frame.setContentPane(newContentPane);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);

        loadFileMessageGUI();
    }

    // MODIFIES: this
    // EFFECTS: create option panel ask user for confirmation to exit the program when they exit.
    //          if user selected Yes, exit the program, otherwise, close this option panel and resume the program.
    private static void saveFileOptionGUI(JFrame frame) {
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JLabel saveMessage = new JLabel("Are you sure you want to leave?");
                saveMessage.setFont(new Font("SansSerif", Font.PLAIN, 28));
                int confirmed = JOptionPane.showConfirmDialog(null, saveMessage, "Exit",
                        JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                    confirmSavedMessage();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    frame.setDefaultCloseOperation((JFrame.DO_NOTHING_ON_CLOSE));
                }
            }
        });
    }

    // EFFECTS: saves the shopping list to file
    protected void saveShoppingList() {
        try {
            shoppingListJsonWriter.open();
            shoppingListJsonWriter.write(shoppingListToSave);
            shoppingListJsonWriter.close();
        } catch (FileNotFoundException e) {
            System.err.println("Unable to write to file: " + JSON_STORE_SHOPPING_LIST);
        }
    }

    // EFFECTS: convert the shoppingListToSave ShoppingList to a DefaultListModel
    private void shoppingListToDefaultListModel() {
        for (int i = 0; i < shoppingListToSave.listLength(); i++) {
            shoppingList.addElement(shoppingListToSave.findItemByIndex(i));
        }
    }

    // MODIFIES: this
    // EFFECTS: creates pop-up window that informs the user that their actions has been save to file.
    protected static void confirmSavedMessage() {
        JFrame confirmSave = new JFrame();
        JLabel confirm = new JLabel("Your action(s) have been saved!");
        confirm.setFont(new Font("SansSerif", Font.PLAIN, 28));
        JOptionPane.showMessageDialog(confirmSave, confirm);
    }

    // MODIFIES: this
    // EFFECTS: creates pop-up window that informs the user that the files has been loaded automatically
    private static void loadFileMessageGUI() {
        JFrame parent = new JFrame();
        JLabel loadMessage = new JLabel("Your shopping list has been loaded!");
        loadMessage.setFont(new Font("SansSerif", Font.PLAIN, 28));
        JOptionPane.showMessageDialog(parent, loadMessage);
    }

    // MODIFIES: this
    // EFFECTS: loads shopping list from file
    private void loadShoppingList() {
        try {
            shoppingListToSave = shoppingListJsonReader.read();
        } catch (IOException e) {
            System.err.println("Unable to read from file: " + JSON_STORE_SHOPPING_LIST);
        } finally {
            shoppingListToDefaultListModel();
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(GraphicUserInterface::createAndShowGUI);
    }
}
