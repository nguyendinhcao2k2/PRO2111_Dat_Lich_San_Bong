$(document).ready(() => {
    this.callApiThongTin();
});

function callApiThongTin() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/api/v1/profile/thong-tin",
        success: function (response) {
            if (response.statusCode === 'OK') {
                thongTinCaNhan.profile = response.content;
            } else {
                window.location.href = "http://localhost:8081/authentication/home-login";
            }
        }, error: function (error) {
            console.log(error);
            window.location.href = "http://localhost:8081/authentication/home-login";
        }
    })
}

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

var thongTinCaNhan = new Vue({
    el: "#thongTinCaNhan",
    data: {
        passwordCurrency: "",
        passwordNew: "",
        passwordConfirm: "",
        profile: {
            id: "",
            email: "",
            image: "",
            taiKhoan: "",
            matKhau: "",
            soDienThoai: "",
            displayName: "",
            idChucVu: "",
            idHangKhachHang: "",
            trangThai: "",
        }
    },
    methods: {
        confirmThayDoiThongTin(event) {
            var regexPhone = /(84|0[3|5|7|8|9])+([0-9]{8})\b/;
            var regexEmail = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
            if (!regexPhone.test(thongTinCaNhan.profile.soDienThoai)) {
                createAndShowToast("bg-warning", "Thông báo!", "Số điện thoại không đúng định dạng!");
                return;
            }
            if (!regexEmail.test(thongTinCaNhan.profile.email)) {
                createAndShowToast("bg-warning", "Thông báo!", "Email không đúng định dạng!");
                return;
            }
            Swal.fire({
                title: "Bạn có chắc chắn thực hiện hành động này!",
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    this.updateProfile();
                }
            });
        },
        updateProfile() {
            $.ajax({
                type: "PUT",
                dataType: "json",
                contentType: "application/json",
                url: "http://localhost:8081/api/v1/profile/update",
                data: JSON.stringify(thongTinCaNhan.profile),
                success: function (result) {
                    if (result.statusCode === 'OK') {
                        createAndShowToast("bg-success", "Thông báo!", "Cập nhật thông tin thành công!");
                        callApiThongTin();
                        $(".anhMoi").val('');
                    } else {
                        createAndShowToast("bg-danger", "Thông báo!", "Lỗi! Thử lại sau");
                    }
                },
                error: function (error) {
                    console.log(error);
                    createAndShowToast("bg-danger", "Thông báo!", "Lỗi! Thử lại sau");
                }
            });
        },
        handleImageProfile(event) {
            const file = event.target.files[0]; // Sử dụng event.target.files thay vì truy cập trực tiếp biến fileInput

            if (file && file.type.startsWith("image/")) {
                // Đây là một tệp ảnh, bạn có thể thực hiện xử lý tiếp theo ở đây
                const render = new FileReader();
                render.onload = function () {
                    const base64String = render.result.split(',')[1];
                    thongTinCaNhan.profile.image = base64String;
                }
                render.readAsDataURL(file);
            } else {
                // Đây không phải là một tệp ảnh, thông báo hoặc xử lý tương ứng
                createAndShowToast("bg-warning", "Thông báo!", "Đây không phải là ảnh xin chọn lại!");
                event.target.value = ""; // Xóa giá trị tệp đã chọn (nếu muốn)
            }
        },
        srcImage(value) {
            return `data:image/png;base64,${value}`;
        },
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
                        setTimeout(function () {
                            window.location.href = "http://localhost:8081/authentication/home-login"
                        }, 2000);
                    } else {
                        createAndShowToast("bg-warning", "Thông báo!", "Mật khẩu cũ của bạn không đúng!");
                    }
                },
                error: function (error) {
                    console.log(error);
                }
            })
        },
    }
})

