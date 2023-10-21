$(document).ready(function () {
    $(".deletebtn").click(function () {
        $.ajax({
            url: "http://localhost:8081/admin/delete/" + $(this).data("id"),
            dataType: "json",
            method: "DELETE",
            success: function () {
                $("h4").text("Xóa thành công!");
                location.reload();
            },
            error: function (error) {
                console.log(error)
            }
        })
    })

    $(".detailbtn").click(function () {
        var selectNSX = $("#selectNSX").empty();
        var selectMS = $("#selectMS").empty();

        $.ajax({
            url: "http://localhost:8081/admin/detail/" + $(this).data("id"),
            dataType: "json",
            method: "GET",
            success: function (data) {
                $("#idCTSP").val(data.chiTietSP.id)
                $("#tenSanPham").val(data.chiTietSP.tenSanPham)
                $("#namBH").val(data.chiTietSP.namBH)
                $("#soLuong").val(data.chiTietSP.soLuongTon)
                $("#giaNhap").val(data.chiTietSP.giaNhap)
                $("#giaBan").val(data.chiTietSP.giaBan)
                $("#voucher").val(data.chiTietSP.voucher)
                $("#moTa").val(data.chiTietSP.moTa)
                $("#size").val(data.chiTietSP.size)
                data.nhaSanXuatList.forEach((nsx) => {
                    var option = $("<option >").val(nsx.id).text(nsx.ten);
                    if (data.chiTietSP.nhaSanXuat.id === nsx.id) {
                        option.attr("selected", "selected");
                    }
                    selectNSX.append(option);

                })
                data.mauSacList.forEach((ms) => {
                    var option = $("<option>").val(ms.id).text(ms.ten);
                    if (data.chiTietSP.mauSac.id === ms.id) {
                        option.attr("selected", "selected");
                    }
                    selectMS.append(option);
                })

            },
            error: function (error) {
                console.log(error);
            }
        })
    })
    $(".formThongBao").hide(6000);
    $("#themess").click(function () {
        $("body").toggleClass("themBody");
    })
    $(".select").change(function () {
        var valueSelect = $("#select").val();
        window.location.href = 'http://localhost:8081/admin/manager?size=' + valueSelect;
    })
    $(".btnSaveMs").click(function () {
        $.ajax({
            url: "http://localhost:8081/admin/save-ms",
            dataType: "json",
            contentType: "application/json",
            method: "POST",
            data: JSON.stringify({
                ma: (Math.random() * 899999999) + 10000000,
                ten: $("#tenMS").val(),
            }),
            success: function () {
                alert("Thành công!");
                location.reload();
            },
            error: function (error) {
                console.log(error);
            }

        })
    })
    $(".btnSaveNsx").click(function () {
        $.ajax({
            url: "http://localhost:8081/admin/save-nsx",
            dataType: "json",
            contentType: "application/json",
            method: "POST",
            data: JSON.stringify({
                ma: (Math.random() * 899999999) + 10000000,
                ten: $("#tenNSX").val(),
            }),
            success: function () {
                alert(" Thành công!");
                location.reload();
            },
            error: function (error) {
                console.log(error);
            }

        })
    })
})

