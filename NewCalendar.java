import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.awt.Window.Type;

public class NewCalendar {

  private JFrame frame;
  private JPanel panel_1;
  private JTable table;
  private JScrollPane scrollPane;
  private JTextField textField;
  private JTextField textField_1;
  private JButton btnNewButton_1;
  private JButton button_1;
  private JButton button;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          NewCalendar window = new NewCalendar();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
  public NewCalendar() {
    initialize();
  }
  private void initialize() {
    Toolkit tool = Toolkit.getDefaultToolkit();
    Dimension scrsize = tool.getScreenSize();
    int scrw = (int) scrsize.getWidth(), scrh = (int) scrsize.getHeight(), fraw = 192 * 3, frah = 108 * 3;
    frame = new JFrame("\u65E5\u5386");
    frame.setResizable(false);
    frame.setType(Type.UTILITY);
    frame.setBounds((scrw - fraw) / 2, (scrh - frah) / 2, fraw, frah);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    panel_1 = new JPanel();
    panel_1.setBounds(0, 100, fraw, frah - 100);
    panel_1.setLayout(null);
    frame.getContentPane().add(panel_1);
    String[] columnNames = new String[] { "\u661F\u671F\u4E00", "\u661F\u671F\u4E8C", "\u661F\u671F\u4E09", "\u661F\u671F\u56DB", "\u661F\u671F\u4E94", "\u661F\u671F\u516D", "\u661F\u671F\u65E5" };
    Object[][] data = new StringBuffer[6][7];
    scrollPane = new JScrollPane();
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setEnabled(false);
    scrollPane.setBounds(0, 0, fraw - 5, 210);
    panel_1.add(scrollPane);
    table = new JTable(data, columnNames); // \u6570\u636E,\u8868\u5934
    table.setEnabled(false);
    table.getTableHeader().setVisible(true);
    table.setBounds(0, 100, fraw, frah - 100);
    table.setRowHeight((frah - 100) / 7 - 5);
    table.setIntercellSpacing(new Dimension(3, 3));
    table.setShowVerticalLines(true);
    table.setShowHorizontalLines(true);
    table.setShowGrid(true);
    table.setBorder(new LineBorder(new Color(128, 128, 128), 3, true));
    scrollPane.setViewportView(table);
    Calendar date = Calendar.getInstance();
    int year = date.get(Calendar.YEAR), month = date.get(Calendar.MONTH) + 1, day = date.get(Calendar.DAY_OF_MONTH);
    date.set(year, month - 1, 1);
    int h = 0, l = date.get(Calendar.DAY_OF_WEEK) - 2;
    if (l == -1) {
      l = 6;
    }
    for (int i = 1; i <= date.getActualMaximum(Calendar.DATE); i++) {
      if (l == 7) {
        l = 0;
        h++;
      }
      if (i == day) {
        data[h][l] = new StringBuffer("  \u3010" + String.valueOf(i) + "\u3011");
      } else {
        data[h][l] = new StringBuffer(String.valueOf(i));
      }
      l++;
    }
    textField = new JTextField(); // \u5E74
    textField.setEditable(false);
    textField.setFont(new Font("Consolas", Font.BOLD, 20));
    textField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
    textField.setHorizontalAlignment(SwingConstants.CENTER);
    textField.setText(String.valueOf(year));
    textField.setBounds(110, 40, 60, 35);
    frame.getContentPane().add(textField);
    textField.setColumns(10);
    textField.setDocument(new PlainDocument() {
      /**
       *
       */
      private static final long serialVersionUID = -3044226712178089282L;
      @Override
      public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str != null && str.length() > 0) {
          StringBuilder sb = new StringBuilder();
          for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) { // filter
              sb.append(str.charAt(i));
            }
          }
          str = sb.toString();
        }
        if (str.equals("")) {
          str = String.valueOf(year);
        }
        super.insertString(offs, str, a);
      }
    });
    textField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == '\n') {
          if (!(Integer.valueOf(textField.getText()) <= 20000 && Integer.valueOf(textField.getText()) >= 1)) {
            textField.setText(String.valueOf(year));
          }
        }
      }
    });
    textField.setText(String.valueOf(year));
    textField.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void removeUpdate(DocumentEvent e) {
      }
      @Override
      public void insertUpdate(DocumentEvent e) {
        for (int i = 0; i < 6; i++) {
          for (int j = 0; j < 7; j++) {
            data[i][j] = new StringBuffer("");
          }
        }
        table.updateUI();
        String yearstr = null, monthstr = textField_1.getText();
        try {
          yearstr = e.getDocument().getText(e.getDocument().getStartPosition().getOffset(),
                                            e.getDocument().getLength());
        } catch (BadLocationException ex) {
          ex.printStackTrace();
        }
        int toyear = Integer.valueOf(yearstr), tomonth = Integer.valueOf(monthstr);
        Calendar today1 = Calendar.getInstance();
        today1.set(toyear, tomonth - 1, 1);
        int hang = 0, lie = today1.get(Calendar.DAY_OF_WEEK) - 2;
        if (lie == -1) {
          lie = 6;
        }
        for (int i = 1; i <= today1.getActualMaximum(Calendar.DATE); i++) {
          if (lie == 7) {
            lie = 0;
            hang++;
          }
          if (i == day && textField_1.getText().equals(String.valueOf(month))
              && textField.getText().equals(String.valueOf(year))) {
            data[hang][lie] = new StringBuffer("\u3010" + String.valueOf(i) + "\u3011");
          } else {
            data[hang][lie] = new StringBuffer(String.valueOf(i));
          }
          lie++;
        }
        table.updateUI();
      }
      @Override
      public void changedUpdate(DocumentEvent e) {
      }
    });
    textField_1 = new JTextField(); // \u6708
    textField_1.setFont(new Font("Consolas", Font.BOLD, 20));
    textField_1.setEditable(false);
    textField_1.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
    textField_1.setDocument(new PlainDocument() {
      /**
       *
       */
      private static final long serialVersionUID = -3830974916066069519L;
      @Override
      public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str != null && str.length() > 0) {
          StringBuilder sb = new StringBuilder();
          for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) { // filter
              sb.append(str.charAt(i));
            }
          }
          str = sb.toString();
        }
        super.insertString(offs, str, a);
      }
    });
    textField_1.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == '\n') {
          if (!(Integer.valueOf(textField_1.getText()) <= 12
                && Integer.valueOf(textField_1.getText()) >= 1)) {
            textField_1.setText(String.valueOf(month));
          }
        }
      }
    });
    textField_1.setHorizontalAlignment(SwingConstants.CENTER);
    textField_1.setBounds(406, 40, 60, 35);
    frame.getContentPane().add(textField_1);
    textField_1.setColumns(10);
    textField_1.setText(String.valueOf(month));
    textField_1.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void removeUpdate(DocumentEvent e) {
      }
      @Override
      public void insertUpdate(DocumentEvent e) {
        for (int i = 0; i < 6; i++) {
          for (int j = 0; j < 7; j++) {
            data[i][j] = new StringBuffer("");
          }
        }
        table.updateUI();
        String yearstr = textField.getText(), monthstr = null;
        try {
          monthstr = e.getDocument().getText(e.getDocument().getStartPosition().getOffset(),
                                             e.getDocument().getLength());
        } catch (BadLocationException ex) {
          ex.printStackTrace();
        }
        int toyear = Integer.valueOf(yearstr), tomonth = Integer.valueOf(monthstr);
        Calendar today = Calendar.getInstance();
        today.set(toyear, tomonth - 1, 1);
        int hang = 0, lie = today.get(Calendar.DAY_OF_WEEK) - 2;
        if (lie == -1) {
          lie = 6;
        }
        for (int i = 1; i <= today.getActualMaximum(Calendar.DATE); i++) {
          if (lie == 7) {
            lie = 0;
            hang++;
          }
          if (i == day && textField_1.getText().equals(String.valueOf(month))
              && textField.getText().equals(String.valueOf(year))) {
            data[hang][lie] = new StringBuffer("\u3010" + String.valueOf(i) + "\u3011");
          } else {
            data[hang][lie] = new StringBuffer(String.valueOf(i));
          }
          lie++;
        }
        table.updateUI();
      }
      @Override
      public void changedUpdate(DocumentEvent e) {
      }
    });
    JLabel label = new JLabel("\u5E74");
    label.setFont(new Font("\u5FAE\u8F6F\u96C5\u9ED1", Font.BOLD, 20));
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setBounds(120, 10, 40, 25);
    frame.getContentPane().add(label);
    JLabel label_1 = new JLabel("\u6708");
    label_1.setFont(new Font("\u5FAE\u8F6F\u96C5\u9ED1", Font.BOLD, 20));
    label_1.setHorizontalAlignment(SwingConstants.CENTER);
    label_1.setBounds(416, 10, 40, 25);
    frame.getContentPane().add(label_1);
    JButton btnNewButton = new JButton(">");
    btnNewButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int year1 = Integer.valueOf(textField.getText());
        if (year1 <= 20000 && year1 >= 1) {
          year1 += 1;
          if (year1 > 20000) {// \u5E74\u4EFD\u4E0A\u9650
            year1 = 20000;
          }
        } else {
          year1 = year;
        }
        textField.setText(String.valueOf(year1));
      }
    });
    btnNewButton.setForeground(Color.BLACK);
    btnNewButton.setBackground(Color.LIGHT_GRAY);
    btnNewButton.setBounds(170, 40, 60, 35);
    frame.getContentPane().add(btnNewButton);
    btnNewButton_1 = new JButton("<");
    btnNewButton_1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int year1 = Integer.valueOf(textField.getText());
        if (year1 <= 20000 && year1 >= 1) {
          year1 -= 1;
          if (year1 < 1) {// \u5E74\u4EFD\u4E0A\u9650
            year1 = 1;
          }
        } else {
          year1 = year;
        }
        textField.setText(String.valueOf(year1));
      }
    });
    btnNewButton_1.setBackground(Color.LIGHT_GRAY);
    btnNewButton_1.setBounds(50, 40, 60, 35);
    frame.getContentPane().add(btnNewButton_1);
    button_1 = new JButton("<");
    button_1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int month1 = Integer.valueOf(textField_1.getText());
        if (month1 <= 12 && month1 >= 1) {
          month1 -= 1;
          if (month1 < 1) {
            month1 = 12;
          }
        } else {
          month1 = month;
        }
        textField_1.setText(String.valueOf(month1));
      }
    });
    button_1.setBounds(346, 40, 60, 35);
    button_1.setBackground(Color.LIGHT_GRAY);
    frame.getContentPane().add(button_1);
    button = new JButton(">");
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int month1 = Integer.valueOf(textField_1.getText());
        if (month1 <= 12 && month1 >= 1) {
          month1 += 1;
          if (month1 > 12) {
            month1 = 1;
          }
        } else {
          month1 = month;
        }
        textField_1.setText(String.valueOf(month1));
      }
    });
    button.setBackground(Color.LIGHT_GRAY);
    button.setForeground(Color.BLACK);
    button.setBackground(Color.LIGHT_GRAY);
    button.setBounds(466, 40, 60, 35);
    frame.getContentPane().add(button);
    frame.setVisible(true);
  }
}