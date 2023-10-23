const viewUrlApi = "http://localhost:8081/authentication/";

function signUp() {
    let accountError = $("#account_error");
    let displayNameError = $("#display_name_error");
    let phoneNumberError = $("#phone_number_error");
    let passwordError = $("#password_error");
    let rePasswordError = $("#re_password_error");

    let account = $("#account").val();
    let display_name = $("#display_name").val();
    let phone_number = $("#phone_number").val();
    let password = $("#password").val();
    let re_password = $("#re_password").val();
    let check = true;

    if (account === "" || account.trim() === "") {
        accountError.text("Account không được để trống.");
        check = false;
    } else if (account.length < 6) {
        accountError.text("Account phải có hơn 6 ký tự.");
        check = false;
    }

    if (display_name === "" || display_name.trim() === "") {
        displayNameError.text("DisplayName không được để trống hoặc chỉ chứa khoảng trắng.");
        check = false;
    } else if (display_name.length < 6) {
        displayNameError.text("DisplayName phải có hơn 6 ký tự");
        check = false;
    }
    let phoneNumberPattern = /^0\d{9}$/;
    if (phone_number === "" || phone_number.trim() === "") {
        phoneNumberError.text("PhoneNumber không được để trống hoặc chỉ chứa khoảng trắng.");
        check = false;
    } else if (!phoneNumberPattern.test(phone_number)) {
        phoneNumberError.text("Số điện thoại phải đúng định dạng.");
        check = false;
    }

    if (password === "") {
        passwordError.text("Password không được để trống.");
        check = false;
    } else if (password.length < 4) {
        passwordError.text("Password phải có hơn 4 ký tự");
        check = false;
    }
    if (re_password === "") {
        rePasswordError.text("Repassword không được để trống.");
        check = false;
    } else if (re_password.length < 4) {
        rePasswordError.text("Repassword phải có hơn 4 ký tự");
        check = false;
    }

    if (password !== re_password) {
        passwordError.text("Password và RePassword phải giống nhau.");
        rePasswordError.text("Password và RePassword phải giống nhau.");
        check = false;
    }

    if (check){
        let data = {
            account: account,
            displayName: display_name,
            phoneNumber: phone_number,
            password: password,
            rePassword: re_password,
        };


        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: viewUrlApi + "/check-out-cart",
            data: JSON.stringify(data),
            success: function (responseData) {
                alert(responseData)
                window.location.href = "/authentication/home-login"
            },
            error: function (e) {
                alert(e);
            }
        });
    }

}