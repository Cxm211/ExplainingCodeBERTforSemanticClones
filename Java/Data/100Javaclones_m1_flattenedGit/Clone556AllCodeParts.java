public class Clone556AllCodeParts { 
public void actionPerformed (ActionEvent arg0) {
int result=JOptionPane.OK_OPTION;
if(dirty)
result=JOptionPane.showConfirmDialog(gui,"Erase the current painting?");
if(result == JOptionPane.OK_OPTION)
clear(canvasImage);
}
 
 public void actionPerformed (ActionEvent ae) { 
     if (ae.getSource () == select) { 
         activeTool = SELECTION_TOOL; 
     } else if (ae.getSource () == draw) { 
         activeTool = DRAW_TOOL; 
     } else if (ae.getSource () == text) { 
         activeTool = TEXT_TOOL; 
     } 
 }

}