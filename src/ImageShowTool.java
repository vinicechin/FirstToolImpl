import javax.swing.*;
import java.awt.*;

/**
 * Created by Vinicius on 12/05/2016.
 */
public class ImageShowTool extends JFrame {
    private MainPanel         ImageInfoPanel;
    private JScrollPane       ImageListPanel;
    private GraphPanel        graphPanel;
    private DataPanel         dataPanel;
    private JMenu             fileMenu;
    private JMenuBar          menuBar;
    private ButtonGroup       buttonGroup1;
    private JRadioButton      byColor;
    private JRadioButton      byYear;
    private JRadioButton      bySize;
    private ListPanel         showListPanel;
    private JComboBox<String> colorOrderSelector;
    private JToggleButton     realSizeList;

    public ImageShowTool(String name){
        super(name);
        initComponents();
        this.setSize(1400, 820);
    }

    private void initComponents(){
        buttonGroup1        = new javax.swing.ButtonGroup();
        ImageInfoPanel      = new MainPanel();
        graphPanel          = new GraphPanel();
        dataPanel           = new DataPanel();
        showListPanel       = new ListPanel(ImageInfoPanel, graphPanel, dataPanel);
        ImageListPanel      = new JScrollPane(showListPanel);
        byColor             = new javax.swing.JRadioButton();
        byYear              = new javax.swing.JRadioButton();
        bySize              = new javax.swing.JRadioButton();
        colorOrderSelector  = new javax.swing.JComboBox<>();
        realSizeList        = new javax.swing.JToggleButton();
        menuBar             = new javax.swing.JMenuBar();
        fileMenu            = new javax.swing.JMenu();

        //create the frame menu File and add buttons to it
        fileMenu.setText("File");
        fileMenu.add(showListPanel.getLoadFile());
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        //configure frame and panels
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ImageInfoPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        showListPanel.setPreferredSize(new Dimension(1270,100));

        //main panel layout
        javax.swing.GroupLayout ImageInfoPanelLayout = new javax.swing.GroupLayout(ImageInfoPanel);
        ImageInfoPanel.setLayout(ImageInfoPanelLayout);
        ImageInfoPanelLayout.setVerticalGroup(
                ImageInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 575, Short.MAX_VALUE)
        );
        ImageInfoPanelLayout.setHorizontalGroup(
                ImageInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 750, Short.MAX_VALUE)
        );

        //graph panel layout
        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setVerticalGroup(
                graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 575, Short.MAX_VALUE)
        );
        graphPanelLayout.setHorizontalGroup(
                graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        dataPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        //data panel layout
        javax.swing.GroupLayout dataPanelLayout = new javax.swing.GroupLayout(dataPanel);
        dataPanel.setLayout(dataPanelLayout);
        dataPanelLayout.setHorizontalGroup(
                dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(200, 250, Short.MAX_VALUE)
        );
        dataPanelLayout.setVerticalGroup(
                dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 575, Short.MAX_VALUE)
        );

        //list layout
        ImageListPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Images List"));
        ImageListPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        ImageListPanel.setBounds(0, 0, 1000, 150);
        
        //configure buttons group
        buttonGroup1.add(byColor);
        byColor.setText("Color");
        byColor.setSelected(true);
        byColor.addActionListener(evt -> jRadioButtonActionPerformed(evt, "color"));

        buttonGroup1.add(byYear);
        byYear.setText("Year");
        byYear.addActionListener(evt -> jRadioButtonActionPerformed(evt, "year"));

        buttonGroup1.add(bySize);
        bySize.setText("Size");
        bySize.addActionListener(evt -> jRadioButtonActionPerformed(evt, "size"));

        colorOrderSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Red", "Green", "Blue", "RGB", "Hue", "Saturation", "Brightness" }));
        colorOrderSelector.setSelectedIndex(3);
        colorOrderSelector.addItemListener(evt -> ColorOrderSelectorItemStateChanged(evt));

        realSizeList.setFont(new java.awt.Font("Tahoma", 0, 10));
        realSizeList.setText("Real Size");
        realSizeList.addActionListener(evt -> realSizeListActionPerformed(evt));

        //frame layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(realSizeList, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(byColor)
                                        .addComponent(byYear)
                                        .addComponent(bySize)
                                        .addComponent(colorOrderSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ImageListPanel)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(dataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ImageInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(ImageListPanel)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(5, 5, 5)
                                                .addComponent(realSizeList)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(byColor)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(byYear)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(bySize)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(colorOrderSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(ImageInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(dataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }

    private void ColorOrderSelectorItemStateChanged(java.awt.event.ItemEvent evt) {
        if(evt.getStateChange() == 1){
            this.showListPanel.setColorOrderType(evt.getItem().toString());
        }
    }

    private void jRadioButtonActionPerformed(java.awt.event.ActionEvent evt, String orderType) {
        //System.out.println("action button pressed, order type: " + orderType);
        this.showListPanel.setOrderType(orderType);
    }

    private void realSizeListActionPerformed(java.awt.event.ActionEvent evt) {
        if(this.showListPanel.getListShowType() == "modified"){
            this.showListPanel.setListShowType("real");
        } else {
            this.showListPanel.setListShowType("modified");
        }
    }

    /** The entry main() method */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new ImageShowTool("teste").setVisible(true);
            System.out.println("Started Frame");
        });
    }
}

