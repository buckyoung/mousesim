import java.util.ArrayList;

public class WorldNode {

	//* Private Constants
	private static final String IMG_EMPTY = " ";
	private static final String IMG_BABY = "\u14D0";
	private static final String IMG_MALE_MOUSE = "\u14C6";
	private static final String IMG_FEMALE_MOUSE = "\u14CF"; //tail to right
	private static final String IMG_FOOD = "`";

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
		return containsAdultMouseAndGender(myGender.opposite());
	}

	public Mouse getUnpregnantFemale() {
		if(hasPotentialPartner(Gender.MALE)) {
			for(Object o : container) {
				if(Mouse.class.isInstance(o) && ((Mouse)o).getGender() == Gender.FEMALE && ((Mouse)o).isPregnant() == false) {
					return (Mouse) o;
				}
			}
		}
		return null;
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
		if(containsAdultMouseAndGender(Gender.FEMALE)) return IMG_FEMALE_MOUSE; 
		if(containsAdultMouseAndGender(Gender.MALE)) return IMG_MALE_MOUSE;
		if(containsMouseBaby()) return IMG_BABY;
		if(containsAny(Food.class)) return IMG_FOOD;
		return IMG_EMPTY;
	}

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

	private boolean containsAdultMouseAndGender(Gender gender) {
		for (Object o : container) {
			if (Mouse.class.isInstance(o) && ((Mouse)o).getGender() == gender && !((Mouse)o).isBaby() ) {
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
