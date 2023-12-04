$(document).ready(() => {
    // $("#togglePassword").click(() => {
    //     this.checkPass('passwordInput','togglePassword','showPass','hidePass')
    // })
});
document.addEventListener('DOMContentLoaded', function () {
    checkPass('passwordInput','togglePassword','showPass','hidePass')
    checkPass('inputPassNew','spanPassNew','showPassNew','hidePassNew')
    checkPass('inputPassConfirm','spanPassConfirm','showPassConfirm','hidePassConfirm')
});
function checkPass(passInput,spanPass,showPass,hidePass) {
    const passwordInput = document.getElementById(passInput);
    const togglePassword = document.getElementById(spanPass);
    const showPassIcon = document.getElementById(showPass);
    const hidePassIcon = document.getElementById(hidePass);

    togglePassword.addEventListener('click', function () {
        const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
        passwordInput.setAttribute('type', type);

        showPassIcon.style.display = type === 'password' ? 'none' : 'inline';
        hidePassIcon.style.display = type === 'password' ? 'inline' : 'none';
    });
}