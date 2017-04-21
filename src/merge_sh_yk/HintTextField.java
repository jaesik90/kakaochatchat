package merge_sh_yk;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class HintTextField extends JTextField implements PropertyChangeListener, DocumentListener, KeyListener, FocusListener{
	   boolean isEmpty= true;
	   String hint;
	   
	   public HintTextField(String hint) {
	      this.hint=hint;
	      this.addKeyListener(this);
	      this.addFocusListener(this);
	      updateState();
	      setText(hint);
	        setForeground(Color.GRAY);
	   }
	   
	   public void updateState() {
	      isEmpty = (this.getText().length() == 0) ? true : false;
	    }

	   @Override
	   public void focusGained(FocusEvent e) {
	      this.setCaretPosition(0);
	      if(this.getText().equals(hint)){
	         //�����ʻ���
	         this.getDocument().removeDocumentListener(this);
	         this.removePropertyChangeListener("foreground", this);
	         //�ؽ�Ʈ�ڽ�����
	         this.setText("");
	          this.setForeground(Color.BLACK);
	          //������ ���
	          this.getDocument().addDocumentListener(this);
	          this.addPropertyChangeListener("foreground", this);
	      }
	   }
	   
	   @Override
	   public void focusLost(FocusEvent e) {
	      if(isEmpty){
	         //������ ����� ������ ����
	         this.getDocument().removeDocumentListener(this);
	          this.removePropertyChangeListener("foreground", this);
	          //�ؽ�Ʈ �ڽ� ����
	            this.setText(hint);
	            this.setForeground(Color.GRAY);
	            //������ ����
	            this.getDocument().addDocumentListener(this);
	            this.addPropertyChangeListener("foreground", this);
	      }
	   }
	   
	   @Override
	   public void propertyChange(PropertyChangeEvent evt) {
	      updateState();   
	   }

	   @Override
	   public void changedUpdate(DocumentEvent e) {
	      updateState();   
	   }

	   @Override
	   public void insertUpdate(DocumentEvent e) {
	      updateState();
	   }

	   @Override
	   public void removeUpdate(DocumentEvent e) {
	      updateState();
	   }

	   @Override
	   public void keyPressed(KeyEvent e) {
	      if(this.getText().equals(hint)){
	         //�����ʻ���
	         this.getDocument().removeDocumentListener(this);
	         this.removePropertyChangeListener("foreground", this);
	         //�ؽ�Ʈ�ڽ�����
	         this.setText("");
	          this.setForeground(Color.BLACK);
	          //������ ���
	          this.getDocument().addDocumentListener(this);
	          this.addPropertyChangeListener("foreground", this);
	      }
	   }

	   @Override
	   public void keyReleased(KeyEvent e) {
	   }

	   @Override
	   public void keyTyped(KeyEvent e) {
	   }

	}
