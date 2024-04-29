import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

class integer{
    public static int press = 0;
    public static int x = 0;
    public static int y =0;
}

public class logic {
    public static JFrame frame;
    public static JPanel panel;
    public static candy[][] array;

    public logic(JFrame frame, JPanel panel, candy[][] array){
        logic.frame = frame;
        logic.panel = panel;
        logic.array = array;
    }

    public static MouseListener mouseListener = new MouseListener() {
        @Override public void mouseClicked(MouseEvent e) {}
        @Override public void mousePressed(MouseEvent e) {
            if(integer.press == 0) {
                int x = e.getX();
                int y = e.getY();
                x = x/ 65;
                y = y/ 65;
                integer.press = 1;
                integer.x = x;
                integer.y = y;
                Graphics g = frame.getGraphics();
                g.drawRect(x * 65 + 8, y * 65 + 31, 65, 65);
            }else{
                int x1 = e.getX();
                int y1 = e.getY();
                x1 = x1/ 65;
                y1 = y1/ 65;
                int x2 = integer.x;
                int y2 = integer.y;
                candy[][] array2;
                Graphics g = frame.getGraphics();
                g.setColor(Color.PINK);
                g.drawRect(x2 * 65 + 8, y2* 65 + 31, 65, 65);
                if ((Math.abs(x2 - x1) == 1 && y1 - y2 == 0) || (Math.abs(y1 - y2) == 1 && x1 - x2 == 0)){
                    ((panel) panel).replacement(array[x1][y1], array[x2][y2]);
                    array2 = change(array, x1, y1, x2, y2);
                    boolean flag = true;
                    while (!check(array2, true)){
                        flag = false;
                        ((panel) panel).ChangeArray(array2);
                        array2 = ((panel) panel).array;
                    }
                    if (flag){
                        ((panel) panel).replacement(array[x1][y1], array[x2][y2]);
                        array2 = change(array2, x1, y1, x2, y2);
                        check(array2, true);
                        ((panel) panel).ChangeArray(array2);
                    }
                }
                integer.press = 0;
            }
        }
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {}
    };

    public static candy[][] change(candy[][] array, int x1, int y1, int x2, int y2){
        candy[][] array2 = array;
        candy temp = array2[x1][y1];
        array2[x1][y1] = array2[x2][y2];
        array2[x2][y2] = temp;
        array2[x2][y2].x = x2;
        array2[x2][y2].X = x2*65;
        array2[x1][y1].x = x1;
        array2[x1][y1].X = x1*65;
        array2[x2][y2].y = y2;
        array2[x2][y2].Y = y2*65;
        array2[x1][y1].y = y1;
        array2[x1][y1].Y = y1*65;
        return array2;
    }

    public static boolean check(candy[][] main_array, boolean animation) {
        ArrayList<candy[][]> list = new ArrayList<candy[][]>();
        String[][] array = new String[10][10];
        for (int i = 0; i < 10 ; i += 1){
            for (int j = 0; j < 10; j += 1){
                array[i][j] = main_array[i][j].color;
            }
        }
        int[][] special = new int[10][10];
        for (int i = 0; i < 10 ; i += 1){
            for (int j = 0; j < 10; j += 1){
                special[i][j] =0;
            }
        }
        for (int i = 0; i < 10 ; i += 1){
            for (int j = 0; j < 10; j += 1){
                String color = array[i][j];
                for (int m = 5; m > 2; m -= 1){
                    if ( i <= 10-m) {
                        boolean flag = true;
                        for (int k = 1; k < m; k += 1) {
                            if(!color.equals(array[i+k][j])){
                                flag = false;
                                break;
                            }
                        }
                        if (flag){
                            candy[][] candies = new candy[m][1];
                            for (int k = 0; k < m; k += 1) {
                                candies[k][0] = main_array[i+k][j];
                            }
                            list.add(candies);
                            if (m == 5){
                                special[i][j] = 4;
                                special[i+1][j] = 5;
                            }else if(m == 4 && special[i][j] == 0){
                                special[i][j] = 2;
                            }
                        }
                    }if ( j <= 10-m) {
                        boolean flag = true;
                        for (int k = 1; k < m; k += 1) {
                            if(!color.equals(array[i][j+k])){
                                flag = false;
                                break;
                            }
                        }
                        if (flag){
                            candy[][] candies = new candy[1][m];
                            for (int k = 0; k < m; k += 1) {
                                candies[0][k] = main_array[i][j+k];
                            }
                            list.add(candies);
                            if (m == 5){
                                special[i][j+m-2] = 5;
                                special[i][j+m-1] = 4;
                            }else if(m == 4 && special[i][j] == 0){
                                special[i][j+m-1] = 3;
                            }
                        }
                    }
                }
            }
        }
        if(list.isEmpty()){
            return true;
        }else {
            ((panel) panel).explosion(list, special, animation);
            return false;
        }
    }
}