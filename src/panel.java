import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class panel extends JPanel {
    public candy[][] array;
    public JPanel panel2;
    public JFrame frame;
    // this is the main panel of the game that shows the candies

    public panel(candy[][] array, JPanel panel, JFrame frame){
        this.array = array;
        this.panel2 = panel;
        this.frame = frame;
        board();
    }

    public void board(){
        setLayout(null);
        setSize(new Dimension(650, 650));
    }

    public void ChangeArray(candy[][] array){
        //this methode changes the candies array of the panel with one you passed to it and show it in panel
        this.array = array;
        Graphics g = super.getGraphics();
        g.setColor(Color.PINK);
        g.fillRect(0,0, 650,650);
        for (int i = 0; i < 10; i += 1){
            for (int j = 0; j < 10; j += 1){
                g.drawImage(array[i][j].image, array[i][j].X, array[i][j].Y, this);
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }

    public void fill(int x1, int y1, int x2, int y2){
        //because we don't want to make the game slow and neither any kind of redundancy this methode makes the
        //wanted part of the panel clear, it actually fills a pink rectangle(same as background) in the area
        Graphics g = super.getGraphics();
        g.setColor(Color.PINK);
        int x = Math.min(x1, x2) * 65;
        int y = Math.min(y1, y2) * 65;
        int x3 = Math.abs(x2 - x1) * 65;
        int y3 = Math.abs(y2 - y1) * 65;
        g.fillRect(x ,y, x3,y3);
    }

    public void replacement(candy first, candy second){
        //this methode makes an animation for two candies that their place are changing
        int i1 = first.x;
        int i2 = second.x;
        int j1 = first.y;
        int j2 = second.y;
        for (int k = 0; k <= 65; k += 5) {
            int i3 = k*(i1 - i2);
            int j3 = k*(j1 - j2);
            Graphics g = super.getGraphics();
            g.setColor(Color.PINK);
            fill(i1,j1, i1 + 1, j1 + 1);
            fill(i2,j2, i2 + 1, j2 + 1);
            for (int i = 0; i < 10; i += 1) {
                for (int j = 0; j < 10; j += 1) {
                    if ((i == i1 && j == j1) || (j == j2 && i == i2)) {
                        if ((i == i1) && (j == j1)) {
                            g.drawImage(first.image, i1 * 65 - i3, j1 * 65 - j3, this);
                        } else {
                            g.drawImage(second.image, i2 * 65 + i3, j2 * 65 + j3, this);
                        }
                    } else {
                        g.drawImage(array[i][j].image, array[i][j].X, array[i][j].Y, this);
                    }
                }
            }
            Toolkit.getDefaultToolkit().sync();
            try {
                Thread.sleep(30);
            }
            catch(InterruptedException ex) {
                Thread.currentThread().interrupt();

            }
        }
    }

    public void explosion(ArrayList<candy[][]> list, int[][] special, boolean animation){
        /*
        "list" is a list of those candy which are going to be exploded
        "special" is array of those candies type
        "animation" is a boolean that tells that we want to see what is happening or not it is because if there was
        a random panel there might be an explodable part in it, and we don't want to see what will happen to them
        after all this methode remove those candies that can't be placed in the panel anymore
        */
        ArrayList<candy> explode = new ArrayList<candy>();
        Graphics g = super.getGraphics();
        for (int n = 0; n < list.size(); n += 1){
            for (int i = 0 ; i < list.get(n).length; i += 1){
                for (int j = 0; j < list.get(n)[0].length; j += 1){
                    explode.add(list.get(n)[i][j]);
                }
            }
        }
        for (int n = 0; n < explode.size(); n += 1){
            if (special[explode.get(n).x][explode.get(n).y] == 0){
                special[explode.get(n).x][explode.get(n).y] = 1;
            }
        }
        special(explode);
        //if there was a special candy in the explosion list, the "special" methode add the following candies to the list
        candy[][] array = ArrayMaker(explode, special);
        //"ArrayMaker" will replace the empty places when the explodable candies are gone
        if (animation) {
            BombAnimation(g, explode);
            ExplodeAnimation(g, array);
            ((panel2) panel2).Redraw(score(explode));
            /*if we want animation this section will apply that into the panel
            "BombAnimation" will appear a bomb on those candies
            "ExplodeAnimation" will refill the empty places, it will look like the "gravity on" mode
            "Redraw" will show the new score that player get after the explosion
            */
        }
        for (int i = 0 ; i < 10; i += 1){
            for (int j = 0; j < 10; j += 1){
                array[i][j].x = i;
                array[i][j].X = i*65;
                array[i][j].y = j;
                array[i][j].Y = j*65;
                this.array[i][j] = array[i][j];
            }
        }
        if(((panel2) panel2).score >= 1500){
            //whenever the player reach the score of 1500 the game will ended
            System.out.println("congratulation you have won by the score of : " + ((panel2) panel2).score);
            try {
                ((panel2) panel2).SetScore(((panel2) panel2).score);
                //"SetScore" will check if this gme was a record for the player or not
            }catch (Exception ignored){}
            System.exit(0);
        }
    }

    public void special(ArrayList<candy> explode){
        for (int n = 0; n < explode.size(); n += 1){
            candy candy = explode.get(n);
            ImageIcon ii = new ImageIcon("pic//blank.png");
            candy.image = ii.getImage();
            if (!candy.type.equals("normal")) {
                if (candy.type.equals("horizontal")) {
                    for (int k = 0; k < 10; k += 1){
                        if(!explode.contains(array[candy.x][k])){
                            explode.add(array[candy.x][k]);
                        }
                    }
                } else if (candy.type.equals("vertical")) {
                    for (int k = 0; k < 10; k += 1){
                        if(!explode.contains(array[k][candy.y])){
                            explode.add(array[k][candy.y]);
                        }
                    }
                } else {
                    for (int i = candy.x - 2; i < candy.x + 3; i += 1){
                        for (int j = candy.y - 2; j < candy.y + 3; j += 1){
                            if(i >= 0 && i < 10 && j >= 0 && j < 10){
                                if(!explode.contains(array[i][j])){
                                    explode.add(array[i][j]);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void bomb(Graphics g, int i, int j, int k) {
        g.drawImage(array[i][j].image, array[i][j].X, array[i][j].Y, this);
        ImageIcon ii = new ImageIcon("pic//bomb//bomb" + k + ".png");
        if ( k != 8) {
            g.drawImage(ii.getImage(), array[i][j].X + 15, array[i][j].Y+15, this);
        }else {
            g.drawImage(ii.getImage(), array[i][j].X, array[i][j].Y, this);
            if(array[i][j].type.equals("bomb")){
                bomb(i,j, 1);
            }else if(array[i][j].type.equals("vertical")){
                bomb(i,j,2);
            }else if(array[i][j].type.equals("horizontal")){
                bomb(i,j,3);
            }
        }
    }

    public void BombAnimation(Graphics g, ArrayList<candy> explode){
        for (int k = 1; k < 9; k += 1){
            for (int n = 0; n < explode.size(); n += 1){
                bomb(g,explode.get(n).x, explode.get(n).y, k);
            }
            Toolkit.getDefaultToolkit().sync();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        for (int n = 0; n < explode.size(); n += 1){
            ImageIcon ii = new ImageIcon("pic//blank.png");
            explode.get(n).image = ii.getImage();
        }
        ChangeArray(array);
    }

    public void bomb(int i, int j, int k){
        Graphics g = super.getGraphics();
        ImageIcon ii;
        if(k == 1) {
            int[] numbers = {180,140,110,80,45,5};
            for (int n = 6; n > 0; n -= 1) {
                ii = new ImageIcon("pic//bomb//normal" + n + ".png");
                g.drawImage(ii.getImage(), array[i][j].X - numbers[n - 1], array[i][j].Y - numbers[n - 1], this);
                Toolkit.getDefaultToolkit().sync();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        if(k == 2) {
            int[] numbers ={35, 50, 220, 370, 600};
            for (int n = 1; n < 6; n += 1) {
                ii = new ImageIcon("pic//bomb//horizontal" + n + ".png");
                g.drawImage(ii.getImage(), array[i][j].X - numbers[n - 1], array[i][j].Y, this);
                Toolkit.getDefaultToolkit().sync();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        if(k == 3) {
            int[] numbers = {35, 50, 220, 370, 600};
            for (int n = 1; n < 6; n += 1) {
                ii = new ImageIcon("pic//bomb//vertical" + n + ".png");
                g.drawImage(ii.getImage(), array[i][j].X, array[i][j].Y - numbers[n - 1], this);
                Toolkit.getDefaultToolkit().sync();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void ExplodeAnimation(Graphics g, candy[][] array){
        for (int n = 0; n <= 65; n += 5){
            for (int i = 0 ; i < 10; i += 1){
                boolean flag = true;
                for (int j = 9; j >= 0; j -= 1){
                    if(!(array[i][j].Y == j*65)){
                        if (flag){
                            flag = false;
                            fill(i,0, i+1, j+1);
                        }
                        int k = ((j*65 - array[i][j].Y) / 65) * n;
                        g.drawImage(array[i][j].image, array[i][j].X, array[i][j].Y + k , this);
                    }
                }
            }
            Toolkit.getDefaultToolkit().sync();
            try {
                Thread.sleep(30);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public candy[][] ArrayMaker( ArrayList<candy> explode, int[][] special){
        candy[][] array = new candy[10][20];
        for (int i = 0; i < 10; i += 1) {
            for (int j = 0; j < 10; j += 1) {
                String[] color = {"blue", "red", "green", "yellow"};
                int rand = (int) (Math.random() * 4);
                array[i][j] = new normal(i, j - 10, color[rand]);
            }
            for (int j = 10; j < 20; j += 1){
                array[i][j] = this.array[i][j-10];
            }
        }
        for (int i1 = 0; i1 < 10; i1 += 1){
            int count = 0;
            for (int j1 = 9; j1 >= 0; j1 -= 1){
                int number = special[i1][j1];
                if (number != 0){
                    count += 1;
                    if(number == 2){
                        array[i1][10 - count] = new vertical(array[i1][10 - count].x, array[i1][10 - count].y, array[i1][10 - count].color);
                    }else if(number == 3){
                        array[i1][10 - count] = new horizontal(array[i1][10 - count].x, array[i1][10 - count].y, array[i1][10 - count].color);
                    }else if(number == 4){
                        array[i1][10 - count] = new bomb(array[i1][10 - count].x, array[i1][10 - count].y, array[i1][10 - count].color);
                    }
                }
            }
        }
    int[][] numbers = new int[10][20];
        for (int i = 0; i < 10; i += 1){
            for (int j = 0; j < 20; j += 1){
                if (explode.contains(array[i][j])){
                    numbers[i][j] = 0;
                }else {
                    numbers[i][j] = 1;
                }
            }
        }
        for (int i = 0; i < 10; i += 1){
            for (int j = 19; j >= 10; j -= 1){
                if(numbers[i][j] == 0){
                    for (int k = 1; k <= 10; k += 1){
                        if (numbers[i][j-k] != 0){
                            array[i][j] = array[i][j-k];
                            array[i][j-k] = null;
                            numbers[i][j] = 1;
                            numbers[i][j-k] = 0;
                            break;
                        }
                    }
                }
            }
        }
        candy[][] array2 = new candy[10][10];
        for (int i = 0; i < 10; i += 1){
            for (int j = 0; j < 10; j += 1){
                array2[i][j] = array[i][j+10];
            }
        }
        return array2;
    }

    public int score(ArrayList<candy> explode){
        int count = 0;
        for (int n = 0; n < explode.size(); n += 1){
            candy candy = explode.get(n);
            if(candy.type.equals("normal")){
                count += 5;
            }else if(candy.type.equals("vertical") || candy.type.equals("horizontal")){
                count += 10;
            }else {
                count += 15;
            }
        }
        return count;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        g.setColor(Color.PINK);
        g.fillRect(0,0,650,650);
        for (int i = 0; i < 10; i += 1){
            for (int j = 0; j < 10; j += 1){
                g.drawImage(array[i][j].image, array[i][j].X, array[i][j].Y, this);
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }
}
