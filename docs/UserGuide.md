---
layout: page
title: User Guide
---

VolunteeRoll is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-F10-1/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. E.g., typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `addstu n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` :
     Adds a **student** named `John Doe`.

   * `addvol n/Jane Roe p/91234567 e/janeroe@example.com a/321, River Rd, #02-02` :
     Adds a **volunteer** named `Jane Roe`.
   
   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

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
</div>

### Viewing help : `help`

Opens a scrollable popup window containing the list of available commands and shortcuts.  
This helps users quickly navigate the app without leaving the main interface.

**Tip:** You can resize or scroll through the Help window to view all commands.


Format: `help`


### Adding a student: `addstu`

Adds a **student** to the address book.

Format: `addstu n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**  
A student can have any number of tags (including 0).
</div>

**Examples:**
* `addstu n/Betsy Crowe t/friend a/Newgate Prison p/1234567 t/criminal`
* `addstu n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`

![addstu](images/addstuJohnDoe.png)


### Adding a volunteer: `addvol`

Adds a **volunteer** to the address book.

Format: `addvol n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**  
A volunteer can have any number of tags (including 0).
</div>

**Examples:**
* `addvol n/Jane Roe p/91234567 e/janeroe@example.com a/321, River Rd, #02-02`
* `addvol n/Alex Yeoh t/mentor e/alex@example.com a/Somewhere`

![addvol](images/addvolAlexYeoh.png)

### Listing all persons : `list`

Shows a list of all persons in the address book.

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

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

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
