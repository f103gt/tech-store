const form = document.getElementById('form');
const firstName = document.getElementById('first-name');
const lastName = document.getElementById('last-name');
const email = document.getElementById('email');
const phone = document.getElementById('phone');
const password = document.getElementById('password');
const passwordConfirmation = document.getElementById('password-confirmation');

const firstNameRegex = /^[A-Z][A-Za-z]{1,49}$/;
const lastNameRegex = /^[A-Z][A-Za-z]{1,49}$/;

const formatPhoneNumber = value => {
    if (!value) return value;
    const phoneNumber = value.replace(/[^\d]/g, '');
    const phoneNumberLength = phoneNumber.length;
    if (phoneNumberLength < 4) return phoneNumber;
    if (phoneNumberLength < 7) {
      return `${phoneNumber.slice(0, 3)} ${phoneNumber.slice(3)}`;
    }
    return `${phoneNumber.slice(0, 3)} ${phoneNumber.slice(3, 6)} ${phoneNumber.slice(6, 10)}`;
  };

form.setAttribute('novalidate', '');

form.addEventListener('submit', e => {
    e.preventDefault();
    validateInputs();
});

const setError = (element, message) => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.error');

    errorDisplay.innerText = message;
    inputControl.classList.add('error');
    inputControl.classList.remove('success');
};

const setSuccess = element => {
    const inputControl = element.parentElement;
    const errorDisplay = inputControl.querySelector('.error');

    errorDisplay.innerText = '';
    inputControl.classList.add('success');
    inputControl.classList.remove('error');
};

const isValidEmail = email => {
    const regex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return regex.test(String(email).toLowerCase());
};

const validateInputs = () => {
    const firstNameValue = firstName.value.trim();
    const lastNameValue = lastName.value.trim();
    const emailValue = email.value.trim();
    const phoneValue = phone.value.trim();
    const passwordValue = password.value.trim();
    const passwordConfirmationValue = passwordConfirmation.value.trim();

    if (firstNameValue === '') {
        setError(firstName, 'First name is required');
    } else if (!firstNameRegex.test(firstNameValue)) {
        setError(firstName,
            'First name must start with a capital letter, contain only alphabetical ' +
            'characters, be between 2 and 50 characters');
    } else {
        setSuccess(firstName);
    }

    if (lastNameValue === '') {
        setError(lastName, 'Last name is required');
    } else if (!firstNameRegex.test(firstNameValue)) {
        setError(firstName,
            'Last name must start with a capital letter, contain only alphabetical ' +
            'characters, be between 2 and 50 characters');
    } else {
        setSuccess(lastName);
    }

    if (emailValue === '') {
        setError(email, 'E-mail is required');
    } else if (!isValidEmail(emailValue)) {
        setError(email, 'Provide a valid e-mail address');
    } else {
        setSuccess(email);
    }

    if (phoneValue === '') {
        setError(phone, 'Phone is required');
    }else {
        const formattedPhone = formatPhoneNumber(phoneValue);
        phone.value = formattedPhone;
        setSuccess(phone);
    }

    if (passwordValue === '') {
        setError(password, 'Password is required');
    } else if (passwordValue.length < 8) {
        setError(password, 'Password must contain at least 8 characters.');
    } else if (passwordValue.length > 20) {
        setError(password, 'Password must not contain more than 20 characters.');
    } else if (!/[A-Z]/.test(passwordValue)) {
        setError(password, 'Password must contain at least one uppercase letter.');
    } else if (!/[a-z]/.test(passwordValue)) {
        setError(password, 'Password must contain at least one lowercase letter.');
    } else if (!/\d/.test(passwordValue)) {
        setError(password, 'Password must contain at least one digit.');
    } else if (!/[!@#$%^&*_=+-]/.test(passwordValue)) {
        setError(password, 'Password must contain at least one special character.');
    } else {
        setSuccess(password);
    }

    if (passwordConfirmationValue === '') {
        setError(passwordConfirmation, 'Please confirm your password');
    } else if (passwordConfirmationValue !== passwordValue) {
        setError(passwordConfirmation, "Passwords do not match");
    } else {
        setSuccess(passwordConfirmation);
    }
};