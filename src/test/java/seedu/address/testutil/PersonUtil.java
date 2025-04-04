package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Person;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        return String.format("%s %s %s %s",
                PREFIX_NAME,
                person.getName().fullName,
                PREFIX_ID,
                person.getId().id);
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getNewId().ifPresent(id -> sb.append(PREFIX_ID).append(" ").append(id.id)
                .append(" "));
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(" ").append(name.fullName)
                .append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(" ").append(phone.value)
                .append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(" ").append(email.value)
                .append(" "));
        descriptor.getCourse().ifPresent(course -> sb.append(PREFIX_COURSE).append(" ")
                .append(course.course).append(" "));
        descriptor.getAttendance().ifPresent(attendance -> sb.append(PREFIX_ATTENDANCE).append(" ")
                .append(attendance.status).append(" "));
        descriptor.getParticipation().ifPresent(participation -> sb.append(PREFIX_PARTICIPATION)
                .append(" ").append(participation.status).append(" "));
        descriptor.getGrade().ifPresent(grade -> {
            String gradeValue = (grade.grade == -1) ? "NA" : String.valueOf(grade.grade);
            sb.append(String.format("%s %s ", PREFIX_GRADE, gradeValue));
        });
        return sb.toString();
    }
}
