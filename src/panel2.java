import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class panel2 extends JPanel {
    //this class represent the side panel that has the "score" on it
    public int score;
    public JFrame frame;

    public panel2(int score, JFrame frame){
        this.score = score;
        this.frame= frame;
        board();
    }

    public void board(){
        setLayout(null);
        setSize(new Dimension(135, 650));
        setLocation(650,0);
        setBounds(650,0,135,650);
    }

    public void Redraw(int score){
        this.score += score;
        Graphics g = this.getGraphics();
        draw(g);
    }

    public void DrawScore(Graphics g){
        //because the size of the drawstring methode wasn't fit to the other size of the panel
        //this methode will show the number pictures instead
        int n1 = score / 1000;
        int n2 = (score / 100) % 10;
        int n3 = (score / 10) % 10;
        int n4 = score % 10;
        ImageIcon p1 = new ImageIcon("pic//numbers//"+n1+".png");
        ImageIcon p2 = new ImageIcon("pic//numbers//"+n2+".png");
        ImageIcon p3 = new ImageIcon("pic//numbers//"+n3+".png");
        ImageIcon p4 = new ImageIcon("pic//numbers//"+n4+".png");
        g.drawImage(p1.getImage(), 20, 100, this);
        g.drawImage(p2.getImage(), 44, 100, this);
        g.drawImage(p3.getImage(), 68, 100, this);
        g.drawImage(p4.getImage(), 92, 100, this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        ImageIcon ii = new ImageIcon("pic//SidePanel.png");
        g.drawImage(ii.getImage(),0,0,this);
        DrawScore(g);
        JButton MainButton = new JButton("Go to menu");
        MainButton.setBounds(0,630,150,20);
        this.add(MainButton);
        MainButton.addActionListener(e -> {
            frame.setVisible(false);
            try {
                SetScore(this.score);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            PlayGround.menu();
        });
        Toolkit.getDefaultToolkit().sync();
    }

    public void SetScore(int score) throws IOException {
        File file = new File("TopScores.txt");
        Scanner scanner = new Scanner(file);
        int[] scores = new int[6];
        for (int i = 0; i < 5; i += 1){
            scores[i] = scanner.nextInt();
        }
        scores[5] = score;
        Arrays.sort(scores);
        PrintStream writer = new PrintStream(file);
        for (int i = 1; i <= 5; i += 1){
            writer.println(scores[i]+"\n");
        }
        writer.close();
    }
}
