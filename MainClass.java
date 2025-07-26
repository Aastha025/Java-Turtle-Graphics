package turtleGraphicsProject;

import javax.swing.JFrame;

public class MainClass {
    public static void main(String[] args) {
        // Create and set up the window
        JFrame frame = new JFrame("Turtle Graphics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create turtle graphics instance
        TurtleGraphics turtleGraphics = new TurtleGraphics();
        
        // Add turtle graphics instance to the frame
        frame.add(turtleGraphics);
        frame.setSize(750, 500);  // Set the frame size
        frame.setVisible(true);   // Make the frame visible
        
        turtleGraphics.handleConsoleInput();
    }
}

