const form = document.getElementById('form');
const email = document.getElementById('email');
const password = document.getElementById('password');

form.setAttribute('novalidate', '');
form.addEventListener('submit', e => {
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

const isValidEmail = email => {
    const regex = /^[a-z0-9]+(?:[.+\\-][a-z0-9]+)*@gmail\.com$/;
    return regex.test(String(email).toLowerCase());
};

const validateInputs = () => {
    const emailValue = email.value.trim();
    const passwordValue = password.value.trim();

    if (emailValue === '') {
        setError(email, 'E-mail is required');
    } else if (!isValidEmail(emailValue)) {
        setError(email, 'Enter a valid e-mail address that belongs to "gmail.com" e-mail service provider.');
    } else {
        setSuccess(email);
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

    // Check if all inputs are valid before submitting the form
    const validInputs = form.querySelectorAll('.input-control.success');
    if (validInputs.length === 2) {
        form.submit(); // Submit the form
    }
};
