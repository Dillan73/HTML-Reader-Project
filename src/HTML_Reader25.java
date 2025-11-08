import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*
Links:
 - https://docs.oracle.com/javase/8/docs/api/javax/swing/JTextArea.html
 - https://stackoverflow.com/questions/66440929/jtextarea-center-alignment
 - https://stackoverflow.com/questions/6635730/how-do-i-put-html-in-a-jlabel-in-java
 - https://stackoverflow.com/questions/20165698/java-how-to-draw-a-border-around-an-undecorated-jframe
 */

public class HTML_Reader25 implements ActionListener {
    private JFrame mainFrame; //the main Panel
    CardLayout cardLayoutMain;
    JPanel cardMain;
     private JPanel UImain; //the Main Card Panel with the User stuff
      private JPanel UIlink; //the link part of the UI
       private JLabel linkLabel; // the label for the link part
       private JTextArea linkText; // the text are for the link part
        String link; // the actual link string
      private JPanel UIterm; // the term part of the UI
       private JLabel termLabel; // the label for the term part
       private JTextArea termText; //the text for the term part
        String term; // the actual term string
    //detailed outline of UI card frame
   private JPanel outputMain; // the Main card panel with the output
    private JTextArea outputText; // the text area with the outputted links


   private JButton submit; // the button that will submit the user's stuff
    boolean UI = true;

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
        mainFrame.setLayout(new BorderLayout());
        //make the main frame with a border layout

        cardLayoutMain = new CardLayout();
        cardMain = new JPanel(cardLayoutMain);

        UImain = new JPanel();
        UImain.setBorder(BorderFactory.createEmptyBorder(5,7,5,3));
        cardMain.add(UImain, "Main UI");
        //make the main UI as one option in the card layout

        cardLayoutMain.first(cardMain);
        mainFrame.add(cardMain, BorderLayout.CENTER);
        //add the main card to the center of the main frame

        UImain.setLayout(new GridLayout(2,1));
        //make the UI frame as a Border Layout and add to the first row of MainFrame

        UIlink = new JPanel();
        UIlink.setLayout(new BorderLayout());
        UImain.add(UIlink);
        //UIlink.setBorder(BorderFactory.createEmptyBorder(6,6,3,6));
        // Link part of UI as border layout in first row of main UI card

        UIterm = new JPanel();
        UIterm.setLayout(new BorderLayout());
        UImain.add(UIterm);
        //UIterm.setBorder(BorderFactory.createEmptyBorder(3,6,6,6));
        //add the UIterm panel as a border layout in the second row of the main UI card


        linkLabel = new JLabel("<html>This text box is where your link goes. Note that any spaces you add will be ignored as links can't have spaces. Input your link here: </html>", JLabel.CENTER);
        UIlink.add(linkLabel, BorderLayout.NORTH);
        //linkLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //add the label for the links to the link part of the main card

        linkText = new JTextArea("");
        UIlink.add(linkText);
        linkText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //add the linkText to the link part of the main card

        termLabel = new JLabel("<html>This text box is how to narrow down to only the links you want with search terms. If there are multiple terms that a link should include, separate each with a single ampersand. For example, Donald&Trump would return only links with both Donald and Trump. Input search terms here: </html>", JLabel.CENTER);
        UIterm.add(termLabel, BorderLayout.NORTH);
        //termLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //add the term label to the term part of the main UI card

        termText = new JTextArea("");
        termText.setLineWrap(true);
        UIterm.add(termText);
        termText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // adds the user term text to the term part of the main UI card

        outputMain = new JPanel();
        outputMain.setLayout(new BorderLayout());
        cardMain.add(outputMain, "Main Output");
        //make the main output as the other card option

        outputText = new JTextArea(" ");
        outputMain.add(outputText);

        submit = new JButton("  Press to see your links!  ");
        submit.setActionCommand("Submit");
        submit.addActionListener(e -> cardLayoutMain.next(cardMain));
        submit.addActionListener(new ButtonClickListener());
        //submit.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        mainFrame.add(submit, BorderLayout.EAST);
        // adds the submit button to the right of the user interface area

        //makeMenu();
        //DO NOT CREATE A MENU RIGHT NOW


        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        mainFrame.setVisible(true);
        //the predefined boring stuff remains
    }

    private boolean sumbitUI() {
        if(UI) {
            String link = linkText.getText();

            try {
                link = link.replace(" ", "");
            } catch (Exception e) {
                linkText.setText("Please do not delete the initial text and put a valid link after the colon!");
                return false;
            }
            //get the link in "link"

            String term = termText.getText();

            try {
                term = term.replace(" ", "");
            } catch (Exception e) {
                termText.setText("Please do not delete the initial text and put a valid link after the colon!");
                return false;
            }
            submit.setText("  Reset  ");
            UI = false;
            return true;
        }
        submit.setText("  Press to see your links!  ");
        UI = true;
        return true;


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