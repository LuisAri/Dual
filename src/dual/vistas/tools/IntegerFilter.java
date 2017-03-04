/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dual.vistas.tools;

import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * Clase que permite validar que la entrada de datos ingresada en un textfield
 * sea solo valores enteros.
 * 
 * @author Hector
 */
public class IntegerFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string,
        AttributeSet attr) throws BadLocationException {

        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.insert(offset, string);

        if (test(sb.toString())) {
            super.insertString(fb, offset, string, attr);
        } else {
//             JOptionPane.showMessageDialog(null, 
//                "Valor de entrada invalido, ingrese solo numeros positivos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean test(String text) {
        try {
            if(text.contains("-")){
                throw new NumberFormatException();
            }
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text,
          AttributeSet attrs) throws BadLocationException {

       Document doc = fb.getDocument();
       StringBuilder sb = new StringBuilder();
       sb.append(doc.getText(0, doc.getLength()));
       sb.replace(offset, offset + length, text);

       if (test(sb.toString())) {
          super.replace(fb, offset, length, text, attrs);
       } else {
          
       }

    }

    @Override
    public void remove(FilterBypass fb, int offset, int length)
          throws BadLocationException {
       Document doc = fb.getDocument();
       StringBuilder sb = new StringBuilder();
       sb.append(doc.getText(0, doc.getLength()));
       sb.delete(offset, offset + length);

       if (test(sb.toString())) {
          super.remove(fb, offset, length);
       } else {
         
       }
    }
}
