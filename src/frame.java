import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class frame extends JFrame {
    public int score = 0;

    public frame(){
        FrameMaker();
    }

    public frame(File file) throws FileNotFoundException {
        FrameMaker2(file);
    }

    public void FrameMaker(){
        candy[][] array = make10();
        Frame(array);
    }

    public void FrameMaker2(File file) throws FileNotFoundException {
        candy[][] array = input10(file);
        Frame(array);
    }

    public void Frame(candy[][] array){
        // "panel" is the main panel that shows the candies
        // "panel2" is the side panel that has "return to menu" button and shows the current score
        JPanel panel2 = new panel2(score, this);
        JPanel panel = new panel(array, panel2, this);
        // "logic" class is the class that holds the actual logic methods of the game
        panel.addMouseListener(new logic(this, panel, array).mouseListener);
        // "check" methode looks if there is any 3,4,5 candies of the same color next to each other
        while (!logic.check(array, false)){
            array = ((panel) panel).array;
        }
        setLayout(null);
        add(panel2);
        add(panel);
        setSize(new Dimension(800,690));
        setLocation(300,20);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public candy[][] make10(){
        candy[][] array = new candy[10][10];
        for (int i = 0; i < 10; i += 1){
            for (int j =0; j < 10; j += 1){
                String[] color = {"blue", "red", "green", "yellow"};
                int rand = (int) (Math.random() * 4);
                array[i][j] = new normal(i,j,color[rand]);
            }
        }
        return array;
    }

    public candy[][] input10(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        score = scanner.nextInt();
        candy[][] array = new candy[10][10];
        try {
            for (int i = 0; i < 10; i += 1){
                String string = scanner.next();
                Scanner scanner1 = new Scanner(string);
                scanner1.useDelimiter(",");
                for (int j = 0 ; j < 10; j += 1){
                    Scanner scanner2 = new Scanner(scanner1.next());
                    String candy = scanner2.next();
                    String type = candy.substring(0, 2);
                    String color = candy.substring(2, 3);
                    type = switch (type) {
                        case "RC" -> "bomb";
                        case "LR" -> "horizontal";
                        case "LC" -> " vertical";
                        default -> "normal";
                    };
                    color = switch (color) {
                        case "B" -> "blue";
                        case "R" -> "red";
                        case "G" -> "green";
                        default -> "yellow";
                    };
                    array[j][i] = new candy(j,i,color,type);
                }
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "the file is corrupted!");
        }
        return array;
    }

    //the "make10" methode makes a random panel of candies, and "input10" makes the panel you asked before
}
