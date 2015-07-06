package com.sbplatform.type;

public enum SexType {

	MALE(0, "男"),
	FEMALE(1, "女");

	private int enumValue;
	private String enumName;

	SexType(int enumValue, String enumName) {
		this.enumValue = enumValue;
		this.enumName = enumName;
	}

	public static SexType valueOf(int ev) {
		SexType enumType = null;
		switch (ev) {
			case 0:
				enumType = SexType.MALE;
				break;
			case 1:
				enumType = SexType.FEMALE;
				break;
			default:
				break;
		}
		return enumType;
	}

	public String getEnumName() {
		return this.enumName;
	}

	public int getEnumValue() {
		return this.enumValue;
	}

	@Override
	public String toString() {
		return getEnumName();
	}
}
