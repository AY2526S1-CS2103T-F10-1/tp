package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 */
public class UniquePersonList implements Iterable<Person> {

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    private final ObservableList<Person> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    private final BiMap<Person, Integer> ids = HashBiMap.create();
    private int nextId = 0;

    private final SetMultimap<Integer, Integer> links = HashMultimap.create();

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a person to the list.
     */
    public void add(Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
        ids.put(toAdd, nextId++);
    }

    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }
        if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        // migrate id FIRST, so listeners triggered by internalList.set(...) can resolve editedPerson
        Integer id = ids.remove(target);
        if (id == null) {
            throw new PersonNotFoundException();
        }
        ids.put(editedPerson, id);

        internalList.set(index, editedPerson);
    }



    /**
     * Removes the equivalent person from the list.
     */
    public void remove(Person toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
        Integer id = ids.remove(toRemove);
        if (id != null) {
            unpairAllById(id);
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        ids.clear();
        ids.putAll(replacement.ids);
        nextId = replacement.nextId;
        links.clear();
        links.putAll(replacement.links);
    }

    public void setPersons(List<Person> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }
        internalList.setAll(persons);
        ids.clear();
        nextId = 0;
        for (Person p : internalList) {
            ids.put(p, nextId++);
        }
        links.clear();
    }

    public ObservableList<Person> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UniquePersonList)) {
            return false;
        }
        UniquePersonList o = (UniquePersonList) other;
        return internalList.equals(o.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    private Integer getId(Person p) {
        requireNonNull(p);
        Integer id = ids.get(p);
        if (id == null) {
            throw new PersonNotFoundException();
        }
        return id;
    }

    private Person getPersonById(int id) {
        Person p = ids.inverse().get(id);
        if (p == null) {
            throw new PersonNotFoundException();
        }
        return p;
    }

    /**
     * Pairs two persons.
     */
    public void pair(Person a, Person b) {
        requireAllNonNull(a, b);
        pairById(getId(a), getId(b));
    }

    private void pairById(int a, int b) {
        if (a == b) {
            throw new IllegalArgumentException("cannot pair with self");
        }
        if (!ids.inverse().containsKey(a) || !ids.inverse().containsKey(b)) {
            throw new NoSuchElementException("id not found");
        }
        links.put(a, b);
        links.put(b, a);
    }

    /**
     * Unpairs two persons.
     */
    public void unpair(Person a, Person b) {
        requireAllNonNull(a, b);
        unpairById(getId(a), getId(b));
    }

    private void unpairById(int a, int b) {
        links.remove(a, b);
        links.remove(b, a);
    }

    /**
     * Unpairs all persons in the list.
     */
    public void unpairAll(Person p) {
        requireNonNull(p);
        unpairAllById(getId(p));
    }

    private void unpairAllById(int id) {
        Set<Integer> partners = Set.copyOf(links.get(id));
        for (Integer other : partners) {
            links.remove(other, id);
        }
        links.removeAll(id);
    }

    /**
     * Returns true if the two persons are paired.
     */
    public boolean isPaired(Person a, Person b) {
        requireAllNonNull(a, b);
        return isPairedById(getId(a), getId(b));
    }

    private boolean isPairedById(int a, int b) {
        return links.get(a).contains(b);
    }

    private Set<Integer> getPairedIds(int id) {
        return Collections.unmodifiableSet(new LinkedHashSet<>(links.get(id)));
    }

    /**
     * Returns the set of paired persons for a given person.
     */
    public Set<Person> getPairedPersons(Person p) {
        requireNonNull(p);
        int id = getId(p);
        return getPairedIds(id).stream()
                .map(this::getPersonById)
                .collect(Collectors.toUnmodifiableSet());
    }

    private boolean personsAreUnique(List<Person> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSamePerson(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
