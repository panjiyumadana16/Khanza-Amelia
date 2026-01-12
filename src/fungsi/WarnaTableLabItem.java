
package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author acer
 */
public class WarnaTableLabItem extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(255,244,244));
        }else{
            component.setBackground(new Color(255,255,255));
        }
        component.setForeground(new Color(50,50,50));
        
        Object cellValue = table.getValueAt(row, 11);
        if (cellValue == null || cellValue.toString().equals("")) {
            component.setBackground(new Color(200, 0, 0));
            component.setForeground(new Color(255, 230, 230));
        }
        return component;
    }
}
