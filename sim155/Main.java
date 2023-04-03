//Main.java
//Author: Max Krasnow
//Academic Collaborators: John Tooby, Leda Cosmides, Andy Delton, Danielle Truxaw, Adar Eisenbruch
//Technical Assistant: Ankit Gupta


package sim155;

import java.util.logging.Level;
import java.util.logging.Logger;
import rsrc.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Main {
    
    //SWITCHES
    boolean release = true;  //false = experimental; true = didactic
    boolean cue;

    JFrame theFrame;
    JTextField benefitField, benefit1Field, benefit2Field, costField, popsizeField, genField, kindmutationrateField, mutationrateField, mutationsizeField, TFTField, ALLCField, ALLDField, successRateField, uField, sField, scField, scscField, lerField, gestField, nurseField, tenureField, killGestNurseField, killNurseField, noInfField, basefitField;
    JTextField ospercentField, dField, wField, iterationsField, errorField, punField, puncostField, groupsizeField, bandnumField, directoryField, ICTField, PTField, RTField, WTField, wGroupField, PCField, PDField, distField, meetRadiusField, daysField, PGTField, GSPField, CTField, SWField, PIDField, GIDField, PDSField, GDSField, TF2TpercField, HTFTpercField, x1ShotField, x2ShotField, x3ShotField, x4ShotField, x5ShotField;
    JComboBox benefitBox, benefit1Box, benefit2Box, costBox, popsizeBox, genBox, games, fullTime, lerBox, successRateBox, tenureBox, wallsBox, ecologyBox, distroBox, cognitionBox, graphBox;
    JCheckBox tft, allc, alld, u, s, sc, scsc, kgn, kn, ni, tf2t,  htft, x1shot, x2shot, x3shot, x4shot, x5shot;
    ArrayList<paramsList> runsList;
    String message;
    ArrayList<result> results;
    JPanel wholePanel, simControl, topPanel, parameters, panel1, panel2, panel3, panel4, panel5, detailsPanel, logoPanel, southPanel, alldPan, allcPan, tftPan, uPan, sPan, scPan, scscPan, niPan, knPan, kgnPan, glosPanel, descPanel, directoryPanel, CoopPan, DefPan, x1Pan, x2Pan, x3Pan, x4Pan, x5Pan;
    JEditorPane descPane, glosPane, sumPane;
    JScrollPane descScroll;
    graphPanel drawPanel;
    environment theEnvironment;
    //paramsList thisRun;
    char action = 'R';
    boolean abort;
    int flag = 1;   // to mark various events of the simulation run, 1: first
    double nvU, nvS, nvSC, nvSCSC, soloU, soloS, soloSC, soloSCSC, vU, vS, vSC, vSCSC, mateU, mateS, mateSC, mateSCSC, optimum;
    double offNI, offKN, offKGN, groupOffNI, groupOffKN, groupOffKGN, alphaNI, alphaKN, alphaKGN;
    double helpedALLD, helpedALLC, helpedTFT, askedALLD, askedALLC, askedTFT, numALLD, numALLC, numTFT, ageALLD, ageALLC, ageTFT;
    Rectangle r = new Rectangle(840,400);
    Rectangle wr = new Rectangle(840,625);
    Rectangle tr = new Rectangle(800,0,775,400);

    Dimension simPan = new Dimension(200,200);
    Dimension detPan = new Dimension(700,200);
    Dimension drawPan = new Dimension(800,400);
    Dimension textPan = new Dimension(750,350);
    Dimension topPan = new Dimension(1000,200);
    Dimension wholeApp = new Dimension(1000,625);
    Dimension paramPan = new Dimension(200,300);
    File batT, langT, incT, hdT;
    String fileSep = System.getProperty("file.separator");  //to obtain the file separator for the concerned OS
    Font theFont = new Font("Arial", 0, 12);

    
    public static void main(String[] args) {
        Main sim = new Main();
        sim.buildGUI();
        
    }
    
    public void buildGUI(){
       abort=false;
       theFrame = new JFrame("AdaptSim! Produced by the UCSB Center for Evolutionary Psychology");
       theFrame.setResizable(true);
       theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       if(release){
           String[] gamesList = {"Introduction", "Vampire Bats", "Incest Avoidance", "Infanticide", "Hawk/Dove"};
           games = new JComboBox(gamesList);
           games.setFont(theFont);
           games.setEditable(false);
           games.addActionListener(new gamesListener());
           games.setMaximumSize(new Dimension(200,20));
       } else {
           String[] gamesList = {"", "Belief Formation", "Belief Implementation", "Public Goods v2","Spatial Cue", "Warmth/Competence", "Belief Formation Redux"};
           //String[] gamesList = {"", "Belief Formation", "Belief Implementation", "Public Goods v2","Public Goods 3","Public Goods Evolve","Spatial Cue", "Age Cue"};
           games = new JComboBox(gamesList);
           games.setFont(theFont);
           games.setEditable(false);
           games.addActionListener(new gamesListener());
           games.setMaximumSize(new Dimension(200,20));
       }
       JLabel gamesLabel = new JLabel("Choose Simulation");
       gamesLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
       gamesLabel.setFont(theFont);
   
       JButton startSim = new JButton("START");
       startSim.setFont(theFont);
       JButton stopSim = new JButton("STOP");
       stopSim.setFont(theFont);
       JButton glosButton = new JButton("Glossary");
       glosButton.setFont(theFont);
       JButton sumButton = new JButton("Sim Text");
       sumButton.setFont(theFont);
       stopSim.addActionListener(new StopButtonListener());
       startSim.addActionListener(new StartButtonListener());
       glosButton.addActionListener(new GlossaryButtonListener());
       sumButton.addActionListener(new SummaryButtonListener());
       results = new ArrayList();
       simControl = new JPanel();
       simControl.setLayout(new BoxLayout(simControl,BoxLayout.Y_AXIS));
       simControl.add(gamesLabel);
       simControl.add(games);
       JPanel buttonPanel1 = new JPanel();
       JPanel buttonPanel2 = new JPanel();
       buttonPanel1.setLayout(new FlowLayout());
       buttonPanel2.setLayout(new FlowLayout());
       buttonPanel1.add(startSim);
       buttonPanel1.add(stopSim);
       buttonPanel2.add(glosButton);
       buttonPanel2.add(sumButton);
       simControl.add(buttonPanel1);
       simControl.add(buttonPanel2);
       simControl.setMaximumSize(simPan);
       
          
        try {
            URL test = this.getClass().getResource("files/general.html");
            descPane = new JEditorPane(test); 
         } catch (Exception ex) {
             Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
         }
       descPane.setEditable(false);
       descScroll = new JScrollPane(descPane);
       descScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       descScroll.setPreferredSize(textPan);
       descPanel = new JPanel();
       descPanel.add(descScroll);


       try {
           URL test = this.getClass().getResource("files/glossary.html");
           glosPane = new JEditorPane(test);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
       glosPane.setEditable(false);
       glosPanel = new JPanel();
       glosPanel.add(new JScrollPane(glosPane));

      
       

       topPanel = new JPanel();
       topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.X_AXIS));
       topPanel.add(simControl);
       detailsPanel = new JPanel();
       detailsPanel.setPreferredSize(detPan);
       detailsPanel.setBackground(Color.LIGHT_GRAY);
       topPanel.add(detailsPanel);
       drawPanel = new graphPanel();
       topPanel.setPreferredSize(topPan);
       topPanel.setMaximumSize(topPan);
       southPanel = new JPanel();
       southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
       southPanel.setPreferredSize(drawPan);
       //southPanel.setMaximumSize(drawPan);
       //southPanel.add(drawPanel);
       southPanel.add(descPanel);
       wholePanel = new JPanel();
       wholePanel.setLayout(new BoxLayout(wholePanel,BoxLayout.Y_AXIS));
       wholePanel.add(topPanel);
       wholePanel.add(southPanel);
       theFrame.setLayout(new BorderLayout());

       theFrame.getContentPane().add(wholePanel);
       
       theFrame.setBounds(wr);
       theFrame.setPreferredSize(wholeApp);
       theFrame.setVisible(true);
       theFrame.validate();
   }

    class graphPanel extends JPanel implements resultListener{
        int iterator, length, height, newx, newy, lastPoint, refy; 
        int counter=0;
        int repeat=1;   // a marker of completion of a particular run consisting of many
        double gens;    // to calculate the no. of generations input
        
        result thisResult;
        String text, text2, text3;
              
        @Override
        public void paintComponent(Graphics g){
            {
                switch (action){
                    case 'R': //PreGraph - just displays the intro screen
                        break;
                    case 'I': //invalid
                        this.removeAll();
                        g.setColor(Color.white);
                        g.fillRect(0,0,r.width,r.height);
                        g.setColor(Color.red);
                        g.drawString(thisResult.message, 10, 10);
                        break;
                    case 'S':  //Setup Graph
                        this.removeAll();
                        break;
                    case 'G':  //Live graphing option - graphs each datapoint that comes in from result listener
                        g.setColor(Color.white);
                        g.fillRect(0,0,r.width,r.height);
                        g.setColor(Color.red);
                        g.drawString(thisResult.message, 10, 10);
                        break;
                    case 'C': // New graphic option for 155 sim (2 panels)
                        action = 'O';
                        g.setColor(Color.WHITE);
                        g.fillRect(0,0,r.width,r.height);
                        //Left Panel
                        // progress status bar
                        g.setColor(Color.RED);
                        g.clearRect(2, r.height-22, 100, 22);
                        g.drawString(Integer.toString((int)thisResult.gen)+"/"+genField.getText(), 2, r.height-2);
                        g.setColor(Color.GREEN);
                        g.drawString(repeat+"/"+iterationsField.getText(), 77, r.height-2);
                        if(release){
                            
                        } else {
                            if(flag==4 || Double.parseDouble(iterationsField.getText())<repeat){repeat=1;}
                        }
                        int repeats;
                        try {
                            if(genBox.getSelectedItem()=="Custom"){
                            repeats=Integer.parseInt(genField.getText());
                            } else {
                                repeats=Integer.parseInt((String)genBox.getSelectedItem());
                            }
                         } catch (Exception ex) {
                             Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                             repeats=Integer.parseInt(genField.getText());
                         }
                        if(thisResult.gen==repeats){repeat++;}  // to update repeat for each run
                        
                        ArrayList<agent> AgentList = sortList(thisResult.AgentList);
                        int side = (int)Math.ceil(Math.sqrt(AgentList.size()));
                        int row = side; //to fix faulty number of agents shown on left panel
                        int rad = Math.min((int)Math.floor((r.width/2)/side),(int)Math.floor(r.height/side));
                        if(AgentList.size()<=side*(side-1)){ row = side-1;}
                        for(int x=0; x<row; x++){
                            for(int y=0; y<side; y++){
                                if(AgentList.size()>0){
                                    agent thisAgent=AgentList.get(0);
                                    AgentList.remove(0);
                                    if(games.getSelectedItem()=="Hawk/Dove"){
                                        if(thisAgent.genes.get(0).value==0){
                                            g.setColor(Color.red);
                                        } else {
                                            g.setColor(Color.blue);
                                        }
                                    }
                                    if(games.getSelectedItem()=="Incest Avoidance"){
                                        if(thisAgent.genes.get(0).value==3 || thisAgent.genes.get(1).value==3){
                                            g.setColor(Color.RED);
                                        } else if(thisAgent.genes.get(0).value==2 || thisAgent.genes.get(1).value==2){
                                            g.setColor(Color.BLUE);
                                        } else if(thisAgent.genes.get(0).value==1 || thisAgent.genes.get(1).value==1){
                                            g.setColor(Color.CYAN);
                                        } else {
                                            g.setColor(Color.darkGray);
                                        }
                                    }
                                    if(games.getSelectedItem()=="Vampire Bats"){
                                        if(thisAgent.genes.get(0).value==0){
                                            g.setColor(Color.red);
                                        } else if(thisAgent.genes.get(0).value==1){
                                            g.setColor(Color.blue);
                                        } else {
                                            g.setColor(Color.green);
                                        }
                                    }
                                    if(games.getSelectedItem()=="Infanticide"){
                                        if(thisAgent.genes.get(0).value==2){
                                            g.setColor(Color.red);
                                        } else if(thisAgent.genes.get(0).value==1){
                                            g.setColor(Color.green);
                                        } else {
                                            g.setColor(Color.darkGray);
                                        }
                                    }
                                    if(games.getSelectedItem()=="Belief Formation"){
                                        int red, green, blue;
                                        red = 255;
                                        green = 0;
                                        blue = 255;
                                        if(thisAgent.genes.get(0).value<0){
                                            blue=blue + (int)Math.round(255.0*thisAgent.genes.get(0).value);
                                        } else {
                                            red=red-(int)Math.round(255.0*Math.abs(thisAgent.genes.get(0).value));
                                        }
                                        //System.out.println(thisAgent.genes.get(0).value + " blue:" + blue + " red:" + red);
                                        if(red<0){red=0;}
                                        if(blue<0){blue=0;}
                                        if(red>255){red=255;}
                                        if(blue>255){blue=255;}
                                        int rgb = ((red)<<16)|((green)<<8)|(blue);
                                        g.setColor(new Color(rgb));
                                    }

                                    if(games.getSelectedItem()=="Belief Formation Redux"){
                                        double bc = thisAgent.genes.get(0).value;
                                        if(bc>1){bc=1;} // to limit the color representation to relevant values
                                        else if(bc<-1){bc=-1;}
                                        int c = (int)Math.round(255.0*(bc+1)/2);
                                        g.setColor(new Color(c,c,c));   // to represent bcGene in shades of grey
                                    }

                                    if(games.getSelectedItem()=="Belief Implementation"){
                                        int red, green, blue;
                                        red=0;
                                        green = 0;
                                        blue=0;
                                        if(thisAgent.genes.get(1).value<.5){
                                            red=255;
                                            blue=(int)Math.round(2*255.0*thisAgent.genes.get(1).value);
                                        } else {
                                            blue=255;
                                            red=255-(int)Math.round(255.0*2*(thisAgent.genes.get(1).value-.5));
                                        }
                                        //System.out.println(thisAgent.genes.get(1).value + " blue:" + blue + " red:" + red);
                                        if(red<0){red=0;}
                                        if(red>255){red=255;}
                                        if(blue<0){blue=0;}
                                        if(blue>255){blue=255;}
                                        int rgb = ((red)<<16)|((green)<<8)|(blue);
                                        g.setColor(new Color(rgb));
                                    }
                                    if(games.getSelectedItem()=="Public Goods Evolve"){
                                        g.setColor(new Color((float)thisAgent.genes.get(3).value, (float)(thisAgent.genes.get(1).value/10), (float)thisAgent.genes.get(2).value, (float)thisAgent.genes.get(0).value));
                                        
                                    }
                                    if(games.getSelectedItem()=="Public Goods v2"){
                                        int red, green, blue;
                                        red=(int)Math.round(255.0*thisAgent.genes.get(1).value);
                                        green = 0;
                                        blue=(int)Math.round(255.0*thisAgent.genes.get(0).value);
                                        //System.out.println(thisAgent.genes.get(1).value + " blue:" + blue + " red:" + red);
                                        if(red<0){red=0;}
                                        if(red>255){red=255;}
                                        if(blue<0){blue=0;}
                                        if(blue>255){blue=255;}
                                        int rgb = ((red)<<16)|((green)<<8)|(blue);
                                        g.setColor(new Color(rgb));
                                        
                                    }
                                    if(games.getSelectedItem()=="Public Goods 3"){
                                        int red, green, blue;
                                        red=(int)Math.round(255.0*thisAgent.genes.get(1).value);
                                        green = (int)Math.round(255.0*thisAgent.genes.get(2).value);
                                        blue=(int)Math.round(255.0*thisAgent.genes.get(0).value);
                                        //System.out.println(thisAgent.genes.get(1).value + " blue:" + blue + " red:" + red);
                                        if(red<0){red=0;}
                                        if(red>255){red=255;}
                                        if(blue<0){blue=0;}
                                        if(blue>255){blue=255;}
                                        if(green<0){green=0;}
                                        if(green>255){green=255;}
                                        int rgb = ((red)<<16)|((green)<<8)|(blue);
                                        g.setColor(new Color(rgb));
                                        
                                    }
                                    if(games.getSelectedItem()=="Spatial Cue"){

                                    }
                                    if(games.getSelectedItem()=="Age Cue"){

                                    }
                                    if(games.getSelectedItem()=="Warmth/Competence"){
                                        int red, green, blue;
                                        red = 255;
                                        green = 0;
                                        blue = 255;
                                        if(thisAgent.genes.get(0).value<0){
                                            blue=blue + (int)Math.round(255.0*thisAgent.genes.get(0).value);
                                        } else {
                                            red=red-(int)Math.round(255.0*Math.abs(thisAgent.genes.get(0).value));
                                        }
                                        //System.out.println(thisAgent.genes.get(0).value + " blue:" + blue + " red:" + red);
                                        if(red<0){red=0;}
                                        if(blue<0){blue=0;}
                                        if(red>255){red=255;}
                                        if(blue>255){blue=255;}
                                        int rgb = ((red)<<16)|((green)<<8)|(blue);
                                        g.setColor(new Color(rgb));
                                    }

                                    g.fillOval(y*(rad), x*(rad), rad, rad); //coloring agent body/face
                                    
                                    // to represent cGene as colors of the periphery
                                    if(games.getSelectedItem()=="Belief Formation Redux"){
                                        g.setColor(new Color(255,0,255,(int) Math.round(thisAgent.genes.get(1).value)*255));
                                        int thicken = (int) rad/10;
                                        for(int thick = 0; thick < thicken; thick++){
                                        g.drawOval(y*(rad)+thick, x*(rad)+thick, rad-2*thick, rad-2*thick);
                                        }
                                    }
                                    
                                    // to represent dGene as colors of eyes and mouth
                                    if(games.getSelectedItem()=="Belief Formation Redux"){
                                        g.setColor(new Color(0,255,255,(int) Math.round(thisAgent.genes.get(2).value)*255));  
                                    }
                                    else{
                                        g.setColor(Color.BLACK);   
                                    }
                                    g.fillOval(y*rad+(int)Math.round(rad*.22), (int)Math.round(x*rad+rad*.3), rad/6, rad/6);
                                    g.fillOval(y*rad+(int)Math.round(rad*.62), (int)Math.round(x*rad+rad*.3), rad/6, rad/6);
                                    g.drawArc(y*rad+(int)Math.round(rad*.12), x*rad+(int)Math.round(rad*.5), (int)Math.round(rad*.75), (int)Math.round(rad*.3), -30, -140);                                    
                                }
                            }
                        }
                        //Right Panel Top
                        if(genField.getText().equalsIgnoreCase("")){
                            iterator = (int)Math.ceil(Double.parseDouble((String)genBox.getSelectedItem())/(double)((r.width/2)-10));
                            length = (int)Math.floor(Double.parseDouble((String)genBox.getSelectedItem())/(double)iterator);
                        } else {
                            if(flag==4){  // flag==4: start button pressed
                                gens = Double.parseDouble(genField.getText());  // to calculate the no. of generations input
                                flag=0;
                            }
                            iterator = (int)Math.ceil(gens/(double)((r.width/2)-10));
                            length = (int)Math.floor(gens/(double)iterator);
                        }
                        height = (int)Math.floor(r.height/2-20);    // the length of y-axis

                        g.setColor(Color.black);
                        if(games.getSelectedItem()=="Hawk/Dove"||games.getSelectedItem()=="Incest Avoidance"||games.getSelectedItem()=="Vampire Bats"||games.getSelectedItem()=="Infanticide"){
                            g.drawString("Gene Frequency", r.width/2, 10);
                            g.drawString(".75", r.width/2-7, (int)Math.floor(r.height/8));
                            g.drawString(".5", r.width/2-7, (int)Math.floor(r.height/4));
                            g.drawString(".25", r.width/2-7, (int)Math.floor(r.height*.375));
                            g.drawLine(r.width/2+10, r.height/2-10, length+r.width/2+10, r.height/2-10);
                            g.drawLine(r.width/2+10, 10, r.width/2+10, r.height/2-10);
                            g.drawLine(r.width/2+5,(int)Math.floor(r.height/8),r.width/2+15,(int)Math.floor(r.height/8));
                            g.drawLine(r.width/2+5,(int)Math.floor(r.height/4),r.width/2+15,(int)Math.floor(r.height/4));
                            g.drawLine(r.width/2+5,(int)Math.floor(r.height*.375),r.width/2+15,(int)Math.floor(r.height*.375));
                            g.drawString("time->", length+r.width/2+11, r.height/2-10);
                        } else {
                            if(games.getSelectedItem()=="Belief Formation"){
                                g.drawLine(r.width/2+10, r.height/4-10, length+r.width/2+10, r.height/4-10);
                                g.drawLine(r.width/2+10, 0, r.width/2+10, r.height/2-20);
                                g.drawString("time->", length+r.width/2+11, r.height/4-10);
                            }
                            else if(games.getSelectedItem()=="Belief Formation Redux"){
                                // top right
                                g.drawLine(r.width/2+10, r.height/4-10, length+r.width/2+10, r.height/4-10);
                                g.drawLine(r.width/2+10, 0, r.width/2+10, r.height/2-20);
                                g.drawString("time->", length+r.width/2+11, r.height/4-10);
                                //bottom right
                                g.drawLine(r.width/2+10, r.height-10, length + r.width/2+10, r.height-10);
                                g.drawLine(r.width/2+10, r.height/2+10, r.width/2+10, r.height-10);
                                g.drawString("time->", length+r.width/2+11, r.height-10);
                                
                                g.drawString("Grey: bcGene, Magenta: cGene, Cyan: dGene", r.width/8+20, r.height-2);

                                for(int x=0; x<=length; x+=100){    // to draw scales on bottom right x-axis
                                    String temp1 = Integer.toString(x*iterator);
                                    g.drawLine(x+r.width/2+10, r.height-5, x+r.width/2+10, r.height-15);
                                    g.drawString(temp1, x+r.width/2+10-10, r.height); 
                                }

                                for(int y=1; y<=4; y++){    // to draw scales on bottom right y-axis
                                    
                                    g.drawLine(r.width/2+5, r.height-10-y*height/4, r.width/2+15, r.height-10-y*height/4);
                                    String temp2 = Double.toString(Math.round(((double)y/4)*100.0)/100.0);
                                    g.drawString(temp2, r.width/2-20, r.height/2+10+height-y*height/4+10); 
                                }
                            }
                            else if(games.getSelectedItem()=="Warmth/Competence"){
                                g.drawString("Generosity Bias (over .5)", r.width/2, 10);
                                g.drawString(".75", r.width/2-7, (int)Math.floor(r.height/8));
                                g.drawString(".5", r.width/2-7, (int)Math.floor(r.height/4));
                                g.drawString(".25", r.width/2-7, (int)Math.floor(r.height*.375));
                                g.drawLine(r.width/2+10, r.height/2-10, length+r.width/2+10, r.height/2-10);
                                g.drawLine(r.width/2+10, 10, r.width/2+10, r.height/2-10);
                                g.drawLine(r.width/2+5,(int)Math.floor(r.height/8),r.width/2+15,(int)Math.floor(r.height/8));
                                g.drawLine(r.width/2+5,(int)Math.floor(r.height/4),r.width/2+15,(int)Math.floor(r.height/4));
                                g.drawLine(r.width/2+5,(int)Math.floor(r.height*.375),r.width/2+15,(int)Math.floor(r.height*.375));
                                g.drawString("time->", length+r.width/2+11, r.height/2-10);
                            } else {
                                g.drawLine(r.width/2+10, r.height/2-10, length+r.width/2+10, r.height/2-10);
                                g.drawLine(r.width/2+10, 10, r.width/2+10, r.height/2-10);
                                g.drawLine(r.width/2+5,(int)Math.floor(r.height/8),r.width/2+15,(int)Math.floor(r.height/8));
                                g.drawLine(r.width/2+5,(int)Math.floor(r.height/4),r.width/2+15,(int)Math.floor(r.height/4));
                                g.drawLine(r.width/2+5,(int)Math.floor(r.height*.375),r.width/2+15,(int)Math.floor(r.height*.375));
                                g.drawString("time->", length+r.width/2+11, r.height/2-10);
                            }

                        }

                        for(int x=0; x<=length; x+=100){    // to draw scales on top right x-axis
                            String temp = Integer.toString(x*iterator);
                            if(games.getSelectedItem()=="Belief Formation" || games.getSelectedItem()=="Belief Formation Redux"){
                                g.drawString(temp, x+r.width/2+10-10, r.height/4);
                                g.drawLine(x+r.width/2+10, r.height/4-5, x+r.width/2+10, r.height/4-15);
                            } else {
                                g.drawString(temp, x+r.width/2+10, r.height/2);
                            }
                        }
                        if(games.getSelectedItem()=="Belief Formation" || games.getSelectedItem()=="Belief Formation Redux"){
                            for(int y=-2; y<=2; y++){    // to draw scales on top right y-axis
                                g.drawLine(r.width/2+5, r.height/4-10-y*height/4, r.width/2+15, r.height/4-10-y*height/4);
                                String temp2 = Double.toString(Math.round(((double)y/4)*100.0)/100.0);
                                g.drawString(temp2, r.width/2-20, r.height/4-10-y*height/4+10); 
                            }
                        }
                        newx=r.width/2+10;
                        if(results!=null){
                            for(int p=0; p<results.size(); p+=iterator){    // this for loop is responsible for unnecessary iterations, we may remove the loop and use dynamic array to store and plot the data
                                for(TypeDistribution thisDist:results.get(p).typesDist){
                                    if(thisDist.type.matches("Hawk")){g.setColor(Color.RED);}
                                    if(thisDist.type.matches("Dove")){g.setColor(Color.BLUE);}
                                    if(thisDist.type.matches("ALLD")){g.setColor(Color.RED);}
                                    if(thisDist.type.matches("ALLC")){g.setColor(Color.BLUE);}
                                    if(thisDist.type.matches("TFT")){g.setColor(Color.GREEN);}
                                    if(thisDist.type.matches("U")){g.setColor(Color.GRAY);}
                                    if(thisDist.type.matches("S")){g.setColor(Color.CYAN);}
                                    if(thisDist.type.matches("SC")){g.setColor(Color.BLUE);}
                                    if(thisDist.type.matches("SCSC")){g.setColor(Color.RED);}
                                    if(thisDist.type.matches("NI")){g.setColor(Color.darkGray);}
                                    if(thisDist.type.matches("KN")){g.setColor(Color.GREEN);}
                                    if(thisDist.type.matches("KGN")){g.setColor(Color.RED);}
                                    if(thisDist.type.matches("BC")){g.setColor(Color.BLACK);}
                                    if(thisDist.type.matches("prCifR")){g.setColor(Color.BLUE);}
                                    if(thisDist.type.matches("prCifOS")){g.setColor(Color.RED);}
                                    if(thisDist.type.matches("ICT")){g.setColor(Color.BLUE);}
                                    if(thisDist.type.matches("PT")){g.setColor(Color.RED);}
                                    if(thisDist.type.matches("RT")){g.setColor(Color.GREEN);}
                                    if(thisDist.type.matches("WT")){g.setColor(Color.CYAN);}
                                    if(thisDist.type.matches("PC")){g.setColor(Color.BLUE);}
                                    if(thisDist.type.matches("PD")){g.setColor(Color.RED);}
                                    if(thisDist.type.matches("PGT")){g.setColor(Color.GRAY);}
                                    if(thisDist.type.matches("GSP")){g.setColor(Color.GREEN);}
                                    if(thisDist.type.matches("CT")){g.setColor(Color.BLUE);}
                                    if(thisDist.type.matches("PT")){g.setColor(Color.RED);}
                                    if(thisDist.type.matches("Pref")){g.setColor(Color.red);}
                                    

                                    newy=10+height-(int)Math.round(thisDist.proportion*height);                                    
                                    if(thisDist.type.matches("BC")){newy=newy-(int)Math.round(r.height/4);} // to bring the zero value at origin
                                    if(thisDist.type.matches("GSP")){newy=10+height-(int)Math.round(thisDist.proportion/10*height);}
                                    if(!thisDist.type.matches("TF2T Rate") && !thisDist.type.matches("HTFT Rate")){
                                        g.fillOval(newx-1, newy-1, 3, 3);   // to represent in top right panel, all except kindgenes
                                    }

                                    //for kindgene plot in the right bottom panel
                                    if(thisDist.type.matches("TF2T Rate") || thisDist.type.matches("HTFT Rate")){
                                        if(thisDist.type.matches("TF2T Rate")){g.setColor(Color.MAGENTA);}
                                        if(thisDist.type.matches("HTFT Rate")){g.setColor(Color.CYAN);}
                                        newy=r.height/2+10+height-(int)Math.round(thisDist.proportion*height);
                                        g.fillOval(newx-1, newy-1, 3, 3);
                                    }                                       
                                //    g.drawLine(newx-1,newy-1,newx+1,newy+1);
                                }
                                newx++;
                            }
                        }
                        if(games.getSelectedItem()=="Vampire Bats"){
                            g.setColor(Color.black);
                            g.drawLine(r.width/2+10, r.height-10, (int)(r.width*.75)-10, r.height-10);
                            g.drawLine((int)(r.width*.75), r.height/2+10, (int)(r.width*.75), r.height-10);
                            g.drawLine(r.width/2+10, r.height/2+10, r.width/2+10, r.height-10);
                            g.drawLine((int)(r.width*.75), r.height-10, r.width-10, r.height-10);
                            for(bat thisBat:thisResult.deadBats){
                                if(thisBat.getType()==0){
                                    numALLD++;
                                    ageALLD+=thisBat.age;
                                } else if(thisBat.getType()==1){
                                    numALLC++;
                                    ageALLC+=thisBat.age;
                                } else {
                                    numTFT++;
                                    ageTFT+=thisBat.age;
                                }
                            }
                            java.util.List<Double> Lmult = Arrays.asList( new Double[]{ageALLD/numALLD, ageALLC/numALLC, ageTFT/numTFT});

                            for(int i=0; i<Lmult.size(); i++){
                                if(Lmult.get(i).isNaN()){
                                    Lmult.set(i, 0.0);
                                }
                            }
                            double max = Collections.max(Lmult);
                            double mult=0;
                            if(max!=0){
                                mult = 150/max;
                            }
                            g.drawString(Integer.toString((int)Math.round(max)), r.width/2+15, r.height/2+15);
                            g.drawString("Life Expectancy (days)", r.width/2+50, r.height);
                            g.setColor(Color.red);
                            g.fillRect(r.width/2+30, r.height-10-(int)Math.round(mult*Lmult.get(0)), 25, (int)Math.round(mult*Lmult.get(0)));
                            g.setColor(Color.blue);
                            g.fillRect(r.width/2+60, r.height-10-(int)Math.round(mult*Lmult.get(1)), 25, (int)Math.round(mult*Lmult.get(1)));
                            g.setColor(Color.green);
                            g.fillRect(r.width/2+90, r.height-10-(int)Math.round(mult*Lmult.get(2)), 25, (int)Math.round(mult*Lmult.get(2)));

                            //System.out.println(mult);
                            for(TypeDistribution thisDist:thisResult.typesDist){
                                if(thisDist.type.equals("ALLD")){
                                    helpedALLD+=thisDist.helped;
                                    askedALLD+=thisDist.asked;
                                }
                                if(thisDist.type.equals("ALLC")){
                                    helpedALLC+=thisDist.helped;
                                    askedALLC+=thisDist.asked;
                                }
                                if(thisDist.type.equals("TFT")){
                                    helpedTFT+=thisDist.helped;
                                    askedTFT+=thisDist.asked;
                                }
                            }
                            Lmult = Arrays.asList( new Double[]{helpedALLD/askedALLD, helpedALLC/askedALLC, helpedTFT/askedTFT});
                            for(int i=0; i<Lmult.size(); i++){
                                if(Lmult.get(i).isNaN()){
                                    Lmult.set(i, 0.0);
                                }
                            }
                            max = roundToSignificantFigures(Collections.max(Lmult), 2);
                            mult=0;
                            if(max!=0){
                                mult = 180/max;
                            }
                            g.setColor(Color.black);
                            g.drawString(Double.toString(max*100), (int)(r.width*.75)+15, r.height/2+15);
                            g.drawString("% Receiving Aid", (int)(r.width*.75)+50, r.height);
                            g.setColor(Color.red);
                            g.fillRect((int)(r.width*.75)+20, r.height-10-(int)Math.round(mult*Lmult.get(0)), 25, (int)Math.round(mult*Lmult.get(0)));
                            g.setColor(Color.blue);
                            g.fillRect((int)(r.width*.75)+50, r.height-10-(int)Math.round(mult*Lmult.get(1)), 25, (int)Math.round(mult*Lmult.get(1)));
                            g.setColor(Color.green);
                            g.fillRect((int)(r.width*.75)+80, r.height-10-(int)Math.round(mult*Lmult.get(2)), 25, (int)Math.round(mult*Lmult.get(2)));
                            //System.out.println(thisResult.deadBats.size() + " - " + helpedALLD/askedALLD + ", " + helpedALLC/askedALLC + ", " + helpedTFT/askedTFT);
                        }

                        if(games.getSelectedItem()=="Incest Avoidance"){
                            g.setColor(Color.black);
                            g.drawLine(r.width/2+10, r.height-10, (int)(r.width*.75)-10, r.height-10);
                            g.drawLine((int)(r.width*.75), r.height/2+10, (int)(r.width*.75), r.height-10);
                            g.drawLine(r.width/2+10, r.height/2+10, r.width/2+10, r.height-10);
                            g.drawLine((int)(r.width*.75), r.height-10, r.width-10, r.height-10);

                            for(TypeDistribution thisDist:thisResult.typesDist){
                                if(thisDist.type.equals("U")){
                                    nvU+=thisDist.nviable;
                                    vU+=thisDist.viable;
                                    soloU+=thisDist.solo;
                                    mateU+=thisDist.mate;
                                }
                                if(thisDist.type.equals("S")){
                                    nvS+=thisDist.nviable;
                                    vS+=thisDist.viable;
                                    soloS+=thisDist.solo;
                                    mateS+=thisDist.mate;
                                }
                                if(thisDist.type.equals("SC")){
                                    nvSC+=thisDist.nviable;
                                    vSC+=thisDist.viable;
                                    soloSC+=thisDist.solo;
                                    mateSC+=thisDist.mate;
                                }
                                if(thisDist.type.equals("SCSC")){
                                    nvSCSC+=thisDist.nviable;
                                    vSCSC+=thisDist.viable;
                                    soloSCSC+=thisDist.solo;
                                    mateSCSC+=thisDist.mate;
                                }
                            }

                            java.util.List<Double> Lmult = Arrays.asList( new Double[]{nvU/(nvU+vU),nvS/(nvS+vS),nvSC/(nvSC+vSC),nvSCSC/(nvSCSC+vSCSC),soloU/(soloU+mateU),soloS/(soloS+mateS),soloSC/(soloSC+mateSC),soloSCSC/(soloSCSC+mateSCSC)});
                            for(int i=0; i<Lmult.size(); i++){
                                if(Lmult.get(i).isNaN()){
                                    Lmult.set(i, 0.0);
                                }
                            }
                            double max = roundToSignificantFigures(Collections.max(Lmult), 2);
                            double mult = 180/max;
                            g.drawString(Double.toString(roundToSignificantFigures(max*100,2)) + "%", r.width/2+15, r.height/2+15);
                            g.drawString("%Nonviable Offspring", r.width/2+50, r.height);
                            g.drawString(Double.toString(roundToSignificantFigures(max*100,2)) + "%", (int)(r.width*.75)+5, r.height/2+15);
                            g.drawString("%Died Unmated", (int)(r.width*.75)+50, r.height);
                            for(double i=.1; i<max; i+=.1){
                                g.drawLine(r.width/2+5, r.height-10-(int)Math.round(i*mult), r.width/2+15, r.height-10-(int)Math.round(i*mult));
                                g.drawLine((int)(r.width*.75)-5, r.height-10-(int)Math.round(i*mult), (int)(r.width*.75), r.height-10-(int)Math.round(i*mult));
                            }

                            g.setColor(Color.darkGray);
                            g.fillRect(r.width/2+30, r.height-10-(int)Math.round(mult*(nvU/(nvU+vU))), 25, (int)Math.round(mult*(nvU/(nvU+vU))));
                            g.fillRect((int)(r.width*.75)+20, r.height-10-(int)Math.round(mult*(soloU/(soloU+mateU))), 25, (int)Math.round(mult*(soloU/(soloU+mateU))));
                            g.setColor(Color.cyan);
                            g.fillRect(r.width/2+60, r.height-10-(int)Math.round(mult*(nvS/(nvS+vS))), 25, (int)Math.round(mult*(nvS/(nvS+vS))));
                            g.fillRect((int)(r.width*.75)+50, r.height-10-(int)Math.round(mult*(soloS/(soloS+mateS))), 25, (int)Math.round(mult*(soloS/(soloS+mateS))));
                            g.setColor(Color.blue);
                            g.fillRect(r.width/2+90, r.height-10-(int)Math.round(mult*(nvSC/(nvSC+vSC))), 25, (int)Math.round(mult*(nvSC/(nvSC+vSC))));
                            g.fillRect((int)(r.width*.75)+80, r.height-10-(int)Math.round(mult*(soloSC/(soloSC+mateSC))), 25, (int)Math.round(mult*(soloSC/(soloSC+mateSC))));
                            g.setColor(Color.red);
                            g.fillRect(r.width/2+120, r.height-10-(int)Math.round(mult*(nvSCSC/(nvSCSC+vSCSC))), 25, (int)Math.round(mult*(nvSCSC/(nvSCSC+vSCSC))));
                            g.fillRect((int)(r.width*.75)+110, r.height-10-(int)Math.round(mult*(soloSCSC/(soloSCSC+mateSCSC))), 25, (int)Math.round(mult*(soloSCSC/(soloSCSC+mateSCSC))));
                        }
                        if(games.getSelectedItem()=="Infanticide"){
                            for(TypeDistribution thisDist:thisResult.typesDist){
                                if(thisDist.type.equals("NI")){
                                    offNI+=thisDist.offspring;
                                    groupOffNI+=thisDist.groupOff;
                                    alphaNI+=thisDist.alphas;
                                }
                                if(thisDist.type.equals("KN")){
                                    offKN+=thisDist.offspring;
                                    groupOffKN+=thisDist.groupOff;
                                    alphaKN+=thisDist.alphas;
                                }
                                if(thisDist.type.equals("KGN")){
                                    offKGN+=thisDist.offspring;
                                    groupOffKGN+=thisDist.groupOff;
                                    alphaKGN+=thisDist.alphas;
                                }
                            }
                            if(alphaNI==0){
                                alphaNI=1;
                            }
                            if(alphaKN==0){
                                alphaKN=1;
                            }
                            if(alphaKGN==0){
                                alphaKGN=1;
                            }
                            java.util.List<Double> Lmult = Arrays.asList( new Double[]{groupOffNI/alphaNI,groupOffKN/alphaKN,groupOffKGN/alphaKGN,offNI/alphaNI,offKN/alphaKN,offKGN/alphaKGN});
                            for(int i=0; i<Lmult.size(); i++){
                                if(Lmult.get(i).isNaN()){
                                    Lmult.set(i, 0.0);
                                }
                            }
                            double max = Math.ceil(Collections.max(Lmult));
                            double mult = 180/max;
                            g.setColor(Color.black);
                            g.drawLine(r.width/2+10, r.height-10, (int)(r.width*.75)-10, r.height-10);
                            g.drawLine((int)(r.width*.75), r.height/2+10, (int)(r.width*.75), r.height-10);
                            g.drawLine(r.width/2+10, r.height/2+10, r.width/2+10, r.height-10);
                            g.drawLine((int)(r.width*.75), r.height-10, r.width-10, r.height-10);
                            for(int i=1; i<max; i++){
                                g.drawLine(r.width/2+5, r.height-10-(int)Math.round(i*mult), r.width/2+15, r.height-10-(int)Math.round(i*mult));
                                g.drawLine((int)(r.width*.75)-5, r.height-10-(int)Math.round(i*mult), (int)(r.width*.75)+5, r.height-10-(int)Math.round(i*mult));
                            }
                            g.drawString(Double.toString(max), r.width/2+15, r.height/2+15);
                            g.drawString("Actual Offspring per Alpha", r.width/2+50, r.height);
                            g.drawString(Double.toString(max), (int)(r.width*.75)+5, r.height/2+15);
                            g.drawString("Group Offspring per Alpha", (int)(r.width*.75)+30, r.height);
                            //System.out.println(offNI/alphaNI + ", " + offKN/alphaKN + ", " + offKGN/alphaKGN + "  -  " + groupOffNI/alphaNI + ", " + groupOffKN/alphaKN + ", " + groupOffKGN/alphaKGN);
                            //System.out.println(offNI + ", " + groupOffNI + ", " + alphaNI);
                            //System.out.println(alphaNI + ", " + alphaKN + ", " + alphaKGN);
                            g.setColor(Color.darkGray);
                            g.fillRect(r.width/2+30, r.height-10-(int)Math.round(mult*offNI/alphaNI), 25, (int)Math.round(mult*offNI/alphaNI));
                            g.fillRect((int)(r.width*.75)+20, r.height-10-(int)Math.round(mult*groupOffNI/alphaNI), 25, (int)Math.round(mult*groupOffNI/alphaNI));
                            g.setColor(Color.green);
                            g.fillRect(r.width/2+60, r.height-10-(int)Math.round(mult*offKN/alphaKN), 25, (int)Math.round(mult*offKN/alphaKN));
                            g.fillRect((int)(r.width*.75)+50, r.height-10-(int)Math.round(mult*groupOffKN/alphaKN), 25, (int)Math.round(mult*groupOffKN/alphaKN));
                            g.setColor(Color.red);
                            g.fillRect(r.width/2+90, r.height-10-(int)Math.round(mult*offKGN/alphaKGN), 25, (int)Math.round(mult*offKGN/alphaKGN));
                            g.fillRect((int)(r.width*.75)+80, r.height-10-(int)Math.round(mult*groupOffKGN/alphaKGN), 25, (int)Math.round(mult*groupOffKGN/alphaKGN));
                        }
                        break;
                    case 'O':  // Static graphing option - graphs full dataset (for cues, for re-draws, etc)
                        g.setColor(Color.WHITE);
                        g.fillRect(0,0,r.width,r.height);
                        if(cue){
                            newx=10;
                            text = "Results: ";
                            g.setColor(Color.BLACK);
                            g.drawString(text, 20, 20);
                            if(games.getSelectedItem()=="Age Cue"){
                                iterator = 2;
                                length = 600;
                            } else {
                                iterator = (int)Math.ceil(Double.parseDouble(daysField.getText())/(double)600);
                                length = (int)Math.floor(Double.parseDouble(daysField.getText())/(double)iterator);
                            }
                            height = 300;
                            g.drawLine(10, height+50, length+10, height+50);
                            g.drawLine(10,50,10,height+50);
                            g.drawLine(10,275,15,275);
                            g.drawLine(10,200,15,200);
                            g.drawLine(10,125,15,125);
                            for(int x=0; x<=length; x+=100){
                                String temp = Integer.toString(x*iterator);
                                g.drawString(temp, x+10, height+70);
                            }
                            if(games.getSelectedItem()=="Spatial Cue"){
                                g.setColor(Color.RED);
                                for(int x = 0; x<thisResult.dailyProb.size(); x+=iterator){
                                    double prob = thisResult.dailyProb.get(x);
                                    newy = 50+height-(int)Math.round(prob*height);
                                    //System.out.println(prob);
                                    g.fillOval(newx-1, newy-1, 3, 3);
                                    g.setColor(Color.BLACK);
                                    refy = 50+height-(int)Math.round((1 - Math.pow((1-Math.PI*Math.pow(thisResult.meetRadius, 2)), thisResult.dailyProb.size()-x-2))*height);
                                    g.fillOval(newx-1, refy-1, 3, 3);
                                    g.setColor(Color.RED);
                                    newx++;
                                }
                            } else {
                                for(int x = 0; x<thisResult.ageCueDist.size(); x+=iterator){
                                    ArrayList<TypeDistribution> thisRes = thisResult.ageCueDist.get(x);
                                    for(int z = 0; z<thisRes.size(); z++){
                                        TypeDistribution thisDist = thisRes.get(z);
                                        newy = 50+height-(int)Math.round(thisDist.proportion*height);
                                        int redness = (int)Double.parseDouble(thisDist.type)*25;
                                        g.setColor(new Color(255,redness,redness));
                                        g.fillOval(newx-1,newy-1,3,3);
                                    }
                                    newx++;
                                }
                            }
                        } 
                        break;

                    case 'P':   //progress status in case of hidden graphPanel
                        g.setColor(Color.RED);
                        g.clearRect(2, r.height-22, 100, 22);   // to erase an area from GUI
                        g.drawString(Integer.toString((int)thisResult.gen)+"/"+genField.getText(), 2, r.height-2);  // to show total generations processed
                        g.setColor(Color.GREEN);
                        g.drawString(repeat+"/"+iterationsField.getText(), 77, r.height-2); // to show total runs processed
                        if(thisResult.gen==Double.parseDouble(genField.getText())){repeat++;}  // to update repeat for each run
                        if(flag==4){repeat=1;flag=0;}   //when start button pressed
                        if(Double.parseDouble(iterationsField.getText())<repeat){repeat=1;}
                        break;

                    case 'D': // Done
                        g.setColor(Color.BLUE);
                        g.clearRect(2, r.height-22, 120, 22);
                        g.drawString("Run Finished", 10, r.height-2);
                        flag=2;
                        break;
                    //end of switch
                }
            }
        }

        @Override
        public void resultReceived(resultEvent event) {     
            thisResult = event.getResult();
            if(!thisResult.message.isEmpty()){
                action='G';
                this.paintImmediately(r);
            }
            if(thisResult.resultType.matches("Spatial Cue") || thisResult.resultType.matches("Age Cue")){
                action = 'O';
                cue = true;
                this.paintImmediately(r);
            } else {
                cue = false;
                if(graphBox.getSelectedItem()=="Yes"){  // when user opts to show graphPanel
                    action = 'C';
                    this.paintImmediately(r);
                }
                else{   // when user opts to not show graphPanel
                    action = 'P';   // to display progress of the simulation runs
                    this.paintImmediately(r);
                }
            }
        }
    }

    class DirectorySelectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event){
            JFileChooser directory = new JFileChooser();
            directory.setFileSelectionMode(2);
            directory.setDialogTitle("Select the directory to save results");
            directory.showSaveDialog(theFrame);
            directoryField.setText(directory.getSelectedFile().getPath());
            theFrame.repaint();
        }
    }
    
    class gamesListener implements ActionListener  {

        @Override
        public void actionPerformed(ActionEvent event) {
            action = 'R';
            southPanel.add(descPanel);
            //drawPanel.repaint();
            detailsPanel.removeAll();
            detailsPanel.setLayout(new BoxLayout(detailsPanel,BoxLayout.Y_AXIS));
            detailsPanel.setBackground(Color.LIGHT_GRAY);
            parameters = new JPanel();
            parameters.setBackground(Color.LIGHT_GRAY);
            panel1 = new JPanel();
            panel1.setBackground(Color.DARK_GRAY);
            panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
            panel1.setMaximumSize(paramPan);
            panel2 = new JPanel();
            panel2.setBackground(Color.DARK_GRAY);
            panel2.setLayout(new BoxLayout(panel2,BoxLayout.Y_AXIS));
            panel2.setMaximumSize(paramPan);
            panel3 = new JPanel();
            panel3.setBackground(Color.DARK_GRAY);
            panel3.setLayout(new BoxLayout(panel3,BoxLayout.Y_AXIS));
            panel3.setMaximumSize(paramPan);
            panel4 = new JPanel();
            panel4.setBackground(Color.DARK_GRAY);
            panel4.setLayout(new BoxLayout(panel4,BoxLayout.Y_AXIS));
            panel4.setMaximumSize(paramPan);
            panel5 = new JPanel();
            panel5.setBackground(Color.DARK_GRAY);
            panel5.setLayout(new BoxLayout(panel5,BoxLayout.Y_AXIS));
            panel5.setMaximumSize(paramPan);
            JPanel hawkPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
            hawkPan.setBackground(Color.red);
            JPanel dovePan = new JPanel(new FlowLayout(FlowLayout.LEFT));
            dovePan.setBackground(Color.blue);
            alldPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
            alldPan.setBackground(Color.red);
            allcPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
            allcPan.setBackground(Color.blue);
            tftPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
            tftPan.setBackground(Color.green);
            uPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
            uPan.setBackground(Color.darkGray);
            sPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
            sPan.setBackground(Color.cyan);
            scPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
            scPan.setBackground(Color.blue);
            scscPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
            scscPan.setBackground(Color.red);
            niPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
            niPan.setBackground(Color.darkGray);
            knPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
            knPan.setBackground(Color.green);
            kgnPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
            kgnPan.setBackground(Color.red);
            CoopPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
            CoopPan.setBackground(Color.magenta);
            x1Pan = new JPanel(new FlowLayout(FlowLayout.LEFT));
            x1Pan.setBackground(Color.gray);
            x2Pan = new JPanel(new FlowLayout(FlowLayout.LEFT));
            x2Pan.setBackground(Color.gray);
            x3Pan = new JPanel(new FlowLayout(FlowLayout.LEFT));
            x3Pan.setBackground(Color.gray);
            x4Pan = new JPanel(new FlowLayout(FlowLayout.LEFT));
            x4Pan.setBackground(Color.gray);
            x5Pan = new JPanel(new FlowLayout(FlowLayout.LEFT));
            x5Pan.setBackground(Color.gray);
            DefPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
            DefPan.setBackground(Color.cyan);
            String[] slowFast = {"Slow", "Fast"};
            fullTime = new JComboBox(slowFast);
            fullTime.setSelectedIndex(1);
            fullTime.setFont(theFont);
            String[] lerRate = {"0", "1", "2", "3", "4", "Custom"};
            lerBox = new JComboBox(lerRate);
            lerBox.setSelectedIndex(3);
            lerBox.setFont(theFont);
            benefitField = new JTextField(3);
            benefitField.setFont(theFont);
            benefit1Field = new JTextField(3);
            benefit1Field.setFont(theFont);
            benefit2Field = new JTextField(3);
            benefit2Field.setFont(theFont);
            costField = new JTextField(3);
            costField.setFont(theFont);
            popsizeField = new JTextField(5);
            popsizeField.setFont(theFont);
            genField = new JTextField(5);
            genField.setFont(theFont);
            basefitField = new JTextField(5);
            basefitField.setFont(theFont);
            groupsizeField = new JTextField(5);
            groupsizeField.setFont(theFont);
            punField = new JTextField(5);
            punField.setFont(theFont);
            puncostField = new JTextField(5);
            puncostField.setFont(theFont);
            bandnumField = new JTextField(5);
            bandnumField.setFont(theFont);
            ospercentField = new JTextField(5);
            ospercentField.setFont(theFont);
            //nShotField = new JTextField(5);
            //nShotField.setFont(theFont);
            x1ShotField = new JTextField(3);
            x1ShotField.setFont(theFont);
            x2ShotField = new JTextField(3);
            x2ShotField.setFont(theFont);
            x3ShotField = new JTextField(3);
            x3ShotField.setFont(theFont);
            x4ShotField = new JTextField(3);
            x4ShotField.setFont(theFont);
            x5ShotField = new JTextField(3);
            x5ShotField.setFont(theFont);
            wGroupField = new JTextField(5);
            wGroupField.setFont(theFont);
            wField = new JTextField(5);
            wField.setFont(theFont);
            dField = new JTextField(5);
            dField.setFont(theFont);
            String[] graphOpts = {"Yes", "No"}; // showGraph options
            graphBox = new JComboBox(graphOpts);
            graphBox.setSelectedIndex(0);   // by default set as "Yes"
            graphBox.setFont(theFont);
            PGTField = new JTextField(5);
            PGTField.setFont(theFont);
            GSPField = new JTextField(5);
            GSPField.setFont(theFont);
            CTField = new JTextField(5);
            CTField.setFont(theFont);
            ICTField = new JTextField(5);
            ICTField.setFont(theFont);
            PTField = new JTextField(5);
            PTField.setFont(theFont);
            RTField = new JTextField(5);
            RTField.setFont(theFont);
            WTField = new JTextField(5);
            WTField.setFont(theFont);
            PCField = new JTextField(5);
            PCField.setFont(theFont);
            PDField = new JTextField(5);
            PDField.setFont(theFont);
            String[] ecologies = {"Mixed", "Non-mixed"};
            ecologyBox = new JComboBox(ecologies);
            ecologyBox.setSelectedIndex(0);
            ecologyBox.setFont(theFont);
            errorField = new JTextField(5);
            errorField.setFont(theFont);
            PIDField = new JTextField(5);
            PIDField.setFont(theFont);
            GIDField = new JTextField(5);
            GIDField.setFont(theFont);
            PDSField = new JTextField(5);
            PDSField.setFont(theFont);
            GDSField = new JTextField(5);
            GDSField.setFont(theFont);
            directoryField = new JTextField(20);
            directoryField.setFont(theFont);
            iterationsField = new JTextField(5);
            iterationsField.setFont(theFont);
            daysField = new JTextField(5);
            daysField.setFont(theFont);
            distField = new JTextField(5);
            distField.setFont(theFont);
            meetRadiusField = new JTextField(5);
            meetRadiusField.setFont(theFont);
            String[] wallsOpts = {"True", "False"};
            wallsBox = new JComboBox(wallsOpts);
            String[] dists = {"Normal", "Uniform"};
            distroBox = new JComboBox(dists);
            String[] cogs = {"Info Choice", "Info Weight"};
            cognitionBox = new JComboBox(cogs);
            kindmutationrateField = new JTextField(3);
            kindmutationrateField.setFont(theFont);
            mutationrateField = new JTextField(3);
            mutationrateField.setFont(theFont);
            mutationsizeField = new JTextField(3);
            mutationsizeField.setFont(theFont);
            TFTField = new JTextField(3);
            TFTField.setFont(theFont);
            ALLCField = new JTextField(3);
            ALLCField.setFont(theFont);
            ALLDField = new JTextField(3);
            ALLDField.setFont(theFont);
            successRateField = new JTextField(3);
            successRateField.setFont(theFont);
            uField = new JTextField(3);
            uField.setFont(theFont);
            sField=new JTextField(3);
            sField.setFont(theFont);
            scField=new JTextField(3);
            scField.setFont(theFont);
            scscField=new JTextField(3);
            scscField.setFont(theFont);
            lerField = new JTextField(3);
            lerField.setFont(theFont);
            gestField = new JTextField(3);
            gestField.setFont(theFont);
            nurseField = new JTextField(3);
            nurseField.setFont(theFont);
            noInfField = new JTextField(3);
            noInfField.setFont(theFont);
            killGestNurseField = new JTextField(3);
            killGestNurseField.setFont(theFont);
            killNurseField = new JTextField(3);
            killNurseField.setFont(theFont);
            tenureField = new JTextField(3);
            tenureField.setFont(theFont);
            SWField = new JTextField(3);
            SWField.setFont(theFont);
            TF2TpercField = new JTextField(3);
            TF2TpercField.setFont(theFont);
            HTFTpercField = new JTextField(3);
            HTFTpercField.setFont(theFont);
            alld = new JCheckBox("ALLD", true);
            alld.setFont(theFont);
            allc = new JCheckBox("ALLC", true);
            allc.setFont(theFont);
            tft = new JCheckBox("TFT", true);
            tft.setFont(theFont);
            u = new JCheckBox("Mates w/ Anyone", true);
            u.setFont(theFont);
            s = new JCheckBox("Avoids Sibs", true);
            s.setFont(theFont);
            sc = new JCheckBox("Avoids Cuzs+", true);
            sc.setFont(theFont);
            scsc = new JCheckBox("Avoids 2nd Cuzs+", true);
            scsc.setFont(theFont);
            kn = new JCheckBox("Infanticidal at Takeover", true);
            kn.setFont(theFont);
            kgn = new JCheckBox("Infanticidal at Takeover+", true);
            kgn.setFont(theFont);
            ni = new JCheckBox("Not Infanticidal", true);
            ni.setFont(theFont);
            tf2t = new JCheckBox("TF2T?", true);
            tf2t.setFont(theFont);
            htft = new JCheckBox("HTFT?", true);
            htft.setFont(theFont);
            x1shot = new JCheckBox("%1shot");
            x1shot.setFont(theFont);
            x2shot = new JCheckBox("%2shot");
            x2shot.setFont(theFont);
            x3shot = new JCheckBox("%3shot");
            x3shot.setFont(theFont);
            x4shot = new JCheckBox("%4shot");
            x4shot.setFont(theFont);
            x5shot = new JCheckBox("%5shot");
            x5shot.setFont(theFont);
            JLabel hawkLabel = new JLabel("Hawks");
            hawkLabel.setFont(theFont);
            hawkLabel.setForeground(Color.white);
            JLabel doveLabel = new JLabel("Doves");
            doveLabel.setFont(theFont);
            doveLabel.setForeground(Color.white);
            JLabel fulltimeLabel = new JLabel("Simulation Speed");
            fulltimeLabel.setForeground(Color.WHITE);
            fulltimeLabel.setFont(theFont);
            JLabel benefitLabel = new JLabel("Benefit");
            benefitLabel.setFont(theFont);
            benefitLabel.setForeground(Color.WHITE);
            JLabel benefit1Label = new JLabel("Benefit-Solo");
            benefit1Label.setFont(theFont);
            benefit1Label.setForeground(Color.WHITE);
            JLabel benefit2Label = new JLabel("Benefit-PG");
            benefit2Label.setFont(theFont);
            benefit2Label.setForeground(Color.WHITE);
            JLabel costLabel = new JLabel("Cost");
            costLabel.setFont(theFont);
            costLabel.setForeground(Color.WHITE);
            JLabel punLabel = new JLabel("Pun. Imposed");
            punLabel.setForeground(Color.WHITE);
            punLabel.setFont(theFont);
            JLabel puncostLabel = new JLabel("Pun. Expense");
            puncostLabel.setForeground(Color.WHITE);
            puncostLabel.setFont(theFont);
            JLabel popsizeLabel = new JLabel("Pop. Size");
            popsizeLabel.setFont(theFont);
            popsizeLabel.setForeground(Color.WHITE);
            JLabel numSWLabel = new JLabel("# Soc Worlds");
            numSWLabel.setFont(theFont);
            numSWLabel.setForeground(Color.WHITE);
            JLabel bandnumLabel = new JLabel("# Bands");
            bandnumLabel.setForeground(Color.WHITE);
            bandnumLabel.setFont(theFont);
            JLabel basefitLabel = new JLabel("Base Fitness");
            basefitLabel.setForeground(Color.WHITE);
            basefitLabel.setFont(theFont);
            JLabel genLabel = new JLabel("# Gens");
            genLabel.setFont(theFont);
            genLabel.setForeground(Color.RED);
            JLabel groupsizeLabel = new JLabel("Group Size");
            groupsizeLabel.setForeground(Color.WHITE);
            groupsizeLabel.setFont(theFont);
            JLabel mutationsizeLabel = new JLabel("Mut. Size (sd)");
            mutationsizeLabel.setFont(theFont);
            mutationsizeLabel.setForeground(Color.WHITE);
            JLabel mutationrateLabel = new JLabel("Param Mut. Rate");
            mutationrateLabel.setFont(theFont);
            mutationrateLabel.setForeground(Color.WHITE);
            JLabel kindmutationrateLabel = new JLabel("Kind Mut. Rate");
            kindmutationrateLabel.setFont(theFont);
            kindmutationrateLabel.setForeground(Color.WHITE);
            JLabel directoryLabel = new JLabel("Save Directory:");
            directoryLabel.setFont(theFont);
            directoryLabel.setForeground(Color.WHITE);
            JLabel errorLabel = new JLabel("Error Rate");
            errorLabel.setFont(theFont);
            errorLabel.setForeground(Color.WHITE);
            JLabel successRateLabel = new JLabel("Hunting Success %");
            successRateLabel.setFont(theFont);
            successRateLabel.setForeground(Color.WHITE);
            JLabel lerLabel = new JLabel("Lethal Equivalent Rate");
            lerLabel.setFont(theFont);
            lerLabel.setForeground(Color.WHITE);
            JLabel nurseLabel = new JLabel("Nursing Length");
            nurseLabel.setFont(theFont);
            nurseLabel.setForeground(Color.WHITE);
            JLabel gestLabel = new JLabel("Gestation Length");
            gestLabel.setFont(theFont);
            gestLabel.setForeground(Color.WHITE);
            JLabel tenureLabel = new JLabel("Ave. Tenure");
            tenureLabel.setFont(theFont);
            tenureLabel.setForeground(Color.WHITE);
            JLabel ospercentLabel = new JLabel("% N-Shot v W");
            ospercentLabel.setForeground(Color.WHITE);
            ospercentLabel.setFont(theFont);
            JLabel nShotLabel = new JLabel("% 1,2,3,4,5 shot");
            nShotLabel.setForeground(Color.WHITE);
            nShotLabel.setFont(theFont);
            JLabel iterationsLabel = new JLabel("# Runs / Combo");
            iterationsLabel.setForeground(Color.GREEN);
            iterationsLabel.setFont(theFont);
            JLabel wLabel = new JLabel("w (lifetime)");
            wLabel.setForeground(Color.WHITE);
            wLabel.setFont(theFont);
            JLabel inxnLabel = new JLabel("Expected Inxns");
            inxnLabel.setForeground(Color.WHITE);
            inxnLabel.setFont(theFont);
            JLabel dLabel = new JLabel("d (discrim)");
            dLabel.setForeground(Color.WHITE);
            dLabel.setFont(theFont);
            JLabel graphLabel = new JLabel("Show Graph?");
            graphLabel.setForeground(Color.WHITE);
            graphLabel.setFont(theFont);
            JLabel PGTLabel = new JLabel("PGT %");
            PGTLabel.setForeground(Color.GRAY);
            PGTLabel.setFont(theFont);
            JLabel GSPLabel = new JLabel("GSP");
            GSPLabel.setForeground(Color.GREEN);
            GSPLabel.setFont(theFont);
            JLabel CTLabel = new JLabel("CT %");
            CTLabel.setForeground(Color.BLUE);
            CTLabel.setFont(theFont);
            JLabel ICTLabel = new JLabel("ICT %");
            ICTLabel.setForeground(Color.BLUE);
            ICTLabel.setFont(theFont);
            JLabel PTLabel = new JLabel("PT %");
            PTLabel.setForeground(Color.RED);
            PTLabel.setFont(theFont);
            JLabel RTLabel = new JLabel("RT %");
            RTLabel.setForeground(Color.GREEN);
            RTLabel.setFont(theFont);
            JLabel WTLabel = new JLabel("WT %");
            WTLabel.setForeground(Color.CYAN);
            WTLabel.setFont(theFont);
            JLabel PCLabel = new JLabel("PC %");
            PCLabel.setForeground(Color.BLUE);
            PCLabel.setFont(theFont);
            JLabel PDLabel = new JLabel("PD %");
            PDLabel.setForeground(Color.RED);
            PDLabel.setFont(theFont);
            JLabel wGroupLabel = new JLabel("w (group)");
            wGroupLabel.setForeground(Color.WHITE);
            wGroupLabel.setFont(theFont);
            JLabel daysLabel = new JLabel("# Days");
            daysLabel.setForeground(Color.WHITE);
            daysLabel.setFont(theFont);
            JLabel distLabel = new JLabel("SD of Walk");
            distLabel.setForeground(Color.WHITE);
            distLabel.setFont(theFont);
            JLabel meetRadiusLabel = new JLabel("Meeting Radius");
            meetRadiusLabel.setForeground(Color.WHITE);
            meetRadiusLabel.setFont(theFont);
            JLabel wallsLabel = new JLabel("Hard Walls?");
            wallsLabel.setForeground(Color.WHITE);
            wallsLabel.setFont(theFont);
            JLabel ecologyLabel = new JLabel("Ecology");
            ecologyLabel.setForeground(Color.WHITE);
            ecologyLabel.setFont(theFont);
            JLabel distTypeLabel = new JLabel("ID Dist");
            distTypeLabel.setForeground(Color.WHITE);
            distTypeLabel.setFont(theFont);
            JLabel PIDLabel = new JLabel("PID Param");
            PIDLabel.setForeground(Color.WHITE);
            PIDLabel.setFont(theFont);
            JLabel GIDLabel = new JLabel("GID Param");
            GIDLabel.setForeground(Color.WHITE);
            GIDLabel.setFont(theFont);
            JLabel PDSLabel = new JLabel("PDS Param");
            PDSLabel.setForeground(Color.WHITE);
            PDSLabel.setFont(theFont);
            JLabel GDSLabel = new JLabel("GDS Param");
            GDSLabel.setForeground(Color.WHITE);
            GDSLabel.setFont(theFont);
            JLabel CogTypeLabel = new JLabel("Cognition Model");
            CogTypeLabel.setForeground(Color.WHITE);
            CogTypeLabel.setFont(theFont);
            CogTypeLabel.setHorizontalTextPosition(2);
            //JLabel TF2TpercLabel = new JLabel("G1 % of TF2T (vs TFT)");
            //TF2TpercLabel.setForeground(Color.MAGENTA);
            //TF2TpercLabel.setFont(theFont);
            //JLabel HTFTpercLabel = new JLabel("G1 % of HTFT (vs ALLD)");
            //HTFTpercLabel.setForeground(Color.CYAN);
            //HTFTpercLabel.setFont(theFont);
            JButton directoryButton = new JButton("Browse");
            directoryButton.addActionListener(new DirectorySelectListener());
            directoryPanel = new JPanel();
            directoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            directoryPanel.setBackground(Color.LIGHT_GRAY);
            directoryPanel.add(directoryLabel);
            directoryPanel.add(directoryField);
            directoryPanel.add(directoryButton);

            JComboBox games = (JComboBox)event.getSource();
            if(games.getSelectedItem()=="Hawk/Dove"){
               try {
                  URL test = this.getClass().getResource("files/hd.html");
                  descPane.removeAll();
                  descPane.setPage(test);
               } catch (Exception ex) {
                   Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
               }
               hawkPan.add(hawkLabel);
               dovePan.add(doveLabel);
               String[] bens = {".1",".2",".3",".4",".5",".6", "Custom"};
                benefitBox = new JComboBox(bens);
                benefitBox.setSelectedIndex(2);
                benefitBox.setFont(theFont);
                String[] costs = {".1",".2",".3",".4",".5",".6", "Custom"};
                costBox = new JComboBox(costs);
                costBox.setSelectedIndex(5);
                costBox.setFont(theFont);
                benefitField.setText("");
                costField.setText("");
                popsizeField.setText("1024");
                genField.setText("600");
                panel1.add(benefitLabel);
                panel1.add(benefitBox);
                panel1.add(benefitField);
                panel2.add(costLabel);
                panel2.add(costBox);
                panel2.add(costField);
                panel3.add(hawkPan);
                panel3.add(dovePan);
                panel4.add(popsizeLabel);
                panel4.add(popsizeField);
                panel4.add(genLabel);
                panel4.add(genField);
                panel4.add(fulltimeLabel);
                panel4.add(fullTime);
                parameters.add(panel1);
                parameters.add(panel2);
                parameters.add(panel3);
                parameters.add(panel4);
                detailsPanel.add(parameters);
                if(!release){
                    detailsPanel.add(directoryPanel);
                }
                theFrame.validate();
            }

            if(games.getSelectedItem()=="Vampire Bats"){
                try {
                  URL test = this.getClass().getResource("files/bats.html");
                  descPane.removeAll();
                  descPane.setPage(test);
               } catch (Exception ex) {
                   Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
               }
                benefitLabel.setText("Gift Size (0-1)");
                benefitField.setText("");
                String[] bens = {".1",".2",".3",".4",".5", "Custom"};
                benefitBox = new JComboBox(bens);
                benefitBox.setSelectedIndex(2);
                benefitBox.setFont(theFont);
                //costField.setText("");
                //costBox = new JComboBox(bens);
                //costBox.setSelectedIndex(2);
                //costBox.setFont(theFont);
                TFTField.setText(".3");
                ALLCField.setText(".3");
                ALLDField.setText(".4");
                alldPan.add(ALLDField);
                alldPan.add(alld);
                allcPan.add(ALLCField);
                allcPan.add(allc);
                tftPan.add(TFTField);
                tftPan.add(tft);
                popsizeField.setText("");
                genField.setText("");
                String[] gens = {"250","500","1000", "Custom"};
                genBox = new JComboBox(gens);
                genBox.setFont(theFont);
                genBox.setSelectedIndex(2);
                successRateField.setText("");
                String[] sRs = {".5", ".6", ".7", "Custom"};
                successRateBox = new JComboBox(sRs);
                successRateBox.setFont(theFont);
                successRateBox.setSelectedIndex(1);
                String[] pops = {"12","25","36", "Custom"};
                popsizeLabel.setText("Roost Size");
                popsizeBox = new JComboBox(pops);
                popsizeBox.setFont(theFont);
                panel1.add(benefitLabel);
                panel1.add(benefitBox);
                panel1.add(benefitField);
                //panel1.add(costLabel);
                //panel1.add(costBox);
                //panel1.add(costField);
                panel2.add(alldPan);
                panel2.add(tftPan);
                panel2.add(allcPan);
                panel1.add(popsizeLabel);
                panel1.add(popsizeBox);
                panel1.add(popsizeField);
                panel3.add(daysLabel);
                panel3.add(genBox);
                panel3.add(genField);
                panel3.add(successRateLabel);
                panel3.add(successRateBox);
                panel3.add(successRateField);
                panel1.add(fulltimeLabel);
                panel1.add(fullTime);
                parameters.add(panel1);
                parameters.add(panel2);
                parameters.add(panel3);
                //parameters.add(panel4);
                detailsPanel.add(parameters);
                if(!release){
                    detailsPanel.add(directoryPanel);
                }
                theFrame.validate();
            }

            if(games.getSelectedItem()=="Incest Avoidance"){
                try {
                  URL test = this.getClass().getResource("files/inc.html");
                  descPane.removeAll();
                  descPane.setPage(test);
               } catch (Exception ex) {
                   Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
               }
                String[] popSize = {"36","64","81", "Custom"};
                popsizeBox = new JComboBox(popSize);
                popsizeBox.setFont(theFont);
                popsizeField.setText("");
                genField.setText("1000");
                mutationrateField.setText(".001");
                uField.setText(".25");
                sField.setText(".25");
                scField.setText(".25");
                scscField.setText(".25");
                uPan.add(uField);
                uPan.add(u);
                sPan.add(sField);
                sPan.add(s);
                scPan.add(scField);
                scPan.add(sc);
                scscPan.add(scscField);
                scscPan.add(scsc);
                panel1.add(uPan);
                panel1.add(sPan);
                panel1.add(scPan);
                panel1.add(scscPan);
                panel2.add(popsizeLabel);
                panel2.add(popsizeBox);
                panel2.add(popsizeField);
                panel2.add(genLabel);
                panel2.add(genField);
                panel3.add(mutationrateLabel);
                panel3.add(mutationrateField);
                panel3.add(lerLabel);
                panel3.add(lerBox);
                panel3.add(lerField);
                panel3.add(fulltimeLabel);
                panel3.add(fullTime);
                parameters.add(panel1);
                parameters.add(panel2);
                parameters.add(panel3);
                detailsPanel.add(parameters);
                if(!release){
                    detailsPanel.add(directoryPanel);
                }
                theFrame.validate();
            }

            if(games.getSelectedItem()=="Infanticide"){
                try {
                  URL test = this.getClass().getResource("files/inf.html");
                  descPane.removeAll();
                  descPane.setPage(test);
               } catch (Exception ex) {
                   Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
               }
                genLabel.setText("Months");
                genField.setText("1000");
                mutationrateField.setText(".001");
                gestField.setText("7");
                nurseField.setText("11");
                tenureField.setText("24");
                noInfField.setText(".4");
                killGestNurseField.setText(".3");
                killNurseField.setText(".3");
                niPan.add(noInfField);
                niPan.add(ni);
                knPan.add(killNurseField);
                knPan.add(kn);
                kgnPan.add(killGestNurseField);
                kgnPan.add(kgn);

                panel1.add(gestLabel);
                panel1.add(gestField);
                panel1.add(nurseLabel);
                panel1.add(nurseField);
                panel1.add(tenureLabel);
                panel1.add(tenureField);
                
                panel2.add(niPan);
                panel2.add(knPan);
                panel2.add(kgnPan);

                panel3.add(genLabel);
                panel3.add(genField);
                panel3.add(mutationrateLabel);
                panel3.add(mutationrateField);
                panel3.add(fulltimeLabel);
                panel3.add(fullTime);

                parameters.add(panel1);
                parameters.add(panel2);
                parameters.add(panel3);
                detailsPanel.add(parameters);
                if(!release){
                    detailsPanel.add(directoryPanel);
                }
                theFrame.validate();
            }

            if(games.getSelectedItem()=="Introduction"){
                try {
                  URL test = this.getClass().getResource("files/general.html");
                  descPane.removeAll();
                  descPane.setPage(test);
               } catch (Exception ex) {
                   Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
               }
                //detailsPanel.add(logoPanel);
                theFrame.validate();
            }
            if(games.getSelectedItem()=="Belief Formation"){
                benefitField.setText("5");
                costField.setText("1");
                basefitField.setText("10");
                popsizeField.setText("200");
                genField.setText("3000");
                iterationsField.setText("1");
                mutationsizeField.setText(".025");
                mutationrateField.setText(".05");
                errorField.setText("0");
                ospercentField.setText(".5");
                wField.setText(".9");
                dField.setText("2");
                panel1.add(benefitLabel);
                panel1.add(benefitField);
                panel1.add(costLabel);
                panel1.add(costField);
                panel1.add(basefitLabel);
                panel1.add(basefitField);
                panel2.add(popsizeLabel);
                panel2.add(popsizeField);
                panel2.add(genLabel);
                panel2.add(genField);
                panel2.add(iterationsLabel);
                panel2.add(iterationsField);
                panel3.add(mutationsizeLabel);
                panel3.add(mutationsizeField);
                panel3.add(mutationrateLabel);
                panel3.add(mutationrateField);
                panel3.add(errorLabel);
                panel3.add(errorField);
                panel4.add(ospercentLabel);
                panel4.add(ospercentField);
                panel4.add(wLabel);
                panel4.add(wField);
                panel4.add(dLabel);
                panel4.add(dField);
                panel4.add(graphLabel);
                panel4.add(graphBox);
                parameters.add(panel1);
                parameters.add(panel2);
                parameters.add(panel3);
                parameters.add(panel4);
                detailsPanel.add(parameters);
                detailsPanel.add(directoryPanel);
                theFrame.validate();

            }
            if(games.getSelectedItem()=="Belief Formation Redux"){
                benefitField.setText("5");
                costField.setText("1");
                basefitField.setText("10");
                popsizeField.setText("200");
                genField.setText("3000");
                iterationsField.setText("1");
                mutationsizeField.setText(".025");
                mutationrateField.setText(".025");
                kindmutationrateField.setText(".001");
                errorField.setText("0");
                ospercentField.setText(".5");
                x1ShotField.setText("1");
                x2ShotField.setText("0");
                x3ShotField.setText("0");
                x4ShotField.setText("0");
                x5ShotField.setText("0");
                x1shot.setSelected(true);
                x2shot.setSelected(false);
                x3shot.setSelected(false);
                x4shot.setSelected(false);
                x5shot.setSelected(false);
                wField.setText(".9");
                dField.setText("2");
                TF2TpercField.setText(".5");
                HTFTpercField.setText(".5");
                panel1.add(benefitLabel);
                panel1.add(benefitField);
                panel1.add(costLabel);
                panel1.add(costField);
                panel1.add(basefitLabel);
                panel1.add(basefitField);
                panel1.add(popsizeLabel);
                panel1.add(popsizeField);
                CoopPan.add(TF2TpercField);
                CoopPan.add(tf2t);
                DefPan.add(HTFTpercField);
                DefPan.add(htft);
                panel2.add(CoopPan);
                panel2.add(DefPan);
                //panel2.add(TF2TpercLabel);
                //panel2.add(TF2TpercField);
                //panel2.add(HTFTpercLabel);
                //panel2.add(HTFTpercField);
                panel2.add(genLabel);
                panel2.add(genField);
                panel2.add(iterationsLabel);
                panel2.add(iterationsField);
                x1Pan.add(x1ShotField);
                x1Pan.add(x1shot);
                x2Pan.add(x2ShotField);
                x2Pan.add(x2shot);
                x3Pan.add(x3ShotField);
                x3Pan.add(x3shot);
                x4Pan.add(x4ShotField);
                x4Pan.add(x4shot);
                x5Pan.add(x5ShotField);
                x5Pan.add(x5shot);
                panel3.add(x1Pan);
                panel3.add(x2Pan);
                panel3.add(x3Pan);
                panel3.add(x4Pan);
                panel3.add(x5Pan);
                panel4.add(mutationsizeLabel);
                panel4.add(mutationsizeField);
                panel4.add(mutationrateLabel);
                panel4.add(mutationrateField);
                panel4.add(kindmutationrateLabel);
                panel4.add(kindmutationrateField);
                panel4.add(errorLabel);
                panel4.add(errorField);
                panel5.add(ospercentLabel);
                panel5.add(ospercentField);
                panel5.add(wLabel);
                panel5.add(wField);
                panel5.add(dLabel);
                panel5.add(dField);
                panel5.add(graphLabel);
                panel5.add(graphBox);
                parameters.add(panel1);
                parameters.add(panel2);
                parameters.add(panel3);
                parameters.add(panel4);
                parameters.add(panel5);
                detailsPanel.add(parameters);
                detailsPanel.add(directoryPanel);
                theFrame.validate();

            }
            if(games.getSelectedItem()=="Belief Implementation"){
                benefitField.setText("5");
                costField.setText("1");
                basefitField.setText("10");
                popsizeField.setText("200");
                genField.setText("3000");
                iterationsField.setText("1");
                mutationsizeField.setText(".025");
                mutationrateField.setText(".05");
                errorField.setText("0");
                ospercentField.setText(".5");
                wField.setText(".9");
                dField.setText("2");
                panel1.add(benefitLabel);
                panel1.add(benefitField);
                panel1.add(costLabel);
                panel1.add(costField);
                panel1.add(basefitLabel);
                panel1.add(basefitField);
                panel2.add(popsizeLabel);
                panel2.add(popsizeField);
                panel2.add(genLabel);
                panel2.add(genField);
                panel2.add(iterationsLabel);
                panel2.add(iterationsField);
                panel3.add(mutationsizeLabel);
                panel3.add(mutationsizeField);
                panel3.add(mutationrateLabel);
                panel3.add(mutationrateField);
                panel3.add(errorLabel);
                panel3.add(errorField);
                panel4.add(ospercentLabel);
                panel4.add(ospercentField);
                panel4.add(wLabel);
                panel4.add(wField);
                panel4.add(dLabel);
                panel4.add(dField);
                panel4.add(graphLabel);
                panel4.add(graphBox);
                parameters.add(panel1);
                parameters.add(panel2);
                parameters.add(panel3);
                parameters.add(panel4);
                detailsPanel.add(parameters);
                detailsPanel.add(directoryPanel);
                theFrame.validate();

            }
            if(games.getSelectedItem()=="Public Goods"){
                benefitField.setText("5");
                costField.setText("1");
                basefitField.setText("0");
                punField.setText("1");
                puncostField.setText("1");
                groupsizeField.setText("5");
                popsizeField.setText("100");
                genField.setText("2400");
                ICTField.setText(".2");
                PTField.setText(".2");
                RTField.setText(".8");
                WTField.setText(".8");
                iterationsField.setText("1");
                mutationsizeField.setText(".025");
                mutationrateField.setText(".05");
                wGroupField.setText(".9");
                wField.setText(".95");
                panel1.add(benefitLabel);
                panel1.add(benefitField);
                panel1.add(costLabel);
                panel1.add(costField);
                panel1.add(basefitLabel);
                panel1.add(basefitField);
                panel1.add(punLabel);
                panel1.add(punField);
                panel2.add(puncostLabel);
                panel2.add(puncostField);
                panel2.add(groupsizeLabel);
                panel2.add(groupsizeField);
                panel2.add(popsizeLabel);
                panel2.add(popsizeField);
                panel2.add(genLabel);
                panel2.add(genField);
                panel3.add(mutationrateLabel);
                panel3.add(mutationrateField);
                panel3.add(ICTLabel);
                panel3.add(ICTField);
                panel3.add(PTLabel);
                panel3.add(PTField);
                panel4.add(RTLabel);
                panel4.add(RTField);
                panel4.add(WTLabel);
                panel4.add(WTField);
                panel4.add(iterationsLabel);
                panel4.add(iterationsField);
                panel4.add(mutationsizeLabel);
                panel4.add(mutationsizeField);
                panel4.add(wLabel);
                panel4.add(wField);
                panel4.add(wGroupLabel);
                panel4.add(wGroupField);
                parameters.add(panel1);
                parameters.add(panel2);
                parameters.add(panel3);
                parameters.add(panel4);
                detailsPanel.add(parameters);
                detailsPanel.add(directoryPanel);
                theFrame.validate();
            }
            if(games.getSelectedItem()=="Public Goods Evolve"){
                benefit1Field.setText(".2");
                benefit2Field.setText(".8");
                costField.setText("1");
                punField.setText(".9");
                puncostField.setText("1");
                popsizeField.setText("50");
                genField.setText("5000");
                iterationsField.setText("1");
                mutationsizeField.setText(".05");
                mutationrateField.setText(".05");
                wField.setText(".99");
                PGTField.setText(".1");
                GSPField.setText("2");
                CTField.setText(".1");
                PTField.setText(".1");
                panel1.add(benefit1Label);
                panel1.add(benefit1Field);
                panel1.add(benefit2Label);
                panel1.add(benefit2Field);
                panel1.add(costLabel);
                panel1.add(costField);
                panel1.add(punLabel);
                panel1.add(punField);
                panel2.add(puncostLabel);
                panel2.add(puncostField);
                panel2.add(popsizeLabel);
                panel2.add(popsizeField);
                panel4.add(genLabel);
                panel4.add(genField);
                panel2.add(mutationrateLabel);
                panel2.add(mutationrateField);
                panel2.add(mutationsizeLabel);
                panel2.add(mutationsizeField);
                panel3.add(PGTLabel);
                panel3.add(PGTField);
                panel3.add(GSPLabel);
                panel3.add(GSPField);
                panel3.add(CTLabel);
                panel3.add(CTField);
                panel3.add(PTLabel);
                panel3.add(PTField);
                panel4.add(iterationsLabel);
                panel4.add(iterationsField);
                panel4.add(wLabel);
                panel4.add(wField);
                parameters.add(panel1);
                parameters.add(panel2);
                parameters.add(panel3);
                parameters.add(panel4);
                detailsPanel.add(parameters);
                detailsPanel.add(directoryPanel);
                theFrame.validate();

            }
            if(games.getSelectedItem()=="Public Goods v2"){
                benefitField.setText("5");
                costField.setText("1");
                basefitField.setText("0");
                punField.setText(".9");
                puncostField.setText("1");
                groupsizeField.setText("5");
                popsizeField.setText("100");
                genField.setText("2000");
                PCField.setText(".1");
                PDField.setText(".1");
                iterationsField.setText("1");
                mutationsizeField.setText(".05");
                mutationrateField.setText(".05");
                wField.setText(".99");
                panel1.add(benefitLabel);
                panel1.add(benefitField);
                panel1.add(costLabel);
                panel1.add(costField);
                panel1.add(basefitLabel);
                panel1.add(basefitField);
                panel1.add(punLabel);
                panel1.add(punField);
                panel2.add(puncostLabel);
                panel2.add(puncostField);
                panel2.add(groupsizeLabel);
                panel2.add(groupsizeField);
                panel2.add(popsizeLabel);
                panel2.add(popsizeField);
                panel2.add(genLabel);
                panel2.add(genField);
                panel3.add(mutationrateLabel);
                panel3.add(mutationrateField);
                panel3.add(PCLabel);
                panel3.add(PCField);
                panel3.add(PDLabel);
                panel3.add(PDField);
                panel3.add(iterationsLabel);
                panel3.add(iterationsField);
                panel4.add(mutationsizeLabel);
                panel4.add(mutationsizeField);
                panel4.add(wLabel);
                panel4.add(wField);
                panel4.add(graphLabel);
                panel4.add(graphBox);
                parameters.add(panel1);
                parameters.add(panel2);
                parameters.add(panel3);
                parameters.add(panel4);
                detailsPanel.add(parameters);
                detailsPanel.add(directoryPanel);
                theFrame.validate();
            }
            if(games.getSelectedItem()=="Public Goods 3"){
                benefitField.setText("4");
                costField.setText("1");
                punField.setText(".9");
                puncostField.setText("1");
                groupsizeField.setText("5");
                popsizeField.setText("250");
                SWField.setText("5");
                genField.setText("10000");
                PCField.setText(".1");
                PDField.setText(".1");
                RTField.setText(".1");
                iterationsField.setText("1");
                mutationsizeField.setText(".05");
                mutationrateField.setText(".05");
                ecologyBox.setSelectedIndex(0);
                wField.setText(".998");
                panel1.add(benefitLabel);
                panel1.add(benefitField);
                panel1.add(costLabel);
                panel1.add(costField);
                panel1.add(punLabel);
                panel1.add(punField);
                panel1.add(puncostLabel);
                panel1.add(puncostField);
                panel2.add(ecologyLabel);
                panel2.add(ecologyBox);
                panel2.add(groupsizeLabel);
                panel2.add(groupsizeField);
                panel2.add(popsizeLabel);
                panel2.add(popsizeField);
                panel2.add(numSWLabel);
                panel2.add(SWField);
                panel4.add(mutationrateLabel);
                panel4.add(mutationrateField);
                panel3.add(PCLabel);
                panel3.add(PCField);
                panel3.add(PDLabel);
                panel3.add(PDField);
                panel3.add(RTLabel);
                panel3.add(RTField);
                panel3.add(iterationsLabel);
                panel3.add(iterationsField);
                panel4.add(mutationsizeLabel);
                panel4.add(mutationsizeField);
                panel4.add(wLabel);
                panel4.add(wField);
                panel4.add(genLabel);
                panel4.add(genField);
                
                parameters.add(panel1);
                parameters.add(panel2);
                parameters.add(panel3);
                parameters.add(panel4);
                detailsPanel.add(parameters);
                detailsPanel.add(directoryPanel);
                theFrame.validate();
            }
            if(games.getSelectedItem()=="Spatial Cue"){
                popsizeField.setText("500");
                daysField.setText("500");
                distField.setText(".03");
                meetRadiusField.setText(".015");
                iterationsField.setText("1");
                panel1.add(popsizeLabel);
                panel1.add(popsizeField);
                panel1.add(daysLabel);
                panel1.add(daysField);
                panel1.add(wallsLabel);
                panel1.add(wallsBox);
                panel2.add(distLabel);
                panel2.add(distField);
                panel2.add(meetRadiusLabel);
                panel2.add(meetRadiusField);
                panel2.add(iterationsLabel);
                panel2.add(iterationsField);
                parameters.add(panel1);
                parameters.add(panel2);
                detailsPanel.add(parameters);
                detailsPanel.add(directoryPanel);
                theFrame.validate();


            }
            if(games.getSelectedItem()=="Age Cue"){
                daysField.setText("600");
                popsizeField.setText("100");
                distField.setText(".03");
                meetRadiusField.setText(".01");
                iterationsField.setText("1");
                panel1.add(popsizeLabel);
                panel1.add(popsizeField);
                panel1.add(daysLabel);
                panel1.add(daysField);
                panel1.add(wallsLabel);
                panel1.add(wallsBox);
                panel2.add(distLabel);
                panel2.add(distField);
                panel2.add(meetRadiusLabel);
                panel2.add(meetRadiusField);
                panel2.add(iterationsLabel);
                panel2.add(iterationsField);
                parameters.add(panel1);
                parameters.add(panel2);
                detailsPanel.add(parameters);
                detailsPanel.add(directoryPanel);
                theFrame.validate();


            }
            if(games.getSelectedItem()=="Warmth/Competence"){
                popsizeField.setText("200");
                genField.setText("3000");
                iterationsField.setText("1");
                mutationsizeField.setText(".025");
                mutationrateField.setText(".05");
                wField.setText("50");
                PIDField.setText(".1");
                GIDField.setText(".1");
                PDSField.setText(".1");
                GDSField.setText(".1");
                panel1.add(popsizeLabel);
                panel1.add(popsizeField);
                panel1.add(genLabel);
                panel1.add(genField);
                panel1.add(iterationsLabel);
                panel1.add(iterationsField);
                panel2.add(inxnLabel);
                panel2.add(wField);
                panel2.add(CogTypeLabel);
                panel2.add(cognitionBox);
                panel2.add(distTypeLabel);
                panel2.add(distroBox);
                panel3.add(PIDLabel);
                panel3.add(PIDField);
                panel3.add(GIDLabel);
                panel3.add(GIDField);
                panel3.add(PDSLabel);
                panel3.add(PDSField);
                panel4.add(GDSLabel);
                panel4.add(GDSField);
                panel4.add(mutationsizeLabel);
                panel4.add(mutationsizeField);
                panel4.add(mutationrateLabel);
                panel4.add(mutationrateField);
                panel4.add(graphLabel);
                panel4.add(graphBox);
                
                parameters.add(panel1);
                parameters.add(panel2);
                parameters.add(panel3);
                parameters.add(panel4);
                detailsPanel.add(parameters);
                detailsPanel.add(directoryPanel);
                theFrame.validate();
            }

            if(games.getSelectedItem()==""){
                theFrame.validate();
            }
        }
    }
    class SummaryButtonListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent event){
            JFrame summary = new JFrame("Simulation Texts");
            JPanel sumPanel = new JPanel();
            sumPanel.add(descPanel);
            summary.add(sumPanel);
            summary.setBounds(tr);
            summary.setPreferredSize(textPan);
            summary.setVisible(true);
            summary.validate();  
            
        }
    }

    class GlossaryButtonListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent event){
            JFrame glossary = new JFrame("Glossary of Terms");
            glosPanel.setLayout(new BoxLayout(glosPanel, BoxLayout.X_AXIS));
            glosPanel.setPreferredSize(drawPan);
            glossary.add(glosPanel);
            glossary.setBounds(wr);
            glossary.setPreferredSize(wholeApp);
            glossary.setVisible(true);
            glossary.validate();  
            
        }
    }

    class StopButtonListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent event){
            action = 'R';
            theFrame.setResizable(true);
            //southPanel.removeAll();
            southPanel.add(descPanel);
            theFrame.validate();
            abort=true;
            results = null;
            theEnvironment = null;
            flag=3;
            System.out.println("Stop Button Pressed");
        }
    }

    class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            flag = 4; // eventmarker update
            theFrame.setResizable(false);
            Dimension southSize = new Dimension(southPanel.getSize());
            theFrame.validate();
            southPanel.removeAll();
            r = new Rectangle(southSize.width, southSize.height);
            //System.out.println(r.width + ", " + r.height);
            drawPanel.setSize(southSize);
            southPanel.add(drawPanel);
            processThread theSim = new processThread();
            Thread t = new Thread(theSim);
            t.start();
        }
    }
    class processThread implements Runnable {

        @Override
        public void run() {
            abort=false;
            action = 'S';
            drawPanel.paintImmediately(r);
            ArrayList<JTextField> fields = new ArrayList();
            ArrayList<JTextField> nullfields = new ArrayList();
            ArrayList<JComboBox> boxs = new ArrayList();
            ArrayList<JTextField> props = new ArrayList();
            ArrayList<JCheckBox> checks = new ArrayList();


            if(games.getSelectedItem()=="Hawk/Dove"){
                fields.add(benefitField);
                fields.add(costField);
                boxs.add(benefitBox);
                boxs.add(costBox);

                if(validateParams(fields, nullfields, boxs, props, checks)){
                    setUpRun();
                    for(int x=0; x<runsList.size(); x++){
                        paramsList thisParam = runsList.get(x);
                        population thePopulation = new population(1, thisParam.popsize, thisParam.typesDist, "Hawk/Dove");
                        theEnvironment = new environment(thePopulation);
                        theEnvironment.addResultListener(drawPanel);
                        int thisgen = 1;
                        results = new ArrayList();
                        while (thisgen<=thisParam.gens && abort==false){
                            theEnvironment.thePopulation.shuffle();
                            theEnvironment.thePopulation.formGroups(2);
                            result thisResult = theEnvironment.thePopulation.hawkDove(thisParam.b, thisParam.c, 1);
                            thisResult.gen=thisgen;
                            thisResult.resultType = "Hawk/Dove";
                            theEnvironment.thePopulation = new population(thisResult.AgentList);
                            theEnvironment.fireResultEvent(thisResult);
                            results.add(thisResult);
                            thisgen++;
                            if(fullTime.getSelectedItem()=="Slow"){
                                long t0, t1;
                                t0 =  System.currentTimeMillis();
                                do{
                                    t1 = System.currentTimeMillis();
                                }
                                while ((t1 - t0) < (250));
                            }
                        }
                    }
                    action = 'D';
                    drawPanel.paintImmediately(r);
                } else {
                    action = 'R';
                    drawPanel.paintImmediately(r);
                }
            }
            if(games.getSelectedItem()=="Vampire Bats"){
                fields.add(benefitField);
                //fields.add(costField);
                fields.add(genField);
                fields.add(successRateField);
                fields.add(popsizeField);
                boxs.add(benefitBox);
                //boxs.add(costBox);
                boxs.add(genBox);
                boxs.add(successRateBox);
                boxs.add(popsizeBox);

                props.add(ALLDField);
                props.add(ALLCField);
                props.add(TFTField);
                checks.add(alld);
                checks.add(allc);
                checks.add(tft);

                if(validateParams(fields, nullfields, boxs, props, checks)){
                    setUpRun();
                    for(int x=0; x<runsList.size(); x++){
                        paramsList thisParam = runsList.get(x);
                        population thePopulation = new population(thisParam.basefit, thisParam.popsize, thisParam.typesDist, "Vampire Bats");
                        theEnvironment = new environment(thePopulation);
                        theEnvironment.addResultListener(drawPanel);
                        int thisgen = 1;
                        results = new ArrayList();
                        helpedALLD=0;
                        helpedALLC=0;
                        helpedTFT=0;
                        askedALLD=0;
                        askedALLC=0;
                        askedTFT=0;
                        numALLD=0;
                        numALLC=0;
                        numTFT=0;
                        ageALLD=0;
                        ageALLC=0;
                        ageTFT=0;
                        while (thisgen<=thisParam.gens && abort==false){
                            theEnvironment.thePopulation.shuffle();
                            result thisResult = theEnvironment.thePopulation.vampireBat(thisParam.b, thisParam.sRate, alld.isSelected(), allc.isSelected(), tft.isSelected());
                            thisResult.gen=thisgen;
                            thisResult.resultType = "Vampire Bats";
                            //theEnvironment.thePopulation = new population(thisResult.AgentList);
                            theEnvironment.fireResultEvent(thisResult);
                            results.add(thisResult);
                            thisgen++;
                            if(fullTime.getSelectedItem()=="Slow"){
                                long t0, t1;
                                t0 =  System.currentTimeMillis();
                                do{
                                    t1 = System.currentTimeMillis();
                                }
                                while ((t1 - t0) < (100));
                            }
                        }
                    }
                    action = 'D';
                    drawPanel.paintImmediately(r);
                } else {
                    action = 'R';
                    drawPanel.paintImmediately(r);
                }
            }
            if(games.getSelectedItem()=="Incest Avoidance"){
                fields.add(popsizeField);
                fields.add(lerField);

                boxs.add(popsizeBox);
                boxs.add(lerBox);

                checks.add(u);
                checks.add(s);
                checks.add(sc);
                checks.add(scsc);
                props.add(uField);
                props.add(sField);
                props.add(scField);
                props.add(scscField);

                if(validateParams(fields, nullfields, boxs, props, checks)){
                    setUpRun();
                    for(int x=0; x<runsList.size(); x++){
                        paramsList thisParam = runsList.get(x);
                        population thePopulation = new population(0,thisParam.popsize,thisParam.typesDist,"Incest Avoidance"); //fix for constructor
                        theEnvironment = new environment(thePopulation);
                        theEnvironment.addResultListener(drawPanel);
                        int thisgen = 1;
                        results = new ArrayList();
                        nvU=0;
                        nvS=0;
                        nvSC=0;
                        nvSCSC=0;
                        vU=0;
                        vS=0;
                        vSC=0;
                        vSCSC=0;
                        soloU=0;
                        soloS=0;
                        soloSC=0;
                        soloSCSC=0;
                        mateU=0;
                        mateS=0;
                        mateSC=0;
                        mateSCSC=0;
                        while (thisgen<=thisParam.gens && abort==false){
                            theEnvironment.thePopulation.shuffle();
                            //System.out.println(thisgen);
                            result thisResult = theEnvironment.thePopulation.IA(thisParam.mr, thisParam.leRate, u.isSelected(), s.isSelected(), sc.isSelected(), scsc.isSelected());
                            if(!thisResult.message.isEmpty()){
                                theEnvironment.fireResultEvent(thisResult);
                                break;
                            } else {
                                thisResult.gen=thisgen;
                                thisResult.resultType = "Incest Avoidance";
                                theEnvironment.thePopulation = new population(thisResult.AgentList);
                                theEnvironment.fireResultEvent(thisResult);
                                results.add(thisResult);
                                thisgen++;
                                if(fullTime.getSelectedItem()=="Slow"){
                                    long t0, t1;
                                    t0 =  System.currentTimeMillis();
                                    do{
                                        t1 = System.currentTimeMillis();
                                    }
                                    while ((t1 - t0) < (200));
                                }
                            }
                        }
                    }
                    action = 'D';
                    drawPanel.paintImmediately(r);
                } else {
                    action = 'R';
                    drawPanel.paintImmediately(r);
                }
            }
            if(games.getSelectedItem()=="Infanticide"){
                checks.add(ni);
                checks.add(kn);
                checks.add(kgn);
                props.add(noInfField);
                props.add(killNurseField);
                props.add(killGestNurseField);

                if(validateParams(fields, nullfields, boxs, props, checks)){
                    setUpRun();
                    for(int x=0; x<runsList.size(); x++){
                        paramsList thisParam = runsList.get(x);
                        population thePopulation = new population(thisParam.typesDist, thisParam.gest, thisParam.nurse);
                        theEnvironment = new environment(thePopulation);
                        theEnvironment.addResultListener(drawPanel);
                        int thisgen = 1;
                        results = new ArrayList();
                        offNI=0;
                        offKN=0;
                        offKGN=0;
                        groupOffNI=0;
                        groupOffKN=0;
                        groupOffKGN=0;
                        alphaNI=0;
                        alphaKN=0;
                        alphaKGN=0;
                        while (thisgen<=thisParam.gens && abort==false){
                            //System.out.println(thisgen);
                            result thisResult = theEnvironment.thePopulation.infanticide(thisParam.mr, (int)thisParam.tenureLength, ni.isSelected(), kn.isSelected(), kgn.isSelected());
                            thisResult.gen=thisgen;
                            thisResult.resultType = "Infanticide";
                            theEnvironment.fireResultEvent(thisResult);
                            results.add(thisResult);
                            thisgen++;
                            if(fullTime.getSelectedItem()=="Slow"){
                                long t0, t1;
                                t0 =  System.currentTimeMillis();
                                do{
                                    t1 = System.currentTimeMillis();
                                }
                                while ((t1 - t0) < (250));
                            }
                        }
                    }
                    action = 'D';
                    drawPanel.paintImmediately(r);
                } else {
                    action = 'R';
                    drawPanel.paintImmediately(r);
                }
            } 
            
            //theFrame.setResizable(true);
        }
    }

    public ArrayList<agent> sortList(ArrayList<agent> list){
        ArrayList<agent> sortList= new ArrayList();
        ArrayList<agent> AgentList = (ArrayList<agent>)list.clone();
        //System.out.print(AgentList.size() + " - ");
        if(games.getSelectedItem()=="Hawk/Dove"){
            for(int i=0; i<AgentList.size(); i++){
                HDagent thisAgent = (HDagent)AgentList.get(i);
                if(thisAgent.genes.get(0).value==1){
                    sortList.add(thisAgent);
                    AgentList.remove(thisAgent);
                    i--;
                }
            }
            sortList.addAll(AgentList);
            AgentList.clear();
        } else if(games.getSelectedItem()=="Incest Avoidance"){
            for(int i=0; i<AgentList.size(); i++){
                IAagent thisAgent = (IAagent)AgentList.get(i);
                if(thisAgent.genes.get(0).value==3||thisAgent.genes.get(1).value==3){
                    sortList.add(thisAgent);
                    AgentList.remove(thisAgent);
                    i--;
                }
            }
            for(int i=0; i<AgentList.size(); i++){
                IAagent thisAgent = (IAagent)AgentList.get(i);
                if(thisAgent.genes.get(0).value==2||thisAgent.genes.get(1).value==2){
                    sortList.add(thisAgent);
                    AgentList.remove(thisAgent);
                    i--;
                }
            }
            for(int i=0; i<AgentList.size(); i++){
                IAagent thisAgent = (IAagent)AgentList.get(i);
                if(thisAgent.genes.get(0).value==1||thisAgent.genes.get(1).value==1){
                    sortList.add(thisAgent);
                    AgentList.remove(thisAgent);
                    i--;
                }
            }
            sortList.addAll(AgentList);
            AgentList.clear();
        } else if(games.getSelectedItem()=="Vampire Bats"){
            for(int i=0; i<AgentList.size(); i++){
                bat thisAgent = (bat)AgentList.get(i);
                if(thisAgent.getType()==0){
                    sortList.add(thisAgent);
                    AgentList.remove(thisAgent);
                    i--;
                }
            }
            for(int i=0; i<AgentList.size(); i++){
                bat thisAgent = (bat)AgentList.get(i);
                if(thisAgent.getType()==1){
                    sortList.add(thisAgent);
                    AgentList.remove(thisAgent);
                    i--;
                }
            }
            sortList.addAll(AgentList);
            AgentList.clear();
        } else if(games.getSelectedItem()=="Infanticide"){
            for(int i=0; i<AgentList.size(); i++){
                langur thisAgent = (langur)AgentList.get(i);
                if(thisAgent.sex=='f'){
                    AgentList.remove(i);
                    i--;
                }
            }
            for(int i=0; i<AgentList.size(); i++){
                langur thisAgent = (langur)AgentList.get(i);
                if(thisAgent.genes.get(0).value==0){
                    sortList.add(thisAgent);
                    AgentList.remove(thisAgent);
                    i--;
                }
            }
            for(int i=0; i<AgentList.size(); i++){
                langur thisAgent = (langur)AgentList.get(i);
                if(thisAgent.genes.get(0).value==1){
                    sortList.add(thisAgent);
                    AgentList.remove(thisAgent);
                    i--;
                }
            }
            sortList.addAll(AgentList);
            AgentList.clear();
        } else {
            sortList.addAll(AgentList);
            AgentList.clear();
        }
        return sortList;
    }

    public static double roundToSignificantFigures(double num, int n) {
    if(num == 0) {
        return 0;
    }
    final double d = Math.ceil(Math.log10(num < 0 ? -num: num));
    final int power = n - (int) d;
    final double magnitude = Math.pow(10, power);
    final long shifted = Math.round(num*magnitude);
    return shifted/magnitude;
}
    private void newSetUp(ArrayList<JTextField> fieldList){
        runsList=new ArrayList();
        int numFields = fieldList.size();
        if(numFields>=1){
            String[] a = fieldList.get(0).getText().split(",");
            for(int ac=0; ac<a.length; ac++){
                if(numFields>=2){
                    String[] b = fieldList.get(1).getText().split(",");
                    for(int bc=0; bc<b.length; bc++){
                        if(numFields>=3){
                            String[] c = fieldList.get(2).getText().split(",");
                            for(int cc=0; cc<c.length; cc++){
                                if(numFields>=4){
                                    String[] d = fieldList.get(3).getText().split(",");
                                    for(int dc=0; dc<d.length; dc++){
                                        if(numFields>=5){
                                            String[] e = fieldList.get(4).getText().split(",");
                                            for(int ec=0; ec<e.length; ec++){
                                                if(numFields>=6){
                                                    String[] f = fieldList.get(5).getText().split(",");
                                                    for(int fc=0; fc<f.length; fc++){
                                                        if(numFields>=7){
                                                            String[] g = fieldList.get(6).getText().split(",");
                                                            for(int gc=0; gc<g.length; gc++){
                                                                if(numFields>=8){
                                                                    String[] h = fieldList.get(7).getText().split(",");
                                                                    for(int hc=0; hc<h.length; hc++){
                                                                        if(numFields>=9){
                                                                            String[] i = fieldList.get(8).getText().split(",");
                                                                            for(int ic=0; ic<i.length; ic++){
                                                                                if(numFields>=10){
                                                                                    String[] j = fieldList.get(9).getText().split(",");
                                                                                    for(int jc=0; jc<j.length; jc++){
                                                                                        if(numFields>=11){
                                                                                            String[] k = fieldList.get(10).getText().split(",");
                                                                                            for(int kc=0; kc<k.length; kc++){
                                                                                                if(numFields>=12){
                                                                                                    String[] l = fieldList.get(11).getText().split(",");
                                                                                                    for(int lc=0; lc<l.length; lc++){
                                                                                                        if(numFields>=13){
                                                                                                            String[] m = fieldList.get(12).getText().split(",");
                                                                                                            for(int mc=0; mc<m.length; mc++){
                                                                                                                if(numFields>=14){
                                                                                                                    String[] n = fieldList.get(13).getText().split(",");
                                                                                                                    for(int nc=0; nc<n.length; nc++){
                                                                                                                        if(numFields>=15){
                                                                                                                            String[] o = fieldList.get(14).getText().split(",");
                                                                                                                            for(int oc=0; oc<o.length; oc++){
                                                                                                                                if(numFields>=16){
                                                                                                                                    String[] p = fieldList.get(15).getText().split(",");
                                                                                                                                    for(int pc=0; pc<p.length; pc++){
                                                                                                                                        if(numFields>=17){
                                                                                                                                            String[] q = fieldList.get(16).getText().split(",");
                                                                                                                                            for(int qc=0; qc<q.length; qc++){
                                                                                                                                                if(numFields>=18){
                                                                                                                                                    String[] r = fieldList.get(17).getText().split(",");
                                                                                                                                                    for(int rc=0; rc<r.length; rc++){
                                                                                                                                                        if(numFields>=19){
                                                                                                                                                            String[] s = fieldList.get(18).getText().split(",");
                                                                                                                                                            for(int sc=0; sc<s.length; sc++){
                                                                                                                                                                if(numFields>=20){
                                                                                                                                                                    String[] t = fieldList.get(19).getText().split(",");
                                                                                                                                                                    for(int tc=0; tc<t.length; tc++){
                                                                                                                                                                        if(numFields>=21){
                                                                                                                                                                            String[] u = fieldList.get(20).getText().split(",");
                                                                                                                                                                            for(int uc=0; uc<u.length; uc++){
                                                                                                                                                                                if(numFields>=22){
                                                                                                                                                                                    String[] v = fieldList.get(21).getText().split(",");
                                                                                                                                                                                    for(int vc=0; vc<v.length; vc++){
                                                                                                                                                                                        if(numFields>=23){
                                                                                                                                                                                            String[] w = fieldList.get(22).getText().split(",");
                                                                                                                                                                                            for(int wc=0; wc<w.length; wc++){
                                                                                                                                                                                                if(numFields>=24){
                                                                                                                                                                                                    String[] x = fieldList.get(23).getText().split(",");
                                                                                                                                                                                                    for(int xc=0; xc<x.length; xc++){
                                                                                                                                                                                                        if(numFields>=25){
                                                                                                                                                                                                            String[] y = fieldList.get(24).getText().split(",");
                                                                                                                                                                                                            for(int yc=0; yc<y.length; yc++){
                                                                                                                                                                                                                if(numFields>=26){
                                                                                                                                                                                                                    String[] z = fieldList.get(25).getText().split(",");
                                                                                                                                                                                                                    for(int zc=0; zc<z.length; zc++){
                                                                                                                                                                                                                        

                                                                                                                                                                                                                    }
                                                                                                                                                                                                                }
                                                                                                                                                                                                            } 
                                                                                                                                                                                                        }
                                                                                                                                                                                                    }
                                                                                                                                                                                                }
                                                                                                                                                                                            }
                                                                                                                                                                                        }
                                                                                                                                                                                    }
                                                                                                                                                                                }
                                                                                                                                                                            } 
                                                                                                                                                                        }
                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            } 
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            } 
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            } 
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } 
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } 
                        }
                    }
                }
            }
        }
    }
    
    private void setUpRun(){ //change to an ArrayList<paramsList>
         runsList = new ArrayList();
         if(games.getSelectedItem()=="Vampire Bats"){
            ArrayList<TypeDistribution> typesDist = new ArrayList();
            if(alld.isSelected()){
                TypeDistribution ALLD = new TypeDistribution("ALLD", Double.parseDouble(ALLDField.getText()));
                typesDist.add(ALLD);
            }
            if(allc.isSelected()){
                TypeDistribution ALLC = new TypeDistribution("ALLC", Double.parseDouble(ALLCField.getText()));
                typesDist.add(ALLC);
            }
            if(tft.isSelected()){
                TypeDistribution TFT = new TypeDistribution("TFT", Double.parseDouble(TFTField.getText()));
                typesDist.add(TFT);
            }
            if(release){
                paramsList thisRun = new paramsList();
                if(benefitBox.getSelectedItem()=="Custom"){
                    thisRun.b=Double.parseDouble(benefitField.getText());
                } else {
                    thisRun.b=Double.parseDouble((String)benefitBox.getSelectedItem());
                }
                if(popsizeBox.getSelectedItem()=="Custom"){
                    thisRun.popsize=Integer.parseInt(popsizeField.getText());
                } else {
                    thisRun.popsize=Integer.parseInt((String)popsizeBox.getSelectedItem());
                }
                if(genBox.getSelectedItem()=="Custom"){
                    thisRun.gens=Integer.parseInt(genField.getText());
                } else {
                    thisRun.gens=Integer.parseInt((String)genBox.getSelectedItem());
                }
                if(successRateBox.getSelectedItem()=="Custom"){
                    thisRun.sRate=Double.parseDouble(successRateField.getText());
                } else {
                    thisRun.sRate=Double.parseDouble((String)successRateBox.getSelectedItem());
                }
                thisRun.game=(String)games.getSelectedItem();
                thisRun.typesDist=typesDist;
                runsList.add(thisRun);
             } else {
                 String[] bens = benefitField.getText().split(",");
                 String[] pops = popsizeField.getText().split(",");
                 String[] gens = genField.getText().split(",");
                 String[] sRates = successRateField.getText().split(",");
                 for(int x = 0; x < bens.length; x++){
                     for(int y = 0; y < pops.length; y++){
                         for(int z = 0; z < gens.length; z++){
                             for(int a = 0; a < sRates.length; a++){
                                 for(int b = 0; b < Math.floor(Double.parseDouble(iterationsField.getText())); b++){
                                     paramsList thisRun = new paramsList();
                                     thisRun.b=Double.parseDouble(bens[x]);
                                     thisRun.popsize=Integer.parseInt(pops[y]);
                                     thisRun.gens=Integer.parseInt(gens[z]);
                                     thisRun.sRate=Double.parseDouble(sRates[a]);
                                     thisRun.i=b;
                                     thisRun.game=(String)games.getSelectedItem();
                                     thisRun.typesDist=typesDist;
                                     thisRun.iteration=b;
                                     runsList.add(thisRun);
                                 }
                             }
                         }
                     }
                 }
             }
         }
         if(games.getSelectedItem()=="Hawk/Dove"){
            ArrayList<TypeDistribution> typesDist = new ArrayList();
            TypeDistribution hawk = new TypeDistribution("Hawk", .5);
            TypeDistribution dove = new TypeDistribution("Dove", .5);
            typesDist.add(hawk);
            typesDist.add(dove);
            if(release){
                paramsList thisRun = new paramsList();
                if(benefitBox.getSelectedItem()=="Custom"){
                    thisRun.b=Double.parseDouble(benefitField.getText());
                } else {
                    thisRun.b=Double.parseDouble((String)benefitBox.getSelectedItem());
                }
                if(costBox.getSelectedItem()=="Custom"){
                    thisRun.c=Double.parseDouble(costField.getText());
                } else {
                    thisRun.c=Double.parseDouble((String)costBox.getSelectedItem());
                }
                thisRun.popsize=Integer.parseInt(popsizeField.getText());
                thisRun.gens=Integer.parseInt(genField.getText());
                thisRun.game=(String)games.getSelectedItem();
                thisRun.typesDist=typesDist;
                runsList.add(thisRun);
            } else {
                 String[] bens = benefitField.getText().split(",");
                 String[] costs = costField.getText().split(",");
                 String[] pops = popsizeField.getText().split(",");
                 String[] gens = genField.getText().split(",");
                 for(int x = 0; x < bens.length; x++){
                     for(int y = 0; y < costs.length; y++){
                         for(int a=0; a < pops.length; a++){
                             for(int b=0; b < gens.length; b++){
                                 for(int z = 0; z < Math.floor(Double.parseDouble(iterationsField.getText())); z++){
                                     paramsList thisRun = new paramsList();
                                     thisRun.b=Double.parseDouble(bens[x]);
                                     thisRun.c=Double.parseDouble(costs[y]);
                                     thisRun.i=z;
                                     thisRun.popsize=Integer.parseInt(pops[a]);
                                     thisRun.gens = Integer.parseInt(gens[b]);
                                     thisRun.game=(String)games.getSelectedItem();
                                     thisRun.typesDist=typesDist;
                                     thisRun.iteration=b;
                                     runsList.add(thisRun);
                                 }
                             }
                         }
                     }
                 }
            }
         }

         if(games.getSelectedItem()=="Incest Avoidance"){
            ArrayList<TypeDistribution> typesDist = new ArrayList();
            if(u.isSelected()){
                TypeDistribution U = new TypeDistribution("U", Double.parseDouble(uField.getText()));
                typesDist.add(U);
            }
            if(s.isSelected()){
                TypeDistribution S = new TypeDistribution("S", Double.parseDouble(sField.getText()));
                typesDist.add(S);
            }
            if(sc.isSelected()){
                TypeDistribution SC = new TypeDistribution("SC", Double.parseDouble(scField.getText()));
                typesDist.add(SC);
            }
            if(scsc.isSelected()){
                TypeDistribution SCSC = new TypeDistribution("SCSC", Double.parseDouble(scscField.getText()));
                typesDist.add(SCSC);
            }
            if(release){
                paramsList thisRun = new paramsList();
                if(popsizeBox.getSelectedItem()=="Custom"){
                        thisRun.popsize=Integer.parseInt(popsizeField.getText());
                } else {
                    thisRun.popsize=Integer.parseInt((String)popsizeBox.getSelectedItem());
                }
                if(lerBox.getSelectedItem()=="Custom"){
                    thisRun.leRate=Integer.parseInt(lerField.getText());
                } else {
                    thisRun.leRate=Integer.parseInt((String)lerBox.getSelectedItem());
                }
                thisRun.mr=Double.parseDouble(mutationrateField.getText());
                thisRun.gens=Integer.parseInt(genField.getText());
                thisRun.game=(String)games.getSelectedItem();
                thisRun.typesDist=typesDist;
                runsList.add(thisRun);
            }


         }

         if(games.getSelectedItem()=="Infanticide"){
            ArrayList<TypeDistribution> typesDist = new ArrayList();
            if(ni.isSelected()){
                TypeDistribution NI = new TypeDistribution("NI", Double.parseDouble(noInfField.getText()));
                typesDist.add(NI);
            }
            if(kn.isSelected()){
                TypeDistribution KN = new TypeDistribution("KN", Double.parseDouble(killNurseField.getText()));
                typesDist.add(KN);
            }
            if(kgn.isSelected()){
                TypeDistribution KGN = new TypeDistribution("KGN", Double.parseDouble(killGestNurseField.getText()));
                typesDist.add(KGN);
            }
            if(release){
                paramsList thisRun = new paramsList();thisRun.gest=Integer.parseInt(gestField.getText());
                thisRun.nurse=Integer.parseInt(nurseField.getText());
                thisRun.tenureLength=Integer.parseInt(tenureField.getText());
                thisRun.gens=Integer.parseInt(genField.getText());
                thisRun.mr=Double.parseDouble(mutationrateField.getText());
                thisRun.game=(String)games.getSelectedItem();
                thisRun.typesDist=typesDist;
                runsList.add(thisRun);
            }
         }

         if(games.getSelectedItem()=="Belief Formation" || games.getSelectedItem()=="Belief Implementation"){
             String[] bens = benefitField.getText().split(",");
             String[] costs = costField.getText().split(",");
             String[] ws = wField.getText().split(",");
             String[] ds = dField.getText().split(",");
             String[] osps = ospercentField.getText().split(",");
             for(int x = 0; x < bens.length; x++){
                 for(int y = 0; y < costs.length; y++){
                     for(int z = 0; z < ws.length; z++){
                        for(int a = 0; a < ds.length; a++){
                             for(int b = 0; b < osps.length; b++){
                                 for(int c = 0; c < Math.floor(Double.parseDouble(iterationsField.getText())); c++){
                                     paramsList thisRun = new paramsList();
                                     thisRun.b=Double.parseDouble(bens[x]);
                                     thisRun.c=Double.parseDouble(costs[y]);
                                     thisRun.w=Double.parseDouble(ws[z]);
                                     thisRun.d=Double.parseDouble(ds[a]);
                                     thisRun.osp=Double.parseDouble(osps[b]);
                                     thisRun.i=c;
                                     thisRun.iteration=c;
                                     runsList.add(thisRun);
                                 }
                             }
                         }
                     }
                 }
             }
         }
         if(games.getSelectedItem()=="Belief Formation Redux"){
             String[] bens = benefitField.getText().split(",");
             String[] costs = costField.getText().split(",");
             String[] ws = wField.getText().split(",");
             String[] ds = dField.getText().split(",");
             String[] osps = ospercentField.getText().split(",");
             for(int x = 0; x < bens.length; x++){
                 for(int y = 0; y < costs.length; y++){
                     for(int z = 0; z < ws.length; z++){
                        for(int a = 0; a < ds.length; a++){
                             for(int b = 0; b < osps.length; b++){
                                for(int d = 0; d < Math.floor(Double.parseDouble(iterationsField.getText())); d++){
                                    paramsList thisRun = new paramsList();
                                    thisRun.b=Double.parseDouble(bens[x]);
                                    thisRun.c=Double.parseDouble(costs[y]);
                                    thisRun.w=Double.parseDouble(ws[z]);
                                    thisRun.d=Double.parseDouble(ds[a]);
                                    thisRun.osp=Double.parseDouble(osps[b]);
                                    thisRun.i=d;
                                    thisRun.iteration=d;
                                    runsList.add(thisRun);
                                    //System.out.println("This Run: " + thisRun.nShot);
                                }
                             }
                         }
                     }
                 }
             }
         }
         if(games.getSelectedItem()=="Warmth/Competence"){
             //need to add in other fields
             String[] ws = wField.getText().split(",");
             String[] pids = PIDField.getText().split(",");
             String[] gids = GIDField.getText().split(",");
             String[] pdss = PDSField.getText().split(",");
             String[] gdss = GDSField.getText().split(",");
             
             for(int x = 0; x < ws.length; x++){
                 for(int y = 0; y < pids.length; y++){
                     for(int z = 0; z < gids.length; z++){
                        for(int a = 0; a < pdss.length; a++){
                             for(int b = 0; b < gdss.length; b++){
                                 for(int c = 0; c < Math.floor(Double.parseDouble(iterationsField.getText())); c++){
                                     paramsList thisRun = new paramsList();
                                     thisRun.w=Double.parseDouble(ws[x]);
                                     thisRun.PID=Double.parseDouble(pids[y]);
                                     thisRun.GID=Double.parseDouble(gids[z]);
                                     thisRun.PDS=Double.parseDouble(pdss[a]);
                                     thisRun.GDS=Double.parseDouble(gdss[b]);
                                     thisRun.i=c;
                                     thisRun.iteration=c;
                                     runsList.add(thisRun);
                                 }
                             }
                         }
                     }
                 }
             }
         }
         if(games.getSelectedItem()=="Public Goods"){
             String[] bens = benefitField.getText().split(",");
             String[] costs = costField.getText().split(",");
             String[] ps = punField.getText().split(",");
             String[] ks = puncostField.getText().split(",");
             String[] ws = wField.getText().split(",");
             for(int x = 0; x < bens.length; x++){
                 for(int y = 0; y < costs.length; y++){
                     for(int z = 0; z < ps.length; z++){
                         for(int a = 0; a < ks.length; a++){
                             for(int b = 0; b < ws.length; b++){
                                for(int c = 0; c < Math.floor(Double.parseDouble(iterationsField.getText())); c++){
                                    paramsList thisRun = new paramsList();
                                    thisRun.b=Double.parseDouble(bens[x]);
                                    thisRun.c=Double.parseDouble(costs[y]);
                                    thisRun.p=Double.parseDouble(ps[z]);
                                    thisRun.k=Double.parseDouble(ks[a]);
                                    thisRun.w=Double.parseDouble(ws[b]);
                                    thisRun.i=c;
                                    thisRun.iteration=c;
                                    runsList.add(thisRun);
                                }
                            }
                         }
                     }
                 }
             }
         }
         if(games.getSelectedItem()=="Public Goods Evolve"){
             String[] b1s = benefit1Field.getText().split(",");
             String[] b2s = benefit2Field.getText().split(",");
             String[] costs = costField.getText().split(",");
             String[] ps = punField.getText().split(",");
             String[] ks = puncostField.getText().split(",");
             String[] ws = wField.getText().split(",");
             for(int x = 0; x < b1s.length; x++){
                 for(int d = 0; d < b2s.length; d++){
                     for(int y = 0; y < costs.length; y++){
                         for(int z = 0; z < ps.length; z++){
                             for(int a = 0; a < ks.length; a++){
                                 for(int b = 0; b < ws.length; b++){
                                    for(int c = 0; c < Math.floor(Double.parseDouble(iterationsField.getText())); c++){
                                        paramsList thisRun = new paramsList();
                                        thisRun.b1=Double.parseDouble(b1s[x]);
                                        thisRun.b2=Double.parseDouble(b2s[d]);
                                        thisRun.c=Double.parseDouble(costs[y]);
                                        thisRun.p=Double.parseDouble(ps[z]);
                                        thisRun.k=Double.parseDouble(ks[a]);
                                        thisRun.w=Double.parseDouble(ws[b]);
                                        thisRun.i=c;
                                        thisRun.iteration=c;
                                        runsList.add(thisRun);
                                    }
                                }
                             }
                         }
                     }
                 }
             }
         }
         if(games.getSelectedItem()=="Public Goods v2"||games.getSelectedItem()=="Public Goods 3"){
             String[] bens = benefitField.getText().split(",");
             String[] costs = costField.getText().split(",");
             String[] ps = punField.getText().split(",");
             String[] ks = puncostField.getText().split(",");
             String[] ws = wField.getText().split(",");
             for(int x = 0; x < bens.length; x++){
                 for(int y = 0; y < costs.length; y++){
                     for(int z = 0; z < ps.length; z++){
                         for(int a = 0; a < ks.length; a++){
                             for(int b = 0; b < ws.length; b++){
                                for(int c = 0; c < Math.floor(Double.parseDouble(iterationsField.getText())); c++){
                                    paramsList thisRun = new paramsList();
                                    thisRun.b=Double.parseDouble(bens[x]);
                                    thisRun.c=Double.parseDouble(costs[y]);
                                    thisRun.p=Double.parseDouble(ps[z]);
                                    thisRun.k=Double.parseDouble(ks[a]);
                                    thisRun.w=Double.parseDouble(ws[b]);
                                    thisRun.i=c;
                                    thisRun.iteration=c;
                                    runsList.add(thisRun);
                                }
                            }
                         }
                     }
                 }
             }
         }
         if(games.getSelectedItem()=="Spatial Cue"){
             String[] ps = popsizeField.getText().split(",");
             String[] days = daysField.getText().split(",");
             String[] dists = distField.getText().split(",");
             String[] meets = meetRadiusField.getText().split(",");
             for(int x = 0; x < ps.length; x++){
                 for(int y = 0; y < days.length; y++){
                     for(int z = 0; z < dists.length; z++){
                         for(int a = 0; a < meets.length; a++){
                             for(int b = 0; b < Integer.parseInt(iterationsField.getText()); b++){
                                 paramsList thisRun = new paramsList();
                                 thisRun.popsize = Integer.parseInt(ps[x]);
                                 thisRun.days = Integer.parseInt(days[y]);
                                 thisRun.dist = Double.parseDouble(dists[z]);
                                 thisRun.meetRadius = Double.parseDouble(meets[a]);
                                 thisRun.i=b;
                                 runsList.add(thisRun);
                             }
                         }
                     }
                 }
             }
         }
         if(games.getSelectedItem()=="Age Cue"){
             String[] ps = popsizeField.getText().split(",");
             String[] dists = distField.getText().split(",");
             String[] meets = meetRadiusField.getText().split(",");
             for(int x = 0; x < ps.length; x++){
                 for(int z = 0; z < dists.length; z++){
                     for(int a = 0; a < meets.length; a++){
                         for(int b = 0; b < Integer.parseInt(iterationsField.getText()); b++){
                             paramsList thisRun = new paramsList();
                             thisRun.days = Integer.parseInt(daysField.getText());
                             thisRun.popsize = Integer.parseInt(ps[x]);
                             thisRun.dist = Double.parseDouble(dists[z]);
                             thisRun.meetRadius = Double.parseDouble(meets[a]);
                             thisRun.i=b;
                             runsList.add(thisRun);
                         }
                     }
                 }
             }
         }
     }

     public boolean validateParams(ArrayList<JTextField> fields, ArrayList<JTextField> nullfields, ArrayList<JComboBox> boxs, ArrayList<JTextField> props, ArrayList<JCheckBox> checks){
         int errors = 0;
         double sum=0;
         boolean validated = false;
         for(JTextField text:nullfields){
             text.setBackground(Color.WHITE);
         }
         for(int i=0; i<checks.size(); i++){
             JCheckBox thisCheck = checks.get(i);
             JTextField thisProp = props.get(i);
             if(thisCheck.isSelected()){
                 sum+=roundToSignificantFigures(Double.parseDouble(thisProp.getText()),3);
             } else {
                 thisProp.setText("0");
             }
         }
         if(checks.isEmpty()){
             sum=1;
         }
         sum = roundToSignificantFigures(sum,2);
        // System.out.println(sum);
         if(sum!=1){
             message = "Proportions must add up to 1";
             errors++;
             for(int i=0; i<checks.size(); i++){
                 JCheckBox thisCheck = checks.get(i);
                 JTextField thisProp = props.get(i);
                 if(thisCheck.isSelected()){
                     thisProp.setBackground(Color.red);
                 }
             }
         } else {
             for(int i=0; i<checks.size(); i++){
                 JTextField thisProp = props.get(i);
                 thisProp.setBackground(Color.white);
             }
         }
         for(int i=0; i<boxs.size(); i++){
             JComboBox thisBox = boxs.get(i);
             JTextField thisField = fields.get(i);
             if(thisBox.getSelectedItem()=="Custom"){
                 try {
                     Double.parseDouble(thisField.getText());
                     thisField.setBackground(Color.white);
                 } catch(NumberFormatException ex){
                     thisField.setBackground(Color.red);
                     errors++;
                     message = "Must either specify custom or leave text field blank";
                 }
             } else {
                 if(thisField.getText().equalsIgnoreCase("")){
                     thisField.setBackground(Color.white);
                 } else {
                     thisField.setText("");
                 }
             }
         }
         if(errors == 0){
             validated = true;
             theFrame.validate();
         } else {
            theFrame.validate();
            action='I'; //THIS ISN'T WORKING YET... wont graph message & artifacts
         }
         return validated;
     }
     
     private void saveFile(File file, String game) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            if(game.matches("Hawk/Dove")){
                for(result thisResult:results){
                    writer.write(thisResult.typesDist.get(0).type + "," + thisResult.typesDist.get(0).proportion + "," + thisResult.typesDist.get(1).type + "," + thisResult.typesDist.get(1).proportion + "\n");
                }
            }
            if(game.matches("Belief Formation")){
                for(result thisResult:results){
                    writer.write(thisResult.typesDist.get(0).type +  "," + thisResult.typesDist.get(0).proportion + "," + thisResult.typesDist.get(0).se + "," + optimum + "\n");
                }
            }
            if(game.matches("Belief Formation Redux")){
                for(result thisResult:results){
                    writer.write(thisResult.typesDist.get(0).type +  "," + thisResult.typesDist.get(0).proportion + "," + thisResult.typesDist.get(0).se + "," + thisResult.typesDist.get(1).type +  "," + thisResult.typesDist.get(1).proportion + "," + thisResult.typesDist.get(2).type +  "," + thisResult.typesDist.get(2).proportion + "," + optimum + "\n");
                }
            }
            if(game.matches("Belief Implementation")){
                for(result thisResult:results){
                    writer.write(thisResult.typesDist.get(0).type + "," + thisResult.typesDist.get(0).proportion + "," + thisResult.typesDist.get(0).se + "," + thisResult.typesDist.get(1).type + "," + thisResult.typesDist.get(1).proportion + "," + thisResult.typesDist.get(1).se + "\n");
                }
            }
            if(game.matches("Public Goods")){
                for(result thisResult:results){
                    writer.write(thisResult.typesDist.get(0).type + "," + thisResult.typesDist.get(0).proportion + "," + thisResult.typesDist.get(1).type + "," + thisResult.typesDist.get(1).proportion + "," + thisResult.typesDist.get(2).type + "," + thisResult.typesDist.get(2).proportion + "\n");
                }
            }
            if(game.matches("Public Goods v2")){
                for(result thisResult:results){
                    writer.write(thisResult.typesDist.get(0).type + "," + thisResult.typesDist.get(0).proportion + "," + thisResult.typesDist.get(1).type + "," + thisResult.typesDist.get(1).proportion + "," + "\n");
                }
            }
            if(game.matches("Public Goods 3")){
                for(result thisResult:results){
                    writer.write(thisResult.typesDist.get(0).type + "," + thisResult.typesDist.get(0).proportion + "," + thisResult.typesDist.get(1).type + "," + thisResult.typesDist.get(1).proportion + thisResult.typesDist.get(2).type + "," + thisResult.typesDist.get(2).proportion + "," + "\n");
                }
            }
            if(game.matches("Public Goods Evolve")){
                for(result thisResult:results){
                    writer.write(thisResult.typesDist.get(0).type + "," + thisResult.typesDist.get(0).proportion + "," + thisResult.typesDist.get(1).type + "," + thisResult.typesDist.get(1).proportion + "," + thisResult.typesDist.get(2).type + "," + thisResult.typesDist.get(2).proportion + "," + thisResult.typesDist.get(3).type + "," + thisResult.typesDist.get(3).proportion + "," + "\n");
                }
            }
            if(game.matches("Warmth/Competence")){
                for(result thisResult:results){
                    writer.write(thisResult.typesDist.get(0).type +  "," + thisResult.typesDist.get(0).proportion + "," + "\n");
                }
            }
            if(game.matches("Spatial Cue")){
                for(int x = 1; x<=results.get(0).dailyProb.size(); x++){
                    writer.write(x + "," + results.get(0).dailyProb.get(x-1) + "," + results.get(0).dailyBase.get(x-1) + "\n");
                }
            }
            if(game.matches("Age Cue")){
                writer.write("Day");
                for(int x = 1; x<=results.get(0).ageCueDist.get(0).size(); x++){
                    TypeDistribution thisDist = results.get(0).ageCueDist.get(0).get(x-1);
                    writer.write("," + thisDist.type);
                }
                writer.write("\n");
                for(int x=1; x<=results.get(0).ageCueDist.size(); x++){
                    ArrayList<TypeDistribution> thisDay = results.get(0).ageCueDist.get(x-1);
                    writer.write(Integer.toString(x));
                    for(int y=0; y<thisDay.size(); y++){
                        writer.write("," + thisDay.get(y).proportion);
                    }
                    writer.write("\n");
                }
            }
            //results.clear();
            writer.close();
        } catch(IOException ex) {
            System.out.println("Couldn't write to file");
            ex.printStackTrace();
        }
        
    }
     
    private void SerializeRun(ArrayList<result> theseResults, File filename){

        try {
           FileOutputStream fos = new FileOutputStream(filename);
           ObjectOutputStream out = new ObjectOutputStream(fos);
           out.writeObject(theseResults);
           out.close(); 
        }
         catch(IOException ex) {
            ex.printStackTrace();
        }
     }
    
    private ArrayList<result> DeSerializeRun(String filename){
         ArrayList<result> thisResult = new ArrayList();
         try {
             FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fis);
             thisResult = (ArrayList<result>)in.readObject();
             in.close();
        } catch(IOException ex) {
            ex.printStackTrace();
        } catch(ClassNotFoundException ex) {
            ex.printStackTrace();
        }
         return thisResult;
     }
    private void AllAgentsFile(File file, String game){
         if(game.matches("Public Goods")){
             try {
                 
                 File summaryfile = new File(file.getAbsolutePath().replaceAll(".csv","")+"_summary.csv");
                 BufferedWriter summarywriter = new BufferedWriter(new FileWriter(summaryfile));
                 summarywriter.write("B,C,P,K,W,GroupSize,BaseFit,MutRate,PopSize,GenNumber,ITC,PT,RT,\n");
                 
                 String directory = directoryField.getText();
                 directory+= fileSep+"temp"+fileSep;
                 File directoryFile = new File(directory);
                 String[] listofFiles = directoryFile.list();
                 for(int i=0; i<listofFiles.length; i++){
                     ArrayList<result> theseResults = DeSerializeRun(directory + listofFiles[i]);
                     for(result thisResult:theseResults){
                         //writer.write(thisResult.b + "," + thisResult.c + "," + thisResult.p + "," + thisResult.k + "," + thisResult.w + "," + thisResult.group + "," + thisResult.basefit + "," + thisResult.mr + "," + thisResult.pop + "," + thisResult.gen + "," + thisResult.typesDist.get(0).proportion + "," + thisResult.typesDist.get(1).proportion + "," + thisResult.typesDist.get(2).proportion + ",");
                         summarywriter.write(thisResult.b + "," + thisResult.c + "," + thisResult.p + "," + thisResult.k + "," + thisResult.w + "," + thisResult.group + "," + thisResult.basefit + "," + thisResult.mr + "," + thisResult.pop + "," + thisResult.gen + "," + thisResult.typesDist.get(0).proportion + "," + thisResult.typesDist.get(1).proportion + "," + thisResult.typesDist.get(2).proportion + ",");
                         //for(agent anAgent:thisResult.AgentList){
                             //PGagent thisAgent = (PGagent) anAgent;
                            //writer.write(thisAgent.getPGparams() + ",");
                         //}
                         //writer.write("\n");
                         summarywriter.write("\n");
                     }
                }
                 //writer.close();
                 summarywriter.close();
             } catch(IOException ex) {
                System.out.println("Couldn't write to file");
                ex.printStackTrace();
            }
        }
         if(game.matches("Public Goods Evolve")){
             try {
                 File summaryfile = new File(file.getAbsolutePath().replaceAll(".csv","")+"summary.csv");
                 BufferedWriter summarywriter = new BufferedWriter(new FileWriter(summaryfile));
                 summarywriter.write("Gen,b1,b2,c,PunCost,PunEffect,W,PopSize,PGT,GSP,CT,PT,CoopRate,PunRate,LastCoopRate,LastPunRate,\n");
                 String directory = directoryField.getText();
                 directory+= fileSep+"temp"+fileSep;
                 File directoryFile = new File(directory);
                 String[] listofFiles = directoryFile.list();
                 for(int i=0; i<listofFiles.length; i++){
                     int j=1;
                     ArrayList<result> theseResults = DeSerializeRun(directory + listofFiles[i]);
                     for(result thisResult:theseResults){
                         summarywriter.write(j + "," + thisResult.b1 + "," + thisResult.b2 + "," + thisResult.c + "," + thisResult.k + "," + thisResult.p + "," + thisResult.w + "," + thisResult.pop + "," + thisResult.typesDist.get(0).proportion + "," + thisResult.typesDist.get(1).proportion + "," + thisResult.typesDist.get(2).proportion + "," + thisResult.typesDist.get(3).proportion + "," + thisResult.coopRate + "," + thisResult.punRate + "," + thisResult.LastcoopRate + "," + thisResult.LastpunRate + ",");
                         summarywriter.write("\n");
                         j++;
                     }
                }
                 summarywriter.close();
             } catch(IOException ex) {
                System.out.println("Couldn't write to file");
                ex.printStackTrace();
            }
        }

         if(game.matches("Public Goods v2")){
             try {
                 File summaryfile = new File(file.getAbsolutePath().replaceAll(".csv","")+"summary.csv");
                 BufferedWriter summarywriter = new BufferedWriter(new FileWriter(summaryfile));
                 summarywriter.write("Gen,bTOc,PunCost,PunEffect,W,GroupSize,PopSize,PC,PD,CoopRate,PunRate,Rho,LastCoopRate,LastPunRate,\n");
                 String directory = directoryField.getText();
                 directory+= fileSep+"temp"+fileSep;
                 File directoryFile = new File(directory);
                 String[] listofFiles = directoryFile.list();
                 for(int i=0; i<listofFiles.length; i++){
                     int j=1;
                     ArrayList<result> theseResults = DeSerializeRun(directory + listofFiles[i]);
                     for(result thisResult:theseResults){
                         summarywriter.write(j + "," + thisResult.b/thisResult.c + "," + thisResult.k + "," + thisResult.p + "," + thisResult.w + "," + thisResult.group + "," + thisResult.pop + "," + thisResult.typesDist.get(0).proportion + "," + thisResult.typesDist.get(1).proportion + "," + thisResult.coopRate + "," + thisResult.punRate + "," + thisResult.rho + "," + thisResult.LastcoopRate + "," + thisResult.LastpunRate + ",");
                         summarywriter.write("\n");
                         j++;
                     }
                }
                 summarywriter.close();
             } catch(IOException ex) {
                System.out.println("Couldn't write to file");
                ex.printStackTrace();
            }
        }
         
         if(game.matches("Public Goods 3")){
             try {
                 File summaryfile = new File(file.getAbsolutePath().replaceAll(".csv","")+"summary.csv");
                 File last500ave = new File(file.getAbsolutePath().replaceAll(".csv", "_last500ave.csv"));
                 BufferedWriter summarywriter = new BufferedWriter(new FileWriter(summaryfile));
                 BufferedWriter last500aveWriter = new BufferedWriter(new FileWriter(last500ave));
                 summarywriter.write("Iteration,Gen,bTOc,PunCost,PunEffect,W,Ecology,ExpInxns,GroupSize,PopSize,Rounds,PC,PD,RT,CoopRate,PunRate,LastCoopRate,LastPunRate,rPC-PD,rPC-RT,rPD-RT,rF-PC,rF-PD,rF-RT,AvePunSpent,AveBenGained,rPun-Gain\n");
                 last500aveWriter.write("Iteration,bTOc,PunCost,PunEffect,W,Ecology,ExpInxns,GroupSize,PopSize,AveRound,PC,PD,RT,CoopRate,PunRate,LastCoopRate,LastPunRate,rPC-PD,rPC-RT,rPD-RT,rF-PC,rF-PD,rF-RT,AvePunSpent,AveBenGained,rPun-Gain,\n");
                 String directory = directoryField.getText();
                 directory+= fileSep+"temp"+fileSep;
                 File directoryFile = new File(directory);
                 String[] listofFiles = directoryFile.list();
                 for(int i=0; i<listofFiles.length; i++){
                     int j=1;
                     ArrayList<result> theseResults = DeSerializeRun(directory + listofFiles[i]);
                     ArrayList<Double> avePC = new ArrayList();
                     ArrayList<Double> avePD = new ArrayList();
                     ArrayList<Double> aveRT = new ArrayList();
                     ArrayList<Double> avePun = new ArrayList();
                     ArrayList<Double> aveCoop = new ArrayList();
                     ArrayList<Double> aveLastPun = new ArrayList();
                     ArrayList<Double> aveLastCoop = new ArrayList();
                     ArrayList<Double> aveR12 = new ArrayList();
                     ArrayList<Double> aveR13 = new ArrayList();
                     ArrayList<Double> aveR23 = new ArrayList();
                     ArrayList<Double> aveRf1 = new ArrayList();
                     ArrayList<Double> aveRf2 = new ArrayList();
                     ArrayList<Double> aveRf3 = new ArrayList();
                     ArrayList<Double> avePunSpent = new ArrayList();
                     ArrayList<Double> aveGain = new ArrayList();
                     ArrayList<Double> aveRPG = new ArrayList();
                     ArrayList<Double> aveRep = new ArrayList();
                     
                     
                     for(result thisResult:theseResults){
                         double expInxns;
                         if(thisResult.ecology.matches("Mixed")){
                             expInxns=shorten(1/(1-thisResult.w)*thisResult.group/(thisResult.pop/thisResult.worlds));
                         } else {
                             expInxns=shorten(1/(1-thisResult.w));
                         }
                         summarywriter.write(thisResult.iteration + "," + j + "," + thisResult.b/thisResult.c + "," + thisResult.k + "," + thisResult.p + "," + thisResult.w + "," + thisResult.ecology + "," + expInxns + "," + thisResult.group + "," + thisResult.pop + "," + thisResult.reps + "," + shorten(thisResult.typesDist.get(0).proportion) + "," + shorten(thisResult.typesDist.get(1).proportion) + "," + shorten(thisResult.typesDist.get(2).proportion) + "," + shorten(thisResult.coopRate) + "," + shorten(thisResult.punRate) + "," + shorten(thisResult.LastcoopRate) + "," + shorten(thisResult.LastpunRate) + "," + shorten(thisResult.rho12) + "," + shorten(thisResult.rho13) + "," + shorten(thisResult.rho23) + "," + shorten(thisResult.rhof1) + "," + shorten(thisResult.rhof2) + "," + shorten(thisResult.rhof3) + "," + shorten(thisResult.avePun) + ","+ shorten(thisResult.aveGain) + "," + shorten(thisResult.rPunGain) + ",");
                         summarywriter.write("\n");
                         
                         if(j==1){
                             last500aveWriter.write(thisResult.iteration + "," + thisResult.b/thisResult.c + "," + thisResult.k + "," + thisResult.p + "," + thisResult.w + "," + thisResult.ecology + "," + expInxns + "," + thisResult.group + "," + thisResult.pop + ",");
                         } else if(j<=thisResult.gen-(int)Math.round(thisResult.gen*.05)){
                         } else {
                             avePC.add(thisResult.typesDist.get(0).proportion);
                             avePD.add(thisResult.typesDist.get(1).proportion);
                             aveRT.add(thisResult.typesDist.get(2).proportion);
                             avePun.add(thisResult.punRate);
                             aveCoop.add(thisResult.coopRate);
                             aveLastPun.add(thisResult.LastpunRate);
                             aveLastCoop.add(thisResult.LastcoopRate);
                             aveR12.add(thisResult.rho12);
                             aveR13.add(thisResult.rho13);
                             aveR23.add(thisResult.rho23);
                             aveRf1.add(thisResult.rhof1);
                             aveRf2.add(thisResult.rhof2);
                             aveRf3.add(thisResult.rhof3);
                             avePunSpent.add(thisResult.avePun);
                             aveGain.add(thisResult.aveGain);
                             aveRPG.add(thisResult.rPunGain);
                             aveRep.add(thisResult.reps);
                             //System.out.println(mean(aveCoop) + ", " + shorten(mean(aveCoop)));
                         }
                         j++;
                     }
                     last500aveWriter.write(shorten(mean(aveRep)) + "," + shorten(mean(avePC)) + "," + shorten(mean(avePD)) + "," + shorten(mean(aveRT)) + "," + shorten(mean(aveCoop)) + "," + shorten(mean(avePun)) + "," + shorten(mean(aveLastCoop)) + "," + shorten(mean(aveLastPun)) + "," + shorten(mean(aveR12)) + "," + shorten(mean(aveR13)) + "," + shorten(mean(aveR23)) + "," + shorten(mean(aveRf1)) + "," + shorten(mean(aveRf2)) + "," + shorten(mean(aveRf3)) + "," + shorten(mean(avePunSpent)) + "," + shorten(mean(aveGain)) + "," + shorten(mean(aveRPG)) + "," + "\n");
                }
                 summarywriter.close();
                 last500aveWriter.close();
             } catch(IOException ex) {
                System.out.println("Couldn't write to file");
                ex.printStackTrace();
            }
        }
         
        if(game.matches("Hawk/Dove")){
             try {
                 //BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                 File summaryfile = new File(file.getAbsolutePath().replaceAll(".csv","")+"summary.csv");
                 BufferedWriter summarywriter = new BufferedWriter(new FileWriter(summaryfile));
                 summarywriter.write("B,C,BaseFit,MutSize,MutRate,PopSize,GenNumber,PropHawk,PropDove,\n");
                 //writer.write("B,C,BaseFit,MutSize,MutRate,PopSize,GenNumber,PropHawk,PropDove,\n");
                 String directory = directoryField.getText();
                 directory+= fileSep+"temp"+fileSep;
                 File directoryFile = new File(directory);
                 String[] listofFiles = directoryFile.list();
                 for(int i=0; i<listofFiles.length; i++){
                     ArrayList<result> theseResults = DeSerializeRun(directory + listofFiles[i]);
                     double opt = theseResults.get(0).optimum;
                     for(result thisResult:theseResults){
                         //writer.write(thisResult.b + "," + thisResult.c + "," + thisResult.basefit + "," + thisResult.ms + "," + thisResult.mr + "," + thisResult.pop + "," + thisResult.gen + "," + thisResult.typesDist.get(0).proportion + "," + thisResult.typesDist.get(1).proportion + ",");
                         summarywriter.write(thisResult.b + "," + thisResult.c + "," + thisResult.basefit + "," + thisResult.ms + "," + thisResult.mr + "," + thisResult.pop + "," + thisResult.gen + "," + thisResult.typesDist.get(0).proportion + "," + thisResult.typesDist.get(1).proportion + ",");
                         //for(agent anAgent:thisResult.AgentList){
                             //HDagent thisAgent = (HDagent) anAgent;
                            //writer.write(thisAgent.getType() + ",");
                         //}
                         //writer.write("\n");
                         summarywriter.write("\n");
                     }
                }
                 //writer.close();
                 summarywriter.close();
             } catch(IOException ex) {
                System.out.println("Couldn't write to file");
                ex.printStackTrace();
            }
        }
        if(game.matches("Belief Formation Redux")){
             try {
                 //BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                 File summaryfile = new File(file.getAbsolutePath().replaceAll(".csv","")+"summary.csv");
                 BufferedWriter summarywriter = new BufferedWriter(new FileWriter(summaryfile));
                 summarywriter.write("B,C,BaseFit,W,D,OSP,x1shot,x2shot,x3shot,x4shot,x5shot,MutSize,ParamMutRate,KindMutRate,PopSize,GenNumber,iteration,MeanBC,OptBC,BCsd,BCse,t,T2FT Rate,HTFT Rate\n");
                 //writer.write("B,C,BaseFit,W,D,OSP,MutSize,MutRate,PopSize,GenNumber,MeanBC,OptBC,BCsd,BCse,t,\n");
                 String directory = directoryField.getText();
                 directory+= fileSep+"temp"+fileSep;
                 File directoryFile = new File(directory);
                 String[] listofFiles = directoryFile.list();
                 for(int i=0; i<listofFiles.length; i++){
                     ArrayList<result> theseResults = DeSerializeRun(directory + listofFiles[i]);
                     double opt = theseResults.get(0).optimum;
                     for(result thisResult:theseResults){
                         //writer.write(thisResult.b + "," + thisResult.c + "," + thisResult.basefit + "," + thisResult.w + "," + thisResult.d + "," + thisResult.osp + "," + thisResult.ms + "," + thisResult.mr + "," + thisResult.pop + "," + thisResult.gen + "," + thisResult.typesDist.get(0).proportion + "," + opt + "," + thisResult.typesDist.get(0).sd + "," + thisResult.typesDist.get(0).se + "," + (thisResult.typesDist.get(0).proportion-opt)/thisResult.typesDist.get(0).se + ",");
                         summarywriter.write(thisResult.b + "," + thisResult.c + "," + thisResult.basefit + "," + thisResult.w + "," + thisResult.d + "," + thisResult.osp + "," + thisResult.x1shot + "," + thisResult.x2shot + ","+ thisResult.x3shot + ","+ thisResult.x4shot + ","+ thisResult.x5shot + ","+ thisResult.ms + "," + thisResult.mr + "," + thisResult.kmr + "," + thisResult.pop + "," + thisResult.gen + "," + thisResult.iteration + "," + thisResult.typesDist.get(0).proportion + "," + opt + "," + thisResult.typesDist.get(0).sd + "," + thisResult.typesDist.get(0).se + "," + (thisResult.typesDist.get(0).proportion-opt)/thisResult.typesDist.get(0).se + "," + thisResult.typesDist.get(1).proportion + ","+ thisResult.typesDist.get(2).proportion + ",");
                         //for(agent thisAgent:thisResult.AgentList){
                           // writer.write(thisAgent.getBC() + ",");
                        // }
                        // writer.write("\n");
                         summarywriter.write("\n");
                     }
                }
                // writer.close();
                 summarywriter.close();
             } catch(IOException ex) {
                System.out.println("Couldn't write to file");
                ex.printStackTrace();
            }
        }
         if(game.matches("Belief Formation")){
             try {
                 //BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                 File summaryfile = new File(file.getAbsolutePath().replaceAll(".csv","")+"summary.csv");
                 BufferedWriter summarywriter = new BufferedWriter(new FileWriter(summaryfile));
                 summarywriter.write("B,C,BaseFit,W,D,OSP,MutSize,MutRate,PopSize,GenNumber,MeanBC,OptBC,BCsd,BCse,t,\n");
                 //writer.write("B,C,BaseFit,W,D,OSP,MutSize,MutRate,PopSize,GenNumber,MeanBC,OptBC,BCsd,BCse,t,\n");
                 String directory = directoryField.getText();
                 directory+= fileSep+"temp"+fileSep;
                 File directoryFile = new File(directory);
                 String[] listofFiles = directoryFile.list();
                 for(int i=0; i<listofFiles.length; i++){
                     ArrayList<result> theseResults = DeSerializeRun(directory + listofFiles[i]);
                     double opt = theseResults.get(0).optimum;
                     for(result thisResult:theseResults){
                         //writer.write(thisResult.b + "," + thisResult.c + "," + thisResult.basefit + "," + thisResult.w + "," + thisResult.d + "," + thisResult.osp + "," + thisResult.ms + "," + thisResult.mr + "," + thisResult.pop + "," + thisResult.gen + "," + thisResult.typesDist.get(0).proportion + "," + opt + "," + thisResult.typesDist.get(0).sd + "," + thisResult.typesDist.get(0).se + "," + (thisResult.typesDist.get(0).proportion-opt)/thisResult.typesDist.get(0).se + ",");
                         summarywriter.write(thisResult.b + "," + thisResult.c + "," + thisResult.basefit + "," + thisResult.w + "," + thisResult.d + "," + thisResult.osp + "," + thisResult.ms + "," + thisResult.mr + "," + thisResult.pop + "," + thisResult.gen + "," + thisResult.typesDist.get(0).proportion + "," + opt + "," + thisResult.typesDist.get(0).sd + "," + thisResult.typesDist.get(0).se + "," + (thisResult.typesDist.get(0).proportion-opt)/thisResult.typesDist.get(0).se + ",");
                         //for(agent thisAgent:thisResult.AgentList){
                           // writer.write(thisAgent.getBC() + ",");
                        // }
                        // writer.write("\n");
                         summarywriter.write("\n");
                     }
                }
                // writer.close();
                 summarywriter.close();
             } catch(IOException ex) {
                System.out.println("Couldn't write to file");
                ex.printStackTrace();
            }
        }
        if(game.matches("Belief Implementation")){
             try {
                 File summaryfile = new File(file.getAbsolutePath().replaceAll(".csv","")+"summary.csv");
                 BufferedWriter summarywriter = new BufferedWriter(new FileWriter(summaryfile));
                 //BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                 summarywriter.write("B,C,BaseFit,W,D,OSP,MutSize,MutRate,PopSize,GenNumber,PrCifR,Rsd,Rse,t,PrCifOS,OSsd,OSse,t,\n");
                 //writer.write("B,C,BaseFit,W,D,OSP,MutSize,MutRate,PopSize,GenNumber,PrCifR,Rsd,Rse,t,PrCifOS,OSsd,OSse,t,\n");
                 String directory = directoryField.getText();
                 directory+= fileSep+"temp"+fileSep;
                 File directoryFile = new File(directory);
                 String[] listofFiles = directoryFile.list();
                 for(int i=0; i<listofFiles.length; i++){
                     ArrayList<result> theseResults = DeSerializeRun(directory + listofFiles[i]);
                     for(result thisResult:theseResults){
                         //writer.write(thisResult.b + "," + thisResult.c + "," + thisResult.basefit + "," + thisResult.w + "," + thisResult.d + "," + thisResult.osp + "," + thisResult.ms + "," + thisResult.mr + "," + thisResult.pop + "," + thisResult.gen + "," + thisResult.typesDist.get(0).proportion + "," + thisResult.typesDist.get(0).sd + "," + thisResult.typesDist.get(0).se + "," + (1-thisResult.typesDist.get(0).proportion)/thisResult.typesDist.get(0).se + "," + thisResult.typesDist.get(1).proportion + "," + thisResult.typesDist.get(1).sd + "," + thisResult.typesDist.get(1).se + "," + (thisResult.typesDist.get(1).proportion)/thisResult.typesDist.get(1).se + ",");
                         summarywriter.write(thisResult.b + "," + thisResult.c + "," + thisResult.basefit + "," + thisResult.w + "," + thisResult.d + "," + thisResult.osp + "," + thisResult.ms + "," + thisResult.mr + "," + thisResult.pop + "," + thisResult.gen + "," + thisResult.typesDist.get(0).proportion + "," + thisResult.typesDist.get(0).sd + "," + thisResult.typesDist.get(0).se + "," + (1-0.028-thisResult.typesDist.get(0).proportion)/thisResult.typesDist.get(0).se + "," + thisResult.typesDist.get(1).proportion + "," + thisResult.typesDist.get(1).sd + "," + thisResult.typesDist.get(1).se + "," + (thisResult.typesDist.get(1).proportion-0.028)/thisResult.typesDist.get(1).se + ",");
                         //for(agent thisAgent:thisResult.AgentList){
                         //   writer.write(thisAgent.getprCifRprCifOS()[0] + " : " + thisAgent.getprCifRprCifOS()[1] + ",");
                         //}
                         summarywriter.write("\n");
                         //writer.write("\n");
                     }
                }
                 //writer.close();
                 summarywriter.close();
             } catch(IOException ex) {
                System.out.println("Couldn't write to file");
                ex.printStackTrace();
            }
        }
        if(game.matches("Warmth/Competence")){
             try {
                 //BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                 File summaryfile = new File(file.getAbsolutePath().replaceAll(".csv","")+"summary.csv");
                 BufferedWriter summarywriter = new BufferedWriter(new FileWriter(summaryfile));
                 summarywriter.write("PopSize,GenNumber,W,CogType,DistType,PID,GID,PDS,GDS,MutSize,MutRate,iteration,Gene,r(notPicked-Gen),r(notPicked-Prod),r(Fitness-Gen),r(Fitness-Prod),r(Fitness-Pref),r(Fitness-PartnerG),r(Fitness-PartnerP),r(pref-PartnerG),r(pref-PartnerP),\n");
                 //***actual repetition rate;
                 String directory = directoryField.getText();
                 directory+= fileSep+"temp"+fileSep;
                 File directoryFile = new File(directory);
                 String[] listofFiles = directoryFile.list();
                 for(int i=0; i<listofFiles.length; i++){
                     ArrayList<result> theseResults = DeSerializeRun(directory + listofFiles[i]);
                     //System.out.println(theseResults.size() + " results in this .ser");
                     for(result thisResult:theseResults){
                         //System.out.println(thisResult.w + " was the w for this result");
                         summarywriter.write(thisResult.pop + "," + thisResult.gen + "," + thisResult.w + "," + thisResult.cogType + "," + thisResult.distType + "," + thisResult.PID + "," + thisResult.GID + "," + thisResult.PDS + "," + thisResult.GDS + "," + thisResult.mr + "," + thisResult.ms + "," +thisResult.iteration + "," + thisResult.typesDist.get(0).proportion + "," + checkedDouble(thisResult.correlations.get(0)) + ","+ checkedDouble(thisResult.correlations.get(1)) + ","+ checkedDouble(thisResult.correlations.get(2)) + ","+ checkedDouble(thisResult.correlations.get(3)) + ","+ checkedDouble(thisResult.correlations.get(4)) + ","+ checkedDouble(thisResult.correlations.get(5)) + ","+ checkedDouble(thisResult.correlations.get(6)) + ","+ checkedDouble(thisResult.correlations.get(7)) + ","+ checkedDouble(thisResult.correlations.get(8)) + "," + ",");
                         summarywriter.write("\n");
                     }
                }
                // writer.close();
                 summarywriter.close();
             } catch(IOException ex) {
                System.out.println("Couldn't write to file");
                ex.printStackTrace();
            }
        }
     }

     private double OptimumX(double osp, double d){
        optimum=0;
        double inc;
        if(osp > 0.5){
            inc = -.00001;
        } else if(osp < 0.5){
            inc = .00001;
        } else {
            return 0;
        }
        double difference=10;
        while(difference>0.000001){
            optimum+=inc;
            difference = Math.abs((osp)*(1/Math.pow((2*Math.PI),0.5))*(Math.exp((-1)*Math.pow((optimum-d/2),2)/2))-(1-osp)*(1/Math.pow((2*Math.PI),0.5))*(Math.exp((-1)*Math.pow((optimum+d/2),2)/2)));
        }
        return optimum;
    }
     
     double mean(ArrayList<Double> theList){
         double runTot = 0;
         int count = 0;
         for(double thisDub:theList){     
             if(Double.isNaN(thisDub)){
                 System.out.println("NaN");
             } else {
                 runTot += thisDub;
                 count++;
             }           
         }
         System.out.println(runTot + ", " + count);
         if(count!=0){
             runTot=runTot/count;
         } else{
             runTot=Double.NaN;
         }
         return runTot;
     }
     
     double shorten(double theNum){
         if(theNum==Double.NaN){
             return Double.NaN;
         } else {
            return(Math.round(theNum*10000)/10000.0d);
         }
     }
     
    private String checkedDouble(Double toCheck){
        String toReturn="";
        if(Double.isFinite(toCheck)){
            toReturn=toCheck.toString();
        } 
        return toReturn;
    }
}