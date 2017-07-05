import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Configure extends JDialog {
    public JPanel contentPane;
    public JButton buttonOK, buttonCancel;

    public JTextField name1, name2,name3,name4,name5,name6,name7,name8,name9,name10,name11,name12,name13,name14,name15, name16;
    public JTextField ip1,ip2,ip3,ip4,ip5,ip6,ip7,ip8,ip9,ip10,ip11,ip12,ip13,ip14,ip15,ip16;
    public JTextField port1,port2,port3,port4,port5,port6,port7,port8,port9,port10,port11,port12,port13,port14,port15,port16;
    public JRadioButton on1,on2,on3,on4,on5,on6,on7,on8,on9,on10,on11,on12,on13,on14,on15,on16;
    public JTextField mac1, mac2, mac3, mac4, mac5, mac6, mac7, mac8, mac9, mac10, mac11, mac12, mac13, mac14, mac15, mac16;

    static int NOON;
    static int i=0;

    JTextField name[]=new JTextField[]{name1,name2,name3,name4,name5,name6,name7,name8,
            name9,name10,name11,name12,name13,name14,name15,name16};
    JTextField ip[]=new JTextField[]{ip1,ip2,ip3,ip4,ip5,ip6,ip7,ip8,
            ip9,ip10,ip11,ip12,ip13,ip14,ip15,ip16};
    JTextField port[]=new JTextField[]{port1,port2,port3,port4,port5,port6,port7,port8,
            port9,port10,port11,port12,port13,port14,port15,port16};
    JRadioButton on[]=new JRadioButton[]{on1,on2,on3,on4,on5,on6,on7,on8,
            on9,on10,on11,on12,on13,on14,on15,on16};
    JTextField mac[]=new JTextField[]{mac1, mac2, mac3, mac4, mac5, mac6, mac7, mac8, mac9, mac10, mac11, mac12, mac13, mac14, mac15, mac16};

    public Configure() {

        setContentPane(contentPane);
        setTitle("Configuration");
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


        try {
            displaySettings();
        } catch (Exception e1) {
            e1.printStackTrace();
        }


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

    }

    public void onOK() throws Exception {
// add your code here
        saveChanges();
        try {
            SetScreens.main(Receiver.mainFrame);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dispose();
        restartApplication();

    }

    public static void restartApplication() throws URISyntaxException, IOException {
        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        final File currentJar = new File(Receiver.class.getProtectionDomain().getCodeSource().getLocation().toURI());

  /* is it a jar file? */
        if(!currentJar.getName().endsWith(".jar"))
            return;

  /* Build command: java -jar application.jar */
        final ArrayList<String> command = new ArrayList<String>();
        command.add(javaBin);
        command.add("-jar");
        command.add(currentJar.getPath());

        final ProcessBuilder builder = new ProcessBuilder(command);
        builder.start();
        System.exit(0);
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main() throws ClassNotFoundException, SQLException {

        Configure dialog = new Configure();

        dialog.pack();
        dialog.setVisible(true);

//        Configure.c.close();
//        Configure.stmt.close();


    }
    public void displaySettings() throws Exception
    {

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("server.dat"), "Cp1252"));

        String line;
        while ((line = br.readLine()) != null) {
            String [] temp=line.split("#");
            name[Integer.parseInt(temp[4])].setText(temp[0]);
            ip[Integer.parseInt(temp[4])].setText(temp[1]);
            port[Integer.parseInt(temp[4])].setText(temp[2]);
            if (temp[3].equals("1"))
                on[Integer.parseInt(temp[4])].setSelected(true);
            else
                on[Integer.parseInt(temp[4])].setSelected(false);
            mac[Integer.parseInt(temp[4])].setText(temp[5]);
        }
        br.close();



    }
    private void saveChanges() throws Exception{




        FileWriter fwOb = new FileWriter("server.dat", false);
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
        FileWriter fw = new FileWriter("server.dat");
        PrintWriter bw = new PrintWriter(fw);
        for (int i=0;i<16;i++)
        {
            String temp_name=name[i].getText();
            String temp_ip=ip[i].getText();
            int temp_port= Integer.parseInt(port[i].getText());
            int temp_on;
            if (on[i].isSelected())
                temp_on=1;
            else
                temp_on=0;
            String temp_mac=mac[i].getText();

            bw.println(temp_name+"#"+temp_ip+"#"+temp_port+"#"+temp_on+"#"+i+"#"+temp_mac);
            //System.out.println(temp_name+"#"+temp_ip+"#"+temp_port+"#"+temp_on+"#"+i);
        }

        bw.close();
        fw.close();


        Configure.i=0;



    }



}
