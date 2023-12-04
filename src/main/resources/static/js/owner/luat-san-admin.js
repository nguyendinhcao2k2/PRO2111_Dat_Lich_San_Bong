<!-- tab3 start  -->
var tab3 = new Vue({
    el: ".tabLuatSan",
    data: {
        idLuatSan: "",
        thongBao: "Bạn có chắc chắn thực hiện hành động?",
        success: "Thao tác thành công!",
        error: "Thao tác thất bại!",
    },
});
$(document).ready(function () {
    callApiLuatSan();
    createLuatSan();
    deleteLuatSan();
    detailLuatSan();
    updateLuatSan();
});

function callApiLuatSan() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/api/v1/admin/luat-san/find-all",
        data: "json",
        success: function (response) {
            if (response.content.length == 0) {
                $(".thongBaoLS").append(`
                                    <div class="alert alert-danger ">
                        <h1 class="" style="font-size: 20px">Không có dữ liệu</h1>
                    </div>
                `);
                $(".btnCreateLuatSan").prop("hidden", false);
                $(".btnDeleteLuatSan").prop("hidden", true);
                $(".btnDetailLuatSan").prop("hidden", true);
                $(".btnUpdateLuatSan").prop("hidden", true);
            } else {
                $(".thongBaoLS").remove();
                $(".btnCreateLuatSan").prop("hidden", true);
                $(".btnDeleteLuatSan").prop("hidden", false);
                $(".btnDetailLuatSan").prop("hidden", false);
                $(".btnUpdateLuatSan").prop("hidden", true);
                $(".thongTinLuatSan").html(response.content[0].thongTin);
                tab3.idLuatSan = response.content[0].id;
            }
        },
        error: function (error) {
            console.log(error);
        },
    });
}

function createLuatSan() {
    $(".btnCreateLuatSan").click(() => {
        if (
            quill.getText() == "" ||
            quill.getText() == null ||
            quill.getText().length == 1
        ) {
            createAndShowToast("bg-warning","Thông báo!","Vui lòng không để trống!");
            return;
        }
        Swal.fire({
            title: "Bạn có chắc chắn thực hiện thao tác này?",
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Xác nhận',
        }).then((result) => {
            if (result.isConfirmed) {
                apiCreateLuatSan();
            }
        });
    });
}

function apiCreateLuatSan() {
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/api/v1/admin/luat-san/create",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            thongTin: quill.root.innerHTML,
        }),
        success: function (response) {
            createAndShowToast("bg-success","Thông báo!","Thao tác thành công!");
            quill.setText("");
            callApiLuatSan();
        },
        error: function (error) {
            createAndShowToast("bg-success","Thông báo!","Thao tác thất bại!");
            console.log(error);
        },
    });
}

function deleteLuatSan() {
    $(".btnDeleteLuatSan").click(() => {
        Swal.fire({
            title: "Bạn có chắc chắn thực hiện thao tác này?",
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Xác nhận',
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    type: "DELETE",
                    url:
                        "http://localhost:8081/api/v1/admin/luat-san/delete/" +
                        tab3.idLuatSan,
                    success: function (response) {
                        if (response.statusCode == "OK") {
                            createAndShowToast("bg-success","Thông báo!","Thao tác thành công!");
                            callApiLuatSan();
                            $(".thongTinLuatSan").text("");
                            $(".thongBaoLS").append(`
                                    <div class="alert alert-danger ">
                        <h1 class="" style="font-size: 20px">Không có dữ liệu</h1>
                    </div>
                `);
                        }
                    },
                    error: function (error) {
                        createAndShowToast("bg-success","Thông báo!","Thao tác thất bại!");
                        console.log(error);
                    },
                });
            }
        });
    });
}

function detailLuatSan() {
    $(".btnDetailLuatSan").click(() => {
        $(".btnUpdateLuatSan").prop("hidden", false);
        callApiFindById(tab3.idLuatSan);
    });
}

function callApiFindById(id) {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: "http://localhost:8081/api/v1/admin/luat-san/find-by/" + id,
        success: function (response) {
            quill.clipboard.dangerouslyPasteHTML(response.content.thongTin);
        },
        error: function (error) {
            console.log(error);
        },
    });
}

function updateLuatSan() {
    $(".btnUpdateLuatSan").click(() => {
        Swal.fire({
            title: "Bạn có chắc chắn thực hiện thao tác này?",
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Xác nhận',
        }).then((result) => {
            if (result.isConfirmed) {
                apiUpdateLoaiSan();
            }
        });
    });
}

function apiUpdateLoaiSan() {
    $.ajax({
        type: "PUT",
        url: "http://localhost:8081/api/v1/admin/luat-san/update",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            id: tab3.idLuatSan,
            thongTin: quill.root.innerHTML,
        }),
        success: function (response) {
            createAndShowToast("bg-success","Thông báo!","Thao tác thành công!");
            quill.setText("");
            callApiLuatSan();
        },
        error: function (error) {
            createAndShowToast("bg-success","Thông báo!","Thao tác thất bại!");
            console.log(error);
        },
    });
}

var quill = new Quill("#editor-container", {
    modules: {
        toolbar: [
            [{header: [1, 2, false]}],
            ["bold", "italic", "underline", "strike"],
            ["code-block"],
            [{color: []}],
            [{size: ["small", false, "large", "huge"]}],
            ["font"],
            ["align", "direction"], // Thêm các tùy chọn giãn dòng và hướng văn bản
            [{list: "ordered"}, {list: "bullet"}],
            ["link", "image", "video"],
            ["clean"],
        ],
    },
    placeholder: "Nội dung luật sân......",
    theme: "snow",
    height: 300,
});


<!-- tab3 end  -->