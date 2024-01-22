package enums;
/**
 * A class that matches enum objects with their corresponding values in the data files.
 * @author Andrew Cheam
 * @version 1.0
 * @since 2023-04-11
 */
public class EnumMatcher {
	/** Creates a new EnumMatcher. */
	public EnumMatcher() {}
	/**
	 * Matches an enum object with its corresponding value in a data file.
	 * @param <T> Generic data type.
	 * @param enumClass The class of the enum object.
	 * @param enumStr The corresponding value of the object in the data file.
	 * @return The value of the enum object.
	 * @throws IllegalArgumentException if no matching enum constant is found.
	 */
    public static <T extends Enum<T>> T matchEnum(Class<T> enumClass, String enumStr) {
        for (T enumValue : enumClass.getEnumConstants()) {
            if (enumValue.name().equalsIgnoreCase(enumStr)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("No matching enum constant found for " + enumStr);
    }
}

