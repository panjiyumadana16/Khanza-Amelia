/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fungsi;

/**
 *
 * @author acer
 */

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class WarnaTableKasirRanap extends DefaultTableCellRenderer{
    private BackgroundMusic music;
    private int i, rencanaPulang=0;
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(255,244,244));
        }else{
            component.setBackground(new Color(255,255,255));
        } 
        if(table.getValueAt(row,16).toString().equals("Rencana Pulang")){
            component.setBackground(new Color(135,206,250));
            component.setForeground(new Color(50,50,50));
//            rencanaPulang=0;
//            for(i=0;i<table.getRowCount();i++){
//                if(table.getValueAt(i,16).toString().equals("Rencana Pulang")){
//                    rencanaPulang++;
//                }
//            }
//            if(rencanaPulang>0){
//                try {
//                    music = new BackgroundMusic("./suara/alarm.mp3");
//                    music.start();
//                } catch (Exception ex) {
//                    System.out.println(ex);
//                }
//            }
        }else if(table.getValueAt(row,16).toString().equals("Kasir")){
            component.setBackground(new Color(135,206,250));
            component.setForeground(new Color(50,50,50));
        }
        if(!table.getValueAt(row,22).toString().equals("0") && !table.getValueAt(row,22).toString().equals("Belum")){
            component.setBackground(new Color(229,204,255));
            component.setForeground(new Color(50,50,50));
        }
        return component;
    }
}
