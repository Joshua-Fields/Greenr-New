<?php
/*
 * Description: PHP file for Contact Us page
 * Author: Rebecka Evans
 * Project: Greenr
 */

    // Variables for name, email, and inquiry taken from the HTML form.
    $name = $_POST['name'];
    $user_email = $_POST['email'];
    $inquiry = $_POST['inquiry'];

    // Variable containing the email address sending the message.
    $email_from = 'contact.rebecka@icloud.com';

    // Creating the structure of the message text with provided variables.
    $email_subject = 'New Greenr Inquiry';
    $email_body = "Name: $name.'\n".
        "Email: $user_email.\n".
        "Inquiry: $inquiry.\n";

    // Variable containing the receiving email.
    $to = "contact.rebecka@gmail.com";

    // Declaring headers for the sending email, and the email where the reply is directed.
    $headers = "From: $email_from \r\n";
    $headers .= "Reply to: $user_email \r\n";

    // Initializing the mail with our variables.
    mail($to,$email_subject,$email_body,$headers);

    header("Location: contact-us.html");
?>