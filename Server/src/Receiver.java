import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Jonnadula Eshwar Prithvi on 18-05-2017.
 */
public class Receiver extends JPanel  {

    public static JFrame mainFrame;
    private static String last = null;
    int na;
    static BufferedImage image;
    

    public Receiver() throws SQLException, IOException {
        prepareGUI();


        ServerSocket ss[]=new ServerSocket[16];
        InputStream is[]=new InputStream[16];
        Socket soc[] = new Socket[16];


        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("server.dat"), "Cp1252"));

        String line;
        int prob=0;
        while ((line = br.readLine()) != null) {
            String[] temp=line.split("#");
            if (temp[3].equals("1"))
            {
                ss[prob]=new ServerSocket(Integer.parseInt(temp[2]));
                soc[prob]=ss[prob].accept();
                is[prob]=soc[prob].getInputStream();
                prob++;
            }
        }
        br.close();


        br = new BufferedReader(new InputStreamReader(new FileInputStream("server.dat"), "Cp1252"));

        while ((line = br.readLine()) != null) {
            String[] temp=line.split("#");
            if (temp[3].equals("1"))
                na++;
        }
        br.close();

        int p;
        while(true)
        {



            if (na==1)
                p=1;
            else if (na>1&&na<=4)
                p=2;
            else if (na>4&&na<=9)
                p=3;
            else
                p=4;

            //System.out.println("while");

           // System.out.println("na"+na);
            for (int i=0;i<na;i++)
            {
                ObjectInputStream ois = null;
                if (is[i]!=null)
                {
                    try {
                        ois = new ObjectInputStream(is[i]);
                    } catch (IOException e) {
//                       System.out.println("Connection Reset of client "+(i+1));
                        ois=null;
                    }

                    int size = 0;
                    try {
                        assert ois != null;
                        size = Integer.parseInt(String.valueOf(ois.readObject()));
                    } catch (IOException | ClassNotFoundException |NullPointerException e) {
                        //e.printStackTrace();
//                        break;
                    }
                    ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
                    int sizeread = 0, bytesin = 0;
                    byte[] buffer = new byte[1024];
                    while (sizeread < size)
                    {
                        try {
                            bytesin = is[i].read(buffer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        sizeread += bytesin;
                        baos.write(buffer, 0, bytesin);
                    }
                    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                    int x = (int) dim.getWidth() - 60;
                    int y = (int) dim.getHeight() - 3 * 75;
                    try {
                        baos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                    try {
                        image = ImageIO.read(bais);
                        System.out.println(i+"  "+image);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    image = resize(image, x / p, y / p);
                    image = resize(image, SetScreens.frame[i].getWidth()-10, SetScreens.frame[i].getHeight()-30);
                    if (image!=null)
                    {
                        SetScreens.frame[i].setContentPane(this);
                        //System.out.println(SetScreens.frame[i].isSelected());
                        this.repaint();
                    }
                    else
                    {
//                        System.out.println("prob"+i);
                        SetScreens.frame[i].dispose();
                        try {
                            System.out.println("Closed "+i);
                            br = new BufferedReader(new InputStreamReader(new FileInputStream("server.dat"), "Cp1252"));
                            ArrayList<String> arrayList=new ArrayList<>();
                            while ((line = br.readLine()) != null) {
                                String[] temp=line.split("#");
                                if (temp[4].equals(""+i))
                                {
                                    arrayList.add(temp[0]+"#"+temp[1]+"#"+temp[2]+"#"+"0"+"#"+temp[4]);
                                }
                                else
                                    arrayList.add(line);
                            }
                            br.close();
                            FileWriter fwOb = new FileWriter("server.dat", false);
                            PrintWriter pwOb = new PrintWriter(fwOb, false);
                            pwOb.flush();
                            pwOb.close();
                            fwOb.close();
                            FileWriter fw = new FileWriter("server.dat");
                            PrintWriter bw = new PrintWriter(fw);
                            arrayList.forEach(bw::println);
                            bw.close();
                            fw.close();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            Configure.restartApplication();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }

    }



    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(image,0,0,null);
    }
    public static void main(String[] args) throws Exception {
        Receiver swingMenuDemo = new Receiver();


    }
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        if (img!=null)
        {
            Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
            BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = dimg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            return dimg;
        }
        else
            return null;

    }



    public static void prepareGUI()  {
        mainFrame = new JFrame("Java SWING Examples");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setSize((int)dim.getWidth(),(int)dim.getHeight());
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);


        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
//
        mainFrame.setVisible(true);
        final JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");



        JMenuItem configure = new JMenuItem("Configure");
        configure.setAccelerator(KeyStroke.getKeyStroke('J', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        //configure.setActionCommand("New");
        configure.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    Configure.main();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        fileMenu.add(configure);

        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        //configure.setActionCommand("New");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
            }
        });
        fileMenu.add(exit);
        JMenu record=new JMenu("Recorder");
        JMenuItem start=new JMenuItem("Open");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    File f = new File("lib/MonteScreenRecorder.jar");
                    if(f.exists() && !f.isDirectory())
                    {
                        String cmd = "java -jar lib/MonteScreenRecorder.jar";
                        Runtime.getRuntime().exec(cmd);
                    }
                    else
                    {
                        String cmd = "java -jar MonteScreenRecorder.jar";
                        Runtime.getRuntime().exec(cmd);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        record.add(start);
        JMenu wol=new JMenu("Wake on LAN");
        JMenuItem startwol=new JMenuItem("Start WOL");
        startwol.setAccelerator(KeyStroke.getKeyStroke('W', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        startwol.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream("server.dat"), "Cp1252"));
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }

                String line;
                ArrayList arrayList=new ArrayList();;
                try {
                    while ((line = br.readLine()) != null) {
                        String[] temp=line.split("#");
                        if (temp[3].equals("1"))
                        {
                            String wolip=temp[1];
                            String mac=temp[5];
                            boolean done=WOL.main(wolip,mac);
                            arrayList.add(done);

                        }
                    }
                    if (arrayList.contains(false))
                        JOptionPane.showMessageDialog(Receiver.mainFrame,"Failed to send Wake-on-LAN packet");
                    else
                        JOptionPane.showMessageDialog(Receiver.mainFrame,"Wake-on-LAN packet sent.");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    br.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        wol.add(startwol);

        menuBar.add(fileMenu);
        menuBar.add(record);
        menuBar.add(wol);
        mainFrame.setJMenuBar(menuBar);


        try {
            SetScreens.main(mainFrame);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        mainFrame.setVisible(true);
    }



}
