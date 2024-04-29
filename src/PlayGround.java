import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PlayGround {
    //this is actually the main class for the game that make the menu and ask the player what he/she wants to do?

    public static void menu(){
        JFrame menu = new JFrame("menu");
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton MainButton = new JButton("play new game");
        JButton InputButton = new JButton("play from a file");
        JButton GradesButton = new JButton("top records");
        MainButton.setBounds(10,10,150,20);
        InputButton.setBounds(10,40,150,20);
        GradesButton.setBounds(10,70,150,20);
        menu.add(MainButton);
        menu.add(InputButton);
        menu.add(GradesButton);
        MainButton.addActionListener(e -> {
            try {
                screen(null);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

        });
        InputButton.addActionListener(e -> {
            menu.setVisible(false);
            menu.remove(MainButton);
            menu.remove(InputButton);
            menu.remove(GradesButton);
            menu.setSize(500,150);
            JTextField usernameTF = new JTextField();
            JLabel usernameLbl = new JLabel("input the file address like : C://Users//Acer//IdeaProjects//candy crush//input.csv");
            JButton loginBtn = new JButton("INPUT");
            usernameTF.setBounds(10,40,450,20);
            usernameLbl.setBounds(10,10,500,20);
            loginBtn.setBounds(10,80,150,20);
            loginBtn.addActionListener(e1 -> {
                String address = usernameTF.getText();
                File file = new File(address);
                try {
                    screen(file);
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "the file doesn't exist!");
                }
                menu.setVisible(false);
            });
            menu.add(loginBtn);
            menu.add(usernameLbl);
            menu.add(usernameTF);
            menu.setVisible(true);

        });
        GradesButton.addActionListener(e -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(new File("TopScores.txt"));
                //this methode open the text file that holds the top scores
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        });
        menu.setLocation(600,300);
        menu.setSize(190,150);
        menu.setLayout(null);
        menu.setVisible(true);
    }

    public static void screen(File file) throws FileNotFoundException {
        //this methode makes the main frame, if the file which is passed to the function doesn't exist the game start
        //with a random page, otherwise it will apply the file that you passed through the menu
        JFrame frame;
        if (file == null){
            frame = new frame();
        }else {
            frame = new frame(file);
        }
        frame.setVisible(true);
    }

    public static void main(String [] args){
        menu();
    }
}