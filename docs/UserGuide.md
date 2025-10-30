---
layout: page
title: User Guide
---

VolunteeRoll is a **desktop application designed to help volunteer coordinators, tutors, and student program managers efficiently manage student and volunteer information.**
It combines the speed of a **Command Line Interface (CLI) with the clarity of a modern Graphical User Interface (GUI)**, making it ideal for users who prefer typing commands to navigate and manage data quickly.

### VolunteeRoll allows users to:
1. Store and organize student and volunteer contact information
2. Match students with suitable volunteers based on shared tags or subjects
3. Maintain a synchronized view of all pairings and unpairings
4. Edit and update data instantly, with automatic saving

### Why use VolunteeRoll?
If you’re managing tutoring programs, community service groups, or any volunteer-based initiative, VolunteeRoll helps you stay organized - without needing spreadsheets or complex databases.

### Who is this guide for?
This guide is intended for:
1. Volunteer coordinators or program leads who manage student–volunteer matching.
2. Users who have basic familiarity with computers and can follow simple command-line instructions.
3. Those who prefer fast, text-based interactions over traditional GUI-heavy tools.

* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

Follow these steps to get VolunteeRoll running in minutes.
1. **Ensure you have Java `17` or above installed in your Computer.**<br>
    * **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. **Download the latest release**
    * Download the latest `.jar` file from [VolunteeRoll releases page](https://github.com/AY2526S1-CS2103T-F10-1/tp/releases).

3. **Set up your workspace**
    * Copy the `.jar` file into your preferred folder (this will be your app’s “home” directory).
4. **Run the application**
    * Open your terminal or command prompt.
    * Navigate (`cd`) into the folder containing the .jar file.
    * Enter the following command: `java -jar addressbook.jar`

5. **Explore the interface** <br>
   Once launched, the app window appears with sample data loaded. You will see:
    * **Tool bar (top)**: The file button lets you exit the application and help button takes you to the help page (you can access this via the `help` command as well)
    * **Command Box (top)**: Where you type commands
    * **Result Display (centre)**: Shows feedback and messages
    * **List panel (centre-bottom)**: Displays all students(left) and Volunteers(right)

![Ui](images/Ui.png)

6. **Try a few commands** <br>
   Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window. <br>
   Some example commands you can try:

    * `list` : Lists all contacts.

    * `addstu n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` :
      Adds a **student** named `John Doe`.

    * `addvol n/Jane Roe p/91234567 e/janeroe@example.com a/321, River Rd, #02-02` :
      Adds a **volunteer** named `Jane Roe`.

    * `delete 3` : Deletes the 3rd contact shown in the current list.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app.

7. **Concepts you should know**
   * **Two types**: Every person is either a Student or a Volunteer. The list panel shows them side-by-side; each card displays its type.
   * **Prefixes**: Commands use prefixes to mark fields:
   n/ name, p/ phone, e/ email, a/ address, t/ tag (repeatable).
   Example: addvol n/Ana p/91234567 e/ana@ex.com a/123 Road t/physics t/weekend
   * **One line only**: Commands must be a single line (no newlines). Pasted multi-line input will be rejected.

8. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.


| Field       | What’s allowed                                                                                                                                                                                       | Examples                                     |
| ----------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------- |
| `n/NAME`    | Letters (any language), numbers, spaces, apostrophes (`’` or `'`), hyphens (`-`), periods (`.`), slashes (`/`), commas (`,`), parentheses. Avoid literal `n/ p/ e/ a/ t/` sequences inside the name. | `O’Malley`, `Arun s / o Kumar`, `Anne-Marie` |
| `p/PHONE`   | At least 3 digits. Optional leading `+`. Spaces and dashes allowed (ignored for matching).                                                                                                           | `+65 9123-4567`, `9312 1534`, `911`          |
| `e/EMAIL`   | Standard email formats.                                                                                                                                                                              | `name@example.com`                           |
| `a/ADDRESS` | Free text.                                                                                                                                                                                           | `123, Clementi Rd, #02-01`                   |
| `t/TAG`     | Single word per tag; repeat `t/` for multiple tags. Case-insensitive matching.                                                                                                                       | `t/math t/weekday`                           |

</div>

<div markdown="block" class="alert alert-info">

**:information_source: How VolunteeRoll detects duplicate entries (What counts as the same Person?):**<br>

VolunteeRoll prevents accidental duplicates using the following rules:

1. Name normalization
   * Case-insensitive comparison.
   * Unicode normalized (e.g., accented forms are compared fairly).
   * Collapses consecutive spaces and removes zero-width characters.
   * Treats s/o and s / o as equivalent (we normalize separators like this).
   * Example: O’Malley, O'Malley, and o’malley compare equal for duplicate checks.
2. Phone normalization
   * Ignores spaces, dashes, and a leading +.
   * Example: +65 9123-4567 ≡ 6591234567 ≡ 91234567 (when country code matches).
3. Duplicate decision
   * If normalized names match, and both phone AND email are identical (after normalization) or both left as defaults/blank, the person is considered a duplicate and will be rejected.
   * Practical effect: same person with same contact info ⇒ duplicate; same name but different phone/email - allowed.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
If you legitimately manage two people with the same normalized name, make sure their phone or email differs.
</div>

</div>

### Viewing help : `help`

Opens a scrollable popup window containing the list of available commands and shortcuts.<br>
This helps users quickly navigate the app without leaving the main interface.

**Tip:** You can resize or scroll through the Help window to view all commands.


Format: `help`


### Adding a student: `addstu`

Adds a **student** to the address book.

Format: `addstu n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
1. A student can have any number of tags (including 0). <br>
2. Phone may include spaces, dashes, or a leading + (e.g., +65 9123-4567).
</div>

**Examples:**
* `addstu n/Betsy Crowe t/friend a/Newgate Prison p/1234567 t/criminal`
* Suggested input : `addstu n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* Expected output:

![addstu](images/addstuJohnDoe.png)

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
1. **Prefixes split fields**. Any literal n/, p/, e/, a/, or t/ inside your name will be treated as the start of a new field. <br>
2. Single line inputs only. Multi-line pastes are not accepted (the newlines will automatically be ignored).
</div>



### Adding a volunteer: `addvol`

Adds a **volunteer** to the address book.

Format: `addvol n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
1. A Volunteer can have any number of tags (including 0).<br>
2. Phone may include spaces, dashes, or a leading + (e.g., +65 9123-4567).
</div>

**Examples:**
* `addvol n/Jane Roe p/91234567 e/janeroe@example.com a/321, River Rd, #02-02`
* Suggested input : `addvol n/Alex Yeoh t/mentor e/alex@example.com a/Somewhere`
* Expected output :

![addvol](images/addvolAlexYeoh.png)

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
1. **Prefixes split fields**. Any literal n/, p/, e/, a/, or t/ inside your name will be treated as the start of a new field.<br>
2. Single line inputs only. Multi-line pastes are not accepted (the newlines will automatically be ignored).
</div>

### Listing all persons : `list`

1. list shows all students (left) and volunteers (right).
2. Indices shown on each card follow the global creation order (not the left/right position). Use these indices for commands like edit, delete, pair, and unpair.

Format: `list`

![list](images/listCommand.png)

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

![edit](images/editBetsyCrower.png)

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>

  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Locating persons by tag: `findtag`

Finds persons who are tagged with the given tag.

This can help you find volunteers who are equipped to teach a particular student with the subjects they struggle with, or vice versa.

Format: `findtag TAG [MORE_TAGS]`

* The search is case-insensitive. e.g `math` will match `Math` and `MATH`
* Only tags are searched.
* Only persons whose tags include **all** the tags searched for will be returned. For example, if Jane Doe is tagged with `math` and not `science`, then she will not appear on the output list after running `findtag math science`.

Examples:

* `findtag math`
* `findtag math science`

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Pairing a student to volunteers, or a volunteer to students : `pair`

Pairs the specified person to other existing persons in the address book.

Format: `pair INDEX 1ST_PARTNER_INDEX 2ND_PARTNER_INDEX ... LAST_PARTNER_INDEX`

* Pairs the person at the specified `INDEX` to the persons specified in `1ST_PARTNER_INDEX 2ND_PARTNER_INDEX ... LAST_PARTNER_INDEX`.
* The indices refer to the respective index number shown in the displayed person list.

Examples:
* `list` followed by `pair 2 1 3` pairs the 2nd person in the address book to the 1st and 3rd persons.

### Unpairing a student from volunteers, or a volunteer from students : `unpair`

Unpairs the specified person to other existing persons in the address book.

Format: `unpair INDEX 1ST_PARTNER_INDEX 2ND_PARTNER_INDEX ... LAST_PARTNER_INDEX`

* Unpairs the person at the specified `INDEX` to the persons specified in `1ST_PARTNER_INDEX 2ND_PARTNER_INDEX ... LAST_PARTNER_INDEX`.
* The indices refer to the respective index number shown in the displayed person list.

Examples:
* `list` followed by `unpair 2 1 3` unpairs the 2nd person in the address book to the 1st and 3rd persons.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

**Q**: How can I check my Java version?<br>
**A**: Run `java -version` in your terminal.

**Q**: What’s the difference between `addstu` and `addvol`?<br>
**A**: Only the **type** created. `addstu` creates a **Student**; `addvol` creates a **Volunteer**. All other fields and prefixes work the same.

**Q**: My name contains `s/o`, `O’`, or `Anne-Marie`. Is that allowed?<br>
**A**: Yes. Names allow letters (any language), numbers, spaces, apostrophes, hyphens, periods, slashes, commas, and parentheses.

**Q**: Why did my name get “cut off” when I typed something like `n/John a/Smith`?<br>
**A**: Prefixes (`n/`, `p/`, `e/`, `a/`, `t/`) **start new fields**. If a name must include a prefix-like bit, insert a space (`a /` instead of `a/`) or write it in parentheses.

**Q**: My phone number has `+65`, spaces, or dashes. Is it valid?<br>
**A**: Yes. Phone numbers may start with `+` and include spaces or `-`, as long as they contain **≥ 3 digits** total.

**Q**: What do indices refer to in the UI with two lists?<br>
**A**: The index shown on each card is the **global index** in the master list (shared by both panes). Use that number for commands like `edit`, `delete`, `pair`, and `unpair`.

**Q**: Why didn’t my new person appear at the bottom of the list immediately?<br>
**A**: The list auto-refreshes on changes. If you don’t see it (rare on some platforms), run `list` to refresh the view.

**Q**: How do I find by tag vs by name?<br>
**A**: Use `find` for names (`find alex david`) and `findtag` for tags (`findtag math weekend`). `findtag` returns only persons containing **all** searched tags.

**Q**: Can I add multiple tags?<br>
**A**: Yes. Repeat the prefix: `t/math t/sec3 t/weekend`.

**Q**: Where is my data saved?<br>
**A**: Automatically to `[JAR location]/data/addressbook.json` after every change. No manual save needed.

**Q**: Can I edit the JSON file directly?<br>
**A**: Yes (advanced). Back it up first. If the format becomes invalid, the app will reset to an empty file on next run.

**Q**: The app says “Invalid command format.” How do I see correct usage?<br>
**A**: Run `help` or type the command name alone (e.g., `addstu`) to see the usage message in the result panel.

**Q**: Do tags care about letter case?<br>
**A**: Tag matching is **case-insensitive**. `math`, `Math`, and `MATH` are treated the same.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.
3. **Name or details of Person are too long** , the app only shows the details until a number of characters and the rest will be displayed by "...". If you wish to view the full name, you can edit the person with an extra tag or any details you wish and copy their full details from the result display box.
--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add student** | `addstu n/NAME [p/PHONE NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…` <br> e.g., `addstu n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Add volunteer** | `addvol n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…` <br> e.g., `addvol n/Jane Roe p/93334444 e/jane@example.com a/45, River Valley Rd, 238000 t/mentor`
**Clear**     | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Find by tag** | `findtag TAG [MORE_TAGS]`<br> e.g., `findtag math science`
**Pair** | `pair INDEX 1ST_PARTNER_INDEX 2ND_PARTNER_INDEX ... LAST_PARTNER_INDEX`<br> e.g., `pair 2 1 3`
**Unpair** | `unpair INDEX 1ST_PARTNER_INDEX 2ND_PARTNER_INDEX ... LAST_PARTNER_INDEX`<br> e.g., `unpair 2 1 3`
**Exit** | `exit`
**List** | `list`
**Help** | `help`
