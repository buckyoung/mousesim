import java.util.ArrayList;

public class WorldNode {

	//* Private Constants
	private static final String IMG_EMPTY = " ";
	private static final String IMG_BABY = "~";
	private static final String IMG_FOOD = "`";
	private static final String IMG_MOUSE = "a";

	//* Private Fields
	private int col;
	private ArrayList<Object> container;
	private String representation;
	private int row;

	//* Public Methods
	public WorldNode(int row, int col) {
		container = new ArrayList<>();
		this.row = row;
		this.col = col;
	}

	public void add(Food x) {
		if(x == null) return;

		container.add(x);
	}

	public void add(Mouse x) {
		if(x == null) return;

		container.add(x);
	}

	public Food getAnyFood() {
		if(hasFood()) {
			for(Object o : container) {
				if(Food.class.isInstance(o)) {
					return (Food) o;
				}
			}
		}
		return null;
	}

	public boolean hasFood() {
		return containsAny(Food.class);
	}

	public boolean hasPotentialPartner(Gender myGender) {
		return containsMouseAndGender(myGender.opposite());
	}

	public Object remove(Object o) {
		if(container.contains(o)){
			container.remove(o);
			return o;
		}
		return null;
	}

	@Override
	public String toString() {

		if(container.isEmpty()) return IMG_EMPTY;

		if(containsMouseBaby()) return IMG_BABY;

		if(containsMouseAndGender(Gender.FEMALE)) return "@"; //DEBUG

		if(containsAny(Mouse.class)) return IMG_MOUSE;

		if(containsAny(Food.class)) return IMG_FOOD;

		return IMG_EMPTY;
	}

	//* Private Methods
	private boolean containsAny(Class clazz) {
		for (Object o : container) {
			if (clazz.isInstance(o) ) {
				return true;
			}
		}
		return false;
	}

	private boolean containsMouseAndGender(Gender gender) {
		for (Object o : container) {
			if (Mouse.class.isInstance(o) && ((Mouse)o).getGender() == gender ) {
				return true;
			}
		}
		return false;
	}

	private boolean containsMouseBaby() {
		for (Object o : container) {
			if (Mouse.class.isInstance(o) && ((Mouse)o).isBaby() ) {
				return true;
			}
		}
		return false;
	}

}
