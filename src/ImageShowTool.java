import javax.swing.*;
import java.awt.*;

/**
 * Created by Vinicius on 12/05/2016.
 */
public class ImageShowTool extends JFrame {
    private MainPanel        ImageInfoPanel;
    private JScrollPane		  ImageListPanel;
    private JMenu             fileMenu;
    private JMenuBar          menuBar;
    private ButtonGroup       buttonGroup1;
    private JRadioButton      byColor;
    private JRadioButton      byYear;
    private JRadioButton      bySize;
    private ListPanel  showListPanel;

    public ImageShowTool(String name){
        super(name);
        initComponents();
        this.setSize(800, 600);
        //this.setResizable(false);
    }

    private void initComponents(){
        buttonGroup1    = new javax.swing.ButtonGroup();
        ImageInfoPanel  = new MainPanel(600,600);
        showListPanel   = new ListPanel(ImageInfoPanel);
        ImageListPanel  = new JScrollPane(showListPanel);
        byColor         = new javax.swing.JRadioButton();
        byYear          = new javax.swing.JRadioButton();
        bySize          = new javax.swing.JRadioButton();
        menuBar         = new javax.swing.JMenuBar();
        fileMenu        = new javax.swing.JMenu();

        //create the frame menu File and add buttons to it
        fileMenu.setText("File");
        fileMenu.add(showListPanel.getLoadFile());
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        //configure frame and panels
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        showListPanel.setPreferredSize(new Dimension(150,2000));
        
        javax.swing.GroupLayout ImageInfoPanelLayout = new javax.swing.GroupLayout(ImageInfoPanel);
        ImageInfoPanel.setLayout(ImageInfoPanelLayout);
        ImageInfoPanelLayout.setHorizontalGroup(
                ImageInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 600, Short.MAX_VALUE)
        );
        ImageInfoPanelLayout.setVerticalGroup(
                ImageInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        ImageListPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Images List"));
        ImageListPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        ImageListPanel.setBounds(0, 0, 200, 600);
        
        //configure buttons group
        buttonGroup1.add(byColor);
        byColor.setText("Color");
        byColor.addActionListener(evt -> jRadioButtonActionPerformed(evt, "color"));

        buttonGroup1.add(byYear);
        byYear.setText("Year");
        byYear.addActionListener(evt -> jRadioButtonActionPerformed(evt, "year"));

        buttonGroup1.add(bySize);
        bySize.setText("Size");
        bySize.addActionListener(evt -> jRadioButtonActionPerformed(evt, "size"));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(byColor)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(byYear)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(bySize))
                                        .addComponent(ImageListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ImageInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(ImageInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(ImageListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(byColor)
                                        .addComponent(byYear)
                                        .addComponent(bySize)))
        );
        pack();
    }

    private void jRadioButtonActionPerformed(java.awt.event.ActionEvent evt, String orderType) {
        //System.out.println("action button pressed, order type: " + orderType);
        this.showListPanel.setOrderType(orderType);
        System.out.println("button pressed");
    }

    /** The entry main() method */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new ImageShowTool("teste").setVisible(true);
            System.out.println("Started Frame");
        });
    }
}

