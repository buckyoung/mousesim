public class NameGenerator {

	private static final String[] maleNames = {
		"Abe", 
		"Andy", 
		"Arthur",
		"Benjy",
		"Bob",
		"Caesar",
		"Conan",
		"Conner",
		"Cornelius",
		"Cyril",
		"D-503",
		"Doug",
		"Ford",
		"Frank", 
		"Frankie",
		"Frederer",
		"Gene",
		"Greg", 
		"H.G.",
		"Harvey", 
		"J.R.R",
		"Jimmy",
		"Kanye", 
		"Karl", 
		"Korben",
		"Larry", 
		"Leonard",
		"Marvin",
		"Norman", 
		"Ollie",
		"Rico",
		"Rodney", 
		"Sal", 
		"Splinter", 
		"Sterling",
		"Teddy",
		"Tristen", 
		"Zaphod",
	};

	private static final String[] femaleNames = {
		"Autumn", 
		"Brittany", 
		"Cathy", 
		"Dizzy",
		"Dorthy", 
		"Emma", 
		"Gayle",
		"Kate", 
		"Kim", 
		"Lana",
		"Leeloo",
		"Linda",
		"Louise",
		"Marla", 
		"Megan", 
		"Mousica", 
		"O-90",
		"Pam",
		"Peggy", 
		"Sally", 
		"Sarah",
		"Shannon",
		"Tina",
		"Trillian",
		"Whitney", 
	};

	private static final String[] lastNames = {
		"Cheddarwood", 
		"Wallsqueaker", 
		"Squeakswell", 
		"Swisser", 
		"Mazerunner", 
		"Wheelrunner", 
		"Pelleteater", 
		"Chompsky", 
		"Skeeter", 
		"Anderson",
		"Whiskers",
		"Zamyatin",
		"Archer",
		"Figgis",
		"Woodhouse",
		"Tunt",
		"Kane",
		"Belcher",
		"Krieger",
		"Trexler",
		"Beeblebrox",
		"Dallas",
		"Zeus",
		"Feynman",
		"Hagen",
		"Chah"
		// "\u2764", //heart
		// "\u262F", //yinyang
		// "\u2744", //snowflake
	};

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