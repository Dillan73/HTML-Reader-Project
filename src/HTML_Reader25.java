//imports
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;
import javax.swing.*;

//Citations
/*
Citations:
 - https://docs.oracle.com/javase/8/docs/api/javax/swing/JTextArea.html
 - https://stackoverflow.com/questions/66440929/jtextarea-center-alignment
 - https://stackoverflow.com/questions/6635730/how-do-i-put-html-in-a-jlabel-in-java
 - https://stackoverflow.com/questions/20165698/java-how-to-draw-a-border-around-an-undecorated-jframe
 - https://docs.oracle.com/javase/8/docs/api/javax/swing/JScrollPane.html
 */
//https://en.wikipedia.org/wiki/Donald_Trump%5E^https://en.wikipedia.org/wiki/Joe_Biden


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

    //Creates the GUI and has the submit button have an ActionListener
    private void prepareGUI() {
        mainFrame = new JFrame("The URL-link-getter!");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new BorderLayout());
        //make the main frame with a border layout

        cardLayoutMain = new CardLayout();
        cardMain = new JPanel(cardLayoutMain);

        JPanel UIFULL = new JPanel();
        UIFULL.setLayout(new BorderLayout());
        cardMain.add(UIFULL, "Main UI");

        UImain = new JPanel();
        UImain.setBorder(BorderFactory.createEmptyBorder(5,7,5,3));
        UIFULL.add(UImain);
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


        linkLabel = new JLabel("<html>Input the url that's links you want outputted. If you want to find links found through multiple urls, separate each url with a ^, and links found in all of the urls will be returned. For example: https://en.wikipedia.org/wiki/Donald_Trump^https://en.wikipedia.org/wiki/Joe_Biden </html>", JLabel.CENTER);
        UIlink.add(linkLabel, BorderLayout.NORTH);
        //linkLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //add the label for the links to the link part of the main card

        linkText = new JTextArea("");
        JScrollPane scrollLink = new JScrollPane(linkText);
        UIlink.add(scrollLink);
        linkText.setLineWrap(true);
        linkText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //add the linkText to the link part of the main card

        termLabel = new JLabel("<html>This text box is to narrow down the links to only ones you desire. While you can leave this blank to output all links, input any term you would all outputted links to include. If you would like to constrain with multiple terms, again separate with ^. Also, an ! before the term will constrain to links without the term. For example, Donald^Trump^!Biden would return only links with Donald and Trump, but not Biden </html>", JLabel.CENTER);
        UIterm.add(termLabel, BorderLayout.NORTH);
        //termLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //add the term label to the term part of the main UI card

        termText = new JTextArea("");
        termText.setLineWrap(true);
        JScrollPane scrollTerm = new JScrollPane(termText);
        UIterm.add(scrollTerm);
        termText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // adds the user term text to the term part of the main UI card

        JPanel FullOutput = new JPanel();
        FullOutput.setLayout(new BorderLayout());
        cardMain.add(FullOutput, "Main Output");
        //add the full output card

        outputMain = new JPanel();
        outputMain.setLayout(new BorderLayout());
        outputText = new JTextArea(" ");
        JScrollPane scrollOutput = new JScrollPane(outputText);
        outputMain.add(scrollOutput);
        FullOutput.add(outputMain);

        JPanel inputButtons = new JPanel();
        inputButtons.setLayout(new BorderLayout());
        UIFULL.add(inputButtons, BorderLayout.EAST);

        submit = new JButton(" \n \n Press to see your links! \n \n ");
        submit.setActionCommand("Submit");
        submit.addActionListener(e -> cardLayoutMain.next(cardMain));
        submit.addActionListener(new ButtonClickListener());
        //submit.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        inputButtons.add(submit, BorderLayout.SOUTH);
        // adds the submit button to the input section

        JButton clear = new JButton("Clear input fields");
        clear.setActionCommand("Clear");
        clear.addActionListener(new ButtonClickListener());
        inputButtons.add(clear);
        // adds the clear button to the input section with an action listener

        JButton reset = new JButton(" Try new inputs! ");
        reset.setActionCommand("Reset");
        reset.addActionListener(e -> cardLayoutMain.next(cardMain));
        //reset.addActionListener(new ButtonClickListener());
        //submit.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        FullOutput.add(reset, BorderLayout.EAST);
        //adds the reset button to the output card

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

    //Has the submit button call submitUI
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            //System.out.println("Making it to BCL");

            if (command.equals("Submit")) {
                boolean success = sumbitUI();
                if(!success){
                    cardLayoutMain.next(cardMain);
                    //making a failed run stay on the input page
                }
            }
            if (command.equals("Reset")) {
                System.out.println("Reset Works");
                outputText.setText("");
            }
            if (command.equals("Clear")) {
                System.out.println("Making it to BCL If");
                linkText.setText("");
                termText.setText("");
            }
        }
    }

    //Call all the inner-working functions to create functionality in order
    private boolean sumbitUI() {
        if(UI) {
            String url = linkText.getText().replace(" ", "").toLowerCase();
            //the link's text area's text reformatted

            String term = termText.getText().replace(" ", "").toLowerCase();
            //the term's text area's text reformatted

            if(url.equals("")){
                linkText.setText("Please add a valid link!");
                return false;
            }

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
                //System.out.println("TERM BLANK");

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

        }
        return true;
    }

    //turns link text to the links
    private String[] getLinks(String link) {
        return link.split("\\^");
    }

    //turn the urls into a array of links found from each url (intersection)
    String[] readUrls(String[] urls){
        if(urls == null || urls.length == 0 || urls[0] == null || urls[0].equals("")){
            return null;
        }
        ArrayList<String> links = new ArrayList<>();
        String[] firstLinks = readUrl(urls[0]);
        if(links == null || firstLinks == null){
            return null;
        }
        Collections.addAll(links, firstLinks);
        //create the initial arraylist with the with url's links
        for(String link:urls){
            ArrayList<String> tempList = new ArrayList<>();
            String[] currLinks = readUrl(link);
            for(String currLink : currLinks){
                if(links.contains(currLink)) {
                    tempList.add(currLink);
                }
            }
            links = tempList;
        }
        //progressively remove links that aren't in the next url
        String[] intersection = new String[links.size()];
        for(int i = 0; i < links.size(); i++){
            intersection[i] = links.get(i);
        }
        return intersection;
        //return the links that are read in all urls
    }


    //Turns a into the links by calling href and src
    String[] readUrl(String link){
        ArrayList<String> listOfLinks= new ArrayList<>();
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
            //System.out.println("getLinks");
            return null;
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
        return term.split("\\^");
        //turns term text to the term guidelines
    }

    //Takes all the links and the terms and makes one list of valid links
    private String[] constrain(String[] allLinks, String[] terms) {
        System.out.println(Arrays.toString(terms));
        ArrayList<String> links= new ArrayList<>();
        for(String link : allLinks){
            //for each link ....
            boolean contains = true;
            for(String term : terms){

                if(term.charAt(0) == '!'){
                    if(link.contains(term.substring(1))){
                        contains = false;
                        System.out.println("Has !" + link+ term + contains);
                        break;
                    }
                }else{
                    if(!link.contains(term)) {
                        contains = false;
                        System.out.println(link + term + contains);
                        break;
                    }
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
        TreeSet<String> outputting = new TreeSet<>();
        outputting.addAll(Arrays.asList(links));
        for(String link: outputting){
            output = output + link + "\n";
        }

        outputText.setText(output);
        if(output.equals("")){
            outputText.setText("No links match the search term(s)!");
        }
        //taking the links and putting them into the output text area with proper formatting
    }

    //Makes the main frame visible
    private void showEventDemo() {
        mainFrame.setVisible(true);
    }

    //default action performed and make menu code
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cut) {
            linkText.cut();
        }
        if (e.getSource() == paste){
            linkText.paste();
        }
        if (e.getSource() == copy){
            linkText.copy();
        }
        if (e.getSource() == selectAll){
            linkText.selectAll();
        }
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


        mainFrame.add(mb);
        mainFrame.setJMenuBar(mb);
    }
}