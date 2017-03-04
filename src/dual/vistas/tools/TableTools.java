/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual.vistas.tools;

import javax.swing.JTable;

/**
 *
 * @author Hector
 */
public class TableTools {
    
    public static float[] getRowAt(JTable table, int row) {
        int colNumber = table.getColumnCount();
        float[] result = new float[colNumber];
        for (int i = 0; i < colNumber; i++) {
            result[i] = Float.parseFloat((String)table.getModel().getValueAt(row, i));
        }
        return result;
    }
}
