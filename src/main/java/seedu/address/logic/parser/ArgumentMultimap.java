package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Stores mapping of prefixes to their respective arguments.
 * Each key may be associated with multiple argument values.
 * Values for a given key are stored in a list, and the insertion ordering is
 * maintained.
 * Keys are unique, but the list of argument values may contain duplicate
 * argument values, i.e. the same argument value
 * can be inserted multiple times for the same prefix.
 */
public class ArgumentMultimap {

    /** Prefixes mapped to their respective arguments **/
    private final Map<Prefix, List<String>> argMultimap = new HashMap<>();

    /**
     * Associates the specified argument value with {@code prefix} key in this map.
     * If the map previously contained a mapping for the key, the new value is
     * appended to the list of existing values.
     *
     * @param prefix   Prefix key with which the specified argument value is to be
     *                 associated
     * @param argValue Argument value to be associated with the specified prefix key
     */
    public void put(Prefix prefix, String argValue) {
        // Updated to use computeIfAbsent to avoid overwriting the list
        argMultimap.computeIfAbsent(prefix, k -> new ArrayList<>()).add(argValue);
    }

    /**
     * Returns the last value of {@code prefix}.
     */
    public Optional<String> getValue(Prefix prefix) {
        List<String> values = getAllValues(prefix);
        return values.isEmpty() ? Optional.empty() : Optional.of(values.get(values.size() - 1));
    }

    /**
     * Returns all values of {@code prefix}.
     * If the prefix does not exist or has no values, this will return an empty
     * list.
     * Modifying the returned list will not affect the underlying data structure of
     * the ArgumentMultimap.
     */
    public List<String> getAllValues(Prefix prefix) {
        return new ArrayList<>(argMultimap.getOrDefault(prefix, new ArrayList<>()));
    }

    /**
     * Returns the preamble (text before the first valid prefix). Trims any
     * leading/trailing spaces.
     */
    public String getPreamble() {
        return getValue(new Prefix("")).orElse("");
    }

    /**
     * Throws a {@code ParseException} if any of the prefixes given in
     * {@code prefixes} appeared more than
     * once among the arguments.
     */
    public void verifyNoDuplicatePrefixesFor(Prefix... prefixes) throws ParseException {
        Prefix[] duplicatedPrefixes = Stream.of(prefixes)
                .filter(prefix -> argMultimap.getOrDefault(prefix, List.of()).size() > 1)
                .toArray(Prefix[]::new);

        if (duplicatedPrefixes.length > 0) {
            throw new ParseException(Messages.getErrorMessageForDuplicatePrefixes(duplicatedPrefixes));
        }
    }

    /**
     * Throws a {@code ParseException} if any of the prefixes given in
     * {@code prefixes} appeared more than
     * once among the arguments.
     */
    public void verifyOnlyOnePrefix(Prefix... prefixes) throws ParseException {
        Prefix[] presentPrefixes = Stream.of(prefixes)
                .filter(prefix -> !argMultimap.getOrDefault(prefix, List.of()).isEmpty())
                .toArray(Prefix[]::new);

        if (presentPrefixes.length > 1) {
            throw new ParseException(Messages.getErrorMessageForMultiplePrefixes(presentPrefixes));
        }
    }
}
