public enum Gender {
	MALE("M"),
	FEMALE("F");

	private String representation;
	private Gender(String s) {
		representation = s;
	}

	@Override
	public String toString() {
		return representation;
	}
}