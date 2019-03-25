package celestek.hexcraft.utility;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Contains various optional filters used mostly for the fullbright model
 */
public final class HexFilters
{
	public static final Optional<Predicate<String>>
	ALWAYS_TRUE = Optional.of(string -> true),
	CONTAINS_GLOW = filterContains("glow");

	private HexFilters() {}

	/**
	 * Returns an optional filter which checks if a string contains the given pattern
	 */
	public static Optional<Predicate<String>> filterContains(String pattern)
	{
		return Optional.of(string -> string.contains(pattern));
	}
}