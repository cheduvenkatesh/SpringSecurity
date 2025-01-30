document.addEventListener("DOMContentLoaded", function() {

    const loginForm = document.getElementById("login");
    const registerForm = document.getElementById("register");
    const userInfoSection = document.getElementById("user-info");
    const userDetails = document.getElementById("user-details");
    const loginStatus = document.getElementById("login-status");
    const registerStatus = document.getElementById("register-status");
    const logoutButton = document.getElementById("logout");

    // Login Form Submission
    loginForm.addEventListener("submit", function(event) {
        event.preventDefault();
        
        const username = document.getElementById("login-username").value;
        const password = document.getElementById("login-password").value;

        fetch("/user/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username, password })
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error("Login failed");
            }
        })
        .then(data => {
            loginStatus.textContent = "Login successful!";
            loginForm.style.display = "none";
            userInfoSection.style.display = "block";
            userDetails.textContent = `Logged in as: ${username}`;
        })
        .catch(error => {
            loginStatus.textContent = error.message;
        });
    });

    // Register Form Submission
    registerForm.addEventListener("submit", function(event) {
        event.preventDefault();

        const username = document.getElementById("reg-username").value;
        const password = document.getElementById("reg-password").value;

        fetch("/user/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username, password })
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error("Registration failed");
            }
        })
        .then(data => {
            registerStatus.textContent = "Registration successful!";
        })
        .catch(error => {
            registerStatus.textContent = error.message;
        });
    });

    // Logout Functionality
    logoutButton.addEventListener("click", function() {
        userInfoSection.style.display = "none";
        loginForm.style.display = "block";
        loginStatus.textContent = "";
        registerStatus.textContent = "";
        userDetails.textContent = "";
    });

});
