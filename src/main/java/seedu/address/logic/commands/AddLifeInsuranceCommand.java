package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BENEFICIARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTRACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIGNING_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.insurance.LifeInsurance;
import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author OscarWang114
/**
 * Creates an insurance relationship in the address book.
 */
public class AddLifeInsuranceCommand extends UndoableCommand {
    public static final String[] COMMAND_WORDS = {"addli", "ali", "+"};
    public static final String COMMAND_WORD = "addli";

    public static final String MESSAGE_USAGE = concatenateCommandWords(COMMAND_WORDS)
            + ": Creates an insurance relationship. "
            + "Parameters: "
            + PREFIX_OWNER + "OWNER "
            + PREFIX_INSURED + "INSURED "
            + PREFIX_BENEFICIARY + "BENEFICIARY "
            + PREFIX_CONTRACT + "CONTRACT"
            + PREFIX_PREMIUM + "PREMIUM "
            + PREFIX_SIGNING_DATE + "SIGNING_DATE "
            + PREFIX_EXPIRY_DATE + "EXPIRY_DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_OWNER + "OWNER "
            + PREFIX_INSURED + "INSURED "
            + PREFIX_BENEFICIARY + "BENEFICIARY "
            + PREFIX_CONTRACT + "CONTRACT"
            + PREFIX_PREMIUM + "PREMIUM "
            + PREFIX_SIGNING_DATE + "SIGNING_DATE "
            + PREFIX_EXPIRY_DATE + "EXPIRY_DATE";

    public static final String MESSAGE_SUCCESS = "New insurance added: %1$s";
    public static final String MESSAGE_DUPLICATE_INSURANCE = "This insurance already exists in the address book";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    //private final UUID id;
    private final LifeInsurance lifeInsuranceToAdd;

    /**
     * Creates an AddLifeInsuranceCommand to add the specified {@code ReadOnlyInsurance}
     */
    public AddLifeInsuranceCommand(String ownerName, String insuredName, String beneficiaryName,
                                   Double premium, String contractPath, LocalDate signingDate,
                                   LocalDate expiryDate) {
        //this.id = UUID.randomUUID();
        this.lifeInsuranceToAdd = new LifeInsurance(ownerName, insuredName, beneficiaryName,
                premium, contractPath, signingDate, expiryDate);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<ReadOnlyPerson> personList = model.getFilteredPersonList();
        List<ReadOnlyInsurance> insuranceList = model.getInsuranceList();
        isAnyPersonInList(personList, lifeInsuranceToAdd);
        model.addInsurance(lifeInsuranceToAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, lifeInsuranceToAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddLifeInsuranceCommand); // instanceof handles nulls
                //&& toAdd.equals(((AddLifeInsuranceCommand) other).toAdd));
        //TODO: need to compare every nonstatic class member.
    }

    /**
     * Check if all the Person parameters required to create an insurance are inside the list
     */
    public void isAnyPersonInList(List<ReadOnlyPerson> list, LifeInsurance lifeInsurance) {
        String ownerName = lifeInsurance.getOwner().getName();
        String insuredName = lifeInsurance.getInsured().getName();
        String beneficiaryName = lifeInsurance.getBeneficiary().getName();
        for (ReadOnlyPerson person: list) {
            String lowerCaseName = person.getName().toString().toLowerCase();
            if (lowerCaseName.equals(ownerName)) {
                lifeInsurance.getOwner().setPerson(person);
            }
            if (lowerCaseName.equals(insuredName)) {
                lifeInsurance.getInsured().setPerson(person);
            }
            if (lowerCaseName.equals(beneficiaryName)) {
                lifeInsurance.getBeneficiary().setPerson(person);
            }
        }
    }
}
