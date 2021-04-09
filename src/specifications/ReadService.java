/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/ReadService.java 2015-03-09 buixuan.
 * ******************************************************/
package specifications;

import java.util.ArrayList;

import data.Circle;
import data.Mod;
import data.Player;
import data.Table;

public interface ReadService {
	public Table getTable();
	public ArrayList<Circle> getTrou();
	public Mod getMod();
	public Player getPlayer1();
	public Player getPlayer2();
	public Player getCurrentPlayer();
	public Player getWinner();
}
