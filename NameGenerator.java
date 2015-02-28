public class NameGenerator {

	private static final String[] maleNames = {
		"Karl", 
		"Harvey", 
		"Greg", 
		"Tristen", 
		"Larry", 
		"Sal", 
		"Kanye", 
		"Andy", 
		"Rodney", 
		"Norman", 
		"Splinter", 
		"Frank", 
		"Abe", 
		"Jimmy",
		"Cyril",
		"Doug",
		"Frederer",
		"Conan",
		"Leonard",
		"Arthur",
		"Ford",
		"D-503",
		"H.G.",
		"J.R.R",
		"Sterling",
		"Bob",
		"Gene",
		"Teddy",
		"Ollie",
		"Sarah",
		"Dizzy",
		"Zaphod",
		"Marvin",
		"Frankie",
		"Benjy",
		"Korben",
		"Cornelius",
		"Caesar"
	};

	private static final String[] femaleNames = {
		"Cathy", 
		"Mousica", 
		"Brittany", 
		"Marla", 
		"Sally", 
		"Peggy", 
		"Kim", 
		"Dorthy", 
		"Megan", 
		"Emma", 
		"Whitney", 
		"Kate", 
		"Autumn", 
		"Shannon",
		"Pam",
		"O-90",
		"Lana",
		"Tina",
		"Louise",
		"Linda",
		"Gayle",
		"Conner",
		"Rico",
		"Busey",
		"Trillian",
		"Leeloo"
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