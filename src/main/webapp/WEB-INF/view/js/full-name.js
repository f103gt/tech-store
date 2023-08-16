const fullNameRegex = /^[A-Z][a-z]{1,49}$/;

const validateFullName = (firstName, lastName) => {
    const firstNameValue = firstName.value.trim();
    const lastNameValue = lastName.value.trim();

    if (firstNameValue === '') {
        setError(firstName, 'First name is required');
    } else if (!fullNameRegex.test(firstNameValue)) {
        setError(firstName, 'First name must start with a capital letter, contain only alphabetical characters, and be between 2 and 50 characters');
    } else {
        setSuccess(firstName);
    }

    if (lastNameValue === '') {
        setError(lastName, 'Last name is required');
    } else if (!fullNameRegex.test(lastNameValue)) {
        setError(lastName, 'Last name must start with a capital letter, contain only alphabetical characters, and be between 2 and 50 characters');
    } else {
        setSuccess(lastName);
    }
};
