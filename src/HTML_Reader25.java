//imports
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

// IDEAS:
/* depth, and operator, or operator, not operator, clear button */

//Citations
/*
Citations:
 - https://docs.oracle.com/javase/8/docs/api/javax/swing/JTextArea.html
 - https://stackoverflow.com/questions/66440929/jtextarea-center-alignment
 - https://stackoverflow.com/questions/6635730/how-do-i-put-html-in-a-jlabel-in-java
 - https://stackoverflow.com/questions/20165698/java-how-to-draw-a-border-around-an-undecorated-jframe
 */

public class HTML_Reader25 implements ActionListener {
//variables
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
    private int WIDTH=800;
    private int HEIGHT=700;


    //Calls HTML_Reader25
    public static void main(String[] args) {
        //Make the GUI and show it after
        HTML_Reader25 GUI = new HTML_Reader25();
        GUI.showEventDemo();
    }
    //calls prepare GUI
    public HTML_Reader25() {
        //Run the PrepareGUI to make the GUI
        prepareGUI();
    }

    //Creates the GUI and calls submit UI when submit/reset is pressed
    private void prepareGUI() {
        mainFrame = new JFrame("The URL-link-getter!");
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


        linkLabel = new JLabel("<html>Input the url that's links you want to read below. Note that spaces, which aren't found in urls, will be ignored: </html>", JLabel.CENTER);
        UIlink.add(linkLabel, BorderLayout.NORTH);
        //linkLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //add the label for the links to the link part of the main card

        linkText = new JTextArea("");
        UIlink.add(linkText);
        linkText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //add the linkText to the link part of the main card

        termLabel = new JLabel("<html>This text box is to narrow down the links to only ones you desire. While you can leave this blank to output all links, input any words or terms you would like links to include, separated with an ampersand (Donald&Trump would return only links that contain Donald and Trump) to narrow down the outputted links to only what you care about: </html>", JLabel.CENTER);
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
        JScrollPane scrollOutput = new JScrollPane(outputText);
        outputMain.add(scrollOutput);

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

    //Call all the inner-working functions to create functionality in order
    private boolean sumbitUI() {
        if(UI) {
            String url = linkText.getText().replace(" ", "");
            //the link's text area's text reformatted

            String term = termText.getText().replace(" ", "").toLowerCase();
            //the term's text area's text reformatted

            //String link  link text;
            String[] urls = getLinks(url);
            String[] allLinks = readUrls(urls);
            //turns the url(s) given into the links
            if(allLinks == null){
                linkText.setText("Please input a valid link!");
                return false;
            }
            //if the user input doens't give links/is invalid lets them know

            if(term.equals("")){
                setOutput(allLinks);
                System.out.println("TERM BLANK");

            } //outputting links if no search through terms is necessary to save time
            else {
                String[] terms = getTerms(term);
                //splitting to all the terms
                String[] links = constrain(allLinks, terms);
                if (links == null) {
                    linkText.setText("Please input a valid link!");
                    return false;
                } // if the links was set to null, this was a catch
                setOutput(links);
                //setting the output to be only the links with all terms
            }
            submit.setText("  Reset  ");
        } else {
            submit.setText("  Press to see your links!  ");
        }
        UI = !UI;
        return true;
    }

    //turns link text to the url guidelines
    private String[] getLinks(String link) {
        return link.split("&");

    }

    //Turns the url guidelines into the links without constraining by calling href and src
    String[] readUrls(String[] links){
        ArrayList<String> listOfLinks= new ArrayList<>();
        for(String link : links){
            try{
                URL url = new URL(link);
                URLConnection urlc = url.openConnection();
                urlc.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; " + "Windows NT 5.1; en-US; rv:1.8.0.11) ");

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(urlc.getInputStream())
                );
                String line;
                while ( (line = reader.readLine()) != null ) {
                    href(line, listOfLinks);
                    src(line, listOfLinks);
                }
                //adding all links with href and or src and or multiple to links arraylist
            }catch(Exception e){
                System.out.println("getLinks");
                return null;
            }
        }
        String[] allLinks = new String[listOfLinks.size()];
        for(int i = 0; i < listOfLinks.size(); i++){
            allLinks[i] = listOfLinks.get(i);
        }
        return allLinks;
        //converting the links to an array to return
    }
        //Helper readUrls function that takes in a line and adds any links if the link has href to the arraylist
        void href(String line, ArrayList<String> links){
            //adding all the links with href in a line to the arraylist
            String[] parts = line.split("href=");
            for(int i = parts.length-1; i >0; i--){
                String after = parts[i].substring(1);
                int quoteIndex = after.indexOf("\"");
                int apostropheIndex = after.indexOf("\'");
                if(quoteIndex < 0){
                    quoteIndex = after.length();
                }
                if(apostropheIndex < 0){
                    apostropheIndex = after.length();
                }
                int index = Math.min(quoteIndex, apostropheIndex);
                String link = after.substring(0,index);
                links.add(link);
            }
        }
        //Helper readUrls function that takes in a line and adds any links if the link has src to the arraylist
        void src(String line, ArrayList<String> links){
            //adding all the links with href in a line to the arraylist
            String[] parts = line.split("src=");
            for(int i = parts.length-1; i >0; i--){
                String after = parts[i].substring(1);
                int quoteIndex = after.indexOf("\"");
                int apostropheIndex = after.indexOf("\'");
                if(quoteIndex < 0){
                    quoteIndex = after.length();
                }
                if(apostropheIndex < 0){
                    apostropheIndex = after.length();
                }
                int index = Math.min(quoteIndex, apostropheIndex);
                String link = after.substring(0,index);
                links.add(link);
            }
            //adding all the links with src in a line to the arraylist
        }

    //turns term text to the term guidelines
    private String[] getTerms(String term) {
        return term.split("&");
        //turns term text to the term guidelines
    }

    //Takes all the links and the terms and makes one list of valid links
    private String[] constrain(String[] allLinks, String[] terms) {
        ArrayList<String> links= new ArrayList<>();
        for(String link : allLinks){
            //for each link ....
            boolean contains = true;
            for(String term : terms){
                if(!link.contains(term)){
                    contains = false;
                    break;
                }

            }
            if(contains){
                links.add(link);
            }
            // ... add it to the arraylist if it follows the terms restrictions
        }
        String[] constrained = new String[links.size()];
        for(int i = 0; i < links.size(); i++){
            constrained[i] = links.get(i);
        }
        return constrained;
        //reformats and returns the links with search term guidelines
    }

    //sets the output with the links given
    void setOutput(String[] links){
        String output = "";
        System.out.println("links: " + Arrays.toString(links));
        for(String link: links){
            output = output + link + "\n";
        }

        outputText.setText(output);
        if(output.equals("")){
            outputText.setText("No links match the search term(s)!");
        }
        //taking the links and putting them into the output text area with proper formatting
    }

    //Makes the menu
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


        mainFrame.add(mb);
        mainFrame.setJMenuBar(mb);
    }

    //Makes the main frame visible
    private void showEventDemo() {
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cut) {
            linkText.cut();
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
                boolean success = sumbitUI();
                if(!success){
                    cardLayoutMain.next(cardMain);
                    //making a failed run stay on the input page
                }
            }
        }
    }


}