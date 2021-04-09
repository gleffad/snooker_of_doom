/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/ViewerService.java 2015-03-09 buixuan.
 * ******************************************************/
package specifications;

import javafx.scene.Group;

public interface ViewerService{
  public void init();
  public void startViewer();
  public Group getPanel();
  public void setCursorPressed(double x, double y);
public void setCursorMoved(double x, double y);
}
