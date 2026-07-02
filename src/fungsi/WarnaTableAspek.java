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
public class WarnaTableAspek extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(255,244,244));
        }else{
            component.setBackground(new Color(255,255,255));
        } 
        
        Object lvlObj = table.getValueAt(row, 2); // kolom lvl

        int lvl = 0;
        if (lvlObj != null) {
            try {
                lvl = Integer.parseInt(lvlObj.toString());
            } catch (Exception e) {
                lvl = -1;
            }
        }
        
        if (lvl == 0) {
            component.setForeground(Color.BLACK);
            component.setBackground(Color.YELLOW);
        }
        
        if (column == 4) {
            setHorizontalAlignment(LEFT);
        } else {
            setHorizontalAlignment(CENTER);
        }
        return component;
    }

}
