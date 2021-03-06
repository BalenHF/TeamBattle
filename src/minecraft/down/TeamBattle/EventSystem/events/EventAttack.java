package down.TeamBattle.EventSystem.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import down.TeamBattle.EventSystem.Cancellable;
import down.TeamBattle.EventSystem.Event;

public class EventAttack extends Event implements Cancellable{

	  private Entity attacker;
	  private Entity target;

	  public EventAttack(Entity attacker, Entity target)
	  {
	    this.attacker = attacker;
	    this.target = target;
	  }

	  public Entity getAttacker() {
	    return this.attacker;
	  }

	  public Entity getTarget() {
	    return this.target;
	  }

	@Override
	public boolean isCancelled() {
		
		return false;
	}

	@Override
	public void setCancelled(boolean cancel) {
		
		
	}
}
