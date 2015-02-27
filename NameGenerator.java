public class NameGenerator {

	private static final String[] maleNames = {"Karl", "Harvey", "Greg", "Tristen", "Larry", "Sal", "Kanye", "Andy", "Rodney", "Norman", "Splinter", "Frank", "Abe", "Jimmy"};
	private static final String[] femaleNames = {"Cathy", "Mousica", "Brittany", "Marla", "Sally", "Peggy", "Kim", "Dorthy", "Megan", "Emma", "Whitney", "Kate", "Autumn", "Shannon"};
	private static final String[] lastNames = {"Cheddarwood", "Wallsqueaker", "Squeakswell", "Swisser", "Mazerunner", "Wheelrunner", "Pelleteater", "Chompsky", "Skeeter", "Anderson"};


	public static String generateFirstName(Gender gender) {
		return (gender == Gender.MALE) ? generateMaleFirstName() : generateFemaleFirstName();
	}

	public static String generateLastName() {
		return lastNames[MouseSim.rand.nextInt(lastNames.length)];
	}

	private static String generateMaleFirstName() {
		return maleNames[MouseSim.rand.nextInt(maleNames.length)];
	}

	private static String generateFemaleFirstName() {
		return femaleNames[MouseSim.rand.nextInt(femaleNames.length)];
	}


}