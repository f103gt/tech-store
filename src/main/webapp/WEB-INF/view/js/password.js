const form = document.getElementById('form');
const password = document.getElementById('password');
const passwordConfirmation = document.getElementById('password-confirmation');

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

const validateInputs = () => {
    const passwordValue = password.value.trim();
    const passwordConfirmationValue = passwordConfirmation.value.trim();


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

    const validInputs = form.querySelectorAll('.input-control.success');
    if (validInputs.length === 2) {
        form.removeEventListener('submit', validateInputs);
        form.submit();
    }
};
