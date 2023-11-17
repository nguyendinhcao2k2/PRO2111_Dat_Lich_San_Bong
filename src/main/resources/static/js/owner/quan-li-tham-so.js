var tabParam = new Vue({
    el: ".tabParam",
    data: {
        listCodeParam: [],
        listParam: [],
        thongBao: "Bạn có chắc chắn thực hiện thao tác?",
        success: "Thao tác thành công!",
        error: "Thao tác thất bại!",
        param: {
            code: "",
            value: "",
            type: "TYPE01",
            chucNang: "",
            note: "OKE",
            trangThai: 0,
        },
        paramUpdate: {
            code: "",
            chucNang: "",
            value: 0,
            id: "",
            trangThai: 0,
            note: "",
            type: "",
            name: "",
        },
    },
    methods: {
        checkValue(event) {
            if (this.param.code == "" || this.param.code == null) {
                this.param.value = "";
                return;
            }
            // check la so
            if (this.param.code != "" || this.param.code != null) {
                this.checkLaSo(event, this.param.value);
            }
            if (this.param.code == "PHAN_TRAM_GIA_TIEN_COC") {
                this.checkThamSo(event);
            }
        },
        checkThamSo(event) {
            if (
                parseInt(event.target.value) < 0 ||
                parseInt(event.target.value) > 100
            ) {
                alert("% tiền cọc chỉ nằm trong khoảng 0% - 100%");
                this.param.value = event.target.value.slice(0, -1);
                return;
            }
        },
        checkLaSo(event, valueInput) {
            const regex = /^[0-9]+$/;
            if (!regex.test(event.target.value)) {
                return (valueInput = event.target.value.replace(/\D/g, ""));
            }
        },
        confirmCreate() {
            if (confirm(tabParam.thongBao)) {
                this.createSysParam();
            }
        },
        createSysParam() {
            $.ajax({
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                url: "http://localhost:8081/api/v1/admin/sys-param/create",
                data: JSON.stringify({
                    code: tabParam.param.code,
                    value: tabParam.param.value,
                    type: tabParam.param.type,
                    chucNang: tabParam.param.chucNang,
                    note: tabParam.param.note,
                    trangThai: parseInt(tabParam.param.trangThai),
                }),
                success: function (response) {
                    if (response.statusCode == "OK") {
                        alert(tabParam.success);
                        callApiListParam();
                        return;
                    }
                    if (response.statusCode == "ACCEPTED") {
                        alert("Bạn chỉ có thể tạo 1 lần với loại tham số này!");
                        return;
                    }
                },
                error: function (error) {
                    alert(tabParam.error);
                    console.log(error);
                },
            });
        },
        detailParam(id) {
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "http://localhost:8081/api/v1/admin/sys-param/by-id/" + id,
                success: function (response) {
                    tabParam.paramUpdate.id = response.content.id;
                    tabParam.paramUpdate.chucNang = response.content.chucNang;
                    tabParam.paramUpdate.code = response.content.code;
                    tabParam.paramUpdate.type = response.content.type;
                    tabParam.paramUpdate.value = response.content.value;
                    tabParam.paramUpdate.note = response.content.note;
                    tabParam.paramUpdate.name = response.content.name;
                    tabParam.paramUpdate.trangThai = parseInt(
                        response.content.trangThai
                    );
                    if (
                        tabParam.paramUpdate.code == "PHAN_TRAM_GIA_TIEN_COC" ||
                        tabParam.paramUpdate.code == "SO_COMBO_DAT"
                    ) {
                        $(".valueParamUpdate").attr("type", "number");
                    }
                    console.log(response.content);
                },
                error: function (error) {
                    console.log(error);
                },
            });
        },
        checkValidaValueUpdate(event) {
            this.checkLaSo(event, this.paramUpdate.value);
            if (
                parseInt(event.target.value) < 0 ||
                parseInt(event.target.value) > 100
            ) {
                alert("% tiền cọc chỉ nằm trong khoảng 0% - 100%");
                this.paramUpdate.value = event.target.value.slice(0, -1);
                return;
            }
        },
        confirmUpdate() {
            if (confirm(tabParam.thongBao)) {
                this.updateParam();
            }
        },
        updateParam() {
            $.ajax({
                type: "PUT",
                dataType: "json",
                contentType: "application/json",
                url: "http://localhost:8081/api/v1/admin/sys-param/update",
                data: JSON.stringify({
                    id: tabParam.paramUpdate.id,
                    value: tabParam.paramUpdate.value,
                    note: tabParam.paramUpdate.note,
                    chucNang: tabParam.paramUpdate.chucNang,
                    code: tabParam.paramUpdate.code,
                    name: tabParam.paramUpdate.name,
                    type: tabParam.paramUpdate.type,
                    trangThai: parseInt(tabParam.paramUpdate.trangThai),
                }),
                success: function (response) {
                    if (response.statusCode == "OK") {
                        alert(tabParam.success);
                        $("#detailParam").modal("toggle");
                        $(".modal-backdrop").remove();
                        callApiListParam();
                        return;
                    }
                },
                error: function (error) {
                    alert(tabParam.error);
                    console.log(error);
                },
            });
        },
        confirmDelete(id) {
            if (confirm(tabParam.thongBao)) {
                $.ajax({
                    type: "DELETE",
                    url:
                        "http://localhost:8081/api/v1/admin/sys-param/delete/" + id,
                    success: function (response) {
                        if (response.statusCode == "OK") {
                            alert(tabParam.success);
                            callApiListParam();
                        }
                    },
                    error: function (error) {
                        alert(tabParam.error);
                        console.log(error);
                    },
                });
            }
        },
    },
});
$(document).ready(() => {
    callApiListCodePaRam();
    checkLoaiThamSo();
    callApiListParam();
});

function callApiListParam() {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: "http://localhost:8081/api/v1/admin/sys-param/find-all",
        success: function (response) {
            tabParam.listParam = response.content;
            console.log(response);
        },
        error: function (error) {
            console.log(error);
        },
    });
}

function callApiListCodePaRam() {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: "http://localhost:8081/api/v1/admin/sys-param/list-code",
        success: function (response) {
            tabParam.listCodeParam = response.content;
        },
        error: function (error) {
            console.log(error);
        },
    });
}

function checkLoaiThamSo() {
    $(".loaiThamSo").change((event) => {
        if (
            event.target.value == "PHAN_TRAM_GIA_TIEN_COC" ||
            event.target.value == "SO_COMBO_DAT"
        ) {
            $(".valueParam").attr("type", "number");
            $(".valueParam").attr("min", 0);
            $(".valueParam").attr("max", 100);
            return;
        }
        $(".valueParam").attr("type", "text");
        $(".valueParam").removeAttr("min");
        $(".valueParam").removeAttr("max");
    });
}
