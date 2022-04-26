package rpg.custom;

import rpg.classes.ActionSet;
import rpg.classes.CustomItem;
import rpg.classes.State;

public class AsylumCellKey extends CustomItem {
  public AsylumCellKey(Integer id, String name, String description, String longDescription) {
    super(id, name, description, longDescription);
  }

  @Override
  public void take(ActionSet a, State s) {
    this.setName("Asylum Cell Key");
    super.take(a, s);
  }
}
