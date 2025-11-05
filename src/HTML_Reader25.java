import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*
Links:
 - https://docs.oracle.com/javase/8/docs/api/javax/swing/JTextArea.html
 - https://stackoverflow.com/questions/66440929/jtextarea-center-alignment
 */

public class HTML_Reader25 implements ActionListener {
    private JFrame mainFrame; //the main Panel
    private JPanel UI; //the Panel with the User stuff
    private JTextArea userLink; // the text area with the user's link
    private JTextArea userTerm; // the text area with the user's search term
    private JButton submit; // the button that will submit the user's stuff
    private JTextArea output; // the text area with the outputted links
    private JMenuBar mb;
    private JMenu file, edit, help;
    private JMenuItem cut, copy, paste, selectAll;
     //typing area
    private int WIDTH=800;
    private int HEIGHT=700;


    public HTML_Reader25() {
        //Run the PrepareGUI to make the GUI
        prepareGUI();
    }

    public static void main(String[] args) {
        //Make the GUI and show it after
        HTML_Reader25 GUI = new HTML_Reader25();
        GUI.showEventDemo();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("HTML Reader");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new GridLayout(2, 1));
        //make the main frame with 2x1

        JPanel UI = new JPanel();
        UI.setLayout(new BorderLayout());
        mainFrame.add(UI);
        //make the UI frame as a Border Layout and add to the first row of MainFrame

        JPanel textAreas = new JPanel();
        textAreas.setLayout(new GridLayout(2,1));
        UI.add(textAreas, BorderLayout.CENTER);
        //make the text areas in the center as a 2x1 (each one row of text

        userLink = new JTextArea("Replace this text with the url you would link to use");
        userLink.setBounds(50, 5, WIDTH-100, HEIGHT-50);
        userLink.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textAreas.add(userLink);
        // adds the user link to the text area panel

        userTerm = new JTextArea("Replace this text with the search term you would like all returned links to contain");
        userTerm.setBounds(50, 5, WIDTH-100, HEIGHT-50);
        userTerm.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textAreas.add(userTerm);
        // adds the user search term to the text area panel

        submit = new JButton("  Submit  ");
        submit.setActionCommand("Submit");
        submit.addActionListener(new ButtonClickListener());
        submit.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        UI.add(submit, BorderLayout.EAST);
        // adds the submit button to the right of the user interface area

        output = new JTextArea("After filling out the link and search term, clicking submit will make the links appear here!");
        output.setBounds(50, 5, WIDTH-100, HEIGHT-50);
        output.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainFrame.add(output);
        //Add the output text area to the mainFrame

        makeMenu();
        //call the predefined example menu for now


        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        mainFrame.setVisible(true);
        //the predefined boring stuff remains
    }

    private void sumbitUI() {

    }
    private void makeMenu(){
        cut = new JMenuItem("cut");
        copy = new JMenuItem("copy");
        paste = new JMenuItem("paste");
        selectAll = new JMenuItem("selectAll");
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);

        mb = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        help = new JMenu("Help");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        mb.add(file);
        mb.add(edit);
        mb.add(help);
        //end menu at top


        mainFrame.add(mb);  //add menu bar
        mainFrame.setJMenuBar(mb);
    }

    private void showEventDemo() {

        JButton okButton = new JButton("OK");
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        okButton.setActionCommand("OK");
        submitButton.setActionCommand("Submit");
        cancelButton.setActionCommand("Cancel");

        okButton.addActionListener(new ButtonClickListener());
        submitButton.addActionListener(new ButtonClickListener());
        cancelButton.addActionListener(new ButtonClickListener());



        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cut) {
            //ta.cut();
        }
        if (e.getSource() == paste){
            //ta.paste();
        }
        if (e.getSource() == copy){
            //ta.copy();
        }
        if (e.getSource() == selectAll){
            //ta.selectAll();
        }
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Submit")) {
                sumbitUI();
            }
        }
    }


}