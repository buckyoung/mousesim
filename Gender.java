public enum Gender {
	MALE("M"),
	FEMALE("F");

	private String representation;
	private Gender(String s) {
		representation = s;
	}

	public Gender opposite() {
		if(this == Gender.MALE) {
			return FEMALE;
		} else {
			return MALE;
		}
	}

	@Override
	public String toString() {
		return representation;
	}
}