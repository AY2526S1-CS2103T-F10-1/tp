package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        Map<String, Person> identityMap = new HashMap<>();
        List<PendingPairing> pendingPairings = new ArrayList<>();

        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);

            String identityKey = toLookupKey(person);
            identityMap.put(identityKey, person);
            pendingPairings.add(new PendingPairing(person, jsonAdaptedPerson.getPairingIdentities()));
        }

        linkPairings(pendingPairings, identityMap);
        return addressBook;
    }

    private void linkPairings(List<PendingPairing> pendingPairings, Map<String, Person> identityMap)
            throws IllegalValueException {
        Set<String> linkedEdges = new HashSet<>();

        for (PendingPairing pending : pendingPairings) {
            Person owner = pending.owner;
            for (JsonAdaptedPerson.PersonIdentity targetIdentity : pending.pairingIdentities) {
                Person target = identityMap.get(targetIdentity.toLookupKey());

                // The case of (target == owner) is already addressed by Person.addPerson() and the catch clause below
                if (target == null) {
                    continue;
                }

                String edgeKey = toEdgeKey(owner, target);
                if (!linkedEdges.add(edgeKey)) {
                    continue; // already linked in opposite direction
                }

                try {
                    owner.addPerson(target);
                } catch (IllegalValueException ive) {
                    // duplicate pairings in savefile, silently ignore
                }
            }
        }
    }

    private String toLookupKey(Person person) {
        return person.getName().fullName + "|" + person.getPhone().value;
    }

    private String toEdgeKey(Person first, Person second) {
        String firstKey = toLookupKey(first);
        String secondKey = toLookupKey(second);
        return firstKey.compareTo(secondKey) <= 0
                ? firstKey + "->" + secondKey
                : secondKey + "->" + firstKey;
    }

    private static class PendingPairing {
        private final Person owner;
        private final List<JsonAdaptedPerson.PersonIdentity> pairingIdentities;

        private PendingPairing(Person owner, List<JsonAdaptedPerson.PersonIdentity> pairingIdentities) {
            this.owner = owner;
            this.pairingIdentities = pairingIdentities;
        }
    }

}
