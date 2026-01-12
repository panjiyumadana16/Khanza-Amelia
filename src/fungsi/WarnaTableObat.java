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
public class WarnaTableObat extends DefaultTableCellRenderer {
    public int kolom;
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(255,244,244));
        }else{
            component.setBackground(new Color(255,255,255));
        } 
        if (column == kolom){
            component.setBackground(new Color(215,215,255));
            component.setForeground(new Color(70,70,70));
        }else{
            component.setForeground(new Color(70,70,70));
        }
        
        if (!table.getValueAt(row, kolom).equals("")){
            component.setBackground(new Color(205,255,205));
        }
        
        if (isSelected == true || hasFocus == true){
            component.setBackground(new Color(215,215,255));
            component.setForeground(new Color(255,0,0));
        }
        return component;
    }

}
