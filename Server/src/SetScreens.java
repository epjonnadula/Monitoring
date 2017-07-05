import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.stream.Stream;

/**
 * Created by Jonnadula Eshwar Prithvi on 18-05-2017.
 */
public class SetScreens {
    public static JInternalFrame frame0,frame1,frame2,frame3,frame4,frame5,frame6,frame7,frame8,frame9,frame10,
                                 frame11,frame12,frame13,frame14,frame15;
    public static JInternalFrame frame[]=new JInternalFrame[]{frame0,frame1,frame2,frame3,frame4,frame5,frame6,frame7,frame8,frame9,frame10,
            frame11,frame12,frame13,frame14,frame15};

    public  static void main(JFrame mainFrame) throws Exception {

        JDesktopPane desktop=new JDesktopPane();
        mainFrame.setContentPane(desktop);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x=(int)dim.getWidth();
        int y=(int)dim.getHeight()-75;


        int na=0;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("server.dat"), "Cp1252"));

        String line;
        while ((line = br.readLine()) != null) {
            String[] temp=line.split("#");
            if (temp[3].equals("1"))
                na++;
        }
        br.close();

//        System.out.println(na+"na");

        if (na==1)
        {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("server.dat"), "Cp1252"));
            int prob=0;
            while ((line = br.readLine()) != null) {
                String[] temp=line.split("#");
                if (temp[3].equals("1"))
                {
                    frame[prob]=new JInternalFrame(temp[0]+"="+temp[1]+":"+temp[2]);
                    frame[prob].setLocation(0,0);
                    frame[prob].setSize(x,y);
                    frame[prob].setVisible(true);
                    frame[prob].setResizable(true);
//                    frame[rs.getInt(5)].setClosable(true);
                    desktop.add(frame[prob]);
                    prob++;
                }
            }
            br.close();


        }
        if(na>1&&na<=4)
        {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("server.dat"), "Cp1252"));
            int i=0,j=0;
            int prob=0;
            while ((line = br.readLine()) != null) {
                String[] temp=line.split("#");
                if (temp[3].equals("1"))
                {
                    frame[prob]=new JInternalFrame(temp[0]+"="+temp[1]+":"+temp[2]);
                    frame[prob].setLocation(j*x/2,i*y/2);
                    frame[prob].setSize(x/2,y/2);
                    frame[prob].setVisible(true);
                    frame[prob].setResizable(true);
//                    frame[rs.getInt(5)].setClosable(true);
                    desktop.add(frame[prob]);
                    prob++;
                    j++;
                }
                if (j==2)
                {
                    i++;
                    j=0;
                }
            }
            br.close();


        }
        if (na>4&&na<=9)
        {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("server.dat"), "Cp1252"));
            int i=0,j=0;
            int prob=0;
            while ((line = br.readLine()) != null) {
                String[] temp=line.split("#");
                if (temp[3].equals("1"))
                {
                    frame[prob]=new JInternalFrame(temp[0]+"="+temp[1]+":"+temp[2]);
                    frame[prob].setLocation(j*x/3,i*y/3);
                    frame[prob].setSize(x/3,y/3);
                    frame[prob].setVisible(true);
                    frame[prob].setResizable(true);
//                    frame[rs.getInt(5)].setClosable(true);
                    desktop.add(frame[prob]);
                    prob++;
                    j++;
                }
                if (j==3)
                {
                    i++;
                    j=0;
                }
            }
            br.close();
        }
        if (na>9&&na<=16)
        {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("server.dat"), "Cp1252"));
            int i=0,j=0;
            int prob=0;
            while ((line = br.readLine()) != null) {
                String[] temp=line.split("#");
                if (temp[3].equals("1"))
                {
                    frame[prob]=new JInternalFrame(temp[0]+"="+temp[1]+":"+temp[2]);
                    frame[prob].setLocation(j*x/4,i*y/4);
                    frame[prob].setSize(x/4,y/4);
                    frame[prob].setVisible(true);
                    frame[prob].setResizable(true);
//                    frame[rs.getInt(5)].setClosable(true);
                    desktop.add(frame[prob]);
                    j++;
                    prob++;
                }
                if (j==4)
                {
                    i++;
                    j=0;
                }
            }
            br.close();
        }

    }
}
