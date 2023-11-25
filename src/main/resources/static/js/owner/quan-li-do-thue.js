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
            if (confirm(tabDoThue.thongBao)) {
                $.ajax({
                    type: "POST",
                    url: "http://localhost:8081/api/v1/admin/do-thue/save",
                    data: JSON.stringify({
                        tenDoThue: tabDoThue.doThue.tenDoThue,
                        soLuong: tabDoThue.doThue.soLuong,
                        donGia: tabDoThue.doThue.donGia,
                        image: tabDoThue.doThue.image,
                    }),
                    dataType: "json",
                    contentType: "application/json",
                    success: function (response) {
                        tabDoThue.uploadImage();
                        alert(tabDoThue.success);
                        callApiGetDoThue();
                    },
                });
            }
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
                tabDoThue.imageFile = formData;
                tabDoThue.doThue.image = this.selectedFile.name;
            } else {
                // Đây không phải là một tệp ảnh, thông báo hoặc xử lý tương ứng
                alert("Đây không phải là ảnh xin chọn lại!");
                fileInput.value = ""; // Xóa giá trị tệp đã chọn (nếu muốn)
            }
            //image check
        },
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
                },
            });
        },
    },
});
$(document).ready(function () {
    callApiGetDoThue();
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

<!-- tab 6 end -->