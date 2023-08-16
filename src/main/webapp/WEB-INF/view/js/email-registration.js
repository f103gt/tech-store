const validateEmail = email => {
    const emailValue = email.value.trim();

    if (emailValue === '') {
        setError(email, 'E-mail is required');
    } else if (!isValidEmail(emailValue)) {
        setError(email, 'Enter a valid e-mail address that belongs to "gmail.com" e-mail service provider.');
    } else {
        setSuccess(email);
    }
};

const isValidEmail = email => {
    const regex = /^[a-z0-9]+(?:[.+\\-][a-z0-9]+)*@gmail\.com$/;
    return regex.test(String(email).toLowerCase());
};
