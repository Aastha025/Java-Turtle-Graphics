package turtleGraphicsProject;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.ImageIcon;
import java.io.PrintWriter;
import uk.ac.leedsbeckett.oop.LBUGraphics;

public class TurtleGraphics extends LBUGraphics {

   
	private static final long serialVersionUID = 1L;
	protected static final int WIDTH = 750;
	protected static final int HEIGHT = 500;
    private List<String> commandHistory = new ArrayList<>();
    private boolean unsavedChanges = false;
    private int penWidth = 1;
    private Color currentColor = Color.WHITE;
    
    private final Color[] colors = {
        Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.WHITE
    };
    
    public TurtleGraphics() {
        super();
        reset();
        System.out.println("***** Welcome to Turtle Graphics *****\n");
    }

   

    @Override
    public void processCommand(String command) {
        command = command.trim();
        commandHistory.add(command);
        unsavedChanges = true;
        String[] parts = command.split("\\s+");
        String cmd = parts[0].toLowerCase();
       

        try {
            switch (cmd) {
                case "about":
                    about();
                    break;
                    
                	
                case "penup":
                    drawOff();
                    System.out.println("Pen is now UP.");
                    break;
                    
                case "pendown":
                    drawOn();
                    System.out.println("Pen is now DOWN.");
                    break;
                    
                case "left":
                    if (parts.length < 2) {
                        System.out.println("Usage: left <degrees>");
                        break;
                    }
                    int leftDegrees = validateNumber(parts[1], 0, 360);
                    turnLeft(leftDegrees);
                    System.out.println("Turn " + leftDegrees + " degrees to the left.");
                    break;
                    
                case "right":
                    if (parts.length < 2) {
                        System.out.println("Usage: right <degrees>");
                        break;
                    }
                    int rightDegrees = validateNumber(parts[1], 0, 360);
                    turnRight(rightDegrees);
                    break;

                case "black":
                    setColor(0);
                    break;
                case "red":
                    setColor(1);
                    break;
                case "green":
                    setColor(2);
                    break;
                case "blue":
                	setColor(3);
                	break;
                
                case "white":
                    setColor(4);
                    break;
     
                case "reset":
                    reset();
                    break;
                    
                case "clear":
                    clear();
                    displayMessage("Turtle resets");
                    System.out.println("Turtle resets to its initial position.");
                    break;
                    
                
                
                case "move":
                    if (parts.length < 2) {
                        System.out.println("Usage: move <distance>");
                        break;
                    }
                   
                    int forwardDist = validateNumber(parts[1], 1, Integer.MAX_VALUE);
                    forward(forwardDist);
            
                    break;

                case "reverse":
                    if (parts.length < 2) {
                        System.out.println("Usage: reverse <distance>");
                        break;
                    }
                    int backDist = validateNumber(parts[1], 1, Integer.MAX_VALUE);
                    forward(-backDist);
                    break;
                 
                case "square":
                    if (parts.length < 2) {
                        System.out.println("Usage: square <size>");
                        break;
                    }
                    drawSquare(validateNumber(parts[1], 1, 1000));
                    break;
                    
                case "triangle":
                    if (parts.length < 2) {
                        System.out.println("Usage: triangle <size> or triangle <side1> <side2> <side3>");
                        break;
                    }
                    if (parts.length == 2) {
                        drawEquilateralTriangle(validateNumber(parts[1], 1, 1000));
                    } else if (parts.length == 4) {
                        drawScaleneTriangle(
                            validateNumber(parts[1], 1, 1000),
                            validateNumber(parts[2], 1, 1000),
                            validateNumber(parts[3], 1, 1000)
                        );
                    } else {
                        System.out.println("Invalid triangle command");
                    }
                    break;
                    
                
                case "pencolor":
                    if (parts.length < 2) {
                        System.out.println("Usage: pencolor R,G,B");
                        break;
                    }
                    String[] rgbValues = parts[1].split(",");
                    if (rgbValues.length != 3) {
                        System.out.println("Invalid format for pencolor. Use: R,G,B");
                        break;
                    }

                    try {
                        int r = validateNumber(rgbValues[0], 0, 255);
                        int g = validateNumber(rgbValues[1], 0, 255);
                        int b = validateNumber(rgbValues[2], 0, 255);

                        setPenColor(r, g, b);
                        System.out.println("Pen color set to: RGB(" + r + "," + g + "," + b + ")");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid RGB values. Ensure they are between 0 and 255.");
                    }
                    break;
             
                case "penwidth":
                    if (parts.length < 2) {
                        System.out.println("Usage: penwidth <width>");
                        break;
                    }

                    try {
                        int width = validateNumber(parts[1], 1, 50);
                        penWidth = width;
                        setStroke(width);
                        System.out.println("Pen width set to: " + width);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid pen width. Please enter a number between 1 and 50.");
                    }
                    break;

                case "save":
                    if (parts.length < 3) {
                        System.out.println("Usage: save [commands|image] filename");
                        break;
                    }
                    saveData(parts);
                    break;
                    
                case "load":
                    if (parts.length < 3) {
                        System.out.println("Usage: load [commands|image] filename");
                        break;
                    }
                    loadData(parts);
                    break;
                    
                case"spin":
                    int angle = 10;
                    int times = 36;
                    if (parts.length > 1) {
                        angle = Integer.parseInt(parts[1]);
                        if (parts.length > 2) {
                            times = Integer.parseInt(parts[2]);
                        }
                    }
                    spin(angle, times);
                    break;
                    
                case "cube":
                    if (parts.length < 2) {
                        System.out.println("Usage: cube <size>");
                        break;
                    }
                    draw3DCube(validateNumber(parts[1], 10, 300));
                    break;
                    
                case "backflip":
                	backflip();
                	break;
                	
                case "spiral":
                    if (parts.length < 4) {
                        System.out.println("Usage: spiral <initialRadius> <loops> <growthRate>");
                        break;
                    }
                    // Validate inputs
                    int radius = validateNumber(parts[1], 10, 100);  // Validating initial radius
                    int loops = validateNumber(parts[2], 1, 20);      // Validating number of loops
                    int growth = validateNumber(parts[3], 1, 20);     // Validating growth rate
                    
                    // Draw the spiral with the validated inputs
                    drawSpiral(radius, loops, growth);
                    break;

                  
                default:
                    System.out.println("Invalid command: " + command);
            }
            
            

            	
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
   
    @Override
    public void about() {
    	super.about();
        System.out.println("Enhanced by AASTHA");  // Console output
        displayMessage("Enhanced by AASTHA");     // Graphics window output
    }
    
    	@Override
    public void reset() {
        super.reset();
        setPenColour(currentColor);
        setStroke(penWidth);
        drawOn();
    }

    public void turnLeft(int degrees) {
        super.left(degrees);
    }

    public void turnRight(int degrees) {
        super.right(degrees);
    }
    
    public void reverse(int distance) {
        forward(-distance);  // Move backward by moving forward with a negative distance
    }


    
    private void setColor(int colorIndex) {
        if (colorIndex >= 0 && colorIndex < colors.length) {
            currentColor = colors[colorIndex];
            setPenColour(currentColor);
            System.out.println("Color set to: " + currentColor);
        }
    }

    private void setPenColor(int r, int g, int b) {
        currentColor = new Color(r, g, b);
        setPenColour(currentColor);
    }
   
                
    private void drawSquare(int size) {
        for (int i = 0; i < 4; i++) {
            forward(size);
            turnRight(90);
        }
    }

    private void drawEquilateralTriangle(int size) {
        for (int i = 0; i < 3; i++) {
            forward(size);
            turnRight(120);
        }
    }

    private void drawScaleneTriangle(int a, int b, int c) {
        if (a + b <= c || a + c <= b || b + c <= a) {
            throw new IllegalArgumentException("Invalid triangle sides");
        }

        double angleA = Math.toDegrees(Math.acos((b * b + c * c - a * a) / (2.0 * b * c)));
        double angleB = Math.toDegrees(Math.acos((a * a + c * c - b * b) / (2.0 * a * c)));

        forward(b);
        turnRight(180 - (int) angleA);
        forward(c);
        turnRight(180 - (int) angleB);
        forward(a);
    }
    
    private void spin(int angle, int times) {
        for (int i = 0; i < times; i++) {
            turnLeft(angle);
            try {
                Thread.sleep(100);  // Delay to show the spinning effect
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Turtle spun " + (angle * times) + " degrees.");
    }


 
    public void draw3DCube(int size) {
        int x = getxPos();
        int y = getyPos();
        int offset = size / 2; // Perspective offset for 3D effect
        Color currentColor = getPenColour();

        // Front square
        drawLine(currentColor, x, y, x + size, y); // Line from top-left to top-right
        drawLine(currentColor, x + size, y, x + size, y + size); // Right line
        drawLine(currentColor, x + size, y + size, x, y + size); // Bottom line
        drawLine(currentColor, x, y + size, x, y); // Left line

        // Back square (offset)
        drawLine(currentColor, x + offset, y - offset, x + size + offset, y - offset); // Top-back line
        drawLine(currentColor, x + size + offset, y - offset, x + size + offset, y + size - offset); // Right-back line
        drawLine(currentColor, x + size + offset, y + size - offset, x + offset, y + size - offset); // Bottom-back line
        drawLine(currentColor, x + offset, y + size - offset, x + offset, y - offset); // Left-back line

        // Connect corners
        drawLine(currentColor, x, y, x + offset, y - offset); // Front to back (top-left corner)
        drawLine(currentColor, x + size, y, x + size + offset, y - offset); // Top-right corner
        drawLine(currentColor, x + size, y + size, x + size + offset, y + size - offset); // Bottom-right corner
        drawLine(currentColor, x, y + size, x + offset, y + size - offset); // Bottom-left corner
    }
    
    //backflip
    public void backflip() {
        System.out.println("Turtle is doing a backflip!");
        drawOff();
        forward(50);  // Move back a bit
        turnRight(180);  // Turn around
        forward(50);  // Move back to the original position
        drawOn();
        System.out.println("Turtle completed the backflip!");
    }
    
    //helper method for spiral
    public void drawSpiral(int initialRadius, int loops, int growthRate) {
        // Save current colour and pen width
        Color prevColor = getPenColour();
        

        // Set up spiral drawing
        drawOff();  // Pen up
        setPenColour(Color.MAGENTA);
        setStroke(2);
        drawOn();   // Pen down

        int stepsPerLoop = 36;  // Number of steps per full rotation

        // Loop through each step of the spiral
        for (int i = 0; i < loops * stepsPerLoop; i++) {
            // Calculate the current radius for this step
            double currentRadius = initialRadius + (i * growthRate / (double)stepsPerLoop);

            // Calculate the distance to move and cast the result to int
            int forwardDistance = (int) (2 * Math.PI * currentRadius / stepsPerLoop);  // Casting to int
            forward(forwardDistance);  // Pass the result as an int

            // Turn the turtle to create the spiral shape
            right(360 / stepsPerLoop);  // Use right() to turn
        }

        // Restore original pen settings
        setPenColour(prevColor);
       
        drawOff();  // Pen up at the end
    }

    //save data
    private void saveData(String[] parts) throws IOException {
        if (parts.length < 3) {
            throw new IllegalArgumentException("Usage: save [commands|image] filename");
        }

        if (parts[1].equalsIgnoreCase("commands")) {
            saveCommands(parts[2]);
        } else if (parts[1].equalsIgnoreCase("image")) {
            saveImage(parts[2]);
        } else {
            throw new IllegalArgumentException("Invalid save type");
        }

        unsavedChanges = false;
    }

    //load data
    private void loadData(String[] parts) throws IOException {
        if (parts.length < 3) {
            throw new IllegalArgumentException("Usage: load [commands|image] filename");
        }

        if (parts[1].equalsIgnoreCase("commands")) {
            loadCommands(parts[2]);
        } else if (parts[1].equalsIgnoreCase("image")) {
            loadImage(parts[2]);
        } else {
            throw new IllegalArgumentException("Invalid load type");
        }

        unsavedChanges = true;
    }

    
//save commands
    private void saveCommands(String filename) throws IOException {
        try (PrintWriter out = new PrintWriter(filename)) {
            for (String cmd : commandHistory) {
            	out.println(cmd);
            }
            System.out.println("Commands saved to:" + filename);
        }
    }

    //load commands 
    private void loadCommands(String filename) throws IOException {
        reset();
        commandHistory.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
            	//if(!commandHistory.contains(line))//prevent re-execution of same command {
                System.out.println("Executing command from file: " + line);
                //processCommand(line);
                commandHistory.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading commands: " + e.getMessage());
        }
    }

//save image
    private void saveImage(String filename) {
        try {
            // Create a buffered image with the current size of the canvas (frame)
            BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

            // Get graphics object from the image to draw the current canvas
            Graphics2D g2d = image.createGraphics();
            // Paint the current canvas to the image
            paint(g2d);  // Calls the paint method which renders the graphics
            
            // Save the image to a file
            File outputFile = new File(filename);
            ImageIO.write(image, "PNG", outputFile); // Save in PNG format
            
            System.out.println("Image saved successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving image: " + e.getMessage());
        }
    }

    private void loadImage(String filename) {
        try {
            // Load the image from file
            File inputFile = new File(filename);
            Image image = ImageIO.read(inputFile);

            // Display the image in a JLabel or your custom drawing area
            JLabel label = new JLabel(new ImageIcon(image));
            JOptionPane.showMessageDialog(this, label, "Loaded Image", JOptionPane.PLAIN_MESSAGE);
            
            System.out.println("Image loaded successfully from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }
        private boolean checkUnsavedChanges() {
        if (unsavedChanges) {
            int option = JOptionPane.showConfirmDialog(null,
                    "You have unsaved changes. Save before continuing?",
                    "Warning", JOptionPane.YES_NO_CANCEL_OPTION);

            if (option == JOptionPane.CANCEL_OPTION) {
                return false; // Stop any action
            }
            if (option == JOptionPane.YES_OPTION) { 	
                String filename = JOptionPane.showInputDialog("Enter filename to save:");
                if (filename != null) {
                    try {
                        saveCommands(filename);
                    } catch (Exception e) {
                        System.out.println("Save failed: " + e.getMessage());
                    }
                }
            }
        }
        return true;
    }
    
    @SuppressWarnings("resource")
    protected void handleConsoleInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Turtle Graphics Console (type 'exit' to quit)");

        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim();

            if (command.equalsIgnoreCase("exit")) {
               if( !checkUnsavedChanges()) {
                continue;
            }
                System.out.println("Good Bye!");
                break;
            }
        if (command.equalsIgnoreCase("clear")) {
            clearCommands();
            continue;
        }
        commandHistory.add(command);     // Only save if it's not 'clear'
        processCommand(command);         // Process normal drawing commands
    }
    }
    
    private void clearCommands() {
        if (!checkUnsavedChanges()) {
            return; // Don't clear if user cancels
        }
        reset(); // clear canvas
        commandHistory.clear();
        unsavedChanges = false;
        System.out.println("Canvas cleared.");
    }


    private int validateNumber(String value, int min, int max) {
        try {
            int num = Integer.parseInt(value);
            if (num < min || num > max) {
                throw new IllegalArgumentException("Value must be between " + min + " and " + max);
            }
            return num;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number: " + value);
        }
    }
}

