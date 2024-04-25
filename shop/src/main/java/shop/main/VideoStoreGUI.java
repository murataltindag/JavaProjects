package shop.main;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class VideoStoreGUI extends JFrame {
    private State currentState = States.StartState;
    private InputSource inputSource = new GUIInputSource();
    private OutputSource outputSource = new GUIOutputSource();

    public VideoStoreGUI() {
        super("Video Store");
        setupButtons();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setVisible(true);
    }

    private void processCommand() {
        currentState.process(inputSource, outputSource);
        currentState = currentState.nextState();
    }

    private void setupButtons() {
        setLayout(new GridLayout(4, 2));

        JButton addButton = new JButton("Add Video");
        addButton.addActionListener(e -> {
            currentState = States.AddState;
            processCommand();
            currentState = currentState.nextState();
        });

        JButton removeButton = new JButton("Remove Video");
        removeButton.addActionListener(e -> {
            currentState = States.RemoveState;
            processCommand();
            currentState = currentState.nextState();
        });

        JButton initButton = new JButton("Initialize with 10 videos");
        initButton.addActionListener(e -> {
            currentState = States.InitState;
            processCommand();
            currentState = currentState.nextState();
        });

        JButton checkOutButton = new JButton("Check Out Video");
        checkOutButton.addActionListener(e -> {
            currentState = States.CheckOutState;
            processCommand();
            currentState = currentState.nextState();
        });

        JButton checkInButton = new JButton("Check In Video");
        checkInButton.addActionListener(e -> {
            currentState = States.CheckInState;
            processCommand();
            currentState = currentState.nextState();
        });

        JButton listButton = new JButton("List Videos");
        listButton.addActionListener(e -> {
            currentState = States.ListState;
            processCommand();
            currentState = currentState.nextState();
        });

        JButton clearButton = new JButton("Clear Inventory");
        clearButton.addActionListener(e -> {
            currentState = States.ClearState;
            processCommand();
            currentState = currentState.nextState();
        });

        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> {
            currentState = States.UndoState;
            processCommand();
            currentState = currentState.nextState();
        });

        JButton redoButton = new JButton("Redo");
        redoButton.addActionListener(e -> {
            currentState = States.RedoState;
            processCommand();
            currentState = currentState.nextState();
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            currentState = States.ExitState;
            processCommand();
            currentState = currentState.nextState();
        });

        add(addButton);
        add(removeButton);
        add(initButton);
        add(checkOutButton);
        add(checkInButton);
        add(listButton);
        add(clearButton);
        add(undoButton);
        add(redoButton);
        add(exitButton);
        
    }

    public static void main(String[] args) {
        new VideoStoreGUI();
    }
}