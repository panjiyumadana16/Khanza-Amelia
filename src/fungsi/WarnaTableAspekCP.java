/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Owner
 */
public class WarnaTableAspekCP extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(255,244,244));
        }else{
            component.setBackground(new Color(255,255,255));
        }
        
        String kosongi = String.valueOf(table.getValueAt(row, 2));
        Object col_wajib_isi = table.getValueAt(row, 3);
        
        if (col_wajib_isi != null) {
            String wajib_isi = col_wajib_isi.toString();
            
            if (wajib_isi.equals("-")){
                if (column >= 7) {
                    if (row % 2 == 1){
                        component.setBackground(Color.decode("#BBF0FC"));
                    }else{
                        component.setBackground(Color.decode("#E3FAFF"));
                    }
                }
            }else if (!wajib_isi.isBlank()){
                String[] col_wajib = wajib_isi.split(",");
                for(String colw : col_wajib){
                    if (column == (Integer.parseInt(colw) + 6)){
                        if (row % 2 == 1){
                            component.setBackground(Color.decode("#BBF0FC"));
                        }else{
                            component.setBackground(Color.decode("#E3FAFF"));
                        }
                    }
                }
            }
        }
        
        if (column >= 7 && kosongi.equals("1")) {
            component.setBackground(Color.decode("#F0F0F0"));
        }
        return component;
    }

}
