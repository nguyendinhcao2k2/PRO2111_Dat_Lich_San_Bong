<!-- tab 6 start -->
var tabDoThue = new Vue({
    el: ".tabDoThue",
    data: {
        listDoThue: [],
        thongBao: "Bạn có chắc chắn thực hiện thao tác?",
        success: "Thao tác thành công!",
        error: "Thao tác thất bại!",
        imageFile: null,
        doThue: {
            tenDoThue: "",
            soLuong: 0,
            donGia: 0,
            image: null,
        },
    },
    methods: {
        confirmSaveDoThue() {
            Swal.fire({
                title: tabDoThue.thongBao,
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Xác nhận',
            }).then((result) => {
                if (result.isConfirmed) {
                    try {
                        $.ajax({
                            type: "POST",
                            url: "http://localhost:8081/api/v1/admin/do-thue/save",
                            contentType: "application/json",
                            data: JSON.stringify({
                                tenDoThue: tabDoThue.doThue.tenDoThue,
                                soLuong: tabDoThue.doThue.soLuong,
                                image: tabDoThue.doThue.image,
                                donGia: tabDoThue.doThue.donGia.replace(/\D/g, ''),
                            }),
                            success: function (response) {
                                console.log(response)
                            },
                        });
                    } catch (error) {
                        console.log(error);
                    }

                }
            })
        },
        handelImg(event) {
            this.selectedFile = this.$refs.fileInput.files[0];
            const formData = new FormData();
            formData.append("file", this.selectedFile);

            //image check
            const fileInput = this.$refs.fileInput;
            const file = event.target.files[0]; // Sử dụng event.target.files thay vì truy cập trực tiếp biến fileInput

            if (file && file.type.startsWith("image/")) {
                // Đây là một tệp ảnh, bạn có thể thực hiện xử lý tiếp theo ở đây
                const render = new FileReader();
                render.onload = function () {
                    const base64String = render.result.split(',')[1];
                    tabDoThue.doThue.image = base64String;
                }
                render.readAsDataURL(file);
            } else {
                // Đây không phải là một tệp ảnh, thông báo hoặc xử lý tương ứng
                alert("Đây không phải là ảnh xin chọn lại!");
                fileInput.value = ""; // Xóa giá trị tệp đã chọn (nếu muốn)
            }
            //image check
        }
        ,
        handelExcel(event) {
            // Thay đổi cách bạn lấy selectedFile
            this.selectedFile = event.target.files[0];
            const formData = new FormData();
            formData.append("file", this.selectedFile);

            // Kiểm tra loại tệp
            const fileInput = event.target; // Thay đổi cách bạn lấy fileInput
            const file = event.target.files[0];

            // Lấy phần mở rộng của tệp (ví dụ: ".xlsx", ".xls")
            const fileExtension = file.name.split('.').pop().toLowerCase();

            if (file && (fileExtension === 'xlsx' || fileExtension === 'xls')) {
                // Đây là một tệp Excel, bạn có thể thực hiện xử lý tiếp theo ở đây
                Swal.fire({
                    title: tabDoThue.thongBao,
                    icon: 'question',
                    showCancelButton: true,
                    confirmButtonText: 'Xác nhận',
                }).then((result) => {
                    if (result.isConfirmed) {
                        $.ajax({
                            type: "POST",
                            url: "http://localhost:8081/api/v1/admin/do-thue/import",
                            dataType: "json",
                            contentType: false,
                            processData: false,
                            data: formData,
                            success: function (reponse) {
                                console.log(reponse);
                                callApiGetDoThue();
                                alert("Import thành công!")
                            },
                            error: function (error) {
                                console.log(error);
                                alert("Vui lòng để đúng thứ tự các trường như file export excel!")
                            }
                        })
                    } else {
                        $(".importExcelDoThue").val(null);
                    }
                });

            } else {
                // Đây không phải là một tệp Excel, thông báo hoặc xử lý tương ứng
                alert("Đây không phải là một tệp Excel, xin chọn lại!");
                fileInput.value = ""; // Xóa giá trị tệp đã chọn (nếu muốn)
            }
        }
        ,
        uploadImage() {
            $.ajax({
                type: "POST",
                url: "http://localhost:8081/api/v1/admin/do-thue/upload-image",
                data: tabDoThue.imageFile,
                processData: false, // Important!
                contentType: false, // Important!
                success: function (response) {
                    console.log(response);
                },
                error: function (error) {
                    console.error("Error uploading file: ", error);
                    $(".importExcelDoThue").val(null);
                },
            });
        }
        ,
        handleSoLuong(event) {
            if (event.target.value === "" || event.target.value === null) {
                tabDoThue.doThue.soLuong = 0;
                return;
            }
            const regex = /^[0-9]+$/;
            if (!regex.test(event.target.value)) {
                tabDoThue.doThue.soLuong = event.target.value.replace(/\D/g, '');
                return;
            }
        }
        ,
        handleDonGia(event) {
            if (event.target.value === "" || event.target.value === null) {
                tabDoThue.doThue.donGia = 0;
                return;
            }
            const regex = /^[0-9]+$/;
            if (!regex.test(event.target.value)) {
                tabDoThue.doThue.donGia = event.target.value.replace(/\D/g, '');
                tabDoThue.doThue.donGia = parseInt(tabDoThue.doThue.donGia).toLocaleString("vi-VN");
                return;
            }
            tabDoThue.doThue.donGia = parseInt(event.target.value).toLocaleString("vi-VN");
        }
        ,
        imgSrc(value) {
            return `data:image/png;base64,${value}`;
        },
        // handleLaSo(event, value) {
        //     if (event.target.value === "" || event.target.value === null) {
        //         value= 0;
        //         return;
        //     }
        //     const regex = /^[0-9]+$/;
        //     if (!regex.test(event.target.value)) {
        //         value = event.target.value.replace(/\D/g, '');
        //         return;
        //     }
        // },
        // handleFormatGiaVN(event, value) {
        // }
    },

});
$(document).ready(function () {
    callApiGetDoThue();
    exportExcelDoThue();
});

function callApiGetDoThue() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/api/v1/admin/do-thue/find-all",
        dataType: "json",
        success: function (response) {
            tabDoThue.listDoThue = response.content.data;
            console.log(response.content.data);
        },
        error: function (error) {
            console.log(error);
        },
    });
}

function exportExcelDoThue() {
    $(".exportDoThue").click(() => {
        window.location.href = "http://localhost:8081/api/v1/admin/do-thue/export";
    });
}

<!-- tab 6 end -->