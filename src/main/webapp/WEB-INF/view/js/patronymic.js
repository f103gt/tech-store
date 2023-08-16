const validatePatronymic = patronymic => {
    const patronymicValue = patronymic.value.trim();

    if (patronymicValue === '') {
        setError(patronymic, 'Patronymic is required');
    } else if (!fullNameRegex.test(patronymicValue)) {
        setError(patronymic, 'Patronymic must start with a capital letter, contain only alphabetical characters, and be between 2 and 50 characters');
    } else {
        setSuccess(patronymic);
    }
};
