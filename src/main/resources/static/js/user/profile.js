$(document).ready(() => {
    // $("#togglePassword").click(() => {
    //     this.checkPass('passwordInput','togglePassword','showPass','hidePass')
    // })
});
document.addEventListener('DOMContentLoaded', function () {
    checkPass('passwordInput', 'togglePassword', 'showPass', 'hidePass')
    checkPass('inputPassNew', 'spanPassNew', 'showPassNew', 'hidePassNew')
    checkPass('inputPassConfirm', 'spanPassConfirm', 'showPassConfirm', 'hidePassConfirm')
});

function checkPass(passInput, spanPass, showPass, hidePass) {
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
};

var changePassword = new Vue({
    el: "#profileChangePass",
    data: {
        passwordCurrency: "",
        passwordNew: "",
        passwordConfirm: "",
    },
    methods: {
        confirmChangePass(event) {
            if (this.passwordNew.length < 6) {
                createAndShowToast("bg-warning", "Thông báo!", "Mật khẩu phải lớn hơn 6 kí tự!");
                return;
            }
            if (this.passwordNew != this.passwordConfirm) {
                createAndShowToast("bg-warning", "Thông báo!", "Mật khẩu xác nhận  không đúng!");
                return;
            }
            Swal.fire({
                title: "Bạn có chắc chắn thực hiện hành động này!",
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    this.calApiCheckPass();
                }
            });

        },
        calApiCheckPass() {
            $.ajax({
                type: "POST",
                url: "http://localhost:8081/api/v1/profile/check-password",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify({
                    passCurrency: this.passwordCurrency,
                    passNew: this.passwordNew
                }),
                success: function (response) {
                    if (response.statusCode === 'OK') {
                        createAndShowToast("bg-success", "Thông báo!", "Đổi mật khẩu thành công!");
                        setTimeout(function (){
                            window.location.href="http://localhost:8081/authentication/home-login"
                        }, 2000);
                    } else {
                        createAndShowToast("bg-warning", "Thông báo!", "Mật khẩu cũ của bạn không đúng!");
                    }
                },
                error: function (error) {
                    console.log(error);
                }
            })
        }
    }
});