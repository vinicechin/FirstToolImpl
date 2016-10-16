import java.awt.event.*;

/**
 * Created by Vinicius on 22/05/2015.
 */
public class SelectionController extends MouseAdapter implements WindowListener{
    private ListPanel listPanel;

    //constructor
    public SelectionController(ListPanel listPanel){
        super();
        this.listPanel = listPanel;
    }

    //setters
    public void setListPanel(ListPanel panel) {
        this.listPanel = panel;
    }

    //getters
    public ListPanel getListPanel() {
        return this.listPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //if the left button of the mouse was pressed
        if (e.getButton() == MouseEvent.BUTTON1) {
            //selects clicked picture if the click was made over a picture
            for (ImageInfo i : this.listPanel.getImageChooser().getImageList()) {
                if (this.listPanel.getCategoryType() == "all" || i.getCategoria().toLowerCase().compareTo(this.listPanel.getCategoryType()) == 0) {
                    if (i.contains(e.getPoint())) {
                        this.listPanel.updateImageMain(i);
                        break;
                    }
                }
            }
            //repaint the panel
            this.listPanel.repaint();
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
