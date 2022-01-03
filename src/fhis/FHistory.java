package fhis;


import static mindustry.Vars.netServer;
import java.util.ArrayList;
import java.util.HashMap;

import arc.Events;
import arc.util.CommandHandler;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.UnitTypes;
import mindustry.game.Team;
import mindustry.game.EventType.BlockBuildBeginEvent;
import mindustry.game.EventType.BlockBuildEndEvent;
import mindustry.game.EventType.ConfigEvent;
import mindustry.game.EventType.GameOverEvent;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.mod.Plugin;
import mindustry.net.Administration.ActionType;
import mindustry.world.Block;


public class FHistory extends Plugin {
	
	HashMap<String, ArrayList<String>> tileactions = new HashMap<String, ArrayList<String>>();	
	@Override
    public void init(){
		
		
		/* No unit control until 20 mins have passed
		netServer.admins.addActionFilter((action) ->{ 
			
			if(action.type == ActionType.placeBlock){
				if(action.tile.isCenter()) {
				String xAndY = action.tile.x + " " + action.tile.y;
				ArrayList<String> tileactionlist = new ArrayList<String>();
				if(!tileactions.containsKey(xAndY)) {
					tileactionlist.add(action.player.name + " [yellow]placed [red]" + action.block);
					tileactions.put(xAndY, tileactionlist);
				} else {
					tileactionlist = tileactions.get(xAndY);
					tileactionlist.add(action.player.name + " [yellow]placed [red]" + action.block);
					tileactions.put(xAndY, tileactionlist);
				}	    
				}
            }
			return true;
		});
		*/
		Events.on(BlockBuildEndEvent.class, event -> {
			if(event.unit.isPlayer()) {
				String xAndY = event.tile.x + " " + event.tile.y;
				ArrayList<String> tileactionlist = new ArrayList<String>();
				if(!tileactions.containsKey(xAndY)) {
					if(!event.breaking) {
						tileactionlist.add(event.unit.getPlayer().name + " [green]placed [purple]" + event.tile.block());
						tileactions.put(xAndY, tileactionlist);
					} else {
						tileactionlist.add(event.unit.getPlayer().name + " [red]broke [purple]" + event.tile.block());
						tileactions.put(xAndY, tileactionlist);
					}
				} else {
					if(!event.breaking) {
						tileactionlist = tileactions.get(xAndY);
						tileactionlist.add(event.unit.getPlayer().name + " [green]placed [purple]" + event.tile.block());
						tileactions.put(xAndY, tileactionlist);
					} else {
						tileactionlist = tileactions.get(xAndY);
						tileactionlist.add(event.unit.getPlayer().name + " [red]broke [purple]" + event.tile.block());
						tileactions.put(xAndY, tileactionlist);
					}
				}	    
			}
		});
		
		
		
		
		
		
		
		
	}
	
	
	
	
	// Turns you infected
	@Override
    public void registerClientCommands(CommandHandler handler){
        handler.<Player>register("history", "Shows history of block", (args, player) -> {
        		String xAndY = player.unit().tileX() + " " + player.unit().tileY();
        		if(tileactions.containsKey(xAndY)) {
        			player.sendMessage("[green][ [yellow]" + player.unit().tileX() + ", " + player.unit().tileY() + "[green] ]");
        			for(String str : tileactions.get(xAndY)) {
        				player.sendMessage(str);
        			}
        		}
        		
        });
        
        
        
          
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
