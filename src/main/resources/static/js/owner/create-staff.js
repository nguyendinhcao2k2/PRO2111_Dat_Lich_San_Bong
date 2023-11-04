$(document).ready(function (){
    // remove();
    getAll();
    save();
})

function save(){
    $(".xacNhan").click((event) =>{
        event.preventDefault();
        // if($(".taiKhoan").val() == null || $(".email").val() == null){
        //     alert("Không được để trống")
        //     return;
        // }
        if($(".matKhau").val() != $(".nhapLaiMatKhau").val()){
            alert("Mật khẩu xác nhận không đúng")
            return;
        }

        $.ajax({
            type: "POST",
            url: "http://localhost:8081/api/v1/admin/account/create-staff",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                email: $(".email").val(),
                taiKhoan: $(".taiKhoan").val(),
                matKhau: $(".matKhau").val(),
                displayName: $(".email").val().split("@")[0]
            }),
            success: function (response) {
                alert("Thêm thành công");
            },
            error: function (e) {
                console.log(e);
            }
        })
    })
}

function getAll() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/api/v1/admin/account/find-all",
        success: function (response) {
            var table = $(".bang");
            response.content.data.forEach(function (item) {
                var tr = $("<tr>");  // Tạo một hàng mới cho mỗi dòng dữ liệu

                // Tạo cột email với sự ngăn cách bên phải
                var email = $(`<td class="column-with-padding">${item.email}</td>`);
                // Tạo cột tài khoản với sự ngăn cách bên phải
                var taiKhoan = $(`<td class="column-with-padding">${item.taiKhoan}</td>`);
                // Tạo cột tên hiển thị với sự ngăn cách bên phải
                var tenHienThi = $(`<td class="column-with-padding">${item.displayName}</td>`);
                // Tạo cột số điện thoại
                var soDienThoai = $(`<td>${item.soDienThoai}</td>`);
                var action = $(`<td>
                        <button
                                style="background-color: white; border: 1px solid red"
                                type="submit"
                                class=" btn btn-primary deletebtn"
                                >
                            <i
                                    class="fa fa-trash"
                                    style="color: red"
                                    aria-hidden="true"
                            ></i>
                        </button>
                    </td>`)
                tr.css({
                    'background-color': '#f8f9fa',  // Màu nền của hàng
                    'color': '#333',  // Màu chữ
                    'border-bottom': '1px solid #ddd',  // Đường viền dưới của hàng
                });
                email.css('font-weight', 'bold');  // Tùy chỉnh font-weight cho cột email

                tr.append(email);
                tr.append(taiKhoan);
                tr.append(tenHienThi);
                tr.append(soDienThoai);
                tr.append(action);
                table.append(tr);
            });
        },
        error: function (e) {
            console.log(e);
        }
    });
}

// function remove() {
//         $(".deletebtn").click(function (event) {
//             event.preventDefault();
//             $.ajax({
//                 // type: "DELETE",
//                 method: "DELETE",
//                 url: "http://localhost:8081/api/v1/admin/account/delete/" + $(this).data("id"),
//                 dataType: "json",
//                 success: function () {
//                     alert("Xóa Oke")
//                 },
//                 error: function (error) {
//                     console.log(error)
//                 }
//             })
//         })
// }

$(document).ready(function () {
    // Thêm sự kiện click cho nút xóa
    $(".deletebtn").click(function () {
        var id = $(this).data("id"); // Lấy ID từ thuộc tính data-id
        console.log(id)

        if (confirm("Bạn có chắc chắn muốn xóa?")) {
            // Nếu người dùng đồng ý xóa, thực hiện AJAX request
            $.ajax({
                type: "DELETE",
                url: "http://localhost:8081/api/v1/admin/account/delete/" + id,
                success: function () {
                    alert("Xóa thành công.");
                    // Sau khi xóa thành công, bạn có thể làm một cái gì đó, ví dụ, cập nhật bảng hoặc làm một hành động khác.
                },
                error: function (error) {
                    console.log(error);
                    alert("Xóa thất bại.");
                }
            });
        }
    });
});




