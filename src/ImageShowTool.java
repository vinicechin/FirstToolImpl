import javax.swing.*;
import java.awt.*;
import org.jfree.chart.*;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Created by Vinicius on 12/05/2016.
 */
public class ImageShowTool extends JFrame {
    private MainPanel         mainPanel;
    private JScrollPane       listScrollPanel;
    private GraphPanel        graphPanel;
    private DataPanel         dataPanel;
    private JMenu             fileMenu;
    private JMenuBar          menuBar;
    private ButtonGroup       buttonGroup1;
    private JRadioButton      byColor;
    private JRadioButton      byYear;
    private JRadioButton      bySize;
    private ListPanel         listPanel;
    private JComboBox<String> colorOrderSelector;
    private JComboBox<String> categorySelector;
    private JToggleButton     realSizeList;
    private int               fWidth;
    private int               fHeight;

    public ImageShowTool(String name){
        super(name);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.fWidth = (int)screenSize.getWidth()-200;
        this.fHeight = (int)screenSize.getHeight()-200;
        this.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
        //this.setSize(1400, 820);
        initComponents();
    }

    private void initComponents(){
        buttonGroup1        = new javax.swing.ButtonGroup();
        mainPanel           = new MainPanel();
        graphPanel          = new GraphPanel();
        dataPanel           = new DataPanel();
        listPanel           = new ListPanel(mainPanel, graphPanel, dataPanel);
        listScrollPanel     = new JScrollPane(listPanel);
        byColor             = new javax.swing.JRadioButton();
        byYear              = new javax.swing.JRadioButton();
        bySize              = new javax.swing.JRadioButton();
        colorOrderSelector  = new javax.swing.JComboBox<>();
        categorySelector    = new javax.swing.JComboBox<>();
        realSizeList        = new javax.swing.JToggleButton();
        menuBar             = new javax.swing.JMenuBar();
        fileMenu            = new javax.swing.JMenu();

        //create the frame menu File and add buttons to it
        fileMenu.setText("File");
        fileMenu.add(listPanel.getLoadFile());
        fileMenu.add(listPanel.getLoadFileWithMaps());
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        //configure frame and panels
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        //listPanel.setPreferredSize(new Dimension(1270,100));
        listPanel.setPreferredSize(new Dimension(this.fWidth-130,this.fHeight/8));

        //main panel layout
        javax.swing.GroupLayout ImageInfoPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(ImageInfoPanelLayout);
        ImageInfoPanelLayout.setVerticalGroup(
                ImageInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, this.fHeight-this.fHeight/8, Short.MAX_VALUE)
        );
        ImageInfoPanelLayout.setHorizontalGroup(
                ImageInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, this.fWidth-650, Short.MAX_VALUE)
        );

        //graph panel layout
        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setVerticalGroup(
                graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, this.fHeight-this.fHeight/8, Short.MAX_VALUE)
        );
        graphPanelLayout.setHorizontalGroup(
                graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        dataPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        //data panel layout
        javax.swing.GroupLayout dataPanelLayout = new javax.swing.GroupLayout(dataPanel);
        dataPanel.setLayout(dataPanelLayout);
        dataPanelLayout.setVerticalGroup(
                dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, this.fHeight-this.fHeight/8, Short.MAX_VALUE)
        );
        dataPanelLayout.setHorizontalGroup(
                dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(100, 275, Short.MAX_VALUE)
        );

        //list layout
        listScrollPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Images List"));
        listScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        listScrollPanel.setBounds(0, 0, this.fWidth-200, this.fHeight/8-50);
        
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

        categorySelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "all","self-portrait","peasant life","cityscape","landscape","figures","still life", "nature", "interior" }));
        categorySelector.setSelectedIndex(0);
        categorySelector.addItemListener(evt -> CategorySelectorItemStateChanged(evt));

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
                                        .addComponent(colorOrderSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(categorySelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(listScrollPanel)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(dataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(listScrollPanel)
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
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(categorySelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(dataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }

    private void ColorOrderSelectorItemStateChanged(java.awt.event.ItemEvent evt) {
        if(evt.getStateChange() == 1){
            this.listPanel.setColorOrderType(evt.getItem().toString());
        }
    }

    private void CategorySelectorItemStateChanged(java.awt.event.ItemEvent evt) {
        if(evt.getStateChange() == 1){
            this.listPanel.setCategoryType(evt.getItem().toString());
        }
    }

    private void jRadioButtonActionPerformed(java.awt.event.ActionEvent evt, String orderType) {
        //System.out.println("action button pressed, order type: " + orderType);
        this.listPanel.setOrderType(orderType);
    }

    private void realSizeListActionPerformed(java.awt.event.ActionEvent evt) {
        if(this.listPanel.getListShowType() == "modified"){
            this.listPanel.setListShowType("real");
        } else {
            this.listPanel.setListShowType("modified");
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

