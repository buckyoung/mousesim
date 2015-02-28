public enum Gender {
	MALE("M"),
	FEMALE("F");

	private String representation;
	private Gender(String s) {
		representation = s;
	}

	public Gender opposite() {
		return (this == MALE) ? FEMALE : MALE;
	}

	@Override
	public String toString() {
		return representation;
	}
}