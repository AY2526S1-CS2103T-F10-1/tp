package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");
    private static final Path SELF_PAIRINGS_PERSON_FILE = TEST_DATA_FOLDER.resolve("selfPairingAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

    /**
     * Tests that the self-pairing in the sample address book is ignored without throwing any errors.
     */
    @Test
    public void toModelType_selfPairing_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(SELF_PAIRINGS_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook selfPairingAddressBook = TypicalPersons.getSelfPairingAddressBook();
        assertEquals(addressBookFromFile, selfPairingAddressBook);
    }

    @Test
    public void toModelType_pairingsRoundTrip_success() throws Exception {
        Person alice = new PersonBuilder().withName("Alice Pauline").withPhone("94351253")
            .withEmail("alice@example.com").withAddress("123, Jurong West Ave 6, #08-111")
            .withTags("friends").build();
        Person benson = new PersonBuilder().withName("Benson Meier").withPhone("98765432")
            .withEmail("johnd@example.com").withAddress("311, Clementi Ave 2, #02-25")
            .withTags("owesMoney", "friends").build();
        Person carl = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();

        alice.addPerson(benson);

        AddressBook source = new AddressBook();
        source.addPerson(alice);
        source.addPerson(benson);
        source.addPerson(carl);

        JsonSerializableAddressBook jsonAddressBook = new JsonSerializableAddressBook(source);
        AddressBook rebuilt = jsonAddressBook.toModelType();

        Person rebuiltAlice = rebuilt.getPersonList().stream()
            .filter(p -> p.isSamePerson(alice))
            .findFirst()
            .orElseThrow();
        Person rebuiltBenson = rebuilt.getPersonList().stream()
            .filter(p -> p.isSamePerson(benson))
            .findFirst()
            .orElseThrow();

        assertEquals(1, rebuiltAlice.getPairings().size());
        assertTrue(rebuiltAlice.getPairings().get(0).isSamePerson(benson));
        assertEquals(1, rebuiltBenson.getPairings().size());
        assertTrue(rebuiltBenson.getPairings().get(0).isSamePerson(alice));
    }

}
