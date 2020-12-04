package moves;

import org.json.simple.JSONObject;

public class Move
{
	public boolean isAttack = false;
	public boolean isJump = false;
	public Position from = new Position(0,0);
	public Position through = new Position(0,0);
	public Position to = new Position(0,0);
	public String toJSONString() {
		JSONObject object = new JSONObject();
		if(isAttack) {
			object.put("isAttack", true);
		}
		
		if(isJump) {
			JSONObject th = new JSONObject();
			th.put("X", through.x);
			th.put("Y", through.y);
			object.put("Through", th);
		}
		
		JSONObject toObj = new JSONObject();
		toObj.put("X", to.x);
		toObj.put("Y", to.y);
		object.put("To", toObj);
		
		JSONObject fromObj = new JSONObject();
		fromObj.put("X", from.x);
		fromObj.put("Y", from.y);
		object.put("From", fromObj);
		JSONObject mobj = new JSONObject();
		mobj.put("Move", object);
		return mobj.toJSONString();
	}
}
