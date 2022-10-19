package pl.edu.pjwstk.tau.chain_of_responsibility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.edu.pjwstk.tau.chain_of_responsibility.validators.MessageValidatorException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.AssertionsForClassTypes.*;

public class TrueMailSenderTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void can_create_mail_sender() {
        var sender = new TrueMailSender();

        assertThat(sender).isNotNull();
    }

    @Test
    public void can_send_correctly_filled_mail() {
        var sender = new TrueMailSender();

        sender.sendMail("cezary@mail.com", "mail title", "mail message text");

        assertThat(outContent.toString()).isEqualTo("Mail sent correctly!");
    }

    @Test
    public void can_not_send_mail_with_no_receiver_address_case_1() {
        var sender = new TrueMailSender();

        assertThatThrownBy(() -> sender.sendMail(null, "mail title", "mail text"))
                .isInstanceOf(MailSendingException.class)
                .hasCauseInstanceOf(MessageValidatorException.class)
                .hasStackTraceContaining("No receiver mail address provided");
    }

    @Test
    public void can_not_send_mail_with_no_receiver_address_case_2() {
        var sender = new TrueMailSender();

        assertThatThrownBy(() -> sender.sendMail("", "mail title", "mail text"))
                .isInstanceOf(MailSendingException.class)
                .hasCauseInstanceOf(MessageValidatorException.class)
                .hasStackTraceContaining("No receiver mail address provided");
    }

    @Test
    public void can_not_send_mail_with_no_receiver_address_case_3() {
        var sender = new TrueMailSender();

        assertThatThrownBy(() -> sender.sendMail("            ", "mail title", "mail text"))
                .isInstanceOf(MailSendingException.class)
                .hasCauseInstanceOf(MessageValidatorException.class)
                .hasStackTraceContaining("No receiver mail address provided");
    }

    @Test
    public void can_not_send_mail_with_wrong_receiver_address_case_1() {
        var sender = new TrueMailSender();

        assertThatThrownBy(() -> sender.sendMail("cezary", "mail title", "mail text"))
                .isInstanceOf(MailSendingException.class)
                .hasCauseInstanceOf(MessageValidatorException.class)
                .hasStackTraceContaining("Wrong receiver mail address pattern");
    }

    @Test
    public void can_not_send_mail_with_wrong_receiver_address_case_2() {
        var sender = new TrueMailSender();

        assertThatThrownBy(() -> sender.sendMail("cezary.gmail.com", "mail title", "mail text"))
                .isInstanceOf(MailSendingException.class)
                .hasCauseInstanceOf(MessageValidatorException.class)
                .hasStackTraceContaining("Wrong receiver mail address pattern");
    }

    @Test
    public void can_not_send_mail_with_wrong_receiver_address_case_3() {
        var sender = new TrueMailSender();

        assertThatThrownBy(() -> sender.sendMail("cezary@mail", "mail title", "mail text"))
                .isInstanceOf(MailSendingException.class)
                .hasCauseInstanceOf(MessageValidatorException.class)
                .hasStackTraceContaining("Wrong receiver mail address pattern");
    }

    @Test
    public void can_not_send_mail_with_wrong_receiver_address_case_4() {
        var sender = new TrueMailSender();

        assertThatThrownBy(() -> sender.sendMail("cezary@mail.", "mail title", "mail text"))
                .isInstanceOf(MailSendingException.class)
                .hasCauseInstanceOf(MessageValidatorException.class)
                .hasStackTraceContaining("Wrong receiver mail address pattern");
    }

    @Test
    public void can_not_send_mail_with_no_title_case_1() {
        var sender = new TrueMailSender();

        assertThatThrownBy(() -> sender.sendMail("cezary@gmail.com", "", "mail text"))
                .isInstanceOf(MailSendingException.class)
                .hasCauseInstanceOf(MessageValidatorException.class)
                .hasStackTraceContaining("No message title provided");
    }

    @Test
    public void can_not_send_mail_with_no_title_case_2() {
        var sender = new TrueMailSender();

        assertThatThrownBy(() -> sender.sendMail("cezary@gmail.com", null, "mail text"))
                .isInstanceOf(MailSendingException.class)
                .hasCauseInstanceOf(MessageValidatorException.class)
                .hasStackTraceContaining("No message title provided");
    }

    @Test
    public void can_not_send_mail_with_no_title_case_3() {
        var sender = new TrueMailSender();

        assertThatThrownBy(() -> sender.sendMail("cezary@gmail.com", "            ", "mail text"))
                .isInstanceOf(MailSendingException.class)
                .hasCauseInstanceOf(MessageValidatorException.class)
                .hasStackTraceContaining("No message title provided");
    }

    @Test
    public void can_not_send_mail_with_too_long_title() {
        var sender = new TrueMailSender();

        assertThatThrownBy(() -> sender.sendMail("cezary@gmail.com", "abcdefghijklmnopqrstuwvxyzabcdefghijklmnopqrstuwvxyz", "mail text"))
                .isInstanceOf(MailSendingException.class)
                .hasCauseInstanceOf(MessageValidatorException.class)
                .hasStackTraceContaining("Message title is to long");
    }

    @Test
    public void can_not_send_mail_with_no_message_text_case_1() {
        var sender = new TrueMailSender();

        assertThatThrownBy(() -> sender.sendMail("cezary@gmail.com", "message title", ""))
                .isInstanceOf(MailSendingException.class)
                .hasCauseInstanceOf(MessageValidatorException.class)
                .hasStackTraceContaining("No text content provided");
    }

    @Test
    public void can_not_send_mail_with_no_message_text_case_2() {
        var sender = new TrueMailSender();

        assertThatThrownBy(() -> sender.sendMail("cezary@gmail.com", "message title", null))
                .isInstanceOf(MailSendingException.class)
                .hasCauseInstanceOf(MessageValidatorException.class)
                .hasStackTraceContaining("No text content provided");
    }

    @Test
    public void can_not_send_mail_with_no_message_text_case_3() {
        var sender = new TrueMailSender();

        assertThatThrownBy(() -> sender.sendMail("cezary@gmail.com", "message title", "           "))
                .isInstanceOf(MailSendingException.class)
                .hasCauseInstanceOf(MessageValidatorException.class)
                .hasStackTraceContaining("No text content provided");
    }
}