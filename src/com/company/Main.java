package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    private static void displayGUI(String msg) {
        JOptionPane.showConfirmDialog(null,
                getPanel(msg),
                "Виу-виу, тревога! ",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE);
    }

    private static JPanel getPanel(String msg) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(msg);
        ImageIcon image = null;
        try {
            image = new ImageIcon(ImageIO.read(new File("out/production/battery_spy_2/com/company/icon_1.png")));
        } catch(MalformedURLException mue) {
            mue.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        label.setIcon(image);
        panel.add(label);

        return panel;
    }

    public static void main(String[] args) {

        boolean flag10percent = false;
        boolean flag20percent = false;

        while (true) {
            String filePath = "/sys/class/power_supply/BAT0/capacity";
//            String filePath = "/home/alexandra/Documents/capacity_custom";
            String content = "100";

            try {
                content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(content);
            int power = (Integer.parseInt(content.trim()));

            if ( (power < 20) && (flag20percent == false)) {
                flag20percent = true;
                String msg = "\t   Эй, осторожно! Кажется батарея скоро разрядится! \n\t Осталось менее 20%...";
                displayGUI(msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if ((power < 10) && (flag10percent == false)) {
                flag10percent = true;
                String msg = "\t   Эй, осторожно! Кажется батарея скоро разрядится! \n\t Осталось менее 10%...";
                displayGUI(msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if ((power > 20)) {
                flag20percent = false;
                flag10percent = false;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
      }
    }


}
