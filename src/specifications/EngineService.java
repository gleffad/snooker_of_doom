/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/EngineService.java 2015-03-09 buixuan.
 * ******************************************************/
package specifications;

public interface EngineService extends StartEngineService, SimulatorService{
  public void init();
}
