const contactInformation = document.getElementById('form');

contactInformation.setAttribute('novalidate', '');
contactInformation.addEventListener('submit', e => {
    e.preventDefault();
    validateInputs();
});

const setError = (element, message) => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.error');

    if (errorDisplay) {
        errorDisplay.innerText = message;
    }
    inputControl.classList.add('error');
    inputControl.classList.remove('success');
};

const setSuccess = element => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.error');

    if (errorDisplay) {
        errorDisplay.innerText = '';
    }

    inputControl.classList.add('success');
    inputControl.classList.remove('error');
};

const validateInputs = () => {
    const firstName = document.getElementById('first-name');
    const lastName = document.getElementById('last-name');
    const patronymic = document.getElementById('patronymic');
    const email = document.getElementById('email');
    const phone = document.getElementById('phone');

    validateFullName(firstName, lastName);
    validatePatronymic(patronymic);
    validateEmail(email);
    validatePhone(phone);

    submitFormIfValid(5);
};

const submitFormIfValid = numValidations => {
    const validInputs = contactInformation.querySelectorAll('.input-control.success');
    if (validInputs.length === numValidations) {
        contactInformation.removeEventListener('submit', validateInputs);
        contactInformation.submit();
    }
};
